package graph;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** Unit tests for A*.
 *  @author Kiet Lam
 */
public class GraphsTesting {

    @Test
    public void testAStar() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");
        Graph<String, String>.Vertex F = g.add("F");
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Edge e1 = g.add(A, B, "A-B");
        Graph<String, String>.Edge e2 = g.add(A, C, "A-C");
        Graph<String, String>.Edge e3 = g.add(B, C, "B-C");
        Graph<String, String>.Edge e4 = g.add(B, D, "B-D");
        Graph<String, String>.Edge e5 = g.add(D, E, "D-E");
        Graph<String, String>.Edge e6 = g.add(D, G, "D-G");
        Graph<String, String>.Edge e7 = g.add(D, F, "D-F");
        Graph<String, String>.Edge e8 = g.add(C, D, "C-D");
        Graph<String, String>.Edge e9 = g.add(F, G, "F-G");

        List<Graph<String, String>.Edge> expectedEdges =
            new ArrayList<Graph<String, String>.Edge>();
        expectedEdges.add(e1);
        expectedEdges.add(e3);
        expectedEdges.add(e8);
        expectedEdges.add(e7);
        expectedEdges.add(e9);

        final Map<String, Double> h = heuristic();
        Distancer<String> distancer = new Distancer<String>() {
            @Override
            public double dist(String v0, String v1) {
                return h.get(v0);
            }
        };

        final Map<String, Double> weights = edgeWeights();
        Weighting<String> eweighter = new Weighting<String>() {

            @Override
            public double weight(String str) {
                return weights.get(str);
            }
        };

        Weighter<String> vweighter = new Weighter<String>() {
            @Override
            public void setWeight(String str, double v) {
            }

            @Override
            public double weight(String str) {
                return 0.0;
            }
        };

        List<Graph<String, String>.Edge> output =
            Graphs.shortestPath(g, A, G, distancer, vweighter,
                                eweighter);
        assertEquals("incorrect A* path", expectedEdges, output);
    }

    private Map<String, Double> edgeWeights() {
        Map<String, Double> map = new HashMap<String, Double>();

        map.put("A-B", 1.0);
        map.put("A-C", 4.0);
        map.put("B-C", 1.0);
        map.put("C-D", 3.0);
        map.put("B-D", 5.0);
        map.put("D-E", 8.0);
        map.put("D-F", 3.0);
        map.put("D-G", 9.0);
        map.put("E-G", 2.0);
        map.put("F-G", 5.0);

        return map;
    }

    private Map<String, Double> heuristic() {
        Map<String, Double> map = new HashMap<String, Double>();

        map.put("A", 9.5);
        map.put("B", 9.0);
        map.put("C", 8.0);
        map.put("D", 7.0);
        map.put("E", 1.5);
        map.put("F", 4.0);
        map.put("G", 0.0);

        return map;
    }
}
