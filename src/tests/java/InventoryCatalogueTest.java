import inventory.InventoryCatalogue;
import inventory.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryCatalogueTest {
    InventoryCatalogue inventoryc1;

    @Test
    public void testAddItem() {
        InventoryCatalogue inventoryc1 = new InventoryCatalogue();
        Item item1 = new Item("Cucumber", "A vegetable");
        assertTrue(inventoryc1.addItem(item1));
        assertFalse(inventoryc1.addItem(item1));
    }

    @Test
    public void testGetItemById() {
        InventoryCatalogue inventoryc1 = new InventoryCatalogue();
        Item item1 = new Item("Cucumber", "A vegetable");
        inventoryc1.addItem(item1);
        assertEquals(item1, inventoryc1.getItemById(item1.getId()));
    }
}
