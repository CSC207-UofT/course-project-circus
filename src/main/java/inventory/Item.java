package inventory;

import java.util.Objects;
import java.util.UUID;

/**
 * An item in the Inventory.
 */
public class Item {
    private final String id;
    private String name;
    private String description;

    /**
     * Construct an Item with a name and description.
     * @param builder an instance of the ItemBuilder class to implement the Builder java design pattern
     */
    public Item(ItemBuilder builder)
    {
        this.id = builder.getId();
        this.name = builder.getName();
        this.description = builder.getDescription();
    }

    /**
     * Copy constructor for the Item class.
     * @param item The item to copy.
     */
    public Item(Item item) {
        id = item.id;
        name = item.name;
        description = item.description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) && Objects.equals(name, item.name) &&
                Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    /**
     * Getter method for id.
     * @return id private variable
     */
    public String getId() {
        return id;
    }
}
