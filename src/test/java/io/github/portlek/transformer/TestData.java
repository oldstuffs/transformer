package io.github.portlek.transformer;

import io.github.portlek.replaceable.RpString;
import io.github.portlek.transformer.declarations.GenericDeclaration;
import java.util.Optional;
import java.util.function.Consumer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class TestData {

  @NotNull
  private final Consumer<@NotNull String> consumer;

  private final int number;

  @NotNull
  private final RpString test;

  public static final class Serializer implements ObjectSerializer<TestData> {

    @NotNull
    @Override
    public Optional<TestData> deserialize(@NotNull final TransformedData transformedData,
                                          @Nullable final GenericDeclaration declaration) {
      return Optional.empty();
    }

    @NotNull
    @Override
    public Optional<TestData> deserialize(@NotNull final TestData field, @NotNull final TransformedData transformedData,
                                          @Nullable final GenericDeclaration declaration) {
      return Optional.of(new TestData(
        field.getConsumer(),
        transformedData.get("number", int.class).orElseThrow(),
        transformedData.get("test", RpString.class, field.getTest()).orElseThrow()));
    }

    @Override
    public void serialize(@NotNull final TestData testData, @NotNull final TransformedData transformedData) {
      transformedData.add("test", testData.getTest(), RpString.class);
      transformedData.add("number", testData.getNumber());
    }

    @Override
    public boolean supports(@NotNull final Class<?> cls) {
      return TestData.class.isAssignableFrom(cls);
    }
  }
}
