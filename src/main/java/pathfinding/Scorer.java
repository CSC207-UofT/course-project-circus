package pathfinding;

import inventory.Item;

public interface Scorer<T extends Item> {
    double computeCost(T from, T to);
}
