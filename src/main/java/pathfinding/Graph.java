package main.java.pathfinding;
import main.java.inventory.Item;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph <T extends Item> {
    private final Set<T> nodes;
    private final Map<String, Set<String>> connections;

    /**
     * Creates an instance of the Graph class. Not sure if it's in use yet, but it's set up just in case.
     * @param n
     * @param c
     */
    public Graph(Set<T> n, Map<String, Set<String>> c)
    {
        nodes = n;
        connections = c;
    }

    public T getNode(String id) {
        return nodes.stream()
                .filter(node -> node.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No node found with ID"));
    }

    public Set<T> getConnections(T node) {
        return connections.get(node.getId().toString()).stream()
                .map(this::getNode)
                .collect(Collectors.toSet());
    }
}
