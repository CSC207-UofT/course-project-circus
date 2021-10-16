package main.java.pathfinding;

import main.java.inventory.Item;

public class RouteNode <T extends Item> implements Comparable<RouteNode<T>> {
    private final T current;
    private T previous;
    private double routeScore;
    private double estimatedScore;

    public RouteNode(T current) {
        this.current = current;
        this.previous = null;
        this.routeScore = Double.POSITIVE_INFINITY;
        this.estimatedScore = Double.POSITIVE_INFINITY;
    }

    public RouteNode(T current, T previous, double routeScore, double estimatedScore) {
        this.current = current;
        this.previous = previous;
        this.routeScore = routeScore;
        this.estimatedScore = estimatedScore;
    }

    @Override
    public int compareTo(RouteNode other) {
        return Double.compare(this.estimatedScore, other.estimatedScore);
    }

    /**
     * Getter method for routeScore.
     * @return routeScore private variable
     */
    public double getRouteScore() {
        return routeScore;
    }

    /**
     * Setter method for routeScore
     * @param routeScore the new value for routeScore
     */
    public void setRouteScore(double routeScore) {
        this.routeScore = routeScore;
    }

    /**
     * Getter method for previous.
     * @return previous private variable
     */
    public T getPrevious() {
        return previous;
    }

    /**
     * Setter method for previous.
     * @param previous the new object that previous is set to
     */
    public void setPrevious(T previous) {
        this.previous = previous;
    }

    /**
     * Getter method for current.
     * @return current private variable
     */
    public T getCurrent() {
        return current;
    }

    /**
     * Getter method for estimatedScore
     * @return estimatedScore private variable
     */
    public double getEstimatedScore() {
        return estimatedScore;
    }

    /**
     * Setter method for estimatedScore
     * @param estimatedScore the new value for estimatedScore
     */
    public void setEstimatedScore(double estimatedScore) {
        this.estimatedScore = estimatedScore;
    }
}
