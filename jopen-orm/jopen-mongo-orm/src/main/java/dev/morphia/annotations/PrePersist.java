package dev.morphia.annotations;


import java.lang.annotation.*;


/**
 * Called before the data has been persisted to the datastore (before mapping is done).
 *
 * @author Scott Hernandez
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PrePersist {
}
