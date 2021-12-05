import warehouse.inventory.Item;
import org.junit.jupiter.api.Test;
import warehouse.inventory.Part;
import warehouse.storage.Rack;

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
        Part p = new Part("Artem", "Smart student whose first language is Python.");
        Item i = new Item(p);
        assertTrue(r.addItem(i));
        assertEquals(1, r.getSize());
        assertTrue(r.addItem(i));
        assertEquals(2, r.getSize());
        Part p2 = new Part("Shon Verch", "A professional agricultural economist.");
        Item k = new Item(p2);
        assertFalse(r.addItem(k));
        assertTrue(r.addItem(i));
        assertFalse(r.addItem(i));
    }
    @Test
    public void removeItemBasicTests()
    {
        Rack r = new Rack(3);
        Part p = new Part("Leon", "Polish \"left wing\" nationalist.");
        Item i = new Item(p);
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
        Part p = new Part("Mr. Martin", "Best TA from STA130");
        Item i = new Item(p);
        r.addItem(i);
        List<Item> l = new ArrayList<>();
        l.add(i);
        assertEquals(l, r.getQueryItems());
        Rack r2 = new Rack();
        assertNotEquals(l, r2.getQueryItems());
    }
}
