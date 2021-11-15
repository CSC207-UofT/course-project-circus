package inventory;

import java.util.UUID;

public class ItemBuilder {
    private final String id;
    private String name;
    private String description;

    public ItemBuilder(String id, String name, String description)
    {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Construct an Item with a name, description, and random id.
     * @param name The name of this Item.
     * @param description The description of this Item.
     */
    public ItemBuilder(String name, String description)
    {
        this(UUID.randomUUID().toString(), name, description);
    }

    public Item build()
    {
        Item i = new Item(this);
        validateItemObject(i);
        return i;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private void validateItemObject(Item i)
    {
        System.out.println("Validating item");
    }

}
