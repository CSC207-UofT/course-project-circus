package warehouse.logistics.assignment;

import warehouse.Warehouse;
import warehouse.inventory.Item;
import warehouse.tiles.Rack;

/**
 * A basic assignment policy that finds the first Rack that can store the given item.
 */
public class BasicRackAssignmentPolicy implements StorageTileAssignmentPolicy<Rack> {
    @Override
    public Rack assign(Item item, Warehouse warehouse) {
        for(Rack rack : warehouse.findTilesOfType(Rack.class)) {
            if(rack.getStorageUnit().canAddItem(item)) {
                return rack;
            }
        }
        return null;
    }
}
