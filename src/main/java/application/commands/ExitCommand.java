package main.java.application.commands;

import main.java.application.ShellApplication;
import main.java.application.commands.framework.ShellCommand;
import main.java.application.commands.framework.ShellCommandArgContainer;
import main.java.application.commands.framework.ShellCommandSpec;

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
