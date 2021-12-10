package warehouse.logistics.optimization.graph;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a graph (in the "graph theory" sense), e.g. a collection of vertices connected by edges.
 */
public class Graph<T extends GraphNode> {
    /**
     * A list
     */
    private final Set<T> nodes;
    /**
     * A mapping from a GraphNode (given by its id) to a Set giving all the nodes that it's connected to.
     */
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
     * Return the GraphNode with the given id.
     * @param id id of intended node
     * @return The node with the given id.
     */
    public T getNode(String id) {
        for (T node: this.nodes){
            if(Objects.equals(node.getId(), id)){
                return node;
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
