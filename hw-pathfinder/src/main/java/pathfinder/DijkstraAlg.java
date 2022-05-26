package pathfinder;

import graph.Graph;
import java.util.*;

import graph.Edge;
import pathfinder.datastructures.Path;

public class DijkstraAlg {

    public static <N> Path<N> findMinCostPath(N start, N dest, Graph<N, Double> g) {

        Queue<Path<N>> active = new PriorityQueue<>();
        active.add(new Path<>(start));
        Set<N> finished = new HashSet<>();

        while (!active.isEmpty()) {
            Path<N> minPath = active.remove();
            N minDest = minPath.getEnd();

            if (minDest.equals(dest)) {
                return minPath;
            }

            if (finished.contains(minDest)) {
                continue;
            }

            for (Edge<N, Double> edge : g.getEdges(minDest)) {
                N nextNode = edge.getChild();
                if (!finished.contains(nextNode)) {
                    Path<N> newPath = minPath.extend(nextNode, edge.getLabel());
                    active.add(newPath);
                }
            }
            finished.add(minDest);
        }
        return active.remove();
    }
}
