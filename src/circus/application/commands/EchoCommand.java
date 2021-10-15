package circus.application.commands;

import circus.application.ShellApplication;

class EchoArgContainer extends ShellCommandArgContainer {
    @ShellCommandArg
    private String input;

    public String getInput() {
        return input;
    }
}

@ShellCommandSpec(name = "echo")
public class EchoCommand extends ShellCommand {
    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer args) {
        return ((EchoArgContainer) args).getInput();
    }

    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new EchoArgContainer();
    }
}
