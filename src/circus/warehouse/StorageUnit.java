package circus.warehouse;

import circus.inventory.Item;
import circus.query.Query;
import circus.Distributable;
import circus.Receivable;

import java.util.List;

/**
 * A unit in the warehouse that can store items.
 */
public abstract class StorageUnit extends Tile implements Receivable, Distributable {
    /**
     * Construct a WarehouseCell with the given position.
     *
     * @param x The horizontal position of the cell.
     * @param y The vertical position of the cell.
     */
    public StorageUnit(int x, int y) {
        super(x, y);
    }

    /**
     * Add an item to this StorageUnit.
     * @param item The item to add.
     * @return True if the Item could be added, False otherwise.
     */
    public abstract boolean addItem (Item item);
    /**
     * Remove the given Item from this StorageUnit.
     * @param item The item to remove.
     * @return True if the Item could be removed, False otherwise.
     */
    public abstract boolean removeItem(Item item);

    @Override
    public boolean receiveItem(Item item) {
        return addItem(item);
    }

    @Override
    public Item distributeItem(Query<Item> itemQuery) {
        List<Item> queryResult = query(itemQuery);
        if (queryResult.size() == 0) {
            // Throw exception
            return null;
        }
        // Try removing the item
        Item item = queryResult.get(0);
        if (!removeItem(item)) {
            return null;
        }
        // Copy before returning
        return new Item(item);
    }
}
