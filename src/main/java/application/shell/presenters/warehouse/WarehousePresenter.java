package application.shell.presenters.warehouse;

import warehouse.WarehouseState;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;

/**
 * Presents a Warehouse with a given coordinate system into a format suitable for the shell application.
 */
public interface WarehousePresenter<T extends WarehouseCoordinateSystem<U>,
        U extends WarehouseCoordinate> {
    String convert(WarehouseState<T, U> state);
}
