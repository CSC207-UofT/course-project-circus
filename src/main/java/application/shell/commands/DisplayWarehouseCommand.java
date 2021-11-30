package application.shell.commands;


import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandArgContainer;
import application.shell.commands.framework.ShellCommandSpec;
import warehouse.*;
import warehouse.storage.Rack;
import warehouse.storage.ReceiveDepot;
import warehouse.storage.ShipDepot;
import warehouse.storage.StorageUnit;
import warehouse.tiles.EmptyTile;
import warehouse.tiles.StorageTile;
import warehouse.tiles.Tile;

import java.util.HashMap;
import java.util.Map;

/**
 * A command to display the layout of the Warehouse.
 */
@ShellCommandSpec(name = "display-warehouse", description = "Display the layout of the warehouse.")
public class DisplayWarehouseCommand extends ShellCommand {
    private final static Map<Class<? extends StorageUnit>, Character> STORAGE_UNIT_SYMBOLS = new HashMap<>();
    private final static Character UNKNOWN_TILE_SYMBOL = 'U';
    private final static Character EMPTY_TILE_SYMBOL = 'X';

    static {
        STORAGE_UNIT_SYMBOLS.put(Rack.class, 'R');
        STORAGE_UNIT_SYMBOLS.put(ReceiveDepot.class, 'I');
        STORAGE_UNIT_SYMBOLS.put(ShipDepot.class, 'O');
    }

    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer args) {
        Warehouse warehouse = application.getWarehouseController().getWarehouse();
        StringBuilder stringBuilder = new StringBuilder();
        // Create X coordinate header
        stringBuilder.append("  ");
        for (int x = 0; x < warehouse.getWidth(); x++) {
            stringBuilder.append(String.format(" %d", x));
        }
        stringBuilder.append("\n");
        // Create tile grid
        for (int y = 0; y < warehouse.getHeight(); y++) {
            stringBuilder.append(String.format(" %d", y));
            for (int x = 0; x < warehouse.getWidth(); x++) {
                try {
                    Tile tile = warehouse.getTileAt(x, y);
                    stringBuilder.append(' ');
                    if ((tile instanceof EmptyTile) || (tile instanceof StorageTile && ((StorageTile)tile).isEmpty())) {
                        stringBuilder.append(EMPTY_TILE_SYMBOL);
                    } else if (tile instanceof StorageTile) {
                        Class<?> clazz = ((StorageTile)tile).getStorageUnit().getClass();
                        Character UNKNOWN_STORAGE_UNIT_SYMBOL = '?';
                        stringBuilder.append(STORAGE_UNIT_SYMBOLS.getOrDefault(clazz,
                                UNKNOWN_STORAGE_UNIT_SYMBOL));
                    } else {

                        stringBuilder.append(UNKNOWN_TILE_SYMBOL);
                    }
                } catch (TileOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
