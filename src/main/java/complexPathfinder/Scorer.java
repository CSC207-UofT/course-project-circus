package complexPathfinder;

import inventory.Item;
import warehouse.Tile;

public interface Scorer<T extends Comparable> {
    double computeCost(T from, T to);
}
