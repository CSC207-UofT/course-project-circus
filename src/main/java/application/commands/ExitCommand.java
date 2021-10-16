package application.commands;

import application.ShellApplication;
import application.commands.framework.ShellCommand;
import application.commands.framework.ShellCommandArgContainer;
import application.commands.framework.ShellCommandSpec;

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
