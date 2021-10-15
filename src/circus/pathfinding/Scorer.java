package circus.pathfinding;

import circus.inventory.Item;

public interface Scorer<T extends Item> {
    double computeCost(T from, T to);
}
