package io.github.portlek.transformer;

import io.github.portlek.replaceable.RpList;
import io.github.portlek.transformer.annotations.Comment;
import io.github.portlek.transformer.annotations.Names;

@Comment({"header-1", "header-2"})
@Names(modifier = Names.Modifier.TO_LOWER_CASE, strategy = Names.Strategy.HYPHEN_CASE)
public class TransformedConfig extends TransformedObject {

  @Comment({"test", "test"})
  public static RpList test = RpList.from("%test%-1", "%test%-2", "%test%-3")
    .regex("%test%");

  @Comment({"header-1", "header-2"})
  public static Test testSection = new Test();

  public static final class Test extends TransformedObject {

    @Comment({"test", "test"})
    public static RpList test = RpList.from("%test%-1", "%test%-2", "%test%-3")
      .regex("%test%");
  }
}
