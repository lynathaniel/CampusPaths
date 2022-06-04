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

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.DijkstraAlg;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import pathfinder.CampusMap;
import java.util.*;
import pathfinder.datastructures.*;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        CampusMap map = new CampusMap();

        // Returns JSON string of a mapping of the building names and their abbreviated forms.
        Spark.get("/buildings", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Map<String, String> buildingNames = map.buildingNames();
                Gson gson = new Gson();
                return gson.toJson(buildingNames);
            }
        });

        // Returns a JSON string of the Path object that represents the shortest
        // distance between two buildings.
        Spark.get("/findPath", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String start = request.queryParams("start");
                String end = request.queryParams("end");
                // Check for incorrect input
                if (!map.shortNameExists(start) || !map.shortNameExists(end)) {
                    Spark.halt(400);
                } else {
                    Path<Point> shortestPath = map.findShortestPath(start, end);
                    Gson gson = new Gson();
                    return gson.toJson(shortestPath);
                }
                return null;
            }
        });
    }

}
