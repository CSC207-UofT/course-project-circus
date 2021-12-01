package warehouse.logistics.assignment;

import warehouse.Warehouse;
import warehouse.inventory.Item;
import warehouse.tiles.ReceiveDepot;

/**
 * A basic assignment policy that finds the first ReceiveDepot that can store the given item.
 */
public class BasicReceiveDepotAssignmentPolicy implements StorageTileAssignmentPolicy<ReceiveDepot> {
    @Override
    public ReceiveDepot assign(Item item, Warehouse warehouse) {
        for(ReceiveDepot receiveDepot : warehouse.findTilesOfType(ReceiveDepot.class)) {
            if(receiveDepot.getStorageUnit().canAddItem(item)) {
                return receiveDepot;
            }
        }
        return null;
    }
}
