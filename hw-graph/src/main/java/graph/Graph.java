package graph;

import java.util.*;

/**
 * Represents a mutable collection of nodes and edges. Edges are the connections
 * between two nodes that specify directionality.
 */
public class Graph<N, E> {

    // RI: nodes != null, nodes = nodes(nodeName1), nodes(nodeName2), ... , nodes(nodeNameN)
    // AF(this) = {nodes(nodeName1) + nodes(nodeName2) + ... + nodes(nodeNameN)}

    private Map<N, Set<Edge<N, E>>> adjacencyList;

    /**
     * Creates an empty graph object.
     */
    public Graph() {
        adjacencyList = new HashMap<>();
        //checkRep();
    }

    /**
     * Creates a graph based on a set of given nodes.
     * @spec.requires nodes != null
     * @param nodes List of given nodes
     */
    public Graph(List<N> nodes) {
        adjacencyList = new HashMap<>();
        for (N node : nodes) {
            addNode(node);
        }
    }

    /**
     * Adds a node to the graph.
     *
     * @param data The data of the node being added to the graph.
     * @spec.requires node != null and this does not contain node
     * @spec.modifies this
     * @spec.effects this += node
     */
    public void addNode(N data) {
        //checkRep();
        if (!adjacencyList.containsKey(data)) {
            adjacencyList.put(data, new HashSet<>());
        }
        //checkRep();
    }

    /**
     * Adds an edge between two nodes in the graph.
     *
     * @param label The given  edge that will connect the two nodes.
     * @param node1 The first given node to connect.
     * @param node2 The second given node to connect.
     * @spec.requires edge != null, node1 != null and this contains node1, node2 != null, this contains node2 and this does not contain edge.
     * @spec.modifies this
     * @spec.effects this += edge
     */
    public void addEdge(N node1, N node2, E label) {
        //checkRep();
        if (!containsNode(node1)) {
            addNode(node1);
        }
        adjacencyList.get(node1).add(new Edge<>(node1, node2, label));
        //checkRep();
    }

    /**
     * Returns a list of the nodes that are the parents of the given node or null if
     * there are no parents associated with the given node.
     *
     * @param child The name of the node that is being checked for any parents.
     * @return A list of all the parents of node. Returns null if there are no parents.
     * @spec.requires node != null.
     */
    /*public List<N> getParents(N child) {
        //checkRep();
        List<N> parents = new ArrayList<>();
        for (N node : adjacencyList.keySet()) {
            if (adjacencyList.get(node).containsKey(child)) {
                parents.add(node);
            }
        }
        //checkRep();
        if (parents.isEmpty()) {
            return null;
        }
        return parents;
    }*/

    /**
     * Returns a list of the nodes that are the children of the given node or null if there
     * are no children associated with the given node.
     *
     * @param parent The given node that is being checked for any children.
     * @return A list of all the children of node. Returns null if there are no children.
     * @spec.requires node != null.
     */
    public List<N> getChildren(N parent) {
        //checkRep();
        if (!adjacencyList.containsKey(parent)) {
            return null;
        }
        Set<Edge<N, E>> edges = adjacencyList.get(parent);
        List<N> children = new ArrayList<>();
        for (Edge<N, E> edge : edges) {
            children.add(edge.getChild());
        }

        return children;
        //checkRep();
    }

    /**
     * Checks if the given edge can be found within the current map.
     *
     * @param edge The edge that is being checked for.
     * @return whether the edge is in the graph or not.
     * @spec.requires edge != null
     */
    public boolean isEdge(Edge<N, E> edge) {
        //checkRep();
        for (N node : adjacencyList.keySet()) {
            if (adjacencyList.get(node).contains(edge)) {
                //checkRep();
                return true;
            }
        }
        //checkRep();
        return false;
    }

    /**
     * Checks if the given node can be found within the current map.
     *
     * @param node The node that is being checked for.
     * @return whether the node is in the graph or not.
     * @spec.requires node != null
     */
    public boolean isNode(N node) {
        //checkRep();
        return adjacencyList.containsKey(node);
    }

    /**
     * Returns a set of all the nodes in the graph.
     *
     * @return list of all the nodes.
     */
    public Set<N> getNodes() {
        //checkRep();
        return adjacencyList.keySet();
    }

    /**
     * Returns a set of all the edges in the graph.
     *
     * @return list of all the edges.
     */
    public Set<Edge<N, E>> getEdges(N parent) {
        return new HashSet<>(adjacencyList.get(parent));
    }

    /**
     * Returns whether a given node is in the graph.
     *
     * @return list of all the nodes.
     */
    public boolean containsNode(N node) {
        return adjacencyList.containsKey(node);
    }

    /**
     * Returns whether a given edge is in the graph.
     *
     * @return true/false if edge is in graph.
     */
    public boolean containsEdge(N parent, Edge<N, E> edge) {
        return adjacencyList.get(parent).contains(edge);
    }

    /**
     * Returns whether the given Edge fields make up an edge
     * within the graph.
     * @param parent
     * @param child
     * @param edgeLabel
     * @return true/false if edge is in graph
     */
    public boolean containsEdge(N parent, N child, E edgeLabel) {
        Set<Edge<N, E>> edges = adjacencyList.get(parent);
        for (Edge<N, E> edge : edges) {
            if (edge.getChild().equals(child) && edge.getLabel().equals(edgeLabel)) {
                return true;
            }
        }
        return false;
    }

    private void checkRep() {
        assert adjacencyList != null;
        for (N node : adjacencyList.keySet()) {
            assert adjacencyList.containsKey(node);
        }
    }
}