package warehouse.logistics.optimization;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.logistics.optimization.graph.TileNode;
import warehouse.logistics.optimization.routefinding.GraphNodeScorer;

/**
 * Score metric for Tiles using distance metric.
 */
public class DistanceTileScorer<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate> implements
        GraphNodeScorer<TileNode> {
    private final WarehouseCoordinateSystem<U> coordinateSystem;

    /**
     * Construct a DistanceTileScorer in the given WarehouseCoordinateSystem.
     * @param coordinateSystem The coordinate system to use for the distance metric.
     */
    public DistanceTileScorer(WarehouseCoordinateSystem<U> coordinateSystem) {
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
        U p1 = coordinateSystem.projectIndexToCoordinate(from.getTile().getIndex());
        U p2 = coordinateSystem.projectIndexToCoordinate(to.getTile().getIndex());
        return coordinateSystem.getDistance(p1, p2);
    }
}
