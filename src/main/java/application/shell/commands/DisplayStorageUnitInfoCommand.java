package application.shell.commands;


import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandArg;
import application.shell.commands.framework.ShellCommandArgContainer;
import application.shell.commands.framework.ShellCommandSpec;
import warehouse.storage.StorageUnit;
import warehouse.Tile;
import warehouse.TileOutOfBoundsException;
import warehouse.Warehouse;

/**
 * Argument container for the DisplayStorageUnitInfoCommand.
 */
class DisplayStorageUnitInfoCommandArgContainer extends ShellCommandArgContainer {
    @ShellCommandArg
    private int x;
    @ShellCommandArg
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

/**
 * A command to display info about a storage unit at a location.
 */
@ShellCommandSpec(name = "display-storage-unit-info", description = "Display info about a storage unit at a location.")
public class DisplayStorageUnitInfoCommand extends ShellCommand {
    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer argContainer) {
        DisplayStorageUnitInfoCommandArgContainer args = (DisplayStorageUnitInfoCommandArgContainer) argContainer;
        Warehouse warehouse = application.getWarehouseController().getWarehouse();
        try {
            Tile tile = warehouse.getTileAt(args.getX(), args.getY());
            StorageUnit storageUnit = tile.getStorageUnit();
            if (storageUnit == null) {
                return String.format("Tile at (%d, %d) is empty!", args.getX(), args.getY());
            } else {
                return storageUnit.toString();
            }
        } catch (TileOutOfBoundsException e) {
            return e.getMessage();
        }
    }

    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new DisplayStorageUnitInfoCommandArgContainer();
    }
}
