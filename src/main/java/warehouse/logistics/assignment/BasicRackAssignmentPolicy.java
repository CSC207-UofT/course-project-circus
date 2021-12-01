package warehouse.logistics.assignment;

import warehouse.Warehouse;
import warehouse.inventory.Item;
import warehouse.tiles.Rack;

/**
 * A basic assignment policy that finds the first Rack that can store the given item.
 */
public class BasicRackAssignmentPolicy implements StorageTileAssignmentPolicy<Rack> {
    private final Warehouse warehouse;

    /**
     * Construct a BasicRackAssignmentPolicy with the given Warehouse.
     * @param warehouse The warehouse to apply the policy on.
     */
    public BasicRackAssignmentPolicy(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public Rack assign(Item item) {
        for(Rack rack : warehouse.findTilesOfType(Rack.class)) {
            if(rack.getStorageUnit().canAddItem(item)) {
                return rack;
            }
        }
        return null;
    }
}
