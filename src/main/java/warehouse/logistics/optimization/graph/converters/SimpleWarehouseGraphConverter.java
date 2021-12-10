package warehouse.logistics.optimization.graph.converters;

import warehouse.WarehouseState;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.tiles.Tile;

/**
 * Default WarehouseGraphConverter that adds Tiles to the Graph if they are empty.
 */
public class SimpleWarehouseGraphConverter<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate>
        extends WarehouseGraphConverter<T, U> {

    @Override
    protected boolean canAddTile(WarehouseState<T, U> warehouseState, Tile tile) {
        return true;
    }
}
