package warehouse.storage;

import org.junit.jupiter.api.Test;
import query.Query;
import warehouse.inventory.Item;
import warehouse.inventory.Part;
import warehouse.storage.containers.InMemoryStorageUnitContainer;
import warehouse.storage.containers.StorageUnitContainer;
import warehouse.storage.strategies.MultiTypeStorageUnitStrategy;
import warehouse.storage.strategies.SingleTypeStorageStrategy;
import warehouse.storage.strategies.StorageUnitStrategy;
import warehouse.tiles.ReceiveDepot;
import warehouse.tiles.ShipDepot;

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

    @Test
    void testReceiveDepot()
    {
        StorageUnitContainer container = new InMemoryStorageUnitContainer();
        StorageUnitStrategy strategy = new SingleTypeStorageStrategy();
        StorageUnit storageUnit = new StorageUnit(2, strategy, container);
        ReceiveDepot receiveDepot = new ReceiveDepot(-1, storageUnit);

        Item item = new Item(new Part("Cucumber", "A vegetable"));

        storageUnit.addItem(item);
        storageUnit.addItem(item);

        assertEquals(storageUnit.getCapacity(), 2);
        assertNotNull(storageUnit.getStrategy());
        assertNotNull(storageUnit.getContainer());
        assertNotNull(storageUnit.getOnItemAddedMessage());
        assertNotNull(storageUnit.getOnItemRemovedMessage());
        assertEquals(-1, receiveDepot.getTile().getIndex());

        Query<Item> query1 = value -> value.getPart().getName().equals("Cucumber");
        assertEquals(item, receiveDepot.distributeItem(query1));
        assertNotNull(receiveDepot.getQueryItems());

        Query<Item> query2 = value -> value.getPart().getName().equals("Artem");
        assertNull(receiveDepot.distributeItem(query2));
        Query<Item> query3 = value -> value.getPart().getName().equals("Cucumber");
        assertNotNull(receiveDepot.distributeItem(query3));
    }

    @Test
    public void testShipDepot()
    {
        StorageUnitContainer container = new InMemoryStorageUnitContainer();
        StorageUnitStrategy strategy = new SingleTypeStorageStrategy();
        StorageUnit storageUnit = new StorageUnit(2, strategy, container);
        Item item = new Item(new Part("Cucumber", "A vegetable"));
        storageUnit.addItem(item);

        ShipDepot shipDepot1 = new ShipDepot(0, 0);
        assertEquals(0, shipDepot1.getTile().getIndex());

        ShipDepot shipDepot2 = new ShipDepot(-1, storageUnit);
        assertTrue(shipDepot2.receiveItem(item));
    }
}
