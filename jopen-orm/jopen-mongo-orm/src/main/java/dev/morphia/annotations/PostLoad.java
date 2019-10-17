package dev.morphia.annotations;


import java.lang.annotation.*;


/**
 * Called after the data has been loaded into the java object.
 *
 * @author Scott Hernandez
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PostLoad {
}
