package io.github.portlek.transformer;

import io.github.portlek.transformer.multiple.MultipleObject;
import io.github.portlek.transformer.multiple.MultipleTransformedObject;
import java.io.File;
import org.jetbrains.annotations.NotNull;

public final class MultipleConfigExample extends MultipleTransformedObject {

  public static MultipleObject<String> message = MultipleObject.of(
    "en_US", "English message.",
    "tr_TR", "Türkçe mesaj."
  );

  public static void load(@NotNull final File folder) {
    new MultipleConfigExample()
      .withFile(
        new File(folder, "en_US.yml"),
        new File(folder, "tr_TR.yml"));
  }
}
