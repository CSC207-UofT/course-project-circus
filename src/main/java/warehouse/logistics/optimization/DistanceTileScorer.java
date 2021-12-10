package warehouse.logistics.optimization;

import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.logistics.optimization.graph.TileNode;
import warehouse.logistics.optimization.routefinding.GraphNodeScorer;

/**
 * Score metric for Tiles using distance metric.
 */
public class DistanceTileScorer implements GraphNodeScorer<TileNode> {
    private final WarehouseCoordinateSystem<?> coordinateSystem;

    /**
     * Construct a DistanceTileScorer in the given WarehouseCoordinateSystem.
     * @param coordinateSystem The coordinate system to use for the distance metric.
     */
    public DistanceTileScorer(WarehouseCoordinateSystem<?> coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
    }

    /**
     * The cost we're using is the distance between two nodes.
     * @param from The starting node
     * @param to The ending node
     * @return The distance between two nodes
     */
    @Override
    public double computeCost(TileNode from, TileNode to) {
        return coordinateSystem.getDistance(from.getTile().getIndex(), to.getTile().getIndex());
    }
}
