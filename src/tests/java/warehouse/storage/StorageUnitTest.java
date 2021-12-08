package warehouse.storage;

import org.junit.jupiter.api.Test;
import query.Query;
import warehouse.inventory.Item;
import warehouse.inventory.Part;
import warehouse.storage.StorageUnit;
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
        ReceiveDepot rd1 = new ReceiveDepot(0, 0);
        ReceiveDepot rd2 = new ReceiveDepot(1, 1, 5);
        ReceiveDepot rd3 = new ReceiveDepot(1, 1, storageUnit);

        Item item1 = new Item(new Part("Cucumber", "A vegetable"));
        Item item2 = new Item(new Part("Banana", "A fruit"));

        storageUnit.addItem(item1);
        storageUnit.addItem(item1);

        assert(storageUnit.getCapacity()>0);
        assertNotNull(storageUnit.getStrategy());
        assertNotNull(storageUnit.getContainer());
        assertNotNull(storageUnit.getOnItemAddedMessage());
        assertNotNull(storageUnit.getOnItemRemovedMessage());
        assertEquals(1, rd3.getTile().getX());
        assertEquals(1, rd3.getTile().getY());

        Query<Item> q1 = new Query<Item>() {
            @Override
            public boolean satisfies(Item value) {
                return value.getPart().getName().equals("Cucumber");
            }
        };

        assertEquals(item1, rd3.distributeItem(q1));
        assertNotNull(rd3.getQueryItems());

        Query<Item> q2 = new Query<Item>() {
            @Override
            public boolean satisfies(Item value) {
                return value.getPart().getName().equals("Artem");
            }
        };
        assertNull(rd3.distributeItem(q2));

    }


    @Test
    public void testShipDepot()
    {
        StorageUnitContainer container = new InMemoryStorageUnitContainer();
        StorageUnitStrategy strategy = new SingleTypeStorageStrategy();
        StorageUnit storageUnit = new StorageUnit(2, strategy, container);
        Item item1 = new Item(new Part("Cucumber", "A vegetable"));
        Item item2 = new Item(new Part("Banana", "A fruit"));

        storageUnit.addItem(item1);

        ShipDepot sd1 = new ShipDepot(0, 0);
        ShipDepot sd2 = new ShipDepot(1, 2, 5);
        ShipDepot sd3 = new ShipDepot(1, 1, storageUnit);
        assertTrue(sd3.receiveItem(item1));
        assertEquals(0, sd1.getTile().getX());
        assertEquals(0, sd1.getTile().getY());
        assertEquals("messaging.Message@", sd3.getOnItemReceivedMessage().
                toString().substring(0,18));
    }
}
