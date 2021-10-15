package circus.application.commands;

import circus.application.ShellApplication;

/**
 * Command to exit the shell application.
 */
@ShellCommandSpec(name = "exit", description = "Closes the shell application.")
public class ExitCommand extends ShellCommand {
    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer args) {
        application.stop();
        return null;
    }
}
