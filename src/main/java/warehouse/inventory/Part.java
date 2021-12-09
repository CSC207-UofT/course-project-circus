package warehouse.inventory;

import utils.RandomUtils;

import java.util.Objects;

/**
 * A representation of an abstract object, i.e. the 'concept' of an actual entity.
 * An actual instance of a Part is an Item, which is a separate entity.
 */
public class Part {
    private final String id;
    private String name;
    private String description;

    /**
     * Construct a Part with a name and description.
     * @param id The id of this Part.
     * @param name The name of this Part.
     * @param description The description of this Part.
     */
    public Part(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Construct a Part with a name, description, and random id.
     * @param name The name of this Part.
     * @param description The description of this Part.
     */
    public Part(String name, String description) {
        this(RandomUtils.randomId(), name, description);
    }

    /**
     * Copy constructor for the Part class.
     * @param part The part to copy.
     */
    public Part(Part part) {
        id = part.id;
        name = part.name;
        description = part.description;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return Objects.equals(id, part.id) && Objects.equals(name, part.name) && Objects.equals(description, part.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }
}
