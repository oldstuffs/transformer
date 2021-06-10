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

package io.github.portlek.transformer.postprocessor;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine section walkers.
 */
public interface SectionWalker {

  /**
   * checks if the line is a path.
   *
   * @param line the line to check.
   *
   * @return {@code true} if the line is a pathç
   */
  boolean isPath(@NotNull String line);

  /**
   * checks if the line has multiline start.
   *
   * @param line the line to check.
   *
   * @return {@code true} if the line has multiline start.
   */
  boolean isPathMultilineStart(@NotNull String line);

  /**
   * gets the name of the line.
   *
   * @param line the line to get.
   *
   * @return obtained line name.
   */
  @NotNull
  String readName(@NotNull String line);

  /**
   * updates the line.
   *
   * @param line the line to update.
   * @param lineInfo the line info to update.
   * @param path the path to update.
   *
   * @return updated line.
   */
  @NotNull
  String update(@NotNull String line, @NotNull LineInfo lineInfo, @NotNull List<LineInfo> path);
}
