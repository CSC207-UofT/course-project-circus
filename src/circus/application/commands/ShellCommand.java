package circus.application.commands;

import circus.application.ShellApplication;

/**
 * A shell command.
 */
public interface ShellCommand {
    /**
     * Execute the command. Optionally mutate the application.
     * @param application The application state.
     * @return the output of the command. This can be null.
     */
    String execute(ShellApplication application);

    /**
     * Check whether the given ShellCommand has a ShellCommandSpec.
     * @return True if it has a spec, and False otherwise.
     */
    static boolean hasSpec(ShellCommand command) {
        return command.getClass().isAnnotationPresent(ShellCommandSpec.class);
    }

    /**
     * Get the ShellCommandSpec for the given ShellCommand.
     * @param command The command whose spec to retrieve.
     * @return the command spec.
     * @throws ShellCommandSpecNotFoundException thrown when the command does not have a ShellCommandSpec annotation.
     */
    static ShellCommandSpec getSpec(ShellCommand command) throws ShellCommandSpecNotFoundException {
        if (!hasSpec(command)) {
            throw new ShellCommandSpecNotFoundException(command.getClass().getSimpleName());
        } else {
            return command.getClass().getAnnotation(ShellCommandSpec.class);
        }
    }
}
