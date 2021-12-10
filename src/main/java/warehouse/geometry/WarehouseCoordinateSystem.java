package warehouse.geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * The coordinate system defining a Warehouse. This is a vector space containing all the coordinates that can be used
 * to describe the position of a Tile in the Warehouse.
 *
 * @remark
 * A coordinate v is said to be associated if there exists a corresponding tile index i such that the projection
 * of v into N is i.
 *
 * Similarly, a tile index i is associated if there exists a corresponding coordinate v in the coordinate system W such
 * that projection of i into W is v. For example, the index -1 is unassociated since every coordinate is mapped to a
 * natural number.
 *
 * A coordinate system is the mapping such that for every coordinate v and index i, the coordinate v is associated
 * if and only if the index i is associated.
 *
 * A tile is associated if its index is associated AND it is an element of the coordinate system.
 * Otherwise, the tile is unassociated.
 */
public interface WarehouseCoordinateSystem<T extends WarehouseCoordinate> {
    /**
     * Get the size of this WarehouseCoordinateSystem, measured as the number of total tiles in the Warehouse.
     * @remark This is used to initialise a contiguous list of Tiles.
     * @return Return the number of tiles in this WarehouseCoordinateSystem.
     */
    int getSize();

    /**
     * Project a WarehouseCoordinate into its corresponding tile index.
     * @remark This is the inverse function of projectIndexToCoordinate. See javadoc for formal invariant.
     * @param coordinate The coordinate of the tile.
     * @return An integer index representing the projection of the given coordinate into the natural numbers (N), or -1
     * if the given coordinate cannot be mapped to a tile index.
     */
    int projectCoordinateToIndex(T coordinate);

    /**
     * Project a tile index into the corresponding WarehouseCoordinate.
     * @remark This is the inverse function of projectCoordinateToIndex. Therefore, the following invariant must hold:
     * for every coordinate v in this system, and for every index i:
     *      i = projectCoordinateToIndex(v) if and only if v = projectIndexToCoordinate(i).
     *
     * @param index The index of the tile.
     * @return A WarehouseCoordinate object representing the projection of the given tile index into the coordinate system,
     * or null if the given index cannot be mapped to a coordinate.
     */
    T projectIndexToCoordinate(int index);

    /**
     * Indicates whether the given WarehouseCoordinate has a corresponding tile index, e.g. whether it is associated.
     * @param coordinate The coordinate to check.
     * @return True if the given coordinate is contained in this WarehouseCoordinateSystem, and False otherwise.
     */
    boolean contains(T coordinate);

    /**
     * Return the neighbouring coordinates to the given coordinate.
     * @param coordinate The coordinate whose neighbours to find.
     * @return A list of coordinates containing the neighbours of the given coordinate. Elements of this list may be null!
     */
    List<T> getNeighbours(T coordinate);


    /**
     * Return the neighbouring tile indices to the given tile index
     * @param index The tile index whose neighbours to find.
     * @return A list of tile indices containing the neighbours of the given tile index. If a neighbour coordinate is null,
     * then the corresponding index is -1.
     */
    default List<Integer> getNeighbours(int index) {
        T p = projectIndexToCoordinate(index);
        List<Integer> neighbours = new ArrayList<>();
        for (T neighbour : getNeighbours(p)) {
            if (neighbour == null) {
                neighbours.add(-1);
            } else {
                neighbours.add(projectCoordinateToIndex(neighbour));
            }
        }
        return neighbours;
    }

    /**
     * Get the distance between the two coordinates. This function should be a metric.
     * See https://en.wikipedia.org/wiki/Metric_(mathematics) for more details.
     * @param p1 The start point.
     * @param p2 The end point.
     * @return The distance between p1 and p2.
     */
    double getDistance(T p1, T p2);

    /**
     * Get the distance between the two tile indices.
     * @param i1 The start tile index.
     * @param i2 The end tile index.
     * @return The distance between the coordinates corresponding to i1 and i2.
     */
    default double getDistance(int i1, int i2) {
        T p1 = projectIndexToCoordinate(i1);
        T p2 = projectIndexToCoordinate(i2);
        return getDistance(p1, p2);
    }
}
