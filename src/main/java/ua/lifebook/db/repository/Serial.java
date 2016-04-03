package ua.lifebook.db.repository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks field as Serial (identity). Values is the field name in the {@link ua.lifebook.db.repository.Table}.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Serial {
    String value() default "Id";
}
