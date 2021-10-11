package circus;

/**
 * Abstract class for pathfinders that discovers the "optimal" path for packages.
 *
 * Classes that extend this one will have to implement the methods we choose here.
 */
public abstract class Pathfinder {
    /**
     * Returns the optimal path for packages. This method will differ based on the type of pathifnder we use.
     *
     * @return an array of strings representing the order of whcih packages it visits.
     */
    public abstract String[] findPath();

}
