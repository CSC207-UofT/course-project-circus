package application.shell.commands;

import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandArg;
import application.shell.commands.framework.ShellCommandArgContainer;
import application.shell.commands.framework.ShellCommandSpec;
import warehouse.WarehouseLayout;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.tiles.*;

/**
 * Argument container for CreateStorageUnitCommand.
 */
class CreateStorageUnitArgContainer extends ShellCommandArgContainer {
    @ShellCommandArg
    private String type;
    @ShellCommandArg
    private String coordinate;
    @ShellCommandArg
    private int capacity;

    public String getType() {
        return type;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public int getCapacity() {
        return capacity;
    }
}

/**
 * A command to create a StorageUnit and add it to the WarehouseLayout.
 */
@ShellCommandSpec(name = "create-storage-unit", description = "Create a StorageUnit and add it to the WarehouseLayout.")
public class CreateStorageUnitCommand<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate>
        extends ShellCommand<T, U> {
    @Override
    public String execute(ShellApplication<T, U> application, ShellCommandArgContainer argContainer) {
        CreateStorageUnitArgContainer args = (CreateStorageUnitArgContainer) argContainer;
        U coordinate = application.getCoordinateParser().parse(args.getCoordinate());
        if (coordinate == null) {
            return String.format("Could not parse coordinate: %s", args.getCoordinate());
        }

        WarehouseLayout<U> warehouseLayout = application.getWarehouse().getState().getLayout();
        // Get tile
        Tile tile = warehouseLayout.getTileAt(coordinate);
        if (tile != null) {
            // Make sure we can add a StorageUnit to the tile.
            String type = args.getType().toLowerCase();
            if (tile instanceof StorageTile) {
                return String.format("Cannot add %s at %s since the tile already has a storage unit",
                        type,
                        coordinate);
            } else {
                StorageTile newTile;
                switch (type) {
                    case "ship-depot":
                        newTile = new ShipDepot(-1, args.getCapacity());
                        break;
                    case "receive-depot":
                        newTile = new ReceiveDepot(-1, args.getCapacity());
                        break;
                    case "rack":
                        newTile = new Rack(-1, args.getCapacity());
                        break;
                    default:
                        return String.format("Unknown StorageUnit type \"%s\"", type);
                }
                warehouseLayout.setTileAt(coordinate, newTile);
                return String.format("Created %s at %s", type, coordinate);
            }
        } else {
            return String.format("invalid tile coordinates: %s", coordinate);
        }
    }

    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new CreateStorageUnitArgContainer();
    }
}

