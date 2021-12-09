package application.shell.commands;


import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandArg;
import application.shell.commands.framework.ShellCommandArgContainer;
import application.shell.commands.framework.ShellCommandSpec;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.geometry.grid.GridWarehouseCoordinateSystem;
import warehouse.tiles.StorageTile;
import warehouse.tiles.Tile;
import warehouse.WarehouseLayout;

/**
 * Argument container for the DisplayStorageUnitInfoCommand.
 */
class DisplayStorageUnitInfoCommandArgContainer extends ShellCommandArgContainer {
    @ShellCommandArg
    private String coordinate;

    public String getCoordinate() {
        return coordinate;
    }
}

/**
 * A command to display info about a storage unit at a location.
 */
@ShellCommandSpec(name = "display-storage-unit-info", description = "Display info about a storage unit at a location.")
public class DisplayStorageUnitInfoCommand<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate>
        extends ShellCommand<T, U> {
    @Override
    public String execute(ShellApplication<T, U> application, ShellCommandArgContainer argContainer) {
        DisplayStorageUnitInfoCommandArgContainer args = (DisplayStorageUnitInfoCommandArgContainer) argContainer;

        U coordinate = application.getCoordinateParser().parse(args.getCoordinate());
        if (coordinate == null) {
            return String.format("Could not parse coordinate: %s", args.getCoordinate());
        }

        WarehouseLayout<U> warehouseLayout = application.getWarehouse().getState().getLayout();
        Tile tile = warehouseLayout.getTileAt(coordinate);
        if (tile != null) {
            if (tile instanceof StorageTile) {
                return ((StorageTile)tile).getStorageUnit().toString();
            } else {
                return String.format("the tile at %s is not a StorageTile", coordinate);
            }
        } else {
            return String.format("invalid tile coordinates: %s", coordinate);
        }
    }

    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new DisplayStorageUnitInfoCommandArgContainer();
    }
}
