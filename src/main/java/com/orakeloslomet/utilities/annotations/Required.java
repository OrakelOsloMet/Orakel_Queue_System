package com.orakeloslomet.utilities.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation defining a field as required
 *
 * @author Fredrik Pedersen
 * @see DTOValidator
 * @since 19/01/2021 at 17:20
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Required {
    boolean value() default true;
}
