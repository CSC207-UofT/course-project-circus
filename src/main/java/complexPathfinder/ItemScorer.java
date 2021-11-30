package complexPathfinder;

public class ItemScorer implements Scorer<GraphNode> {
    /**
     * Computes the cost of two items (the distance between two items) using some formula we come up with.
     * @param from The first item being compared
     * @param to The second item being compared
     * @return The cost of the comparison of two items
     */
    @Override
    public double computeCost(GraphNode from, GraphNode to) {
        return 0;
    }
}
