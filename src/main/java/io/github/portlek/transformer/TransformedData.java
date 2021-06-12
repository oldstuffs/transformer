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

import io.github.portlek.transformer.declarations.GenericDeclaration;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that represents transformed data.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransformedData {

  /**
   * the deserialized map.
   */
  private final Map<String, Object> deserializedMap;

  /**
   * the resolver.
   */
  @NotNull
  private final TransformResolver resolver;

  /**
   * the serialization.
   */
  private final boolean serialization;

  /**
   * the serialized map.
   */
  private final Map<String, Object> serializedMap;

  /**
   * creates a new transformed data instance for deserialization.
   *
   * @param resolver the resolver to create.
   * @param map the map to create.
   *
   * @return a transformed data instance for deserialization.
   */
  @NotNull
  public static TransformedData deserialization(@NotNull final TransformResolver resolver,
                                                @NotNull final Map<String, Object> map) {
    return new TransformedData(new ConcurrentHashMap<>(map), resolver, false, new ConcurrentHashMap<>());
  }

  /**
   * creates a new transformed data instance for serialization.
   *
   * @param resolver the resolver to create.
   *
   * @return a transformed data instance for serialization.
   */
  @NotNull
  public static TransformedData serialization(@NotNull final TransformResolver resolver) {
    return new TransformedData(new ConcurrentHashMap<>(), resolver, true, new ConcurrentHashMap<>());
  }

  /**
   * adds the value to the path.
   *
   * @param path the path to add.
   * @param value the value to add.
   */
  public void add(@NotNull final String path, @NotNull final Object value) {
    this.serializedMap.put(path, this.resolver.serialize(value, null, true));
  }

  /**
   * adds the value to the path.
   *
   * @param path the path to add.
   * @param value the value to add.
   * @param cls the cls to add.
   * @param <T> type of the value class.
   */
  public <T> void add(@NotNull final String path, @Nullable final Object value, @NotNull final Class<T> cls) {
    this.serializedMap.put(path, this.resolver.serialize(
      value,
      GenericDeclaration.of(cls),
      true));
  }

  /**
   * adds the map to the path.
   *
   * @param path the path to add.
   * @param map the map to add.
   * @param keyClass the key class to add.
   * @param valueClass the value class to add.
   * @param <K> type of the key class.
   * @param <V> type of the value class.
   */
  @SuppressWarnings("unchecked")
  public <K, V> void addAsMap(@NotNull final String path, @NotNull final Map<K, V> map,
                              @NotNull final Class<K> keyClass, @NotNull final Class<V> valueClass) {
    this.serializedMap.put(path, this.resolver.serializeMap(
      (Map<Object, Object>) map,
      GenericDeclaration.of(map.getClass(), keyClass, valueClass),
      true));
  }

  /**
   * adds the collection to the path.
   *
   * @param path the path to add.
   * @param collection the collection to add.
   * @param elementClass the element class to add.
   * @param <T> type of the element class.
   */
  public <T> void addCollection(@NotNull final String path, @NotNull final Collection<T> collection,
                                @NotNull final Class<T> elementClass) {
    this.serializedMap.put(path, this.resolver.serializeCollection(
      collection,
      GenericDeclaration.of(collection.getClass(), elementClass),
      true));
  }

  /**
   * adds formatted string to the path.
   *
   * @param path the path to add.
   * @param format the format to add.
   * @param args the args to add.
   */
  public void addFormatted(@NotNull final String path, @NotNull final String format, @NotNull final Object... args) {
    this.add(path, MessageFormat.format(format, args));
  }

  /**
   * checks if the deserialized map contains the key.
   *
   * @param key the key to check.
   *
   * @return {@code true} if the deserialized map contains the key.
   */
  public boolean containsKey(@NotNull final String key) {
    return this.canDeserialize() && this.deserializedMap.containsKey(key);
  }

  /**
   * gets a value from deserialized map.
   *
   * @param key the key to get.
   * @param objectClass the object class to get.
   * @param <T> type of the value.
   *
   * @return obtained value.
   */
  @NotNull
  public <T> Optional<T> get(@NotNull final String key, @NotNull final Class<T> objectClass) {
    return this.get(key, objectClass, null);
  }

  /**
   * gets a value from deserialized map.
   *
   * @param key the key to get.
   * @param objectClass the object class to get.
   * @param defaultValue the default value to get.
   * @param <T> type of the value.
   *
   * @return obtained value.
   */
  @NotNull
  public <T> Optional<T> get(@NotNull final String key, @NotNull final Class<T> objectClass,
                             @Nullable final T defaultValue) {
    if (this.canSerialize()) {
      return Optional.empty();
    }
    final var object = this.deserializedMap.get(key);
    if (object == null) {
      return Optional.empty();
    }
    return Optional.of(this.resolver.deserialize(
      object,
      GenericDeclaration.of(object),
      objectClass,
      null,
      defaultValue));
  }

  /**
   * gets a value from deserialized map as list.
   *
   * @param key the key to get.
   * @param elementClass the element class to get.
   * @param <T> type of the elements of list.
   *
   * @return obtained list value.
   */
  @SuppressWarnings("unchecked")
  @NotNull
  public <T> Optional<List<T>> getAsList(@NotNull final String key, @NotNull final Class<T> elementClass) {
    if (this.canSerialize()) {
      return Optional.empty();
    }
    final var object = this.deserializedMap.get(key);
    if (object == null) {
      return Optional.empty();
    }
    return Optional.of(this.resolver.deserialize(
      object,
      GenericDeclaration.of(object),
      List.class,
      GenericDeclaration.of(List.class, elementClass),
      null));
  }

  /**
   * gets a value from deserialized map as map.
   *
   * @param key the key to get.
   * @param keyClass the key class to get.
   * @param valueClass the value class to get.
   * @param <K> type of the keys of map.
   * @param <V> type of the values of map.
   *
   * @return obtained map value.
   */
  @NotNull
  public <K, V> Optional<Map<K, V>> getAsMap(@NotNull final String key, @NotNull final Class<K> keyClass,
                                             @NotNull final Class<V> valueClass) {
    if (this.canSerialize()) {
      return Optional.empty();
    }
    final var object = this.deserializedMap.get(key);
    if (object == null) {
      return Optional.empty();
    }
    return Optional.of(this.resolver.deserialize(
      object,
      GenericDeclaration.of(object),
      Map.class,
      GenericDeclaration.of(Map.class, keyClass, valueClass),
      null));
  }

  /**
   * obtains deserialized map.
   *
   * @return deserialized map.
   */
  @NotNull
  public Map<String, Object> getDeserializedMap() {
    return Collections.unmodifiableMap(this.deserializedMap);
  }

  /**
   * obtains serialized map.
   *
   * @return serialized map.
   */
  @NotNull
  public Map<String, Object> getSerializedMap() {
    return Collections.unmodifiableMap(this.serializedMap);
  }

  /**
   * checks if the data can deserialize.
   *
   * @return {@code true} if data can deserialize.
   */
  private boolean canDeserialize() {
    return !this.serialization;
  }

  /**
   * checks if the data can serialize.
   *
   * @return {@code true} if data can serialize.
   */
  private boolean canSerialize() {
    return this.serialization;
  }
}
