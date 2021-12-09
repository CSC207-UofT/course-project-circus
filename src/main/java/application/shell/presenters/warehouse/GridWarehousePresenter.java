package application.shell.presenters.warehouse;

import warehouse.WarehouseLayout;
import warehouse.WarehouseState;
import warehouse.geometry.grid.GridWarehouseCoordinateSystem;
import warehouse.geometry.grid.Point;
import warehouse.tiles.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Presents a Warehouse with a GridWarehouseCoordinateSystem.
 */
public class GridWarehousePresenter implements
        WarehousePresenter<GridWarehouseCoordinateSystem, Point> {
    private final static Map<Class<? extends Tile>, Character> TILE_SYMBOLS = new HashMap<>();
    private final static Character UNKNOWN_TILE_SYMBOL = '?';

    static {
        TILE_SYMBOLS.put(EmptyTile.class, '.');
        TILE_SYMBOLS.put(Rack.class, 'X');
        TILE_SYMBOLS.put(ReceiveDepot.class, 'R');
        TILE_SYMBOLS.put(ShipDepot.class, 'S');
    }

    /**
     * Converts a GridWarehouse
     * @param state The warehouse state.
     * @return A string representing the given warehouse state with the given coordinate system.
     */
    @Override
    public String convert(WarehouseState<GridWarehouseCoordinateSystem, Point> state) {
        StringBuilder stringBuilder = new StringBuilder();
        // Create legend bar
        GridWarehouseCoordinateSystem coordinateSystem = state.getCoordinateSystem();
        int legendWidth = 2 * (coordinateSystem.getWidth() + 1);
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
        for (int x = 0; x < coordinateSystem.getWidth(); x++) {
            stringBuilder.append(String.format(" %d", x));
        }
        stringBuilder.append("\n");
        // Create tile grid
        WarehouseLayout<Point> layout = state.getLayout();
        for (int y = 0; y < coordinateSystem.getHeight(); y++) {
            stringBuilder.append(String.format(" %d", y));
            for (int x = 0; x < coordinateSystem.getWidth(); x++) {
                stringBuilder.append(' ');

                Tile tile = layout.getTileAt(new Point(x, y));
                Class<? extends Tile> clazz = tile.getClass();
                char symbol = TILE_SYMBOLS.getOrDefault(clazz, UNKNOWN_TILE_SYMBOL);
                stringBuilder.append(symbol);
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
