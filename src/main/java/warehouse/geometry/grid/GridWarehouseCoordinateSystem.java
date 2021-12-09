package warehouse.geometry.grid;

import warehouse.geometry.WarehouseCoordinateSystem;

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
