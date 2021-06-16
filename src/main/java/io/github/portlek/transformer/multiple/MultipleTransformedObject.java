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
package io.github.portlek.transformer.multiple;

import io.github.portlek.transformer.TransformResolver;
import io.github.portlek.transformer.Transformed;
import io.github.portlek.transformer.declarations.TransformedObjectDeclaration;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * an abstract class that represents multiple transformed objects.
 */
public abstract class MultipleTransformedObject implements Transformed {

  /**
   * the declaration.
   */
  @Nullable
  @Getter
  private TransformedObjectDeclaration declaration;

  /**
   * the paths.
   */
  @NotNull
  private Collection<Path> paths = Set.of();

  /**
   * the resolver.
   */
  @Nullable
  @Getter
  private TransformResolver resolver;

  /**
   * obtains all keys.
   *
   * @return all keys.
   */
  @NotNull
  @Override
  public final List<String> getAllKeys() {
    return new ArrayList<>(Objects.requireNonNull(this.declaration, "declaration").getNonMigratedFields().keySet());
  }

  /**
   * sets the declaration.
   *
   * @param declaration the declaration to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final MultipleTransformedObject withDeclaration(@NotNull final TransformedObjectDeclaration declaration) {
    this.declaration = declaration;
    return this;
  }

  /**
   * sets the {@link #paths}.
   *
   * @param files the files to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final MultipleTransformedObject withFile(@NotNull final File... files) {
    return this.withFile(Arrays.stream(files).map(File::toPath).toArray(Path[]::new));
  }

  /**
   * sets the {@link #paths}.
   *
   * @param paths the paths to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final MultipleTransformedObject withFile(@NotNull final String... paths) {
    return this.withFile(Arrays.stream(paths).map(Path::of).toArray(Path[]::new));
  }

  /**
   * sets the {@link #paths}.
   *
   * @param paths the paths to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final MultipleTransformedObject withFile(@NotNull final Path... paths) {
    this.paths = Set.of(paths);
    return this;
  }

  /**
   * sets the resolver.
   *
   * @param resolver the resolver to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final MultipleTransformedObject withResolver(@NotNull final TransformResolver resolver) {
    this.resolver = resolver.withCurrentObject(this);
    return this;
  }
}
