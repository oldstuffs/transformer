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

package io.github.portlek.transformer.transformers;

import io.github.portlek.transformer.Transformer;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that represents transformers between {@link String} and {@link Locale}.
 */
public final class TransformerStringToLocale extends Transformer.Base<String, Locale> {

  /**
   * ctor.
   */
  public TransformerStringToLocale() {
    super(String.class, Locale.class,
      TransformerStringToLocale::toLocale);
  }

  @Nullable
  private static Locale toLocale(@NotNull final String s) {
    final var trim = s.trim();
    if (trim.isEmpty()) {
      return Locale.ROOT;
    }
    final var strings = trim.split("_");
    if (trim.contains("_") && strings.length != 2) {
      return Locale.ROOT;
    }
    if (strings.length != 2) {
      return null;
    }
    return new Locale(strings[0], strings[1]);
  }
}
