package warehouse.logistics.assignment;

import warehouse.Warehouse;
import warehouse.inventory.Item;
import warehouse.tiles.ShipDepot;

/**
 * A basic assignment policy that finds the first ShipDepot that can store the given item.
 */
public class BasicShipDepotAssignmentPolicy implements StorageTileAssignmentPolicy<ShipDepot> {
    private final Warehouse warehouse;

    /**
     * Construct a BasicShipDepotAssignmentPolicy with the given Warehouse.
     * @param warehouse The warehouse to apply the policy on.
     */
    public BasicShipDepotAssignmentPolicy(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public ShipDepot assign(Item item) {
        for(ShipDepot shipDepot : warehouse.findTilesOfType(ShipDepot.class)) {
            if(shipDepot.getStorageUnit().canAddItem(item)) {
                return shipDepot;
            }
        }
        return null;
    }
}
