package warehouse.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A catalogue of Parts.
 */
public class PartCatalogue {
    private final Map<String, Part> parts;

    /**
     * Construct an empty PartCatalogue.
     */
    public PartCatalogue() {
        parts = new HashMap<>();
    }

    /**
     * Add a Part to the catalogue. The Part is added if and only if there does not exist a Part in this
     * PartCatalogue with the same id as the Part to add.
     * @param part The Part to add.
     * @return True if the Part could be added, and False otherwise.
     */
    public boolean addPart(Part part) {
        if (parts.containsKey(part.getId())) {
            return false;
        } else {
            parts.put(part.getId(), part);
            return true;
        }
    }

    /**
     * Get the Items in this PartCatalogue.
     * @return a List of Parts.
     */
    public List<Part> getParts() {
        return new ArrayList<>(parts.values());
    }

    /**
     * Get the Part with the given id.
     * @param id The id of the Part.
     * @return the Part with the given id, or null if no such Part exists.
     */
    public Part getPartById(String id) {
        return parts.getOrDefault(id, null);
    }

    /**
     * Remove the Part with the given id.
     */
    public void removePartById(String id) {
        parts.remove(id);
    }
}