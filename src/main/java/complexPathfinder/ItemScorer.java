package complexPathfinder;

import inventory.Item;

public class ItemScorer implements Scorer<Comparable> {

    /**
     * Computes the cost of two items (the distance between two items) using some formula we come up with.
     * @param from The first item being compared
     * @param to The second item being compared
     * @return The cost of the comparison of two items
     */
    @Override
    public double computeCost(Comparable from, Comparable to) {
        return 0;
    }
}
