package warehouse.logistics.assignment;

import warehouse.WarehouseLayout;
import warehouse.inventory.Item;
import warehouse.tiles.ReceiveDepot;
import warehouse.geometry.WarehouseCoordinate;

/**
 * A basic assignment policy that finds the first ReceiveDepot that can store the given item.
 */
public class BasicReceiveDepotAssignmentPolicy implements StorageTileAssignmentPolicy<ReceiveDepot> {
    @Override
    public ReceiveDepot assign(WarehouseLayout<?> warehouseLayout, Item item) {
        for(ReceiveDepot receiveDepot : warehouseLayout.findTilesOfType(ReceiveDepot.class)) {
            if(receiveDepot.getStorageUnit().canAddItem(item)) {
                return receiveDepot;
            }
        }
        return null;
    }
}
