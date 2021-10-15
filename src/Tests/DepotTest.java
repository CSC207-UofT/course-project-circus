package Tests;

import circus.inventory.Item;
import circus.warehouse.Depot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DepotTest {
    Depot d1;
    Depot d2;
    Depot d3;

    @Test
    public void testAddItem() {
        d1 = new Depot(5,1,2);
        d2 = new  Depot(0,3,7);
        Item item1 = new Item("Mango", "A delectable fruit");
        assertTrue(d1.addItem(item1));
        assertFalse(d2.addItem(item1));
    }

    @Test
    public void testGetCapacity(){
        d1 = new Depot(5,5,6);
        d2 = new  Depot(0,6,7);
        assertEquals(5,d1.getCapacity());
        assertEquals(0, d2.getCapacity());
    }

    @Test
    public void testHasInfiniteCapacity(){
        d2 = new  Depot(0,5,6);
        d3 = new Depot(-1,6,7);
        assertTrue(d3.hasInfiniteCapacity());
        assertFalse(d2.hasInfiniteCapacity());
    }

    @Test
    public void testGetSize (){
        Item item1 = new Item("Mango", "A delectable fruit");
        Item item2 = new Item("Crack Cocaine", "A children's candy");
        d1 = new Depot(5,6,7);
        d1.addItem(item1);
        d1.addItem(item2);
        assertEquals(2, d1.getSize());
    }

    @Test
    public void testRemoveItem(){
        Item item1 = new Item("Mango", "A delectable fruit");
        Item item2 = new Item("Crack Cocaine", "A children's candy");
        d1 = new Depot(5,6,7);
        d1.addItem(item1);
        d1.addItem(item2);
        d2 = new Depot(5,3,5);
        assertTrue(d1.removeItem(item1));
        assertFalse(d2.removeItem(item2));
    }


}