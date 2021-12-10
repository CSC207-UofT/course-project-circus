package warehouse.logistics.optimization.routefinding;

import warehouse.logistics.optimization.graph.GraphNode;

/**
 * A score metric for nodes in a graph.
 */
public interface GraphNodeScorer<T extends GraphNode> {
    /**
     * Compute the cost between the given nodes.
     */
    double computeCost(T from, T to);
}
