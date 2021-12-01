package concretePathfinding;
import complexPathfinder.*;
import warehouse.*;

public class TileScorer implements Scorer{

    /**
     * The cost we're using is the distance between two nodes.
     * @param from The starting node
     * @param to The ending node
     * @return The distance between two nodes
     */
    @Override
    public double computeCost(GraphNode from, GraphNode to) {
        int x1 = ((TileNode)from).getT().getX();
        int y1 = ((TileNode)from).getT().getY();
        int x2 = ((TileNode)to).getT().getX();
        int y2 = ((TileNode)to).getT().getY();
        return Math.sqrt(Math.pow(y2 - y1, 2)+Math.pow(x2 - x1, 2));
    }
}
