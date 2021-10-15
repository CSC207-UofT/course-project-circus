package Tests; 

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


import circus.warehouse.Rack;
import org.junit.jupiter.api.Test;

public class RackTest {
    @Test
    public void testCapacity()
    {
        Rack r = new Rack();
        assertEquals(r.getCapacity(), -1);
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
    public void addItemBasics()
    {
        Rack r = new Rack(10);

    }
    @Test
    public void testCapacity()
    {
        Rack r = new Rack();
        assertEquals(r.getCapacity(), -1);
    }
}
