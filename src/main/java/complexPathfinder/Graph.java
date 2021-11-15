package complexPathfinder;

import warehouse.inventory.Item;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph<T extends Item> {
    private final Set<T> nodes;
    private final Map<String, Set<String>> connections;

    /**
     * Creates an instance of the Graph class.
     * @param nodes Nodes of this graph.
     * @param connections Connections (edges) between nodes.
     */
    public Graph(Set<T> nodes, Map<String, Set<String>> connections)
    {
        this.nodes = nodes;
        this.connections = connections;
    }

    public T getNode(String id) {
        return nodes.stream()
                .filter(node -> node.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No node found with ID"));
    }

    public Set<T> getConnections(T node) {
        return connections.get(node.getId()).stream()
                .map(this::getNode)
                .collect(Collectors.toSet());
    }
}
