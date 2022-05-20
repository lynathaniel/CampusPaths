package graph.junitTests;

import graph.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * GraphTest is a glassbox test of the Graph class.
 */
public class GraphTest {

    private Graph<String, String> graph1 = new Graph<>();
    private Graph<String, String> graph2 = new Graph<>();

    private String node1 = "node 1";
    private String node2 = "node 2";
    private String node3 = "node 3";

    private String edge1 = "edge 1";
    private String edge2 = "edge 2";
    private String edge3 = "edge 3";

    /**
     * Tests checking for valid nodes in a graph.
     */
    @Test
    public void testIsNode() {
        assertFalse(graph1.isNode(node1));
        graph1.addNode(node1);
        assertTrue(graph1.isNode(node1));
        assertFalse(graph1.isNode(node2));
        graph1.addNode(node2);
        assertTrue(graph1.isNode(node2));
        assertFalse(graph1.isNode(node3));
        graph1.addNode(node3);
        assertTrue(graph1.isNode(node3));
    }

    /**
     * Tests checking for valid edges between nodes in a graph.
     */
    @Test
    public void testIsEdge() {
        graph2.addNode(node1);
        graph2.addNode(node2);
        graph2.addNode(node3);
        assertFalse(graph2.isEdge(edge1));
        graph2.addEdge(node1, node2, edge1);
        assertTrue(graph2.isEdge(edge1));
        assertFalse(graph2.isEdge(edge2));
        graph2.addEdge(node1, node2, edge2);
        assertTrue(graph2.isEdge(edge2));
        assertFalse(graph2.isEdge(edge3));
        graph2.addEdge(node1, node2, edge3);
        assertTrue(graph2.isEdge(edge3));
    }

    /**
     * Tests that duplicates are handled properly.
     */
    @Test
    public void testDuplicates() {
        graph1.addNode(node1);
        graph1.addNode(node1);
        graph1.addNode(node1);
        graph1.addNode(node1);
        graph1.addNode(node1);
        Set<String> set = graph1.getNodes();
        assertTrue(set.size() == 1);

        graph2.addNode(node1);
        graph2.addNode(node1);
        graph2.addNode(node1);
        graph2.addNode(node1);
        graph2.addNode(node1);
        Set<String> set2 = graph2.getNodes();
        assertTrue(set2.size() == 1);
    }
}
