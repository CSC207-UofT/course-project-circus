package main.java.application.commands.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for providing the metadata specification of a shell command argument.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ShellCommandArg {
    /**
     * Alias for this argument. If empty, then the field name is used instead.
     */
    String name() default "";
    /**
     * Whether this argument is positional. If false, it is a keyword argument.
     */
    boolean isPositional() default true;
    /**
     * An integer value used to specify the order of this argument relative to the others. The positional arguments are
     * sorted by index, in ascending order.
     */
    int positionalIndex() default 0;

    /**
     * The description of this argument.
     */
    String description() default "";
}
