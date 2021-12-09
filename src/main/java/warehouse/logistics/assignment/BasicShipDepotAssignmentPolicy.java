package warehouse.logistics.assignment;

import warehouse.WarehouseLayout;
import warehouse.inventory.Item;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.tiles.ShipDepot;

/**
 * A basic assignment policy that finds the first ShipDepot that can store the given item.
 */
public class BasicShipDepotAssignmentPolicy implements StorageTileAssignmentPolicy<ShipDepot> {
    @Override
    public ShipDepot assign(WarehouseLayout<?> warehouseLayout, Item item) {
        for(ShipDepot shipDepot : warehouseLayout.findTilesOfType(ShipDepot.class)) {
            if(shipDepot.getStorageUnit().canAddItem(item)) {
                return shipDepot;
            }
        }
        return null;
    }
}
