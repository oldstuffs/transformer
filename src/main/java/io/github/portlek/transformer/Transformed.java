package io.github.portlek.transformer;

import io.github.portlek.transformer.declarations.TransformedObjectDeclaration;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * an interface to determine transformed objects.
 */
public interface Transformed {

  /**
   * obtains the all keys.
   *
   * @return all keys.
   */
  @NotNull
  List<String> getAllKeys();

  /**
   * obtain the declaration.
   *
   * @return declaration.
   */
  @Nullable
  TransformedObjectDeclaration getDeclaration();
}
