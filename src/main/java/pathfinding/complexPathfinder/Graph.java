package pathfinding.complexPathfinder;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph<T extends GraphNode> {
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

    /**
     * Returns the target node
     * @param id id of intended node
     * @return The node with the given id.
     */
    public T getNode(String id) {
        for (T node: this.nodes){
            if(Objects.equals(node.getId(), id)){
                return (T) node;
            }
        }
        return null;
    }

    /**
     * Returns the connections that are associated with a specific node
     * @param node the intended node's connections
     * @return the connections
     */
    public Set<T> getConnections(T node) {
        return connections.get(node.getId()).stream()
                .map(this::getNode)
                .collect(Collectors.toSet());
    }
}
