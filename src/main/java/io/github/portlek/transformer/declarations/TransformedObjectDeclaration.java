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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that represents transformed class declarations.
 */
@ToString
@EqualsAndHashCode
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
  @Getter
  private final Comment header;

  /**
   * the object class.
   */
  @NotNull
  @Getter
  private final Class<?> objectClass;

  /**
   * the transformer version.
   */
  @Nullable
  @Getter
  @Setter
  private Version version;

  /**
   * ctor.
   *
   * @param fields the fields.
   * @param header the header.
   * @param objectClass the object class.
   * @param version the version.
   */
  private TransformedObjectDeclaration(@NotNull final Map<String, FieldDeclaration> fields,
                                       @Nullable final Comment header, @NotNull final Class<?> objectClass,
                                       @Nullable final Version version) {
    this.fields = fields;
    this.header = header;
    this.objectClass = objectClass;
    this.version = version;
  }

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
            if (f1.getMigration() != null) {
              return f2;
            }
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
   * obtains the fields.
   *
   * @return fields.
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
    return this.fields.entrySet().stream()
      .filter(entry -> entry.getValue().isMigrated(this))
      .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  /**
   * obtains the non migrated fields.
   *
   * @return non migrated fields.
   */
  @NotNull
  public Map<String, FieldDeclaration> getNonMigratedFields() {
    return this.fields.entrySet().stream()
      .filter(entry -> entry.getValue().isNotMigrated(this))
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  /**
   * obtains the version as integer.
   *
   * @return version as integer.
   */
  public int getVersionInteger() {
    return this.version == null ? 1 : this.version.value();
  }
}
