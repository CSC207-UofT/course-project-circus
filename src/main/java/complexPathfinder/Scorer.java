package complexPathfinder;

import inventory.Item;
import warehouse.Tile;

public interface Scorer<T extends GraphNode> {
    double computeCost(T from, T to);
}
