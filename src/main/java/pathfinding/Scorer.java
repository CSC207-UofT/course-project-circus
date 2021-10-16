package main.java.pathfinding;

import main.java.inventory.Item;

public interface Scorer<T extends Item> {
    double computeCost(T from, T to);
}
