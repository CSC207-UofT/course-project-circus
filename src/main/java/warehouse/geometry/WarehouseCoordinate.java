package warehouse.geometry;

/**
 * A coordinate in the Warehouse. Used to represent the position of an object in the warehouse.
 */
public interface WarehouseCoordinate {
    boolean equals(Object o);
    int hashCode();
    String toString();
}
