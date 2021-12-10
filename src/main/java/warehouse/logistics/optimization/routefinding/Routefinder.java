package warehouse.logistics.optimization.routefinding;

import warehouse.logistics.optimization.graph.Graph;
import warehouse.logistics.optimization.graph.GraphNode;

import java.util.List;

/**
 * The base class for all routefinding algorithms.
 */
public abstract class Routefinder<T extends GraphNode> {
    protected final Graph<T> graph;

    /**
     * Construct a Routefinder operating on the given graph.
     * @param graph The graph to find routes on.
     */
    public Routefinder(Graph<T> graph) {
        this.graph = graph;
    }

    /**
     * Finds the optimal route from the given source to destination nodes subject to some
     * constraint functions.
     * @param source The source node.
     * @param destination The source destination.
     * @return Returns the optimal Route from source to destination subject to constraints, or
     * null if no such Route exists.
     */
    public abstract List<T> findRoute(T source, T destination);
}
