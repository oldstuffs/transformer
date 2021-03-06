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

package io.github.portlek.transformer;

import io.github.portlek.transformer.declarations.GenericHolder;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * an interface to determine transformers.
 *
 * @param <R> type of the raw value.
 * @param <F> type of the final value.
 */
public interface Transformer<R, F> extends GenericHolder<R, F> {

  /**
   * creates a simple transformer.
   *
   * @param rawType the raw type to create.
   * @param finalType the final type to create.
   * @param transformation the transformation to create.
   * @param <R> type of the raw value.
   * @param <F> type of the final value.
   *
   * @return a newly created transformer.
   */
  @NotNull
  static <R, F> Transformer<R, F> create(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                                         @NotNull final Function<@NotNull R, @Nullable F> transformation) {
    return new Impl<>(rawType, finalType, transformation);
  }

  /**
   * creates a simple transformer.
   *
   * @param rawType the raw type to create.
   * @param finalType the final type to create.
   * @param transformation the transformation to create.
   * @param transformationWithField the transformation with field to create.
   * @param <R> type of the raw value.
   * @param <F> type of the final value.
   *
   * @return a newly created transformer.
   */
  @NotNull
  static <R, F> Transformer<R, F> create(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                                         @NotNull final Function<@NotNull R, @Nullable F> transformation,
                                         @NotNull final BiFunction<@NotNull R, @NotNull F, @Nullable F> transformationWithField) {
    return new Impl<>(rawType, finalType, transformation, transformationWithField);
  }

  /**
   * transforms the {@link R} into {@link F}.
   *
   * @param r the r to transform.
   *
   * @return transformed value.
   */
  @NotNull
  Optional<F> transform(@NotNull R r);

  /**
   * transforms the {@link R} into {@link F} with field.
   *
   * @param r the r to transform.
   * @param field the field to transform.
   *
   * @return transformed value.
   */
  @NotNull
  Optional<F> transformWithField(@NotNull R r, @NotNull F field);

  /**
   * an abstract that envelopes to {@link Transformer}.
   *
   * @param <R> type of the raw value.
   * @param <F> type of the final value.
   */
  @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
  abstract class Base<R, F> implements Transformer<R, F> {

    /**
     * the holder.
     */
    @NotNull
    @Delegate
    private final GenericHolder<R, F> holder;

    /**
     * the transformation.
     */
    @NotNull
    private final Function<@NotNull R, @Nullable F> transformation;

    /**
     * the transformation with field.
     */
    @NotNull
    private final BiFunction<@NotNull R, @NotNull F, @Nullable F> transformationWithField;

    /**
     * ctor.
     *
     * @param rawType the raw type to create.
     * @param finalType the final type to create.
     * @param transformation the transformation.
     */
    protected Base(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                   @NotNull final Function<@NotNull R, @Nullable F> transformation) {
      this(GenericHolder.create(rawType, finalType), transformation,
        (r, field) -> transformation.apply(r));
    }

    /**
     * ctor.
     *
     * @param rawType the raw type to create.
     * @param finalType the final type to create.
     * @param transformation the transformation.
     * @param transformationWithField the transformation with field.
     */
    protected Base(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                   @NotNull final Function<@NotNull R, @Nullable F> transformation,
                   @NotNull final BiFunction<@NotNull R, @NotNull F, @Nullable F> transformationWithField) {
      this(GenericHolder.create(rawType, finalType), transformation, transformationWithField);
    }

    @NotNull
    @Override
    public final Optional<F> transform(@NotNull final R r) {
      return Optional.ofNullable(this.transformation.apply(r));
    }

    @NotNull
    @Override
    public final Optional<F> transformWithField(@NotNull final R r, @NotNull final F field) {
      return Optional.ofNullable(this.transformationWithField.apply(r, field));
    }
  }

  /**
   * a simple implementation of {@link Transformer}.
   *
   * @param <R> type of the raw value.
   * @param <F> type of the final value.
   */
  final class Impl<R, F> extends Base<R, F> {

    /**
     * ctor.
     *
     * @param rawType the raw type to create.
     * @param finalType the final type to create.
     * @param transformation the transformation.
     */
    private Impl(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                 @NotNull final Function<@NotNull R, @Nullable F> transformation) {
      super(rawType, finalType, transformation,
        (r, field) -> transformation.apply(r));
    }

    /**
     * ctor.
     *
     * @param rawType the raw type to create.
     * @param finalType the final type to create.
     * @param transformation the transformation.
     * @param transformationWithField the transformation with field.
     */
    private Impl(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                 @NotNull final Function<@NotNull R, @Nullable F> transformation,
                 @NotNull final BiFunction<@NotNull R, @NotNull F, @Nullable F> transformationWithField) {
      super(rawType, finalType, transformation, transformationWithField);
    }
  }
}
