package io.github.portlek.transformer.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * an annotation to define transformer version.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TransformerVersion {

  /**
   * obtains the transformer version.
   *
   * @return transformer version.
   */
  int value() default 1;
}
