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

    @Test
    public void testAStarUnreachable() {
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
        Graph<String, String>.Edge e7 = g.add(D, F, "D-F");
        Graph<String, String>.Edge e8 = g.add(C, D, "C-D");

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
        assertNull("incorrect A* path", output);
    }

    @Test
    public void testAStarWeighted() {
        Graph<WeightedString, WeightedString> g =
            new UndirectedGraph<WeightedString, WeightedString>();
        Graph<WeightedString, WeightedString>.Vertex A =
            g.add(new WeightedString("A", 0.0));
        Graph<WeightedString, WeightedString>.Vertex B =
            g.add(new WeightedString("B", 0.0));
        Graph<WeightedString, WeightedString>.Vertex C =
            g.add(new WeightedString("C", 0.0));
        Graph<WeightedString, WeightedString>.Vertex D =
            g.add(new WeightedString("D", 0.0));
        Graph<WeightedString, WeightedString>.Vertex E =
            g.add(new WeightedString("E", 0.0));
        Graph<WeightedString, WeightedString>.Vertex F =
            g.add(new WeightedString("F", 0.0));
        Graph<WeightedString, WeightedString>.Vertex G =
            g.add(new WeightedString("G", 0.0));

        Graph<WeightedString, WeightedString>.Edge e1 =
            g.add(A, B, new WeightedString("A-B", 1.0));
        Graph<WeightedString, WeightedString>.Edge e2 =
            g.add(A, C, new WeightedString("A-C", 4.0));
        Graph<WeightedString, WeightedString>.Edge e3 =
            g.add(B, C, new WeightedString("B-C", 1.0));
        Graph<WeightedString, WeightedString>.Edge e4 =
            g.add(B, D, new WeightedString("B-D", 5.0));
        Graph<WeightedString, WeightedString>.Edge e5 =
            g.add(D, E, new WeightedString("D-E", 8.0));
        Graph<WeightedString, WeightedString>.Edge e7 =
            g.add(D, F, new WeightedString("D-F", 3.0));
        Graph<WeightedString, WeightedString>.Edge e8 =
            g.add(C, D, new WeightedString("C-D", 3.0));
        Graph<WeightedString, WeightedString>.Edge e6 =
            g.add(D, G, new WeightedString("D-G", 9.0));
        Graph<WeightedString, WeightedString>.Edge e9 =
            g.add(F, G, new WeightedString("F-G", 5.0));

        final Map<String, Double> h = heuristic();
        Distancer<WeightedString> distancer = new Distancer<WeightedString>() {
            @Override
            public double dist(WeightedString v0, WeightedString v1) {
                return h.get(v0.getString());
            }
        };

        List<Graph<WeightedString, WeightedString>.Edge> expectedEdges =
            new ArrayList<Graph<WeightedString, WeightedString>.Edge>();
        expectedEdges.add(e1);
        expectedEdges.add(e3);
        expectedEdges.add(e8);
        expectedEdges.add(e7);
        expectedEdges.add(e9);

        List<Graph<WeightedString, WeightedString>.Edge> output =
            Graphs.shortestPath(g, A, G, distancer);
        assertEquals("incorrect A* path", expectedEdges, output);
    }

    @Test
    public void testAStarSideEffect() {
        Graph<WeightedString, WeightedString> g =
            new UndirectedGraph<WeightedString, WeightedString>();
        WeightedString wA = new WeightedString("A", 0.0);
        Graph<WeightedString, WeightedString>.Vertex A = g.add(wA);
        WeightedString wB = new WeightedString("B", 0.0);
        Graph<WeightedString, WeightedString>.Vertex B = g.add(wB);
        WeightedString wC = new WeightedString("C", 0.0);
        Graph<WeightedString, WeightedString>.Vertex C = g.add(wC);
        WeightedString wD = new WeightedString("D", 0.0);
        Graph<WeightedString, WeightedString>.Vertex D = g.add(wD);
        WeightedString wE = new WeightedString("E", 0.0);
        Graph<WeightedString, WeightedString>.Vertex E = g.add(wE);
        WeightedString wF = new WeightedString("F", 0.0);
        Graph<WeightedString, WeightedString>.Vertex F = g.add(wF);
        WeightedString wG = new WeightedString("G", 0.0);
        Graph<WeightedString, WeightedString>.Vertex G = g.add(wG);

        Graph<WeightedString, WeightedString>.Edge e1 =
            g.add(A, B, new WeightedString("A-B", 1.0));
        Graph<WeightedString, WeightedString>.Edge e2 =
            g.add(A, C, new WeightedString("A-C", 4.0));
        Graph<WeightedString, WeightedString>.Edge e3 =
            g.add(B, C, new WeightedString("B-C", 1.0));
        Graph<WeightedString, WeightedString>.Edge e4 =
            g.add(B, D, new WeightedString("B-D", 5.0));
        Graph<WeightedString, WeightedString>.Edge e5 =
            g.add(D, E, new WeightedString("D-E", 8.0));
        Graph<WeightedString, WeightedString>.Edge e7 =
            g.add(D, F, new WeightedString("D-F", 3.0));
        Graph<WeightedString, WeightedString>.Edge e8 =
            g.add(C, D, new WeightedString("C-D", 3.0));
        Graph<WeightedString, WeightedString>.Edge e6 =
            g.add(D, G, new WeightedString("D-G", 9.0));
        Graph<WeightedString, WeightedString>.Edge e9 =
            g.add(F, G, new WeightedString("F-G", 5.0));

        final Map<String, Double> h = heuristic();
        Distancer<WeightedString> distancer = new Distancer<WeightedString>() {
            @Override
            public double dist(WeightedString v0, WeightedString v1) {
                return h.get(v0.getString());
            }
        };

        Graphs.shortestPath(g, A, G, distancer);

        assertEquals("incorrect vertex weight", wA.weight(), 0.0, 1E-10);
        assertEquals("incorrect vertex weight", wB.weight(), 1.0, 1E-10);
        assertEquals("incorrect vertex weight", wC.weight(), 2.0, 1E-10);
        assertEquals("incorrect vertex weight", wD.weight(), 5.0, 1E-10);
        assertEquals("incorrect vertex weight", wF.weight(), 8.0, 1E-10);
        assertEquals("incorrect vertex weight", wG.weight(), 13.0, 1E-10);
    }

    private static class WeightedString implements Weightable, Weighted {

        private double _weight;

        private String _str;

        WeightedString(String str, double weight) {
            _str = str;
            setWeight(weight);
        }

        public String getString() {
            return _str;
        }

        @Override
        public String toString() {
            return _str;
        }

        @Override
        public void setWeight(double w) {
            _weight = w;
        }

        @Override
        public double weight() {
            return _weight;
        }
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
