package pathfinding.concretePathfinding;

import pathfinding.complexPathfinder.*;
import warehouse.tiles.Tile;

import java.util.ArrayList;

/**
 * This class will be in charge of the Pathfinder as a whole and will be the class that the WarehouseLayout interacts with
 * rather than other more basic classes.
 */
public class PathfinderController {
    private final TileScorer nextNodeScorer;
    private final TileScorer targetNodeScorer;
    private final Tile[][] map;
    private final TileNode from;
    private final TileNode to;

    /**
     * Constructs an instance of the PathfinderController.
     * @param map - 2D array of all tiles in the warehouse
     */

    public PathfinderController(Tile[][] map, Tile from, Tile to) {
        this.nextNodeScorer = new TileScorer();
        this.targetNodeScorer = new TileScorer();
        this.from = new TileNode(from);
        this.to = new TileNode(to);
        this.map = map;
    }

    public ArrayList<Tile> getPath() {
        Pathfinder<TileNode> pathfinder = new Pathfinder<>(
                new GraphCreator(this.map).getGraph(),
                this.nextNodeScorer, this.targetNodeScorer);

        ArrayList<TileNode> result = (ArrayList<TileNode>) pathfinder.findPath(this.from, this.to);
        ArrayList<Tile> path = new ArrayList<>();
        for (TileNode t : result){
            path.add(t.getTile());
        }
        return path;
    }


}
