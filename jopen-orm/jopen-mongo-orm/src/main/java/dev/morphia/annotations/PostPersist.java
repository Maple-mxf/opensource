package dev.morphia.annotations;


import java.lang.annotation.*;


/**
 * Called after the data has been persisted from the java object.
 *
 * @author Scott Hernandez
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PostPersist {
}
