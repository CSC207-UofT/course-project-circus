package warehouse.inventory;

import org.junit.jupiter.api.Test;
import warehouse.inventory.Item;
import warehouse.inventory.Part;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    /**
     * Testing getId with all parameters specified
     */
    @Test
    void getId(){
        Part part = new Part("1", "Empty", "An Empty Part");
        Item item = new Item(part.getId(), part);
        assertEquals("1", item.getId());
    }

    /**
     * Testing getId with a random id.
     */
    @Test
    void getIdRandomized(){
        Part part = new Part("Empty", "An Empty Part");
        Item item = new Item(part);
        String id = item.getId();
        assertEquals(id, item.getId());
    }

    /**
     * Testing getPart with all parameters specified
     */
    @Test
    void getPart() {
        Part part = new Part("1", "Empty", "An Empty Part");
        Item item = new Item(part.getId(), part);
        assertEquals(part, item.getPart());
    }

    /**
     * Testing toSting method after instantiation
     */
    @Test
    void testToString() {
        Part part = new Part("1", "Empty", "An Empty Part");
        Item item = new Item(part.getId(), part);
        assertEquals("Part{id=1, name='Empty', description='An Empty Part'}", part.toString());

    }
}
