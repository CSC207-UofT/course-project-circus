package application.shell.commands;


import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandArgContainer;
import application.shell.commands.framework.ShellCommandSpec;
import warehouse.*;
import warehouse.storage.StorageUnit;
import warehouse.tiles.*;

import java.util.HashMap;
import java.util.Map;

/**
 * A command to display the layout of the Warehouse.
 */
@ShellCommandSpec(name = "display-warehouse", description = "Display the layout of the warehouse.")
public class DisplayWarehouseCommand extends ShellCommand {
    private final static Map<Class<? extends Tile>, Character> TILE_SYMBOLS = new HashMap<>();
    private final static Character UNKNOWN_TILE_SYMBOL = '?';

    static {

        TILE_SYMBOLS.put(EmptyTile.class, '.');
        TILE_SYMBOLS.put(Rack.class, 'X');
        TILE_SYMBOLS.put(ReceiveDepot.class, 'R');
        TILE_SYMBOLS.put(ShipDepot.class, 'S');
    }

    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer args) {
        Warehouse warehouse = application.getWarehouseController().getWarehouse();
        StringBuilder stringBuilder = new StringBuilder();
        // Create legend bar
        int legendWidth = 2 * (warehouse.getWidth() + 1);
        String legendBar = "=".repeat(Math.max(0, legendWidth)) + "\n";

        // Create legend
        stringBuilder.append(legendBar);
        for (Class<? extends Tile> clazz : TILE_SYMBOLS.keySet()) {
            String clazzName = clazz.getSimpleName();
            String space = " ".repeat(legendWidth - clazzName.length() - 5);
            char symbol = TILE_SYMBOLS.get(clazz);

            stringBuilder.append(String.format("%s%s%c\n", clazzName, space, symbol));
        }
        stringBuilder.append(legendBar);

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
                stringBuilder.append(' ');

                Tile tile = warehouse.getTileAt(x, y);
                Class<? extends Tile> clazz = tile.getClass();
                char symbol = TILE_SYMBOLS.getOrDefault(clazz, UNKNOWN_TILE_SYMBOL);
                stringBuilder.append(symbol);
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
