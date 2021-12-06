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
        assertEquals("Electronic", part.getName());
    }

    @Test
    void getDescription() {
        Part part = new Part("1", "Electronic", "A Part that fills Electronics");
        assertEquals("A Part that fills Electronics", part.getDescription());
    }

    @Test
    void setName() {
        Part partRandomId = new Part("Null", "To be filled with Phones");
        partRandomId.setName("Phones");
        assertEquals("Phones", partRandomId.getName());
    }

    @Test
    void setDescription() {
        Part part = new Part("1", "Electronic", "Unknown");
        part.setDescription("A Part that fills Electronics");
        assertEquals("A Part that fills Electronics", part.getDescription());
    }

    @Test
    void testToString() {
        Part part = new Part("1", "Electronic", "A Part that fills Electronics");
        assertEquals("Part{id=1, name='Electronic', description='A Part that fills Electronics'}", part.toString());
    }

    @Test
    void getId() {
        Part part = new Part("1", "Electronic", "A Part that fills Electronics");
        assertEquals("1", part.getId());
    }
}
