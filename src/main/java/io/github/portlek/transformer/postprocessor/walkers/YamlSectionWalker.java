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

package io.github.portlek.transformer.postprocessor.walkers;

import io.github.portlek.transformer.postprocessor.SectionWalker;
import org.jetbrains.annotations.NotNull;

/**
 * an abstract implementation of {@link SectionWalker} for YAML.
 */
public abstract class YamlSectionWalker implements SectionWalker {

  @Override
  public boolean isPath(@NotNull final String line) {
    final var name = this.readName(line);
    return !name.isEmpty() && name.charAt(0) != '-' && name.charAt(0) != '#';
  }

  @Override
  public boolean isPathMultilineStart(@NotNull final String line) {
    final var trimmed = line.trim();
    return !line.isEmpty() &&
      (trimmed.endsWith(">") || trimmed.endsWith(">-") || trimmed.endsWith("|") || trimmed.endsWith("|-"));
  }

  @NotNull
  @Override
  public String readName(@NotNull final String line) {
    return line.split(":", 2)[0].trim();
  }
}
