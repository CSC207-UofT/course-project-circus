package warehouse.geometry.grid;

import warehouse.geometry.WarehouseCoordinateSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * A WarehouseCoordinateSystem that consists of a rectangular grid of tiles.
 */
public class GridWarehouseCoordinateSystem implements WarehouseCoordinateSystem<Point> {
    private final int width;
    private final int height;

    /**
     * Construct a GridWarehouseCoordinateSystem.
     * @param width The width of the coordinate system, in number of tiles.
     * @param height The height of the coordinate system, in number of tiles.
     */
    public GridWarehouseCoordinateSystem(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Get the size of the grid defined by this coordinate system.
     * @return Return the number of tiles in this GridWarehouseCoordinateSystem.
     */
    @Override
    public int getSize() {
        return width * height;
    }

    /**
     * Project a Point into its corresponding tile index.
     * @remark This is the inverse function of projectIndexToCoordinate. See javadoc for formal invariant.
     * @param coordinate The coordinate of the tile.
     * @return An integer index representing the projection of the given coordinate into the natural numbers (N), or -1
     * if the given coordinate cannot be mapped to a tile index.
     */
    @Override
    public int projectCoordinateToIndex(Point coordinate) {
        if (contains(coordinate)) {
            return width * coordinate.getY() + coordinate.getX();
        } else {
            return -1;
        }
    }

    /**
     * Project a tile index into the corresponding Point.
     * @remark This is the inverse function of projectCoordinateToIndex. Therefore, the following invariant must hold:
     * for every coordinate v in this system, and for every index i:
     *      i = projectCoordinateToIndex(v) if and only if v = projectIndexToCoordinate(i).
     *
     * @param index The index of the tile.
     * @return A Point object representing the projection of the given tile index into the grid, or null if the given
     * index cannot be mapped to a coordinate.
     */
    @Override
    public Point projectIndexToCoordinate(int index) {
        if (index >= 0 && index < getSize()) {
            return new Point(index % width, index / width);
        } else {
            return null;
        }
    }

    /**
     * Indicates whether the given Point has a corresponding tile index, e.g. whether it is associated.
     * @param coordinate The coordinate to check.
     * @return True if the given coordinate is contained in the grid, and False otherwise.
     */
    @Override
    public boolean contains(Point coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Return the neighbouring coordinates to the given coordinate.
     * @param coordinate The coordinate whose neighbours to find.
     * @return A list of coordinates containing the neighbours of the given coordinate, in the other
     * north, east, south, west, north-east, south-east, south-west, north-west. If a neighbouring coordinate is not
     * in the coordinate system, then the entry in the returned list is null.
     */
    @Override
    public List<Point> getNeighbours(Point coordinate) {
        List<Point> neighbours = new ArrayList<>();
        // Add cardinal directions
        Point north = new Point(coordinate.getX(), coordinate.getY() - 1);
        neighbours.add(contains(north) ? north : null);
        Point east = new Point(coordinate.getX() + 1, coordinate.getY());
        neighbours.add(contains(east) ? east : null);
        Point south = new Point(coordinate.getX(), coordinate.getY() + 1);
        neighbours.add(contains(south) ? south : null);
        Point west = new Point(coordinate.getX() - 1, coordinate.getY());
        neighbours.add(contains(west) ? west : null);
        // Add diagonals
        Point northEast = new Point(coordinate.getX() + 1, coordinate.getY() - 1);
        neighbours.add(contains(northEast) ? northEast : null);
        Point southEast = new Point(coordinate.getX() + 1, coordinate.getY() + 1);
        neighbours.add(contains(southEast) ? southEast : null);
        Point southWest = new Point(coordinate.getX() - 1, coordinate.getY() + 1);
        neighbours.add(contains(southWest) ? southWest : null);
        Point northWest = new Point(coordinate.getX() - 1, coordinate.getY() - 1);
        neighbours.add(contains(southWest) ? northWest : null);
        // Done!
        return neighbours;
    }

    /**
     * Return the 2-norm of the given points (euclidean distance).
     * @param p1 The start point.
     * @param p2 The end point.
     * @return The distance between p1 and p2.
     */
    @Override
    public double getDistance(Point p1, Point p2) {
        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    /**
     * Get the width of the grid.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the grid.
     */
    public int getHeight() {
        return height;
    }
}
