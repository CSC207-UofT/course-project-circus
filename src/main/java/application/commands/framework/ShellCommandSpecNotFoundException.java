package main.java.application.commands.framework;

/**
 * An exception that is thrown when the ShellCommandSpec annotation is not found.
 */
public class ShellCommandSpecNotFoundException extends Exception {
    /**
     * Construct a ShellCommandSpecNotFoundException with a class name.
     * @param className The name of the class that does not have ShellCommandSpec annotation.
     */
    public ShellCommandSpecNotFoundException(String className) {
        super(String.format("The class %s is not annotated with ShellCommandSpec", className));
    }
}
