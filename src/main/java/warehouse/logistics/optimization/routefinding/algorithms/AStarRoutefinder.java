package warehouse.logistics.optimization.routefinding.algorithms;

import warehouse.logistics.optimization.graph.Graph;
import warehouse.logistics.optimization.graph.GraphNode;
import warehouse.logistics.optimization.routefinding.GraphNodeScorer;
import warehouse.logistics.optimization.routefinding.Routefinder;

import java.util.*;

/**
 * A Routefinder that implements the A* algorithm. This finds the route such that the sum of scores across the
 * route is minimized.
 */
public class AStarRoutefinder<T extends GraphNode> extends Routefinder<T> {
    private final GraphNodeScorer<T> nextNodeScorer;
    private final GraphNodeScorer<T> targetScorer;

    /**
     * Construct an AStarRoutefinder.
     * @param graph The graph to perform routefinding on.
     * @param nextNodeScorer The score metric used for computing the score between connected nodes.
     * @param targetScorer The score metric used for computing the score between the source and destination nodes.
     */
    public AStarRoutefinder(Graph<T> graph, GraphNodeScorer<T> nextNodeScorer,
                            GraphNodeScorer<T> targetScorer) {
        super(graph);
        this.nextNodeScorer = nextNodeScorer;
        this.targetScorer = targetScorer;
    }

    /**
     * Find an optimal Route between the given source and destination nodes such that the sum of scores between nodes
     * on the route is minimized.
     * @param source The source node.
     * @param destination The source destination.
     * @return the optimal Route, or null if no such Route could be found.
     */
    @Override
    public List<T> findRoute(T source, T destination) {
        Queue<AStarRouteNode<T>> openSet = new PriorityQueue<>();
        Map<T, AStarRouteNode<T>> allNodes = new HashMap<>();

        double targetCost = targetScorer.computeCost(source, destination);
        AStarRouteNode<T> start = new AStarRouteNode<>(source, null, 0, targetCost);
        openSet.add(start);
        allNodes.put(destination, start);

        String destinationId = destination.getId();
        while (!openSet.isEmpty()) {
            AStarRouteNode<T> next = openSet.poll();
            if (next.getCurrent().getId().equals(destinationId)) {
                // We've reached the target
                // Build the route by backtracking
                return buildRoute(allNodes, next);
            }

            graph.getConnections(next.getCurrent()).forEach(connection -> {
                AStarRouteNode<T> nextNode = allNodes.getOrDefault(connection, new AStarRouteNode<T>(connection));
                allNodes.put(connection, nextNode);

                double newScore = next.getRouteScore() + nextNodeScorer.computeCost(next.getCurrent(), connection);
                if (newScore < nextNode.getRouteScore()) {
                    nextNode.setPrevious(next.getCurrent());
                    nextNode.setRouteScore(newScore);
                    nextNode.setEstimatedScore(newScore + targetScorer.computeCost(connection, destination));
                    openSet.add(nextNode);
                }
            });
        }
        return null;
    }

    /**
     * Build a Route by backtracking from the destination to the start through the node mapping.
     * @param allNodes The node mapping.
     * @param destination The destination node.
     * @return a Route.
     */
    private List<T> buildRoute(Map<T, AStarRouteNode<T>> allNodes, AStarRouteNode<T> destination) {
        List<T> route = new ArrayList<>();
        AStarRouteNode<T> current = destination;
        route.add(0, current.getCurrent());
        current = allNodes.get(current.getPrevious());
        while (current != null) {
            route.add(0, current.getCurrent());
            current = allNodes.get(current.getPrevious());
        }
        return route;
    }
}
