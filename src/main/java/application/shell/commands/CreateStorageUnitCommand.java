package application.shell.commands;

import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandArg;
import application.shell.commands.framework.ShellCommandArgContainer;
import application.shell.commands.framework.ShellCommandSpec;
import warehouse.*;
import warehouse.storage.StorageUnit;
import warehouse.storage.containers.InMemoryStorageUnitContainer;
import warehouse.storage.strategies.MultiTypeStorageUnitStrategy;
import warehouse.storage.strategies.SingleTypeStorageStrategy;
import warehouse.tiles.*;

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
        Warehouse warehouse = application.getWarehouseController().getWarehouse();
        // Get tile
        Tile tile = warehouse.getTileAt(args.getX(), args.getY());
        if (tile != null) {
            // Make sure we can add a StorageUnit to the tile.
            String type = args.getType().toLowerCase();
            if (tile instanceof StorageTile) {
                return String.format("Cannot add %s at (%d, %d) since the tile already has a storage unit",
                        type,
                        args.getX(),
                        args.getY());
            } else {
                StorageTile newTile;
                switch (type) {
                    case "ship-depot":
                        newTile = new ShipDepot(args.getX(), args.getY(), new StorageUnit(args.getCapacity(),
                                new MultiTypeStorageUnitStrategy(), new InMemoryStorageUnitContainer()));
                        break;
                    case "receive-depot":
                        newTile = new ReceiveDepot(args.getX(), args.getY(), new StorageUnit(args.getCapacity(),
                                new MultiTypeStorageUnitStrategy(), new InMemoryStorageUnitContainer()));
                        break;
                    case "rack":
                        newTile = new Rack(args.getX(), args.getY(), new StorageUnit(args.getCapacity(),
                                new SingleTypeStorageStrategy(), new InMemoryStorageUnitContainer()));
                        break;
                    default:
                        return String.format("Unknown StorageUnit type \"%s\"", type);
                }
                warehouse.setTile(newTile);
                return String.format("Created %s at (%d, %d)", type, args.getX(), args.getY());
            }
        } else {
            return String.format("invalid tile coordinates: (%d, %d)", args.getX(), args.getY());
        }
    }

    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new CreateStorageUnitArgContainer();
    }
}

