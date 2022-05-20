package pathfinder;

import graph.Graph;
import java.util.*;

import pathfinder.datastructures.Path;

public class DijkstraAlg {

    public static <N> List<Path<N>> findMinCostPath(N start, N dest, Graph<N, Double> g) {

        Queue<List<Path<N>>> active = new PriorityQueue<>();
        Set<N> finished = new HashSet<>();

        List<Path<N>> l = new ArrayList<>();
        l.add(new Path<>(start));
        active.add(l);

        while (!active.isEmpty()) {
            List<Path<N>> minPath = active.remove();
            N minDest = minPath.get(minPath.size() - 1).getEnd();
            double minCost = minPath.get(minPath.size() - 1).getCost();
            if (minDest.equals(dest)) {
                return minPath;
            }
            if (finished.contains(minDest)) {
                continue;
            }
            for (N child : g.getChildren(minDest)) {
                if (!finished.contains(child)) {
                    minPath.get(minPath.size() - 1).extend(child, g.getEdge(minDest, child));
                    List<Path<N>> newPath = new ArrayList<>(minPath);
                    newPath.add(new Path<N>(child));
                    active.add(newPath);
                }
            }
            finished.add(minDest);
        }
        return null;
    }
}
