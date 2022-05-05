package graph;

import java.util.*;

/**
 * Represents a mutable collection of nodes and edges. Edges are the connections
 * between two nodes that specify directionality.
 */
public class Graph {

    /**
     * Creates an empty graph object.
     */
    public Graph() {}

    /**
     * Creates a graph using the given nodes and edges.
     * @param nodes Array containing the nodes to make into a graph
     * @param edges The edges connecting the nodes of the graph together.
     * @spec.requires nodes != null and no duplicates. edges contains the same nodes as in node
     */
    public Graph(String[] nodes, String[] edges) {}

    /**
     * Adds a node to the graph.
     * @param node The given node to add to the graph.
     * @spec.requires node != null & this does not contain node
     * @spec.effects this += node
     */
    public void addNode(String node) {}

    /**
     * Adds an edge between two nodes in the graph.
     * @param edge The given labeled edge that will connect the two nodes.
     * @param node1 The first given node to connect.
     * @param node2 The second given node to connect.
     * @spec.requires edge != null, node1 != null & this contains node1,
     *                  node2 != null, this contains node2 & this does not contain edge.
     * @spec.effects this += edge
     */
    public void addEdge(String edge, String node1, String node2) {}

    /**
     * Returns a list of the nodes that are the parents of the given node.
     * @param node The given node that is being checked for any parents.
     * @spec.requires node != null.
     * @return a list of all the parents of node.
     */
    public List<String> getParents(String node) { return null; }

    /**
     * Returns a list of the nodes that are the children of the given node.
     * @param node The given node that is being checked for any children.
     * @spec.requires node != null.
     * @return a list of all the children of node.
     */
    public List<String> getChildren(String node) { return null; }

    /**
     * Checks if the given edge can be found within the current map.
     * @param edge The edge that is being checked for.
     * @spec.requires edge != null
     * @return whether the edge is in the graph or not.
     */
    public boolean isEdge(String edge) { return false; }

    /**
     * Checks if the given node can be found within the current map.
     * @param node The node that is being checked for.
     * @spec.requires node != null
     * @return whether the node is in the graph or not.
     */
    public boolean isNode(String node) { return false; }

    /**
     * Returns a set of all the nodes in the graph.
     * @return list of all the nodes.
     */
    public Set<String> getNodes() { return null; }
}
