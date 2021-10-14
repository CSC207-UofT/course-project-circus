package circus.pathfinding;

import circus.inventory.Item;

public class ItemScorer implements Scorer<Item> {

    /**
     * Computes the cost of two items (the distance between two items) using some formula we come up with.
     * @param from The first item
     * @param to The second item
     * @return The cost of two items
     */
    @Override
    public double computeCost(Item from, Item to) {
        return 0;
    }
}
