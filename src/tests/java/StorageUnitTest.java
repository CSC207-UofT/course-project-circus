
import org.junit.jupiter.api.Test;
import warehouse.inventory.Item;
import warehouse.inventory.Part;
import warehouse.storage.StorageUnit;
import warehouse.storage.containers.InMemoryStorageUnitContainer;
import warehouse.storage.containers.StorageUnitContainer;
import warehouse.storage.strategies.MultiTypeStorageUnitStrategy;
import warehouse.storage.strategies.SingleTypeStorageStrategy;
import warehouse.storage.strategies.StorageUnitStrategy;

import static org.junit.jupiter.api.Assertions.*;

public class StorageUnitTest {
    /**
     * Test for single-type storage unit strategy.
     */
    @Test
    void addItemSingleTypeTest() {
        StorageUnitContainer container = new InMemoryStorageUnitContainer();
        StorageUnitStrategy strategy = new SingleTypeStorageStrategy();
        StorageUnit storageUnit = new StorageUnit(2, strategy, container);
        Item item1 = new Item(new Part("Cucumber", "A vegetable"));
        Item item2 = new Item(new Part("Banana", "A fruit"));
        assertTrue(storageUnit.addItem(item1));
        assertFalse(storageUnit.addItem(item2));
        assertTrue(storageUnit.addItem(item1));
        assertFalse(storageUnit.addItem(item1));
    }

    /**
     * Test for single-type storage unit strategy.
     */
    @Test
    void addItemMultiTypeTest() {
        StorageUnitContainer container = new InMemoryStorageUnitContainer();
        StorageUnitStrategy strategy = new MultiTypeStorageUnitStrategy();
        StorageUnit storageUnit = new StorageUnit(2, strategy, container);
        Item item1 = new Item(new Part("Cucumber", "A vegetable"));
        Item item2 = new Item(new Part("Banana", "A fruit"));
        assertTrue(storageUnit.addItem(item1));
        assertTrue(storageUnit.addItem(item2));
        assertFalse(storageUnit.addItem(item1));
    }

    @Test
    void removeItemTest() {
        StorageUnitContainer container = new InMemoryStorageUnitContainer();
        StorageUnitStrategy strategy = new SingleTypeStorageStrategy();
        StorageUnit storageUnit = new StorageUnit(2, strategy, container);
        Item item1 = new Item(new Part("Cucumber", "A vegetable"));
        Item item2 = new Item(new Part("Banana", "A fruit"));
        storageUnit.addItem(item1);
        assertTrue(storageUnit.removeItem(item1));
        assertFalse(storageUnit.removeItem(item2));
    }

    @Test
    void canAddItemTest() {
        StorageUnitContainer container = new InMemoryStorageUnitContainer();
        StorageUnitStrategy strategy = new SingleTypeStorageStrategy();
        StorageUnit storageUnit = new StorageUnit(2, strategy, container);
        Item item1 = new Item(new Part("Cucumber", "A vegetable"));
        Item item2 = new Item(new Part("Banana", "A fruit"));
        assertTrue(storageUnit.canAddItem(item1));
        storageUnit.addItem(item1);
        assertFalse(storageUnit.canAddItem(item2));
        storageUnit.addItem(item1);
        assertFalse(storageUnit.canAddItem(item1));
    }
}
