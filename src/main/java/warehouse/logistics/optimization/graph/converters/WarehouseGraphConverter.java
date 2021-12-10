package warehouse.logistics.optimization.graph.converters;

import warehouse.Warehouse;
import warehouse.WarehouseLayout;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.logistics.optimization.graph.Graph;
import warehouse.logistics.optimization.graph.TileNode;
import warehouse.tiles.Tile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Converts an arbitrary Warehouse to a Graph that can be used for routefinding.
 */
public abstract class WarehouseGraphConverter<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate> {
    /**
     * Convert the given Warehouse to a graph.
     * @param warehouse The Warehouse to convert.
     * @return A graph of WarehouseNodes.
     */
    public Graph<TileNode> convert(Warehouse<T, U> warehouse) {
        Set<TileNode> emptyNodes = getEmptyTilesAsNodes(warehouse);
        Map<String, Set<String>> connections = new HashMap<>();
        for (TileNode node : emptyNodes) {
            connections.put(node.getId(), getNeighboursTo(warehouse, node.getTile()));
        }
        return new Graph<>(emptyNodes, connections);
    }

    /**
     * Return the empty tiles of the given Warehouse as TileNode objects.
     */
    private Set<TileNode> getEmptyTilesAsNodes(Warehouse<T, U> warehouse) {
        Set<TileNode> nodes = new HashSet<>();
        WarehouseLayout<U> layout = warehouse.getState().getLayout();
        for (Tile tile : layout.getTiles()) {
            if (canAddTile(warehouse, tile)) {
                nodes.add(new TileNode(tile));
            }
        }
        return nodes;
    }

    /**
     * Return the ids of neighbouring nodes to the given node.
     */
    private Set<String> getNeighboursTo(Warehouse<T, U> warehouse, Tile tile) {
        HashSet<String> neighbours = new HashSet<>();
        WarehouseLayout<U> layout = warehouse.getState().getLayout();
        WarehouseCoordinateSystem<U> coordinateSystem = layout.getCoordinateSystem();

        U tilePosition = coordinateSystem.projectIndexToCoordinate(tile.getIndex());
        for (U point : coordinateSystem.getNeighbours(tilePosition)) {
            if (point == null) continue;
            Tile neighbour = layout.getTileAt(point);
            if (canAddTile(warehouse, neighbour)) {
                neighbours.add(new TileNode(neighbour).getId());
            }
        }

        return neighbours;
    }

    /**
     * Return whether to include the given Tile in the graph.
     * @param warehouse The Warehouse being converted.
     * @param tile The tile to check.
     * @return True if the Tile should be added to the graph, and False otherwise.
     */
    protected abstract boolean canAddTile(Warehouse<T, U> warehouse, Tile tile);
}
