package trip;

/* You MAY add public @Test methods to this class.  You may also add
 * additional public classes containing "Testing" in their name. These
 * may not be part of your trip package per se (that is, it must be
 * possible to remove them and still have your package work). */

import org.junit.Test;
import ucb.junit.textui;
import static org.junit.Assert.*;

import graph.Graph;
import graph.Graphs;
import graph.UndirectedGraph;
import graph.Distancer;
import graph.Weightable;

import java.util.List;

/** Unit tests for the trip package. */
public class Testing {

    /** Run all JUnit tests in the graph package. */
    public static void main(String[] ignored) {
        System.exit(textui.runClasses(trip.Testing.class));
    }

    @Test
    public void testMe() {
    }

    @Test
    public void testAStarWeighted() {
        Graph<WeightedString, WeightedString> g
            = new UndirectedGraph<WeightedString, WeightedString>();

        Graph<WeightedString, WeightedString>.Vertex A
            = g.add(new WeightedString("A"));

        Graph<WeightedString, WeightedString>.Vertex B
            = g.add(new WeightedString("B"));

        g.add(A, B, new WeightedString("A-B", 1.0));

        Distancer<WeightedString> distancer = new Distancer<WeightedString>() {
            @Override
            public double dist(WeightedString v0, WeightedString v1) {
                return 0.0;
            }
        };

        List<Graph<WeightedString, WeightedString>.Edge> output
            = Graphs.shortestPath(g, A, B, distancer);
        assertNotNull("Why does this always fail???", output);
    }

    public class WeightedString implements Weightable {

        private String _str;

        private double _weight = Double.POSITIVE_INFINITY;

        WeightedString(String str) {
            _str = str;
        }

        WeightedString(String str, double weight) {
            _str = str;
            _weight = weight;
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
}
