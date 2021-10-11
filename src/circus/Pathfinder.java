package circus;
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
