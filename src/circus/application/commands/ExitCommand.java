package circus.application.commands;

import circus.application.ShellApplication;

/**
 * Command to exit the shell application.
 */
@ShellCommandSpec(name = "exit", description = "Closes the shell application.")
public class ExitCommand implements ShellCommand {
    @Override
    public String execute(ShellApplication application) {
        application.stop();
        return null;
    }
}
