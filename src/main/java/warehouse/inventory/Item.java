package warehouse.inventory;

import java.util.UUID;

/**
 * A physical instance of a Part.
 */
public class Item {
    private final String id;
    private final Part part;

    /**
     * Construct an Item with a randomly generated id.
     * @param part The master abstract Part that this Item is an instance of.
     */
    public Item(Part part) {
        this(UUID.randomUUID().toString(), part);
    }

    /**
     * Construct an Item.
     * @param id The unique-id of this Item (e.g. a barcode or serial number).
     * @param part The master abstract Part that this Item is an instance of.
     */
    public Item(String id, Part part) {
        this.id = id;
        this.part = part;
    }

    public String getId() {
        return id;
    }

    public Part getPart() {
        return part;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", part=" + part +
                '}';
    }
}
