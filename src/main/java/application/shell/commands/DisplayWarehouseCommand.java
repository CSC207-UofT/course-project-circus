package application.shell.commands;


import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandArgContainer;
import application.shell.commands.framework.ShellCommandSpec;
import warehouse.WarehouseState;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;

/**
 * A command to display the layout of the WarehouseLayout.
 */
@ShellCommandSpec(name = "display-warehouse", description = "Display the layout of the warehouse.")
public class DisplayWarehouseCommand<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate>
        extends ShellCommand<T, U> {

    @Override
    public String execute(ShellApplication<T, U> application, ShellCommandArgContainer args) {
        WarehouseState<T, U> state = application.getWarehouse().getState();
        return application.getWarehousePresenter().convert(state);
    }
}
