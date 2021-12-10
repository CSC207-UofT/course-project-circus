package warehouse.logistics.optimization.graph;

/**
 * A node in the graph.
 */
public interface GraphNode {
    /**
     * Get the id of this node.
     */
    String getId();

    /**
     * Get the multiplier on the score metric. Lower is better.
     * @return The multiplier
     */
    default double getScoreMultiplier() {
        return 1;
    }
}
