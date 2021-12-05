package concretePathfinding;

import complexPathfinder.*;
import warehouse.Tile;
import warehouse.TileOutOfBoundsException;

import java.util.List;

/**
 * This class will be in charge of the Pathfinder as a whole and will be the class that the Warehouse interacts with
 * rather than other more basic classes.
 */
public class PathfinderController {
    private final Graph graph ;
    private final TileScorer nextNodeScorer;
    private final TileScorer targetNodeScorer;
    private TileNode from;
    private TileNode to;

    /**
     * Constructs an instance of the PathfinderController.
     * @param map - 2D array of all tiles in the warehouse
     */

    public PathfinderController(Tile[][] map, Tile from, Tile to) throws TileOutOfBoundsException {
        this.graph =  new GraphCreator(map).getGraph();
        this.nextNodeScorer = new TileScorer();
        this.targetNodeScorer = new TileScorer();
        this.from = new TileNode(from);
        this.to = new TileNode(to);
    }

    public List getPath(){
        Pathfinder pathfinder = new Pathfinder(this.graph, this.nextNodeScorer, this.targetNodeScorer);

        return pathfinder.findPath(this.from, this.to);
    }


}
