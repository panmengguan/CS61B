package graph;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

/** Unit tests for UndirectedGraph.
 *  @author Kiet Lam
 */
public class UndirectedGraphTesting {

    @Test
    public void emptyGraph() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    @Test
    public void testGraphNumVertices() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        g.add("A");
        g.add("B");
        assertEquals("incorrect number of vertices", 2, g.vertexSize());
    }

    @Test
    public void testGraphNumEdges() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex v1 = g.add("A");
        Graph<String, String>.Vertex v2 = g.add("B");
        g.add(v1, v2, "A-B");
        assertEquals("incorrect number of edges", 1, g.edgeSize());
    }

    @Test
    public void testGraphMultipleEdges() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex v1 = g.add("A");
        Graph<String, String>.Vertex v2 = g.add("B");
        Graph<String, String>.Vertex v3 = g.add("C");
        Graph<String, String>.Vertex v4 = g.add("D");
        g.add(v1, v2, "A-B");
        g.add(v3, v4, "C-D");

        assertEquals("incorrect number of edges", 2, g.edgeSize());
    }

    @Test
    public void testGraphDuplicatedEdges() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex v1 = g.add("A");
        Graph<String, String>.Vertex v2 = g.add("B");
        g.add(v1, v2, "A-B");
        g.add(v1, v2, "A-B");
        assertEquals("incorrect number of edges", 2, g.edgeSize());
    }

    @Test
    public void testSelfLoop() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex A = g.add("A");
        g.add(A, A);
        assertEquals("incorrect number of edges", 1, g.edgeSize());

        g.add(A, A);
        assertEquals("incorrect number of edges", 2, g.edgeSize());
    }

    @Test
    public void testDegree(){
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        g.add(G, A);
        g.add(G, C);
        g.add(C, B);
        g.add(A, B);
        g.add(A, D);
        g.add(D, E);

        assertEquals("incorrect number of degree", 2, g.degree(G));
        assertEquals("incorrect number of degree", 2, g.degree(C));
        assertEquals("incorrect number of degree", 2, g.degree(B));
        assertEquals("incorrect number of degree", 3, g.degree(A));
        assertEquals("incorrect number of degree", 2, g.degree(D));
        assertEquals("incorrect number of degree", 1, g.degree(E));
    }

    @Test
    public void testDegreeSelfLoop() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex A = g.add("A");

        g.add(A, A);

        assertEquals("incorrect number of degree", 2, g.degree(A));
    }

    @Test
    public void testNotContain() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex B = g.add("B");
        assertFalse("graph does not contain edge", g.contains(A, B));
    }

    @Test
    public void testContains() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        g.add(G, A);
        g.add(G, C);
        g.add(C, B);
        g.add(A, B);
        g.add(A, D);
        g.add(D, E);

        assertTrue("graph does contain edge from G to A", g.contains(G, A));
        assertTrue("graph does contain edge from G to C", g.contains(G, C));
        assertTrue("graph does contain edge from C to B", g.contains(C, B));
        assertTrue("graph does contain edge from A to B", g.contains(A, B));
        assertTrue("graph does contain edge from A to D", g.contains(A, D));
        assertTrue("graph does contain edge from D to E", g.contains(D, E));

        assertTrue("graph does contain edge from A to G", g.contains(A, G));
        assertTrue("graph does contain edge from C to G", g.contains(C, G));
        assertTrue("graph does contain edge from B to C", g.contains(B, C));
        assertTrue("graph does contain edge from B to A", g.contains(B, A));
        assertTrue("graph does contain edge from D to A", g.contains(D, A));
        assertTrue("graph does contain edge from E to D", g.contains(E, D));

        assertFalse("graph does not contain edge from C to E", g.contains(C, E));
    }

    @Test
    public void testContainsEdgeLabel() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        g.add(G, A, "G-A");
        g.add(G, C, "G-C");
        g.add(C, B, "C-B");
        g.add(A, B, "A-B");
        g.add(A, D, "A-D");
        g.add(D, E, "D-E");

        assertTrue("graph does contain edge G-A from G to A",
                   g.contains(G, A, "G-A"));
        assertTrue("graph does contain edge G-C from G to C",
                   g.contains(G, C, "G-C"));
        assertTrue("graph does contain edge C-B from C to B",
                   g.contains(C, B, "C-B"));
        assertTrue("graph does contain edge A-B from A to B",
                   g.contains(A, B, "A-B"));
        assertTrue("graph does contain edge A-D from A to D",
                   g.contains(A, D, "A-D"));
        assertTrue("graph does contain edge D-E from D to E",
                   g.contains(D, E, "D-E"));

        assertTrue("graph does contain edge G-A from A to G",
                   g.contains(A, G, "G-A"));
        assertTrue("graph does contain edge G-C from C to G",
                   g.contains(C, G, "G-C"));
        assertTrue("graph does contain edge C-B from B to C",
                   g.contains(B, C, "C-B"));
        assertTrue("graph does contain edge A-B from B to A",
                   g.contains(B, A, "A-B"));
        assertTrue("graph does contain edge A-D from D to A",
                   g.contains(D, A, "A-D"));
        assertTrue("graph does contain edge D-E from E to D",
                   g.contains(E, D, "D-E"));

        assertFalse("graph does not contain edge A-G from G to A",
                    g.contains(G, A, "A-G"));
    }

    @Test
    public void testRemoveVertex() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        g.add(G, A);
        g.add(G, C);
        g.add(C, B);
        g.add(A, B);
        g.add(A, D);
        g.add(D, E);

        g.remove(A);

        assertEquals("incorrect vertex size", 5, g.vertexSize());
        assertEquals("incorrect edge size", 3, g.edgeSize());

        assertFalse("Graph does not contain edge from G to A", g.contains(G, A));
        assertFalse("Graph does not contain edge from A to G", g.contains(A, G));

        assertTrue("Graph does contain edge G to C", g.contains(G, C));
    }

    @Test
    public void testRemoveEdge() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        Graph<String, String>.Edge e1 = g.add(G, A);
        Graph<String, String>.Edge e2 = g.add(G, C);
        Graph<String, String>.Edge e3 = g.add(C, B);
        Graph<String, String>.Edge e4 = g.add(A, B);
        Graph<String, String>.Edge e5 = g.add(A, D);
        Graph<String, String>.Edge e6 = g.add(D, E);

        g.remove(e1);

        assertEquals("incorrect number of vertices", 6, g.vertexSize());
        assertEquals("incorrect number of edges", 5, g.edgeSize());

        assertFalse("Graph does not contain edge from G to A", g.contains(G, A));
        assertFalse("Graph does not contain edge from A to G", g.contains(A, G));

        assertTrue("Graph does contain edge from A to B", g.contains(A, B));
        assertTrue("Graph does contain edge from A to D", g.contains(A, D));
    }

    @Test
    public void testRemoveVertices() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        g.add(G, A);
        g.add(G, C);
        g.add(C, B);
        g.add(A, B);
        g.add(A, D);
        g.add(D, E);

        g.add(B, A);
        g.remove(A, B);

        assertEquals("incorrect vertex size", 6, g.vertexSize());
        assertEquals("incorrect edge size", 5, g.edgeSize());

        assertFalse("Graph does not contain edge from G to A", g.contains(G, A));
        assertFalse("Graph does not contain edge from A to G", g.contains(A, G));

        assertTrue("Graph does contain edge G to E", g.contains(G, E));
        g.remove(G, E);

        assertEquals("incorrect vertex size", 6, g.vertexSize());
        assertEquals("incorrect edge size", 5, g.edgeSize());

        assertTrue("Graph does contain edge G to E", g.contains(G, E));
    }

    @Test
    public void testVerticesIteration() {
        Graph<String, String> g = new UndirectedGraph<String, String>();

        Set<Graph<String, String>.Vertex> vertices =
            new HashSet<Graph<String, String>.Vertex>();

        Set<Graph<String, String>.Vertex> vertices2 =
            new HashSet<Graph<String, String>.Vertex>();

        for (Graph<String, String>.Vertex e: g.vertices()) {
            vertices2.add(e);
        }

        assertEquals("Empty graph should have no vertices",
                     vertices, vertices2);

        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        g.add(G, A);
        g.add(G, C);
        g.add(C, B);
        g.add(A, B);
        g.add(A, D);
        g.add(D, E);

        vertices.add(G);
        vertices.add(A);
        vertices.add(C);
        vertices.add(B);
        vertices.add(D);
        vertices.add(E);

        vertices2 =
            new HashSet<Graph<String, String>.Vertex>();

        for (Graph<String, String>.Vertex e: g.vertices()) {
            vertices2.add(e);
        }

        assertEquals("Vertices should be equivalent", vertices, vertices2);
    }

    @Test
    public void testNeighbors() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        g.add(G, A);
        g.add(G, C);
        g.add(C, B);
        g.add(A, B);
        g.add(A, D);
        g.add(D, E);

        Set<Graph<String, String>.Vertex> expectedNeighbors =
            new HashSet<Graph<String, String>.Vertex>();

        expectedNeighbors.add(G);
        expectedNeighbors.add(D);
        expectedNeighbors.add(B);

        Set<Graph<String, String>.Vertex> neighbors =
            new HashSet<Graph<String, String>.Vertex>();

        for (Graph<String, String>.Vertex e: g.neighbors(A)) {
            neighbors.add(e);
        }

        assertEquals("Successors should be equivalent", expectedNeighbors,
                     neighbors);
    }

    @Test
    public void testEmptyNeighbors() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex v = g.add("A");
        Set<Graph<String, String>.Vertex> expectedNeighbors =
            new HashSet<Graph<String, String>.Vertex>();

        Set<Graph<String, String>.Vertex> neighbors =
            new HashSet<Graph<String, String>.Vertex>();

        for (Graph<String, String>.Vertex e: g.neighbors(v)) {
            neighbors.add(e);
        }

        assertEquals("Vertex A should have no neighbors", expectedNeighbors,
                     neighbors);
    }

    @Test
    public void testEdges() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        Graph<String, String>.Edge e1 = g.add(G, A);
        Graph<String, String>.Edge e2 = g.add(G, C);
        Graph<String, String>.Edge e3 = g.add(C, B);
        Graph<String, String>.Edge e4 = g.add(A, B);
        Graph<String, String>.Edge e5 = g.add(A, D);
        Graph<String, String>.Edge e6 = g.add(D, E);

        Set<Graph<String, String>.Edge> expectedEdges =
            new HashSet<Graph<String, String>.Edge>();

        expectedEdges.add(e1);
        expectedEdges.add(e2);
        expectedEdges.add(e4);

        Set<Graph<String, String>.Edge> edges =
            new HashSet<Graph<String, String>.Edge>();

        for (Graph<String, String>.Edge e: g.edges(A)) {
            edges.add(e);
        }

        assertEquals("Edges should be equivalent", expectedEdges, edges);
    }

    // TODO: Test orderEdges()
}
