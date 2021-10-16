package inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A catalogue of Items in the Warehouse.
 */
public class InventoryCatalogue {
    private final Map<String, Item> items;

    /**
     * Construct an empty InventoryCatalogue.
     */
    public InventoryCatalogue() {
        items = new HashMap<>();
    }

    /**
     * Add an Item to the catalogue. The Item is added if and only if there does not exist an Item in this
     * InventoryCatalogue with the same id as the Item to add.
     * @param item The Item to add.
     * @return True if the Item could be added, and False otherwise.
     */
    public boolean addItem(Item item) {
        if (items.containsKey(item.getId())) {
            return false;
        } else {
            items.put(item.getId(), item);
            return true;
        }
    }

    /**
     * Get the Items in this InventoryCatalogue.
     * @return a List of Items.
     */
    public List<Item> getItems() {
        return items.values().stream().toList();
    }

    /**
     * Get the Item with the given id.
     * @param id The id of the Item.
     * @return the Item with the given id, or null if no such Item exists.
     */
    public Item getItemById(String id) {
        return items.getOrDefault(id, null);
    }
}
