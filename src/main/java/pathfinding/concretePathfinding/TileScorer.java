package pathfinding.concretePathfinding;
import pathfinding.complexPathfinder.*;

public class TileScorer implements Scorer<TileNode> {

    /**
     * The cost we're using is the distance between two nodes.
     * @param from The starting node
     * @param to The ending node
     * @return The distance between two nodes
     */
    @Override
    public double computeCost(TileNode from, TileNode to) {
        //TODO:
//        int x1 = from.getTile().getX();
//        int y1 = from.getTile().getY();
//        int x2 = to.getTile().getX();
//        int y2 = to.getTile().getY();
//        return Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
        return 0;
    }
}
