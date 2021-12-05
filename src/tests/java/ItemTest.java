import org.junit.jupiter.api.Test;
import warehouse.inventory.Item;
import warehouse.inventory.Part;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    /**
     * Testing getId with all parameters specified
     */
    @Test
    public void getIdTest(){
        Part part = new Part("1", "Empty", "An Empty Part");
        Item item = new Item(part.getId(), part);
        assertEquals(item.getId().toString(), "1");
    }



}
