package circus.application.commands;

import circus.application.ShellApplication;
import circus.application.commands.framework.ShellCommand;
import circus.application.commands.framework.ShellCommandArgContainer;
import circus.application.commands.framework.ShellCommandSpec;

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
