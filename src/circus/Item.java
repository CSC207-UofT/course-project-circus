package circus;

import java.util.UUID;

/**
 * An item in the Inventory.
 */
public class Item {
    private final UUID id;
    private String name;
    private String description;

    /**
     * Construct an Item with a name and description.
     * @param name The name of this Item.
     * @param description The description of this Item.
     * @param id The UUID of this item.
     */
    public Item(String name, String description, UUID id) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Construct an Item with a name, description, and random id.
     * @param name The name of this Item.
     * @param description The description of this Item.
     */
    public Item(String name, String description) {
        this(name, description, UUID.randomUUID());
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Getter method for id.
     * @return id private variable
     */
    public UUID getId() {
        return id;
    }
}
