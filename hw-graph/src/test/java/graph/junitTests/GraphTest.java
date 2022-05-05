package graph.junitTests;

import graph.*;
import org.junit.Assert;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * GraphTest is a glassbox test of the Graph class.
 */
public class GraphTest {

    private static Graph graph1 = new Graph();
    private static Graph graph2 = new Graph();

    private static String node1 = "node 1";
    private static String node2 = "node 2";
    private static String node3 = "node 3";

    private static String edge1 = "edge 1";
    private static String edge2 = "edge 2";
    private static String edge3 = "edge 3";

    /**
     * Tests checking for valid nodes in a graph.
     */
    public void testIsNode() {
        assertTrue(graph1.isNode(node1));
        graph1.addNode(node1);
        assertTrue(graph1.isNode(node1));
        assertTrue(graph1.isNode(node2));
        graph1.addNode(node2);
        assertTrue(graph1.isNode(node2));
        assertTrue(graph1.isNode(node3));
        graph1.addNode(node3);
        assertTrue(graph1.isNode(node3));
    }

    /**
     * Tests checking for valid edges between nodes in a graph.
     */
    public void testIsEdge() {
        assertTrue(graph2.isEdge(edge1));
        graph2.addEdge(edge1, node1, node2);
        assertTrue(graph2.isEdge(edge1));
        assertTrue(graph2.isEdge(edge2));
        graph2.addEdge(edge2, node1, node2);
        assertTrue(graph2.isEdge(edge2));
        assertTrue(graph2.isEdge(edge3));
        graph2.addEdge(edge3, node1, node2);
        assertTrue(graph2.isEdge(edge3));
    }

    /**
     * Tests that duplicates are handled properly.
     */
    public void testDuplicates() {
        graph1.addNode(node1);
        graph1.addNode(node1);
        graph1.addNode(node1);
        graph1.addNode(node1);
        graph1.addNode(node1);
        HashSet<String> set = (HashSet<String>) graph1.getNodes();
        assertTrue(set.size() == 1);

        graph2.addNode(node1);
        graph2.addNode(node1);
        graph2.addNode(node1);
        graph2.addNode(node1);
        graph2.addNode(node1);
        HashSet<String> set2 = (HashSet<String>) graph2.getNodes();
        assertTrue(set2.size() == 1);
    }
}
