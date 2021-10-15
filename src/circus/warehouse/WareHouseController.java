package circus.warehouse;


import circus.inventory.Item;

/**
 * This class will be in charge of the WareHouse as a whole and will be the class that the User interacts with
 * rather than other more basic classes.
 */
public class WareHouseController {

    /**
     * The rack, depot, and layout that the warehouse will be using.
     *
     * We want this to be able to mutate the warehouse state. We want it to be able to add an item to the warehouse,
     * and find a location in the rack that's free/available. Look up items maybe.
     */
    private Layout layout;
    private StorageUnit storageunit;

    /**
     * Constructs an instance of the WareHouseController
     */
    public WareHouseController(Layout l, StorageUnit s)
    {
        this.layout = l;
        this.storageunit = s;
    }

    /**
     * Adds an item into a storage unit in the layout.
     * @param i The item being added.
     * @return True if the item was succesfully added, otherwise, false.
     */
    public boolean addItem(Item i) {
        {
            if (i != null) {
                StorageUnit s = layout.findStorageUnit();
                if (s == null)
                    return false;
                s.addItem(i);
                return true;
            }
            return false;
        }

    }

}
