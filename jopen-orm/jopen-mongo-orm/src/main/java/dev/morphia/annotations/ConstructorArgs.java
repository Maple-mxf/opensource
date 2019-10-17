package dev.morphia.annotations;


import java.lang.annotation.*;


/**
 * Indicates that this field can be constructed from the stored fields; it doesn't require a no-args constructor. Please list the names of
 * args/fields, in order.
 *
 * @author Scott Hernandez
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConstructorArgs {
    /**
     * @return The fields to use
     */
    String[] value();
}
