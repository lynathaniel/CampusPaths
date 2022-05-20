package graph;

import java.util.*;

/**
 * Represents a mutable collection of nodes and edges. Edges are the connections
 * between two nodes that specify directionality.
 */
public class Graph<N, E> {

    // RI: nodes != null, nodes = nodes(nodeName1), nodes(nodeName2), ... , nodes(nodeNameN)
    // AF(this) = {nodes(nodeName1) + nodes(nodeName2) + ... + nodes(nodeNameN)}

    private Map<N, Map<N, E>> adjacencyList;

    /**
     * Creates an empty graph object.
     */
    public Graph() {
        adjacencyList = new HashMap<>();
        //checkRep();
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
            adjacencyList.put(data, new HashMap<>());
        }
        //checkRep();
    }

    /**
     * Adds an edge between two nodes in the graph.
     *
     * @param label The given labeled edge that will connect the two nodes.
     * @param node1 The first given node to connect.
     * @param node2 The second given node to connect.
     * @spec.requires edge != null, node1 != null and this contains node1, node2 != null, this contains node2 and this does not contain edge.
     * @spec.modifies this
     * @spec.effects this += edge
     */
    public void addEdge(N node1, N node2, E label) {
        //checkRep();
        adjacencyList.get(node1).put(node2, label);
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
    public List<N> getParents(N child) {
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
    }

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
        List<N> children = new ArrayList<>();
        //List<Edge<N, E>> edges = adjacencyList.get(parent);
        for (N node : adjacencyList.keySet()) {
            if (node.equals(parent)) {
                children = new ArrayList<>(adjacencyList.get(parent).keySet());
                return children;
            }
        }
        //checkRep();
        /*if (children.isEmpty()) {
            return null;
        }*/
        return null;
    }

    /**
     * Checks if the given edge can be found within the current map.
     *
     * @param edge The edge that is being checked for.
     * @return whether the edge is in the graph or not.
     * @spec.requires edge != null
     */
    public boolean isEdge(E edge) {
        //checkRep();
        for (N node : adjacencyList.keySet()) {
            if (adjacencyList.get(node).containsValue(edge)) {
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
     * @param nodeName The node that is being checked for.
     * @return whether the node is in the graph or not.
     * @spec.requires node != null
     */
    public boolean isNode(String nodeName) {
        //checkRep();
        return adjacencyList.containsKey(nodeName);
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
    public Set<E> getEdges(N parent) {
        return new HashSet<E>(adjacencyList.get(parent).values());
    }

    /**
     * Returns a the edge connecting the two given nodes.
     *
     * @return edge between two nodes.
     */
    public E getEdge(N parent, N child) {
        return adjacencyList.get(parent).get(child);
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
     * @return list of all the nodes.
     */
    public boolean containsEdge(N parent, E edge) {
        return adjacencyList.get(parent).containsValue(edge);
    }

    private void checkRep() {
        assert adjacencyList != null;
        for (N node : adjacencyList.keySet()) {
            assert adjacencyList.containsKey(node);
        }
    }
}