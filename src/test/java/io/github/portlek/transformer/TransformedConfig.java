package io.github.portlek.transformer;

import io.github.portlek.replaceable.RpList;
import io.github.portlek.transformer.annotations.Comment;
import io.github.portlek.transformer.annotations.CustomKey;
import io.github.portlek.transformer.annotations.Migration;
import io.github.portlek.transformer.annotations.Names;
import io.github.portlek.transformer.annotations.Version;

@Version(5)
@Names(modifier = Names.Modifier.TO_LOWER_CASE, strategy = Names.Strategy.HYPHEN_CASE)
public class TransformedConfig extends TransformedObject {

  public static RpList test = RpList.from("%test%-1", "%test%-2", "%test%-3")
    .regex("%test%");

  @Migration(3)
  public static TestData testData = new TestData(s -> {
    System.out.println(s);
  }, 100, "test data");

  @CustomKey("test-data")
  @Migration(4)
  public static TestData testData2 = new TestData(s -> {
    System.out.println(s);
  }, 100, "test data");

  @CustomKey("test-data")
  public static TestData testData3 = new TestData(s -> {
    System.out.println(s);
  }, 100, "test data");

  public static Test testSection = new Test();

  public static final class Test extends TransformedObject {

    public static RpList test = RpList.from("%test%-1", "%test%-2", "%test%-3")
      .regex("%test%");
  }
}
