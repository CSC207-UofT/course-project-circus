package pathfinding.concretePathfinding;
import pathfinding.complexPathfinder.Graph;
import warehouse.tiles.EmptyTile;
import warehouse.tiles.Tile;

import java.util.*;

/**
 * This class will be an assistant class to the PathfinderController which will help it with Graph construction.
 *
 */
public class GraphCreator {
    private final Tile[][] map;

    /**
     * Creates an instance of GraphCreator
     * @param tiles the set of all tiles in the warehouse
     */
    public GraphCreator(Tile[][] tiles) {
        this.map = tiles;
    }

    /**
     *
     * @return the set of empty TileNodes in the warehouse
     */
    public Set<TileNode> getNodes()
    {
        Set<TileNode> nodes = new HashSet<>();
        for (Tile[] tile : this.map) {
            for (Tile value : tile) {
                if (isEmpty(value)) {
                    nodes.add(new TileNode(value));
                }
            }
        }

        return nodes;
    }

    /**
     * @param node TileNode in the warehouse layout.
     * @return Set of Id's of all connected nodes.
     */
    public Set<String> getConnections(TileNode node) {
        //TODO:
//        Set<String> connections = new HashSet<>();
//        if(node.getTile().getY() < map[0].length-1) {
//            if (isEmpty(this.map[node.getTile().getX()][node.getTile().getY() + 1])) {
//                connections.add((new TileNode(this.map[node.getTile().getX()][node.getTile().getY() + 1])).getId());
//            }
//        }
//        if(node.getTile().getY() - 1 >= 0) {
//            if(isEmpty(this.map[node.getTile().getX()][node.getTile().getY()-1])) {
//                connections.add((new TileNode(this.map[node.getTile().getX()][node.getTile().getY()-1])).getId());
//            }
//        }
//        if(node.getTile().getX()< map.length-1) {
//            if (isEmpty(this.map[node.getTile().getX() + 1][node.getTile().getY()])) {
//                connections.add((new TileNode(this.map[node.getTile().getX() + 1][node.getTile().getY()])).getId());
//            }
//        }
//        if(node.getTile().getX()-1 >=0) {
//            if  (isEmpty(this.map[node.getTile().getX() - 1][node.getTile().getY()])) {
//                connections.add((new TileNode(this.map[node.getTile().getX() - 1][node.getTile().getY()]).getId()));
//            }
//        }
//        return connections;
        return null;
    }

    /**
     * Return whether the given tile is empty.
     * @param tile The tile to check.
     * @return True if the tile is empty, and False otherwise.
     */
    private boolean isEmpty(Tile tile) {
        return tile instanceof EmptyTile;
    }

    /**
     *
     * @return returns the graph of the warehouse
     */
    public Graph<TileNode> getGraph() {
        Set<TileNode> tileSet = this.getNodes();
        Map<String, Set<String>> connections = new HashMap<String, Set<String>>();
        for (TileNode t : tileSet) {
            connections.put(t.getId(), this.getConnections(t));
        }
        return new Graph<>(tileSet, connections);
    }
}
