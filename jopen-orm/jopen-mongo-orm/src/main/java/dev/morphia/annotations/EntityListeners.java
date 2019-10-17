package dev.morphia.annotations;


import java.lang.annotation.*;


/**
 * Specifies other classes to participate in the @Entity's lifecycle
 *
 * @author Scott Hernandez
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EntityListeners {
    /**
     * @return The listeners to use for this entity
     */
    Class<?>[] value();
}
