package dev.morphia.annotations;


import java.lang.annotation.*;


/**
 * @deprecated use {@link PostPersist} instead
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Deprecated
public @interface PreSave {
}
