package warehouse.logistics.optimization.routefinding;

import warehouse.logistics.optimization.graph.Graph;
import warehouse.logistics.optimization.graph.GraphNode;

import java.util.List;

/**
 * The base class for all routefinding algorithms.
 */
public interface Routefinder<T extends GraphNode> {
    /**
     * Finds the optimal route from the given source to destination nodes subject to some
     * constraint functions.
     * @param graph The graph to find routes on.
     * @param source The source node.
     * @param destination The source destination.
     * @return Returns the optimal Route from source to destination subject to constraints, or
     * null if no such Route exists.
     */
    List<T> findRoute(Graph<T> graph, T source, T destination);
}
