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

import io.github.portlek.transformer.exceptions.TransformException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that represents multiple objects.
 *
 * @param <T> type of the objects.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultipleObject<T> {

  /**
   * the objects.
   */
  @NotNull
  private final Map<String, T> objects;

  /**
   * the multiple transformed object.
   */
  @Nullable
  private MultipleTransformedObject object;

  /**
   * creates a multiple object from the values.
   *
   * @param keys the keys to create.
   * @param values the values to create.
   * @param <T> type of the objects.
   *
   * @return a newly created multiple object instance.
   */
  @NotNull
  public static <T> MultipleObject<T> of(@NotNull final String[] keys, @NotNull final T[] values) {
    if (keys.length != values.length) {
      throw new TransformException("Lengths of keys and values are not equals!");
    }
    return new MultipleObject<>(IntStream.range(0, keys.length)
      .boxed()
      .collect(Collectors.toUnmodifiableMap(index -> keys[index], index -> values[index], (a, b) -> b)));
  }

  /**
   * creates a multiple object from the values.
   *
   * @param values the values to create.
   * @param <T> type of the objects.
   *
   * @return a newly created multiple object instance.
   */
  @NotNull
  public static <T> MultipleObject<T> of(@NotNull final Map<String, T> values) {
    return new MultipleObject<>(Map.copyOf(values));
  }

  /**
   * creates a multiple object from the values.
   *
   * @param values the values to create.
   * @param <T> type of the objects.
   *
   * @return a newly created multiple object instance.
   */
  @SafeVarargs
  @NotNull
  public static <T> MultipleObject<T> of(@NotNull final Map.Entry<String, T>... values) {
    return MultipleObject.of(Map.ofEntries(values));
  }

  /**
   * creates a multiple object from the values.
   *
   * @param key1 the key 1 to create.
   * @param value1 the value 1 to create.
   * @param <T> type of the objects.
   *
   * @return a newly created multiple object instance.
   */
  @NotNull
  public static <T> MultipleObject<T> of(@NotNull final String key1, @NotNull final T value1) {
    return MultipleObject.of(Map.of(key1, value1));
  }

  /**
   * creates a multiple object from the values.
   *
   * @param key1 the key 1 to create.
   * @param value1 the value 1 to create.
   * @param <T> type of the objects.
   *
   * @return a newly created multiple object instance.
   */
  @NotNull
  public static <T> MultipleObject<T> of(@NotNull final String key1, @NotNull final T value1,
                                         @NotNull final String key2, @NotNull final T value2) {
    return MultipleObject.of(Map.of(key1, value1, key2, value2));
  }

  /**
   * creates a multiple object from the values.
   *
   * @param key1 the key 1 to create.
   * @param value1 the value 1 to create.
   * @param key2 the key 2 to create.
   * @param value2 the value 2 to create.
   * @param key3 the key 3 to create.
   * @param value3 the value 3 to create.
   * @param <T> type of the objects.
   *
   * @return a newly created multiple object instance.
   */
  @NotNull
  public static <T> MultipleObject<T> of(@NotNull final String key1, @NotNull final T value1,
                                         @NotNull final String key2, @NotNull final T value2,
                                         @NotNull final String key3, @NotNull final T value3) {
    return MultipleObject.of(Map.of(key1, value1, key2, value2, key3, value3));
  }

  /**
   * creates a multiple object from the values.
   *
   * @param key1 the key 1 to create.
   * @param value1 the value 1 to create.
   * @param key2 the key 2 to create.
   * @param value2 the value 2 to create.
   * @param key3 the key 3 to create.
   * @param value3 the value 3 to create.
   * @param key4 the key 4 to create.
   * @param value4 the value 4 to create.
   * @param <T> type of the objects.
   *
   * @return a newly created multiple object instance.
   */
  @NotNull
  public static <T> MultipleObject<T> of(@NotNull final String key1, @NotNull final T value1,
                                         @NotNull final String key2, @NotNull final T value2,
                                         @NotNull final String key3, @NotNull final T value3,
                                         @NotNull final String key4, @NotNull final T value4) {
    return MultipleObject.of(Map.of(key1, value1, key2, value2, key3, value3, key4, value4));
  }

  /**
   * creates a multiple object from the values.
   *
   * @param key1 the key 1 to create.
   * @param value1 the value 1 to create.
   * @param key2 the key 2 to create.
   * @param value2 the value 2 to create.
   * @param key3 the key 3 to create.
   * @param value3 the value 3 to create.
   * @param key4 the key 4 to create.
   * @param value4 the value 4 to create.
   * @param key5 the key 5 to create.
   * @param value5 the value 5 to create.
   * @param <T> type of the objects.
   *
   * @return a newly created multiple object instance.
   */
  @NotNull
  public static <T> MultipleObject<T> of(@NotNull final String key1, @NotNull final T value1,
                                         @NotNull final String key2, @NotNull final T value2,
                                         @NotNull final String key3, @NotNull final T value3,
                                         @NotNull final String key4, @NotNull final T value4,
                                         @NotNull final String key5, @NotNull final T value5) {
    return MultipleObject.of(Map.of(key1, value1, key2, value2, key3, value3, key4, value4, key5, value5));
  }

  /**
   * sets the multiple transformed object.
   *
   * @param object the object to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public MultipleObject<T> withObject(@NotNull final MultipleTransformedObject object) {
    this.object = object;
    return this;
  }
}
