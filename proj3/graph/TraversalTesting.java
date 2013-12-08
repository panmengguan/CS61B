package graph;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/** Unit tests for Traversal.
 *  @author Kiet Lam
 */
public class TraversalTesting {

    @Test
    public void testDFSAllVertices() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");
        Graph<String, String>.Vertex G = g.add("G");

        g.add(A, B);
        g.add(A, C);
        g.add(A, D);
        g.add(C, E);
        g.add(D, E);
        g.add(D, F);

        List<Graph<String, String>.Vertex> allVertices =
            new ArrayList<Graph<String, String>.Vertex>();

        allVertices.add(A);
        allVertices.add(B);
        allVertices.add(C);
        allVertices.add(D);
        allVertices.add(B);
        allVertices.add(C);
        allVertices.add(D);
        allVertices.add(E);
        allVertices.add(F);
        allVertices.add(A);
        allVertices.add(B);
        allVertices.add(C);
        allVertices.add(E);
        allVertices.add(F);
        allVertices.add(D);
        allVertices.add(E);
        allVertices.add(F);

        ListTraversal<String, String> traverser = new ListTraversal();
        traverser.depthFirstTraverse(g, A);

        assertEquals("incorrect traversal", allVertices,
                     traverser.getAllVertices());
    }

    @Test
    public void testDFSPrevisit() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");
        Graph<String, String>.Vertex G = g.add("G");

        g.add(A, B);
        g.add(A, C);
        g.add(A, D);
        g.add(C, E);
        g.add(D, E);
        g.add(D, F);

        List<Graph<String, String>.Vertex> preVertices =
            new ArrayList<Graph<String, String>.Vertex>();

        preVertices.add(B);
        preVertices.add(C);
        preVertices.add(D);
        preVertices.add(E);
        preVertices.add(F);

        ListTraversal<String, String> traverser = new ListTraversal();
        traverser.depthFirstTraverse(g, A);

        assertEquals("previsit vertices incorrect", preVertices,
                     traverser.getPrevisited());
    }

    @Test
    public void testDFSVisit() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");
        Graph<String, String>.Vertex G = g.add("G");

        g.add(A, B);
        g.add(A, C);
        g.add(A, D);
        g.add(C, E);
        g.add(D, E);
        g.add(D, F);

        List<Graph<String, String>.Vertex> vertices =
            new ArrayList<Graph<String, String>.Vertex>();

        vertices.add(A);
        vertices.add(B);
        vertices.add(C);
        vertices.add(D);
        vertices.add(E);
        vertices.add(F);

        ListTraversal<String, String> traverser = new ListTraversal();
        traverser.depthFirstTraverse(g, A);

        assertEquals("visit vertices incorrect", vertices,
                     traverser.getVertices());
    }

    @Test
    public void testDFSPostVisit() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");
        Graph<String, String>.Vertex G = g.add("G");

        g.add(A, B);
        g.add(A, C);
        g.add(A, D);
        g.add(C, E);
        g.add(D, E);
        g.add(D, F);

        List<Graph<String, String>.Vertex> postVertices =
            new ArrayList<Graph<String, String>.Vertex>();

        postVertices.add(A);
        postVertices.add(B);
        postVertices.add(C);
        postVertices.add(D);
        postVertices.add(E);
        postVertices.add(F);
    }

    @Test
    public void testBFSAllVertices() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");
        Graph<String, String>.Vertex F = g.add("F");

        g.add(A, B);
        g.add(A, C);
        g.add(A, D);

        g.add(D, E);
        g.add(D, F);

        List<Graph<String, String>.Vertex> allVertices =
            new ArrayList<Graph<String, String>.Vertex>();

        allVertices.add(A);
        allVertices.add(B);
        allVertices.add(C);
        allVertices.add(D);
        allVertices.add(B);
        allVertices.add(C);
        allVertices.add(D);
        allVertices.add(E);
        allVertices.add(F);
        allVertices.add(A);
        allVertices.add(B);
        allVertices.add(C);
        allVertices.add(E);
        allVertices.add(F);
        allVertices.add(D);
        allVertices.add(E);
        allVertices.add(F);

        assertEquals("incorrect BFS visit", allVertices,
                     traverser.getVertices());
    }

    @Test
    public void testBFSVisit() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");
        Graph<String, String>.Vertex F = g.add("F");

        g.add(A, B);
        g.add(A, C);
        g.add(A, D);

        g.add(D, E);
        g.add(D, F);

        List<Graph<String, String>.Vertex> vertices =
            new ArrayList<Graph<String, String>.Vertex>();

        vertices.add(A);
        vertices.add(B);
        vertices.add(C);
        vertices.add(D);
        vertices.add(E);
        vertices.add(F);

        ListTraversal<String, String> traverser = new ListTraversal();
        traverser.breadthFirstTraverse(g, A);

        assertEquals("incorrect BFS visit", vertices,
                     traverser.getVertices());
    }

    @Test
    public void testBFSPrevisit() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");
        Graph<String, String>.Vertex F = g.add("F");

        g.add(A, B);
        g.add(A, C);
        g.add(A, D);

        g.add(D, E);
        g.add(D, F);

        List<Graph<String, String>.Vertex> preVertices =
            new ArrayList<Graph<String, String>.Vertex>();

        preVertices.add(B);
        preVertices.add(C);
        preVertices.add(D);
        preVertices.add(E);
        preVertices.add(F);

        ListTraversal<String, String> traverser = new ListTraversal();
        traverser.breadthFirstTraverse(g, A);

        assertEquals("incorrect BFS visit", preVertices,
                     traverser.getPrevisited());
    }

    @Test
    public void testBFSPostvisit() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");
        Graph<String, String>.Vertex F = g.add("F");

        g.add(A, B);
        g.add(A, C);
        g.add(A, D);

        g.add(D, E);
        g.add(D, F);

        List<Graph<String, String>.Vertex> postVertices =
            new ArrayList<Graph<String, String>.Vertex>();

        postVertices.add(A);
        postVertices.add(B);
        postVertices.add(C);
        postVertices.add(D);
        postVertices.add(E);
        postVertices.add(F);

        ListTraversal<String, String> traverser = new ListTraversal();
        traverser.breadthFirstTraverse(g, A);

        assertEquals("incorrect BFS visit", postVertices,
                     traverser.getPostvisited());
    }

    /** Build up a list of vertices traversed.*/
    private static class ListTraversal extends Traversal<String, String> {

        /** List of vertices traversed.*/
        private List<Graph<String, String>.Vertex> vertices;

        /** List of pre-visted vertices.*/
        private List<Graph<String, String>.Vertex> preVertices;

        /** List of post-visited vertices.*/
        private List<Graph<String, String>.Vertex> postVertices;

        /** List of vertices touched (inclues all pre, visited, and post).*/
        private List<Graph<String, String>.Vertex> allVertices;

        /** Construct a list traversal object.*/
        ListTraversal() {
            vertices = new ArrayList<Graph<String, String>.Vertex>();
        }

        /** Returns a list of vertices traversed.*/
        List<Graph<String, String>.Vertex> getVertices() {
            return vertices;
        }

        /** Returns a list of vertices touched.*/
        List<Graph<String, String>.Vertex> getAllVertices() {
            return allVertices;
        }

        /** Returns a list of pre-visited vertices.*/
        List<Graph<String, String>.Vertex> getPrevisited() {
            return preVertices;
        }

        /** Returns a list of post-visited vertices.*/
        List<Graph<String, String>.Vertex> getPostvisited() {
            return postVertices;
        }

        @Override
        protected void visit(Graph<String, String>.Vertex v) {
            vertices.add(v);
            allVertices.add(v);
        }

        @Override
        protected void preVisit(Graph<VLabel, ELabel>.Edge e,
                                Graph<VLabel, ELabel>.Vertex v0) {
            preVertices.add(v0);
            allVertices.add(v0);
        }

        @Override
        protected void postVisit(Graph<VLabel, ELabel>.Vertex v) {
            postVertices.add(v);
            allVertices.add(v);
        }
    }
}
