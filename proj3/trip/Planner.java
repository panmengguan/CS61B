package trip;

import graph.Graph;
import graph.Graphs;
import graph.Distancer;
import graph.UndirectedGraph;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.io.PrintWriter;

import java.math.BigDecimal;

/** A trip planner that finds the shortest path back between two locations.
 *  @author Kiet Lam.*/
public class Planner {

    /** The graph of the map using Location as vertices and Distance as edges.*/
    private Graph<Location, Distance> _graph;

    /** The map of a location name and the actual vertices in the graph.*/
    private Map<String, Graph<Location, Distance>.Vertex> _locationsMap;

    /** The output pattern used to format output.*/
    private static final String OUTPUT_PATTERN
        = "%d. Take %s %s for %f miles.";

    /** The output pattern to format the final output when normalizing.*/
    private static final String FINAL_OUTPUT_PATTERN
        = "%d. Take %s %s for %.1f miles.";

    /** The destination output pattern.*/
    private static final String DESTINATION_PATTERN
        = "%d. Take %s %s for %.1f miles to %s.";

    /** The normal output pattern.*/
    private static final String NORMAL_PATTERN
        = "([0-9]+)\\.\\sTake\\s([^\\s]+)\\s([^\\s]+)\\sfor"
        + "\\s([.0-9]+)\\smiles\\.\\s*";

    /** The final destination pattern.*/
    private static final String FINAL_PATTERN
        = "([0-9]+)\\.\\sTake\\s([^\\s]+)\\s([^\\s]+)\\sfor"
        + "\\s([.0-9]+)\\smiles\\sto\\s([^\\s.]+)\\.";

    /** The map of a distance's road and route to the actual distance object.*/
    private Map<String, Distance> _distances;

    /** The error writer to write errors to.*/
    private PrintWriter _err;

    /** The output writer to write outputs to.*/
    private PrintWriter _out;

    /** Create a trip planner that will print output to OUT
     *  and print error to ERR.*/
    Planner(PrintWriter out, PrintWriter err) {
        _graph = new UndirectedGraph<Location, Distance>();
        _locationsMap = new HashMap<String, Graph<Location, Distance>.Vertex>();
        _distances = new HashMap<String, Distance>();
        _out = out;
        _err = err;
    }

    /** Add a location LOCATION to our graph.*/
    public void addLocation(Location location) {
        Graph<Location, Distance>.Vertex vertex =
            _graph.add(location);
        _locationsMap.put(location.name(), vertex);
    }

    /** Add a distance DISTANCE to our graph.*/
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

    /** Returns a list of directions to get to all the LOCATIONS.*/
    public List<String> planTrip(List<String> locations) {
        List<Graph<Location, Distance>.Edge> edges =
            new ArrayList<Graph<Location, Distance>.Edge>();
        Distancer<Location> euclideanDistancer = new Distancer<Location>() {

            @Override
            public double dist(Location l1, Location l2) {
                return Math.sqrt(Math.pow(l1.x() - l2.x(), 2)
                                 + Math.pow(l1.y() - l2.y(), 2));
            }
        };

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
                    prevVertex = vertex;
                }
            }
        }

        directions = normalizeDirections(directions, end);
        return directions;
    }

    /** Returns a list of directions by going through each edge in EDGES
     *  and building up a list of outputs starting from BEGIN and COUNTER.*/
    private static List<String>
    generateDirections(List<Graph<Location, Distance>.Edge> edges,
                       Graph<Location, Distance>.Vertex begin,
                       int counter) {
        List<String> directions = new ArrayList<String>();

        String destination = "";

        for (int i = 0; i < edges.size(); i += 1) {
            Graph<Location, Distance>.Edge e = edges.get(i);
            Distance distance = e.getLabel();
            Graph<Location, Distance>.Vertex other = e.getV(begin);
            String road = distance.road();
            String direction = distance.directionTo(begin.getLabel().name());
            BigDecimal dist = new BigDecimal(distance.distance());

            if (i == edges.size() - 1) {
                directions.add(String.format(DESTINATION_PATTERN, counter, road,
                                             direction, dist,
                                             other.getLabel().name()));
            } else {
                directions.add(String.format(OUTPUT_PATTERN, counter, road,
                                             direction, dist));
            }

            counter += 1;
            begin = other;
        }

        return directions;
    }

    /** Returns a list of normalized directions (all distances summed up)
     *  from DIRECTIONS and ending at END.*/
    private static List<String> normalizeDirections(List<String> directions,
                                                    String end) {
        List<String> newDirections = new ArrayList<String>();
        int currentCounter = 1;
        BigDecimal cumulativeDist = new BigDecimal(0.0);
        String destination = "";

        for (int i = 0; i < directions.size(); i += 1) {
            String dir = directions.get(i);
            int counter = getCounter(dir);
            String road = getRoad(dir);
            String route = getRoute(dir);
            cumulativeDist
                = cumulativeDist.add(new BigDecimal(getDistance(dir)));
            if (!getDestination(dir).equals("")) {
                destination = getDestination(dir);
            }

            if (i < directions.size() - 1) {
                String next = directions.get(i + 1);
                if (!road.equals(getRoad(next))
                    || !route.equals(getRoute(next))) {
                    newDirections.add(toOutput(currentCounter, road, route,
                                               cumulativeDist, destination));
                    cumulativeDist = new BigDecimal(0.0);
                    destination = "";
                    currentCounter += 1;
                }
            } else {
                newDirections.add(toOutput(currentCounter, road, route,
                                           cumulativeDist, destination));
                cumulativeDist = new BigDecimal(0.0);
            }
        }

        return newDirections;
    }

    /** Returns appropriate outut from COUNTER, ROAD, ROUTE, DIST and
     *  DESTINATION.*/
    private static String toOutput(int counter, String road, String route,
                            BigDecimal dist, String destination) {
        if (destination.equals("")) {
            return String.format(FINAL_OUTPUT_PATTERN, counter, road, route,
                                 dist);
        } else {
            return String.format(DESTINATION_PATTERN, counter, road, route,
                                 dist, destination);
        }
    }

    /** Returns the coutner in the string STR.*/
    private static int getCounter(String str) {
        String pat = "^([0-9]+)\\..*";
        return Integer.parseInt(getMatch(str, pat));
    }

    /** Returns the destination from DEST.*/
    private static String getDestination(String dest) {
        if (!dest.matches(FINAL_PATTERN)) {
            return "";
        }

        String pat = ".*\\s(.*)\\.$";
        return getMatch(dest, pat);
    }

    /** Returns the distance from DIST.*/
    private static double getDistance(String dist) {
        String pat = "[0-9]+\\.\\sTake\\s[^\\s]+\\s[^\\s]+\\sfor\\s"
            + "([-+]?[0-9]*\\.?[0-9]+)\\s.*";
        return Double.parseDouble(getMatch(dist, pat));
    }

    /** Returns the road of the string ROAD.*/
    private static String getRoad(String road) {
        String pat = "[0-9]+\\.\\sTake\\s([^\\s]+)\\s.*";
        return getMatch(road, pat);
    }

    /** Returns the route of the string ROUTE.*/
    private static String getRoute(String route) {
        String pat = "[0-9]+\\.\\sTake\\s[^\\s]+\\s([^\\s]+)\\s.*";
        return getMatch(route, pat);
    }

    /** Returns a match for string STR given a pattern PAT.*/
    private static String getMatch(String str, String pat) {
        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(str);
        matcher.matches();
        return matcher.group(1);
    }
}
