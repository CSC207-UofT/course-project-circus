package main.java.pathfinding;
import main.java.inventory.Item;

import java.util.*;

/**
 * Abstract class for pathfinders that discovers the "optimal" path for packages.
 *
 * Classes that extend this one will have to implement the methods we choose here.
 */
public abstract class Pathfinder<T extends Item> {

    private final Graph<T> graph;
    private final Scorer<T> nextNodeScorer;
    private final Scorer<T> targetScorer;

    /**
     * Creates an instance of the Pathfinder class. Not sure if it's in use yet, but it's set up just in case.
     * @param g
     * @param next
     * @param target
     */
    public Pathfinder(Graph<T> g, Scorer<T> next, Scorer<T> target)
    {
        graph = g;
        nextNodeScorer = next;
        targetScorer = target;
    }

    /**
     * Returns the optimal path for packages. This method will differ based on the type of pathifnder we use.
     *
     * This is where the A* algorithm comes into play. We have the graph that we are finding the routes across, and
     * our two scorers – one for the exact score for the next node, and one for the estimated score to our destination.
     * We've also got a method that will take a start and end node and compute the best route between the two.
     *
     * The use of a PriorityQueue for the open set means that we automatically get the best entry off of it, based on
     * our compareTo() method from earlier.
     * Then we iterate until either we run out of nodes to look at, or the best available node is our destination.
     * When we've found our destination, we can build our route by repeatedly looking at the previous node until we
     * reach our starting point.
     *
     * If the algorithm doesn't reach its destination, we iterate over the connected nodes from our graph. For each of
     * these, we get the RouteNode that we have for it – creating a new one if needed. Then, we compute the new score
     * for this node and see if it's cheaper than what we had so far. If it is then we update it to match this new route
     * and add it to the open set for consideration next time around.
     *
     * @return an array of strings representing the order of whcih packages it visits.
     */
    public List<T> findPath(T from, T to)
    {
        Queue<RouteNode> openSet = new PriorityQueue<>();
        Map<T, RouteNode<T>> allNodes = new HashMap<>();

        RouteNode<T> start = new RouteNode<>(from, null, 0d, targetScorer.computeCost(from, to));
        openSet.add(start);
        allNodes.put(from, start);

        while (!openSet.isEmpty()) {
            RouteNode<T> next = openSet.poll();
            if (next.getCurrent().equals(to)) {
                List<T> route = new ArrayList<>();
                RouteNode<T> current = next;
                do {
                    route.add(0, current.getCurrent());
                    current = allNodes.get(current.getPrevious());
                } while (current != null);
                return route;
            }
            graph.getConnections(next.getCurrent()).forEach(connection -> {
                RouteNode<T> nextNode = allNodes.getOrDefault(connection, new RouteNode<>(connection));
                allNodes.put(connection, nextNode);

                double newScore = next.getRouteScore() + nextNodeScorer.computeCost(next.getCurrent(), connection);
                if (newScore < nextNode.getRouteScore()) {
                    nextNode.setPrevious(next.getCurrent());
                    nextNode.setRouteScore(newScore);
                    nextNode.setEstimatedScore(newScore + targetScorer.computeCost(connection, to));
                    openSet.add(nextNode);
                }
            });
            throw new IllegalStateException("No route found");
        }
        return new ArrayList<>();
    }

}
