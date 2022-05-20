/*
 * Copyright (C) 2022 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder.scriptTestRunner;

import graph.Graph;
import pathfinder.DijkstraAlg;
import pathfinder.datastructures.Path;

import java.io.*;
import java.util.*;

// Majority of the testing code was taken from the GraphTestDriver written for UW CSE 331 SPR 22 and slightly altered to
// fit this application.

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {

    private final Map<String, Graph<String, Double>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;

    // Leave this constructor public
    public PathfinderTestDriver(Reader r, Writer w) {
        // TODO: Implement this, reading commands from `r` and writing output to `w`.
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    // Leave this method public
    public void runTests() throws IOException  {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }
    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        Graph<String, Double> g = new Graph<>();

        graphs.put(graphName, g);
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {

        Graph<String, Double> g = graphs.get(graphName);
        g.addNode(nodeName);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        double edgeLabel = Double.parseDouble(arguments.get(3));

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         double edgeLabel) {
        Graph<String, Double> g = graphs.get(graphName);
        g.addEdge(parentName, childName, edgeLabel);
        output.println("added edge " + edgeLabel + " from " + parentName + " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        Graph<String, Double> g = graphs.get(graphName);
        Set<String> nodes = g.getNodes();
        StringBuilder stringNodes = new StringBuilder();
        for (String node : nodes) {
            stringNodes.append(" ").append(node);
        }
        output.println(graphName + " contains:" + stringNodes);
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {

        Graph<String, Double> g = graphs.get(graphName);
        List<String> children = g.getChildren(parentName);
        StringBuilder childNodes = new StringBuilder();
        if (!children.isEmpty()) {
            for (String node : children) {
                childNodes.append(" " + node + "(" + String.format("%.3f", g.getEdge(parentName, node)) + ")");
            }
            output.println("the children of " + parentName + " in " + graphName + " are: " + childNodes.toString().trim());
        } else {
            output.println("the children of " + parentName + " in " + graphName + " are:");
        }
    }

    private void findPath(List<String> arguments) {
        if(arguments.size() != 3) {
            throw new CommandException("bad arguments to FindPath: " + arguments);
        }
        String graphName = arguments.get(0);
        String node1 = arguments.get(1);
        String node2 = arguments.get(2);
        findPath(graphName, node1, node2);
    }

    private void findPath(String graphName, String node1, String node2) {
        Graph<String, Double> g = graphs.get(graphName);
        List<Path<String>> shortestPath = DijkstraAlg.findMinCostPath(node1, node2, g);
        if (!g.containsNode(node1) || !g.containsNode(node2)) {
            if (!g.containsNode(node1)) {
                output.println("unknown: " + node1);
            }
            if (!g.containsNode(node2)) {
                output.println("unknown: " + node2);
            }
        } else {
            output.println("path from " + node1 + " to " + node2 + ":");
            if (shortestPath == null) {
                output.println("no path found");
            } else if (node1.equals(node2)) {
                output.println("total cost: 0.000");
            }
            double totalWeight = 0.0;
            for (Path<String> segments : shortestPath) {
                double weight = segments.getCost();
                totalWeight += weight;
                String formattedWeight = String.format(" with weight %.3f", weight);
                output.println(segments.getStart() + " to " + segments.getEnd() + formattedWeight);
            }
            String formattedTotalWeight = String.format("total cost: %.3f", totalWeight);
            output.println(formattedTotalWeight);
        }

    }
    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
