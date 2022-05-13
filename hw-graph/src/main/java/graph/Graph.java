package graph;

import java.util.*;

/**
 * Represents a mutable collection of nodes and edges. Edges are the connections
 * between two nodes that specify directionality.
 */
public class Graph {

    // RI: nodes != null, nodes = nodes(nodeName1), nodes(nodeName2), ... , nodes(nodeNameN)
    // AF(this) = {nodes(nodeName1) + nodes(nodeName2) + ... + nodes(nodeNameN)}

    private Map<String, Node> nodes;

    /**
     * Creates an empty graph object.
     */
    public Graph() {
        nodes = new HashMap<>();
        //checkRep();
    }

    /**
     * Adds a node to the graph.
     *
     * @param nodeName The name of the node being added to the graph.
     * @spec.requires node != null and this does not contain node
     * @spec.modifies this
     * @spec.effects this += node
     */
    public void addNode(String nodeName) {
        //checkRep();
        nodes.put(nodeName, new Node(nodeName));
        //checkRep();
    }

    /**
     * Adds an edge between two nodes in the graph.
     * @param edgeLabel The given labeled edge that will connect the two nodes.
     * @param node1     The first given node to connect.
     * @param node2     The second given node to connect.
     * @spec.requires edge != null, node1 != null and this contains node1, node2 != null, this contains node2 and this does not contain edge.
     * @spec.modifies this
     * @spec.effects this += edge
     */
    public void addEdge(String node1, String node2, String edgeLabel) {
        //checkRep();
        Edge e = new Edge(node1, node2, edgeLabel);
        Node parent = nodes.get(node1);
        parent.addEdge(e);
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
    public List<String> getParents(String child) {
        //checkRep();
        List<String> parents = new ArrayList<>();
        for (String nodeName : nodes.keySet()) {
            if (nodes.get(nodeName).isParent(child)) {
                parents.add(nodeName);
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
    public List<String> getChildren(String parent) {
        //checkRep();
        List<String> children = new ArrayList<>();
        Node parentNode = nodes.get(parent);
        for (String nodeName : nodes.keySet()) {
            if (parentNode.isParent(nodeName)) {
                children.add(nodeName + "(" + parentNode.getEdge(nodeName) + ") ");
            }
        }
        //checkRep();
        if (children.isEmpty()) {
            return null;
        }
        return children;
    }

    /**
     * Checks if the given edge can be found within the current map.
     *
     * @param edge The edge that is being checked for.
     * @return whether the edge is in the graph or not.
     * @spec.requires edge != null
     */
    public boolean isEdge(String edge) {
        //checkRep();
        for (String nodeName : nodes.keySet()) {
            if (nodes.get(nodeName).containsEdge(edge)) {
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
        return nodes.containsKey(nodeName);
    }

    /**
     * Returns a set of all the nodes in the graph.
     *
     * @return list of all the nodes.
     */
    public Set<String> getNodes() {
        //checkRep();
        return nodes.keySet();
    }

    private void checkRep() {
        assert nodes != null;
        for (String nodeName : nodes.keySet()) {
            assert nodes.containsKey(nodeName);
        }
    }

    private class Node {

        // RI: name != null, outgoingEdges != null, outgoingEdges = edge1(this,other1,label1), edge2(this,other2,label2), ... , edgeN(this,otherN,labelN)
        // AF(this) = name & outgoingEdges{edge1(this,other1,label1) + edge2(this,other2,label2) + ... + edgeN(this,otherN,labelN)}

        public String name;
        private List<Edge> outgoingEdges;

        // Create an instance of a node class using a given list of edges.
        private Node(String name, ArrayList<Edge> edges) {
            this.name = name;
            outgoingEdges = new ArrayList<>(edges);
            //checkRep();
        }

        // Create an instance of a node.
        public Node(String name) {
            this.name = name;
            outgoingEdges = new ArrayList<>();
            //checkRep();
        }

        // Add an outgoing edge to this node.
        private void addEdge(Edge e) {
            //checkRep();
            outgoingEdges.add(e);
            //checkRep();
        }

        // Check if this node is the parent of another node in an edge.
        private boolean isParent(String child) {
            //checkRep();
            for (Edge e : outgoingEdges) {
                if (e.node2.equals(child)) {
                    //checkRep();
                    return true;
                }
            }
            //checkRep();
            return false;
        }

        // Check if this node contains the given edge.
        private boolean containsEdge(String edge) {
            //checkRep();
            for (Edge e : outgoingEdges) {
                if (e.label.equals(edge)) {
                    //checkRep();
                    return true;
                }
            }
            //checkRep();
            return false;
        }

        // Return the edge connecting the parent node (this) and the child node (otherNode).
        // Returns null if there is no edge.
        private String getEdge(String otherNode) {
            //checkRep();
            for (Edge e : outgoingEdges) {
                if (e.node1.equals(name) && e.node2.equals(otherNode)) {
                    //checkRep();
                    return e.label;
                }
            }
            //checkRep();
            return null;
        }

        private void checkRep() {
            assert name != null;
            assert outgoingEdges != null;
            for (int i = 0; i < outgoingEdges.size(); i++) {
                assert outgoingEdges.get(i) != null;
            }
        }
    }

    private class Edge {

        // RI: node1 != null, node2 != null, label != null
        // AF(this) = node1 + node2 + label

        private String node1;
        private String node2;
        private String label;

        private Edge(String node1, String node2, String label) {
            this.node1 = node1;
            this.node2 = node2;
            this.label = label;
        }
    }
}
