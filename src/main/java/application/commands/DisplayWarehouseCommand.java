package main.java.application.commands;


import main.java.application.ShellApplication;
import main.java.application.commands.framework.ShellCommand;
import main.java.application.commands.framework.ShellCommandArgContainer;
import main.java.application.commands.framework.ShellCommandSpec;
import main.java.warehouse.*;

import java.util.HashMap;
import java.util.Map;

/**
 * A command to display the layout of the Warehouse.
 */
@ShellCommandSpec(name = "display-warehouse", description = "Display the layout of the warehouse.")
public class DisplayWarehouseCommand extends ShellCommand {
    private final static Map<Class<? extends StorageUnit>, Character> STORAGE_UNIT_SYMBOLS = new HashMap<>();

    static {
        STORAGE_UNIT_SYMBOLS.put(Rack.class, 'R');
        STORAGE_UNIT_SYMBOLS.put(Depot.class, 'D');
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
                    if (tile.isEmpty()) {
                        Character EMPTY_TILE_SYMBOL = 'X';
                        stringBuilder.append(EMPTY_TILE_SYMBOL);
                    } else {
                        Class<?> clazz = tile.getStorageUnit().getClass();
                        Character UNKNOWN_STORAGE_UNIT_SYMBOL = '?';
                        stringBuilder.append(STORAGE_UNIT_SYMBOLS.getOrDefault(clazz,
                                UNKNOWN_STORAGE_UNIT_SYMBOL));
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
