package warehouse.logistics.assignment;

import warehouse.Warehouse;
import warehouse.inventory.Item;
import warehouse.tiles.ShipDepot;
import warehouse.tiles.StorageTile;

/**
 * A basic assignment policy that finds the first ShipDepot that can store the given item.
 */
public class BasicShipDepotAssignmentPolicy implements StorageTileAssignmentPolicy<ShipDepot> {
    @Override
    public ShipDepot assign(Item item, Warehouse warehouse) {
        for(ShipDepot shipDepot : warehouse.findTilesOfType(ShipDepot.class)) {
            if(shipDepot.getStorageUnit().canAddItem(item)) {
                return shipDepot;
            }
        }
        return null;
    }
}
