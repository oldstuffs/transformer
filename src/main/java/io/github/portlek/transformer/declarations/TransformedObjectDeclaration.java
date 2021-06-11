/*
 * MIT License
 *
 * Copyright (c) 2021 Hasan Demirtaş
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.github.portlek.transformer.declarations;

import io.github.portlek.reflection.clazz.ClassOf;
import io.github.portlek.transformer.TransformedObject;
import io.github.portlek.transformer.annotations.Comment;
import io.github.portlek.transformer.annotations.Exclude;
import io.github.portlek.transformer.annotations.Names;
import io.github.portlek.transformer.annotations.Version;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that represents transformed class declarations.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransformedObjectDeclaration {

  /**
   * the caches.
   */
  private static final Map<Class<?>, TransformedObjectDeclaration> CACHES =
    new ConcurrentHashMap<>();

  /**
   * the fields.
   */
  @NotNull
  private final Map<String, FieldDeclaration> fields;

  /**
   * the header.
   */
  @Nullable
  private final Comment header;

  /**
   * the object class.
   */
  @NotNull
  private final Class<?> objectClass;

  /**
   * the transformer version.
   */
  @Nullable
  private final Version version;

  /**
   * creates a new transformed object declaration.
   *
   * @param cls the cls to create.
   * @param object the object to create.
   *
   * @return a newly created transformed object declaration.
   */
  @NotNull
  public static TransformedObjectDeclaration of(@NotNull final Class<?> cls,
                                                @Nullable final TransformedObject object) {
    return TransformedObjectDeclaration.CACHES.computeIfAbsent(cls, clazz -> {
      final var classOf = new ClassOf<>(clazz);
      return new TransformedObjectDeclaration(
        classOf.getDeclaredFields().stream()
          .filter(field -> !field.getName().startsWith("this$"))
          .filter(field -> !field.hasAnnotation(Exclude.class))
          .map(field -> FieldDeclaration.of(Names.Calculated.calculateNames(clazz), object, clazz, field))
          .collect(Collectors.toMap(FieldDeclaration::getPath, Function.identity(), (f1, f2) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", f1));
          }, LinkedHashMap::new)),
        classOf.getAnnotation(Comment.class).orElse(null),
        clazz,
        classOf.getAnnotation(Version.class).orElse(null));
    });
  }

  /**
   * creates a new transformed object declaration.
   *
   * @param object the object to create.
   *
   * @return a newly created transformed object declaration.
   */
  @NotNull
  public static TransformedObjectDeclaration of(@NotNull final TransformedObject object) {
    return TransformedObjectDeclaration.of(object.getClass(), object);
  }

  /**
   * creates a new transformed object declaration.
   *
   * @param cls the cls to create.
   *
   * @return a newly created transformed object declaration.
   */
  @NotNull
  public static TransformedObjectDeclaration of(@NotNull final Class<?> cls) {
    return TransformedObjectDeclaration.of(cls, null);
  }

  /**
   * obtains the all fields.
   *
   * @return all fields.
   */
  @NotNull
  public Map<String, FieldDeclaration> getAllFields() {
    return Collections.unmodifiableMap(this.fields);
  }

  /**
   * obtains the migrated fields.
   *
   * @return migrated fields.
   */
  @NotNull
  public Map<String, FieldDeclaration> getMigratedFields() {
    final var map = new HashMap<String, FieldDeclaration>();
    this.fields.forEach((s, fieldDeclaration) -> {
      if (fieldDeclaration.getMigration() != null &&
        fieldDeclaration.getMigration().value() > 0) {
        map.put(s, fieldDeclaration);
      }
    });
    return Collections.unmodifiableMap(map);
  }

  /**
   * obtains the non migrated fields.
   *
   * @return non migrated fields.
   */
  @NotNull
  public Map<String, FieldDeclaration> getNonMigratedFields() {
    final var map = new HashMap<String, FieldDeclaration>();
    this.fields.forEach((s, fieldDeclaration) -> {
      if (fieldDeclaration.getMigration() == null ||
        fieldDeclaration.getMigration().value() <= 0) {
        map.put(s, fieldDeclaration);
      }
    });
    return Collections.unmodifiableMap(map);
  }
}
