package warehouse;

import warehouse.inventory.PartCatalogue;

/**
 * Base state for all applications.
 */
public class WarehouseState {
    private final Warehouse warehouse;
    private final PartCatalogue partCatalogue;

    /**
     * Construct a WarehouseState.
     * @param warehouse The warehouse.
     * @param partCatalogue The part catalogue.
     */
    public WarehouseState(Warehouse warehouse, PartCatalogue partCatalogue) {
        this.warehouse = warehouse;
        this.partCatalogue = partCatalogue;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public PartCatalogue getPartCatalogue() {
        return partCatalogue;
    }
}
