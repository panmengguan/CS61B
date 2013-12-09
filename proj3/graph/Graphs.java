package graph;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

/** Assorted graph algorithms.
 *  @author Kiet Lam
 */
public final class Graphs {

    /* A* Search Algorithms */

    /** Returns a path from V0 to V1 in G of minimum weight, according
     *  to the edge weighter EWEIGHTER.  VLABEL and ELABEL are the types of
     *  vertex and edge labels.  Assumes that H is a distance measure
     *  between vertices satisfying the two properties:
     *     a. H.dist(v, V1) <= shortest path from v to V1 for any v, and
     *     b. H.dist(v, w) <= H.dist(w, V1) + weight of edge (v, w), where
     *        v and w are any vertices in G.
     *
     *  As a side effect, uses VWEIGHTER to set the weight of vertex v
     *  to the weight of a minimal path from V0 to v, for each v in
     *  the returned path and for each v such that
     *       minimum path length from V0 to v + H.dist(v, V1)
     *              < minimum path length from V0 to V1.
     *  The final weights of other vertices are not defined.  If V1 is
     *  unreachable from V0, returns null and sets the minimum path weights of
     *  all reachable nodes.  The distance to a node unreachable from V0 is
     *  Double.POSITIVE_INFINITY. */
    public static <VLabel, ELabel> List<Graph<VLabel, ELabel>.Edge>
    shortestPath(Graph<VLabel, ELabel> G, Graph<VLabel, ELabel>.Vertex V0,
                 Graph<VLabel, ELabel>.Vertex V1,
                 Distancer<? super VLabel> h,
                 Weighter<? super VLabel> vweighter,
                 Weighting<? super ELabel> eweighter) {
        Set<Graph<VLabel, ELabel>.Vertex> closed =
            new HashSet<Graph<VLabel, ELabel>.Vertex>();
        Comparator<VertexCost<VLabel, ELabel>> comparator = getComparator();
        Fringe<VertexCost<VLabel, ELabel>> fringe
            = new PriorityQueueFringe<VertexCost<VLabel,
            ELabel>>(comparator);
        fringe.push(new VertexCost<VLabel, ELabel>(V0, 0.0));
        Map<Graph<VLabel, ELabel>.Vertex, Graph<VLabel, ELabel>.Vertex> from =
            new HashMap<Graph<VLabel, ELabel>.Vertex, Graph<VLabel,
            ELabel>.Vertex>();

        Map<Graph<VLabel, ELabel>.Vertex, Double> costMap
            = new HashMap<Graph<VLabel, ELabel>.Vertex, Double>();
        costMap.put(V0, 0.0);

        Map<Graph<VLabel, ELabel>.Vertex, Double> totalCostMap
            = new HashMap<Graph<VLabel, ELabel>.Vertex, Double>();

        totalCostMap.put(V0, costMap.get(V0) + h.dist(V0.getLabel(),
                                                      V1.getLabel()));
        while (!fringe.isEmpty()) {
            VertexCost<VLabel, ELabel> vc = fringe.pop();
            Graph<VLabel, ELabel>.Vertex vertex = vc.getVertex();

            if (vertex.equals(V1)) {
                return buildEdges(G, buildPath(from, V1), V0, eweighter);
            }

            closed.add(vertex);

            for (Graph<VLabel, ELabel>.Edge e: G.outEdges(vertex)) {
                Graph<VLabel, ELabel>.Vertex child = e.getV(vertex);
                double backwardCost = costMap.get(vertex)
                    + eweighter.weight(e.getLabel());
                double totalCost = backwardCost
                    + h.dist(e.getV(vertex).getLabel(), V1.getLabel());

                if (closed.contains(e.getV(vertex))) {
                    continue;
                }

                if (!totalCostMap.containsKey(child)
                    || totalCost < totalCostMap.get(child)) {
                    from.put(child, vertex);
                    costMap.put(child, backwardCost);
                    vweighter.setWeight(child.getLabel(), backwardCost);
                    totalCostMap.put(child, totalCost);

                    fringe.push(new VertexCost<VLabel, ELabel>(child,
                                                               totalCost));
                }
            }
        }

        return null;
    }

    /** Returns the edges from a list of PATH given GRAPH,
     *  BEGIN and EWEIGHTER in a Graph made up of VLABEL vertices
     *  and ELABEL edges.*/
    private static <VLabel, ELabel> List<Graph<VLabel, ELabel>.Edge>
    buildEdges(Graph<VLabel, ELabel> graph,
               List<Graph<VLabel, ELabel>.Vertex> path,
               Graph<VLabel, ELabel>.Vertex begin,
               Weighting<? super ELabel> eweighter) {

        List<Graph<VLabel, ELabel>.Edge> edges =
            new ArrayList<Graph<VLabel, ELabel>.Edge>();

        Graph<VLabel, ELabel>.Vertex current = begin;

        for (Graph<VLabel, ELabel>.Vertex v: path) {
            Graph<VLabel, ELabel>.Edge lowestE =
                getMinEdge(graph, current, v, eweighter);
            edges.add(lowestE);
            current = v;
        }

        edges.removeAll(Collections.singleton(null));

        return edges;
    }

