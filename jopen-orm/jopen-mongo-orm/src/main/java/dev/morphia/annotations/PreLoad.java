package dev.morphia.annotations;


import java.lang.annotation.*;


/**
 * Called before the data has been loaded from the datastore.
 *
 * @author Scott Hernandez
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PreLoad {
}
