package warehouse.logistics.assignment;

import warehouse.WarehouseLayout;
import warehouse.inventory.Item;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.tiles.Rack;

/**
 * A basic assignment policy that finds the first Rack that can store the given item.
 */
public class BasicRackAssignmentPolicy implements StorageTileAssignmentPolicy<Rack> {
    @Override
    public Rack assign(WarehouseLayout<?> warehouseLayout, Item item) {
        for(Rack rack : warehouseLayout.findTilesOfType(Rack.class)) {
            if(rack.getStorageUnit().canAddItem(item)) {
                return rack;
            }
        }
        return null;
    }
}
