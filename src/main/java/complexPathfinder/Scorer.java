package complexPathfinder;

import inventory.Item;
import warehouse.Tile;

public interface Scorer<T extends Item> {
    double computeCost(T from, T to);
}
