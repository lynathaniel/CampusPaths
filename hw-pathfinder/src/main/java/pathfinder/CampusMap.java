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

package pathfinder;

import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import graph.Graph;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.*;

public class CampusMap implements ModelAPI<Point> {

    private Graph<Point, Double> campusGraph;
    private Map<String, CampusBuilding> buildings;
    private Map<String, String> buildingNames;

    public CampusMap() {
        List<CampusBuilding> builds = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        List<CampusPath> paths = CampusPathsParser.parseCampusPaths("campus_paths.csv");
        campusGraph = new Graph<>();
        buildings = new HashMap<>();
        buildingNames = new HashMap<>();
        for (CampusPath path : paths) {
            Point start = new Point(path.getX1(), path.getY1());
            Point end = new Point(path.getX2(), path.getY2());
            campusGraph.addEdge(start, end, path.getDistance());
        }
        for (CampusBuilding building : builds) {
            String shortName = building.getShortName();
            buildings.put(shortName, building);
            buildingNames.put(shortName, building.getLongName());
        }
    }

    @Override
    public boolean shortNameExists(String shortName) {
        return buildings.containsKey(shortName);
    }

    @Override
    public String longNameForShort(String shortName) {
        if (!shortNameExists(shortName)) {
            throw new IllegalArgumentException();
        }
        return buildings.get(shortName).getLongName();
    }

    @Override
    public Map<String, String> buildingNames() {
        return Map.copyOf(buildingNames);
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        if (!buildings.containsKey(startShortName) || !buildings.containsKey(endShortName)) {
            throw new IllegalArgumentException();
        }
        CampusBuilding start = buildings.get(startShortName);
        CampusBuilding end = buildings.get(endShortName);

        Point startPoint = new Point(start.getX(), start.getY());
        Point endPoint = new Point(end.getX(), end.getY());

        return DijkstraAlg.findMinCostPath(startPoint, endPoint, campusGraph);
    }
}
