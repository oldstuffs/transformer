package io.github.portlek.transformer.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * an annotation to define migrated version of the field.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Migration {

  /**
   * obtains the migrated version.
   *
   * does not affect 
   *
   * @return migrated version.
   */
  int value() default -1;
}
