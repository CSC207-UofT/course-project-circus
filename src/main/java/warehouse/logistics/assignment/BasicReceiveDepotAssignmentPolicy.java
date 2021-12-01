package warehouse.logistics.assignment;

import warehouse.Warehouse;
import warehouse.inventory.Item;
import warehouse.tiles.ReceiveDepot;

/**
 * A basic assignment policy that finds the first ReceiveDepot that can store the given item.
 */
public class BasicReceiveDepotAssignmentPolicy implements StorageTileAssignmentPolicy<ReceiveDepot> {
    private final Warehouse warehouse;

    /**
     * Construct a BasicReceiveDepotAssignmentPolicy with the given Warehouse.
     * @param warehouse The warehouse to apply the policy on.
     */
    public BasicReceiveDepotAssignmentPolicy(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public ReceiveDepot assign(Item item) {
        for(ReceiveDepot receiveDepot : warehouse.findTilesOfType(ReceiveDepot.class)) {
            if(receiveDepot.getStorageUnit().canAddItem(item)) {
                return receiveDepot;
            }
        }
        return null;
    }
}
