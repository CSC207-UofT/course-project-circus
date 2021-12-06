package warehouse.inventory;

import org.junit.jupiter.api.Test;
import warehouse.inventory.Part;

import static org.junit.jupiter.api.Assertions.*;

public class PartTest {

    /**
     * Testing getName for the name of the Part
     */
    @Test
    void getName(){
        Part part = new Part("1", "Electronic", "A Part that fills Electronics");
        Part partRandomId = new Part("Phones", "To be filled with Phones");
        assertEquals("Electronic", part.getName());
        assertEquals("Phones", partRandomId.getName());
    }

    @Test
    void getDescription() {
        Part part = new Part("1", "Electronic", "A Part that fills Electronics");
        Part partRandomId = new Part("Phones", "To be filled with Phones");
        assertEquals("A Part that fills Electronics", part.getDescription());
        assertEquals("To be filled with Phones", partRandomId.getDescription());

    }

    @Test
    void setName() {
        Part part = new Part("1", "Unknown", "A Part that fills Electronics");
        Part partRandomId = new Part("Null", "To be filled with Phones");
        part.setName("Electronic");
        partRandomId.setName("Phones");
        assertEquals("Electronic", part.getName());
        assertEquals("Phones", partRandomId.getName());
    }

    @Test
    void setDescription() {
        Part part = new Part("1", "Electronic", "Unknown");
        Part partRandomId = new Part("Phone", "Empty");
        part.setDescription("A Part that fills Electronics");
        partRandomId.setDescription("To be filled with Phones");
        assertEquals("A Part that fills Electronics", part.getDescription());
        assertEquals("To be filled with Phones", partRandomId.getDescription());
    }

    @Test
    void testToString() {
        Part part = new Part("1", "Electronic", "A Part that fills Electronics");
        Part partRandomId = new Part("Phones", "To be filled with Phones");
        String randomId = partRandomId.getId();
        assertEquals("Part{id=1, name='Electronic', description='A Part that fills Electronics'}", part.toString());
        assertEquals("Part{id=" + randomId + ", name='Phones', description='To be filled with Phones'}", partRandomId.toString());
    }

    @Test
    void getId() {
        Part part = new Part("1", "Electronic", "A Part that fills Electronics");
        Part partRandomId = new Part("Phones", "To be filled with Phones");
        String randomId = partRandomId.getId();
        assertEquals("1", part.getId());
        assertEquals(randomId, partRandomId.getId());
    }
}
