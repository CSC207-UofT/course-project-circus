package application.shell.commands;

import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandArg;
import application.shell.commands.framework.ShellCommandArgContainer;
import application.shell.commands.framework.ShellCommandSpec;
import warehouse.*;
import warehouse.storage.Rack;
import warehouse.storage.StorageUnit;
import warehouse.tiles.StorageTile;
import warehouse.tiles.Tile;

/**
 * Argument container for CreateStorageUnitCommand.
 */
class CreateStorageUnitArgContainer extends ShellCommandArgContainer {
    @ShellCommandArg
    private String type;
    @ShellCommandArg
    private int x;
    @ShellCommandArg
    private int y;
    @ShellCommandArg
    private int capacity;

    public String getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCapacity() {
        return capacity;
    }
}

/**
 * A command to create a StorageUnit and add it to the Warehouse.
 */
@ShellCommandSpec(name = "create-storage-unit", description = "Create a StorageUnit and add it to the Warehouse.")
public class CreateStorageUnitCommand extends ShellCommand {
    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer argContainer) {
        CreateStorageUnitArgContainer args = (CreateStorageUnitArgContainer) argContainer;
        String type = args.getType().toLowerCase();
        StorageUnit storageUnitToAdd;
        if (type.equals("depot")) {
            // TODO: Add depot
            storageUnitToAdd = null;
//            storageUnitToAdd = new Depot(args.getCapacity());
        } else if (type.equals("rack")) {
            storageUnitToAdd = new Rack(args.getCapacity());
        } else {
            return String.format("Unknown StorageUnit type \"%s\"", type);
        }

        Warehouse warehouse = application.getWarehouseController().getWarehouse();
        try {
            Tile tile = warehouse.getTileAt(args.getX(), args.getY());
            if (tile instanceof StorageTile && !((StorageTile)tile).isEmpty()) {
                return String.format("Cannot add %s at (%d, %d) since the tile already has a storage unit",
                        type,
                        args.getX(),
                        args.getY());
            } else {
                Tile newTile = new StorageTile(args.getX(), args.getY(), storageUnitToAdd);
                warehouse.setTile(newTile);
                return String.format("Created %s at (%d, %d)", type, args.getX(), args.getY());
            }
        } catch (TileOutOfBoundsException e) {
            return e.getMessage();
        }
    }

    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new CreateStorageUnitArgContainer();
    }
}

