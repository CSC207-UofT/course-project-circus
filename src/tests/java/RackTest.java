package tests.java;


import inventory.Item;
import warehouse.Rack;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RackTest {
    @Test
    public void testCapacity()
    {
        Rack r = new Rack();
        assertEquals(-1, r.getCapacity());
    }

    @Test
    public void testInfiniteCapacity()
    {
        Rack r = new Rack();
        Rack r2 = new Rack(100);

        assertTrue(r.hasInfiniteCapacity());
        assertFalse(r2.hasInfiniteCapacity());
    }
    @Test
    public void addItemBasicTests()
    {
        Rack r = new Rack(3);
        Item i = new Item("Artem", "Smart student whose first language is Python.");
        assertTrue(r.addItem(i));
        assertEquals(1, r.getSize());
        assertTrue(r.addItem(i));
        assertEquals(2, r.getSize());
        Item k = new Item("Shon Verch", "A professional agricultural economist.");
        assertFalse(r.addItem(k));
        assertTrue(r.addItem(i));
        assertFalse(r.addItem(i));
    }
    @Test
    public void removeItemBasicTests()
    {
        Rack r = new Rack(3);
        Item i = new Item("Leon", "Polish \"left wing\" nationalist.");
        r.addItem(i);
        r.addItem(i);
        r.addItem(i);
        assertEquals(3, r.getSize());
        assertTrue(r.removeItem(i));
        assertEquals(2, r.getSize());
        assertEquals(3, r.getCapacity());
    }

    @Test
    public void getQueryItemsBasicTests()
    {
        Rack r = new Rack(3);
        Item i = new Item("Mr. Martin", "Best TA from STA130");
        r.addItem(i);
        List<Item> l = new ArrayList<>();
        l.add(i);
        assertEquals(l, r.getQueryItems());
        Rack r2 = new Rack();
        assertNotEquals(l, r2.getQueryItems());
    }
}