    /** Returns the path given a map FROM and the CURRENT vertex
     *  in a Graph made up of VLABEL vertices
     *  and ELABEL edges.*/
    private static <VLabel, ELabel>
    List<Graph<VLabel, ELabel>.Vertex>
    buildPath(Map<Graph<VLabel, ELabel>.Vertex,
              Graph<VLabel, ELabel>.Vertex> from,
              Graph<VLabel, ELabel>.Vertex current) {
        if (from.containsKey(current)) {
            List<Graph<VLabel, ELabel>.Vertex> ls =
                buildPath(from, from.get(current));
            ls.add(current);
            return ls;
        } else {
            List<Graph<VLabel, ELabel>.Vertex> ls =
                new ArrayList<Graph<VLabel, ELabel>.Vertex>();
            ls.add(current);
            return ls;
        }
    }

    /** Returns the edges from a list of PATH given GRAPH,
     *  BEGIN and EWEIGHTER in a Graph made up of VLABEL vertices
     *  and ELABEL weighted edges.*/
    private static <VLabel, ELabel extends Weighted>
    List<Graph<VLabel, ELabel>.Edge>
    buildWeightedEdges(Graph<VLabel, ELabel> graph,
               List<Graph<VLabel, ELabel>.Vertex> path,
               Graph<VLabel, ELabel>.Vertex begin) {

        List<Graph<VLabel, ELabel>.Edge> edges =
            new ArrayList<Graph<VLabel, ELabel>.Edge>();

        Graph<VLabel, ELabel>.Vertex current = begin;

        for (Graph<VLabel, ELabel>.Vertex v: path) {
            Graph<VLabel, ELabel>.Edge lowestE =
                getMinWeightedEdge(graph, current, v);
            edges.add(lowestE);
            current = v;
        }

        edges.removeAll(Collections.singleton(null));

        return edges;
    }

    /** Returns the minimum edge to connect V0 to V1 in a GRAPH
     *  made up of VLABEL vertices and ELABEL edges using an EWEIGHTER.*/
    private static <VLabel, ELabel> Graph<VLabel, ELabel>.Edge
    getMinEdge(Graph<VLabel, ELabel> graph,
               Graph<VLabel, ELabel>.Vertex v0,
               Graph<VLabel, ELabel>.Vertex v1,
               final Weighting<? super ELabel> eweighter) {
        Graph<VLabel, ELabel>.Edge lowestE = null;
        double lowestCost = Double.POSITIVE_INFINITY;

        for (Graph<VLabel, ELabel>.Edge e: graph.inEdges(v1)) {
            if (!e.getV(v1).equals(v0)) {
                continue;
            }

            double cost = eweighter.weight(e.getLabel());

            if (cost < lowestCost) {
                lowestE = e;
                lowestCost = cost;
            }
        }

        return lowestE;
    }

    /** Returns the minimum edge to connect V0 to V1 in a GRAPH
     *  made up of VLABEL vertices and ELABEL edges using an EWEIGHTER.*/
    private static <VLabel, ELabel extends Weighted> Graph<VLabel, ELabel>.Edge
    getMinWeightedEdge(Graph<VLabel, ELabel> graph,
                           Graph<VLabel, ELabel>.Vertex v0,
                           Graph<VLabel, ELabel>.Vertex v1) {
        Graph<VLabel, ELabel>.Edge lowestE = null;
        double lowestCost = Double.POSITIVE_INFINITY;

        for (Graph<VLabel, ELabel>.Edge e: graph.inEdges(v1)) {
            if (!e.getV(v1).equals(v0)) {
                continue;
            }

            double cost = e.getLabel().weight();

            if (cost < lowestCost) {
                lowestE = e;
                lowestCost = cost;
            }
        }

        return lowestE;
    }

