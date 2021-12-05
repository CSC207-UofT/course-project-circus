import org.junit.jupiter.api.Test;
import warehouse.inventory.PartCatalogue;
import warehouse.inventory.Part;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PartCatalogueTest {

    PartCatalogue pc;

    /**
     * Testing addPart() with no duplicate id
     */
    @Test
    void testAddPart(){
        pc = new PartCatalogue();
        Part part = new Part("649", "Tickets", "Lottery tickets");
        assertTrue(pc.addPart(part));
    }

    /**
     * Testing addPart() when id already exists
     */
    @Test
    void testAddPartWithDuplicate(){
        pc = new PartCatalogue();
        Part part = new Part("649", "Tickets", "Lottery tickets");
        Part part2 = new Part("649", "Tickets", "Lottery tickets duplicate");
        pc.addPart(part);
        assertFalse(pc.addPart(part));
        assertFalse(pc.addPart(part2));
    }

    /**
     * Testing getParts -- should return an ArrayList of all parts in that PartCatalogue hashmap
     */
    @Test
    void getParts() {
        pc = new PartCatalogue();
        List<Part> parts = new ArrayList<>();
        Part part = new Part("649", "Tickets", "Lottery tickets");
        Part part2 = new Part("123", "Cards", "Playing cards");
        pc.addPart(part);
        pc.addPart(part2);
        parts.add(part2);
        parts.add(part);
        assertEquals(parts, pc.getParts());
    }

    /**
     * Testing getPartById -- retrieving Part by its ID
     */
    @Test
    void getPartById() {
        pc = new PartCatalogue();
        Part part = new Part("649", "Tickets", "Lottery tickets");
        Part part2 = new Part("123", "Cards", "Playing cards");
        pc.addPart(part);
        pc.addPart(part2);
        assertEquals(pc.getPartById("649"), part);
        assertEquals(pc.getPartById("123"), part2);

    }
}
