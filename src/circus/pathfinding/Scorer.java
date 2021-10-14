package circus.pathfinding;

import circus.inventory.Item;

public interface Scorer<T extends Item> {
    public double computeCost(T from, T to);
}