    /** Returns a path from V0 to V1 in G of minimum weight, according
     *  to the weights of its edge labels.  VLABEL and ELABEL are the types of
     *  vertex and edge labels.  Assumes that H is a distance measure
     *  between vertices satisfying the two properties:
     *     a. H.dist(v, V1) <= shortest path from v to V1 for any v, and
     *     b. H.dist(v, w) <= H.dist(w, V1) + weight of edge (v, w), where
     *        v and w are any vertices in G.
     *
     *  As a side effect, sets the weight of vertex v to the weight of
     *  a minimal path from V0 to v, for each v in the returned path
     *  and for each v such that
     *       minimum path length from V0 to v + H.dist(v, V1)
     *           < minimum path length from V0 to V1.
     *  The final weights of other vertices are not defined.
     *
     *  This function has the same effect as the 6-argument version of
     *  shortestPath, but uses the .weight and .setWeight methods of
     *  the edges and vertices themselves to determine and set
     *  weights. If V1 is unreachable from V0, returns null and sets
     *  the minimum path weights of all reachable nodes.  The distance
     *  to a node unreachable from V0 is Double.POSITIVE_INFINITY. */
    public static
    <VLabel extends Weightable, ELabel extends Weighted>
    List<Graph<VLabel, ELabel>.Edge>
    shortestPath(Graph<VLabel, ELabel> G,
                 Graph<VLabel, ELabel>.Vertex V0,
                 Graph<VLabel, ELabel>.Vertex V1,
                 Distancer<? super VLabel> h) {
        Set<Graph<VLabel, ELabel>.Vertex> closed =
            new HashSet<Graph<VLabel, ELabel>.Vertex>();
        Comparator<VertexCost<VLabel, ELabel>> comparator = getComparator();
        Fringe<VertexCost<VLabel, ELabel>> fringe
            = new PriorityQueueFringe<VertexCost<VLabel,
            ELabel>>(comparator);
        fringe.push(new VertexCost<VLabel, ELabel>(V0, 0.0));
        Map<Graph<VLabel, ELabel>.Vertex, Graph<VLabel, ELabel>.Vertex> from =
            new HashMap<Graph<VLabel, ELabel>.Vertex, Graph<VLabel,
            ELabel>.Vertex>();

        Map<Graph<VLabel, ELabel>.Vertex, Double> costMap
            = new HashMap<Graph<VLabel, ELabel>.Vertex, Double>();
        costMap.put(V0, 0.0);

        Map<Graph<VLabel, ELabel>.Vertex, Double> totalCostMap
            = new HashMap<Graph<VLabel, ELabel>.Vertex, Double>();

        totalCostMap.put(V0, costMap.get(V0) + h.dist(V0.getLabel(),
                                                      V1.getLabel()));
        while (!fringe.isEmpty()) {
            VertexCost<VLabel, ELabel> vc = fringe.pop();
            Graph<VLabel, ELabel>.Vertex vertex = vc.getVertex();

            if (vertex.equals(V1)) {
                return buildWeightedEdges(G, buildPath(from, V1), V0);
            }

            closed.add(vertex);

            for (Graph<VLabel, ELabel>.Edge e: G.outEdges(vertex)) {
                Graph<VLabel, ELabel>.Vertex child = e.getV(vertex);
                double backwardCost = costMap.get(vertex)
                    + e.getLabel().weight();
                double totalCost = backwardCost
                    + h.dist(e.getV(vertex).getLabel(), V1.getLabel());

                if (closed.contains(e.getV(vertex))) {
                    continue;
                }

                if (!totalCostMap.containsKey(child)
                    || totalCost < totalCostMap.get(child)) {
                    from.put(child, vertex);
                    costMap.put(child, backwardCost);
                    child.getLabel().setWeight(backwardCost);
                    totalCostMap.put(child, totalCost);

                    fringe.push(new VertexCost<VLabel, ELabel>(child,
                                                               totalCost));
                }
            }
        }

        return null;
    }

    /** Returns a distancer whose dist method always returns 0. */
    public static final Distancer<Object> ZERO_DISTANCER =
        new Distancer<Object>() {
            @Override
            public double dist(Object v0, Object v1) {
                return 0.0;
            }
        };

    /** Returns a comparator for a VertexCost in a Graph of
     *  VLABEL vertices and ELABEL edges.*/
    private static <VLabel, ELabel> Comparator<VertexCost<VLabel, ELabel>>
    getComparator() {
        return new Comparator<VertexCost<VLabel, ELabel>>() {

            @Override
            public int compare(VertexCost<VLabel, ELabel> v1,
                                   VertexCost<VLabel, ELabel> v2) {
                return Double.compare(v1.getCost(), v2.getCost());
            }
        };
    }

    /** Class to represent vertex and its cost.*/
    private static class VertexCost<VLabel, ELabel> {

        /** Our vertex.*/
        private Graph<VLabel, ELabel>.Vertex _vertex;

        /** The cost of the vertex to the goal.*/
        private double _cost;

        /** Create a vertex with a cost to the goal from VERTEX and COST.*/
        VertexCost(Graph<VLabel, ELabel>.Vertex vertex, double cost) {
            _vertex = vertex;
            _cost = cost;
        }

        /** Returns the vertex.*/
        Graph<VLabel, ELabel>.Vertex getVertex() {
            return _vertex;
        }

        /** Returns the cost.*/
        double getCost() {
            return _cost;
        }

        @Override
        public String toString() {
            return "Vertex: " + _vertex.toString() + " cost: " + _cost;
        }
    }
}
