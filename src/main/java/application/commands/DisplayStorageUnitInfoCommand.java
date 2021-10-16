package main.java.application.commands;


import main.java.application.ShellApplication;
import main.java.application.commands.framework.ShellCommand;
import main.java.application.commands.framework.ShellCommandSpec;
import main.java.warehouse.StorageUnit;
import main.java.warehouse.Tile;
import main.java.warehouse.TileOutOfBoundsException;
import main.java.warehouse.Warehouse;
import main.java.application.commands.framework.ShellCommandArg;
import main.java.application.commands.framework.ShellCommandArgContainer;

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
