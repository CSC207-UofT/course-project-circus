package application.shell.commands;

import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandArgContainer;
import application.shell.commands.framework.ShellCommandSpec;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.grid.GridWarehouseCoordinateSystem;

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
