package main.java.application.commands;

import main.java.application.ShellApplication;
import main.java.application.commands.framework.*;

class HelpCommandArgContainer extends ShellCommandArgContainer {
    @ShellCommandArg
    private String name;

    public String getName() {
        return name;
    }
}

@ShellCommandSpec(name = "help", description = "Provides help information for commands.")
public class HelpCommand extends ShellCommand {
    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer argContainer) {
        HelpCommandArgContainer args = (HelpCommandArgContainer) argContainer;
        ShellCommand command = application.getCommandExecutor().getCommandByName(args.getName());
        if (command == null) {
            return String.format("Could not find command with name \"%s\"", args.getName());
        } else {
            try {
                return command.getSpec().description();
            } catch (ShellCommandSpecNotFoundException e) {
                return e.getMessage();
            }
        }
    }

    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new HelpCommandArgContainer();
    }
}
