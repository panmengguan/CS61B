package trip;

import graph.Graph;
import graph.Graphs;
import graph.Distancer;
import graph.UndirectedGraph;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashMap;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.io.PrintWriter;

/** A trip planner that finds the shortest path back between two locations
 *  @author Kiet Lam.*/
public class Planner {

    private Graph<Location, Distance> _graph;

    private Map<String, Graph<Location, Distance>.Vertex> _locationsMap;

    private static final String OUTPUT_PATTERN
        = "%d. Take %s %s for %.1f miles.";

    private Map<String, Distance> _distances;

    private PrintWriter _err;

    private PrintWriter _out;

    Planner(PrintWriter out, PrintWriter err) {
        _graph = new UndirectedGraph<Location, Distance>();
        _locationsMap = new HashMap<String, Graph<Location, Distance>.Vertex>();
        _distances = new HashMap<String, Distance>();
        _out = out;
        _err = err;
    }

    public void addLocation(Location location) {
        Graph<Location, Distance>.Vertex vertex =
            _graph.add(location);
        _locationsMap.put(location.name(), vertex);
    }

    public void addDistance(Distance distance) {
        String c0 = distance.c0();
        String c1 = distance.c1();

        if (!_locationsMap.containsKey(c0) || !_locationsMap.containsKey(c1)) {
            Main.reportError(_err, "Location does not exist");
            return;
        }

        Graph<Location, Distance>.Vertex v0
            = _locationsMap.get(c0);

        Graph<Location, Distance>.Vertex v1
            = _locationsMap.get(c1);

        _graph.add(v0, v1, distance);
        _distances.put(distance.identifier(), distance);
    }

    public List<String> planTrip(List<String> locations) {
        List<Graph<Location, Distance>.Edge> edges =
            new ArrayList<Graph<Location, Distance>.Edge>();

        List<String> directions = new ArrayList<String>();

        int directionCounter = 1;

        Graph<Location, Distance>.Vertex prevVertex = null;

        String end = "";

        for (int i = 0; i < locations.size(); i += 1) {
            if (prevVertex == null) {
                String location = locations.get(i);
                if (!_locationsMap.containsKey(location)) {
                    Main.reportError(_err, "location does not exist");
                    continue;
                } else {
                    prevVertex = _locationsMap.get(location);
                }

                i += 1;
            }

            String location = locations.get(i);
            end = location;

            if (!_locationsMap.containsKey(location)) {
                Main.reportError(_err, "location does not exist");
            } else {
                Graph<Location, Distance>.Vertex vertex
                    = _locationsMap.get(location);
                List<Graph<Location, Distance>.Edge> path
                    = Graphs.shortestPath(_graph, prevVertex, vertex,
                                          euclideanDistancer);
                if (path == null) {
                    Main.reportError(_err,
                                     "Path does not exist to location!");
                } else {
                    List<String> dirs = generateDirections(path, prevVertex,
                                                           directionCounter);
                    directions.addAll(dirs);
                    directionCounter += path.size();
                }
            }
        }

        directions = normalizeDirections(directions, end);
        return directions;
    }

    private static List<String>
    generateDirections(List<Graph<Location, Distance>.Edge> edges,
                       Graph<Location, Distance>.Vertex begin,
                       int counter) {
        List<String> directions = new ArrayList<String>();

        for (Graph<Location, Distance>.Edge e: edges) {
            Distance distance = e.getLabel();
            Graph<Location, Distance>.Vertex other
                = e.getV(begin);
            String road = distance.road();
            String direction = distance.directionTo(begin.getLabel().name());
            double dist = distance.distance();

            directions.add(String.format(OUTPUT_PATTERN, counter, road,
                                         direction, dist));

            counter += 1;
            begin = other;
        }

        return directions;
    }

    private static List<String> normalizeDirections(List<String> directions,
                                                    String end) {
        List<String> newDirections = new ArrayList<String>();
        String pat = "([0-9])+\\.\\sTake\\s([^\\s]+)\\s([^\\s]+)\\sfor"
            + "\\s([.0-9]+)\\smiles.*";

        Pattern pattern = Pattern.compile(pat);

        int currentCounter = 0;
        double cumulativeDist = 0.0;
        String road = "";
        String route = "";

        for (String dir: directions) {
            Matcher matcher = pattern.matcher(dir);
            matcher.matches();

            int counter = Integer.parseInt(matcher.group(1));
            String ro = matcher.group(2);
            String rout = matcher.group(3);
            double dist = Double.parseDouble(matcher.group(4));

            if (road.equals(ro) && route.equals(rout)) {
                cumulativeDist += dist;
            } else if (currentCounter == 0) {
                currentCounter += 1;
                road = ro;
                route = rout;
                cumulativeDist = dist;
            } else {
                double cumu = (double) Math.round(cumulativeDist * 10) / 10;
                String str = String.format(OUTPUT_PATTERN, currentCounter, road,
                                           route, cumu);
                newDirections.add(str);
                road = ro;
                route = rout;
                cumulativeDist = dist;
                currentCounter += 1;
            }
        }

        if (currentCounter != 0) {
            double cumu = (double) Math.round(cumulativeDist * 10) / 10;
            String str = String.format(OUTPUT_PATTERN, currentCounter, road,
                                       route, cumu);
            str = str.substring(0, str.length() - 2) + " to " + end;
            newDirections.add(str);
        }

        return newDirections;
    }

    private static Distancer<Location> euclideanDistancer =
        new Distancer<Location>() {

        @Override
        public double dist(Location l1, Location l2) {
            return Math.sqrt(Math.pow(l1.x() - l2.x(), 2)
                             + Math.pow(l1.y() - l2.y(), 2));
        }
    };
}
