package graph;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/** Unit tests for DirectedGraph.
 *  @author Kiet Lam
 */
public class DirectedGraphTesting {

    @Test
    public void emptyGraph() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    @Test
    public void testGraphNumVertices() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        g.add("A");
        g.add("B");
        assertEquals("incorrect number of vertices", 2, g.vertexSize());
    }

    @Test
    public void testGraphNumEdges() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex v1 = g.add("A");
        Graph<String, String>.Vertex v2 = g.add("B");
        g.add(v1, v2, "A-B");
        assertEquals("incorrect number of edges", 1, g.edgeSize());
    }

    @Test
    public void testGraphMultipleEdges() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex v1 = g.add("A");
        Graph<String, String>.Vertex v2 = g.add("B");
        g.add(v1, v2, "A-B");

        Graph<String, String>.Vertex v3 = g.add("C");
        Graph<String, String>.Vertex v4 = g.add("B");
        g.add(v3, v4, "C-B");

        assertEquals("incorrect number of edges", 2, g.edgeSize());
    }

    @Test
    public void testGraphDuplicatedEdges() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex v1 = g.add("A");
        Graph<String, String>.Vertex v2 = g.add("B");
        g.add(v1, v2, "A-B1");
        g.add(v1, v2, "A-B2");
        assertEquals("incorrect number of edges", 2, g.edgeSize());
    }

    @Test
    public void testSelfLoop() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex C = g.add("C");
        g.add(C, C);
        assertEquals("incorrect number of edges", 1, g.edgeSize());

        g.add(C, C);
        assertEquals("incorrect number of edges", 2, g.edgeSize());
    }

    @Test
    public void testOutDegree() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");
        Graph<String, String>.Vertex F = g.add("F");

        g.add(A, B);
        g.add(B, C);
        g.add(C, C);
        g.add(C, B);

        g.add(A, D);
        g.add(D, E);
        g.add(E, G);
        g.add(G, D);

        assertEquals("incorrect number of outdegree", 2, g.outDegree(A));
        assertEquals("incorrect number of outdegree", 1, g.outDegree(B));
        assertEquals("incorrect number of outdegree", 2, g.outDegree(C));
        assertEquals("incorrect number of outdegree", 1, g.outDegree(D));
        assertEquals("incorrect number of outdegree", 1, g.outDegree(E));
        assertEquals("incorrect number of outdegree", 0, g.outDegree(F));
    }

    @Test
    public void testInDegree() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");
        Graph<String, String>.Vertex F = g.add("F");

        g.add(A, B);
        g.add(B, C);
        g.add(C, C);
        g.add(C, B);

        g.add(A, D);
        g.add(D, E);
        g.add(E, G);
        g.add(G, D);

        assertEquals("incorrect number of indegree", 0, g.inDegree(A));
        assertEquals("incorrect number of indegree", 2, g.inDegree(B));
        assertEquals("incorrect number of indegree", 2, g.inDegree(C));
        assertEquals("incorrect number of indegree", 2, g.inDegree(D));
        assertEquals("incorrect number of indegree", 1, g.inDegree(E));
        assertEquals("incorrect number of indegree", 1, g.inDegree(G));
        assertEquals("incorrect number of indegree", 0, g.inDegree(F));
    }

    @Test
    public void testNotContain() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        g.add(A, B);
        g.add(B, C);
        g.add(C, C);
        g.add(C, B);

        g.add(A, D);
        g.add(D, E);
        g.add(E, G);
        g.add(G, D);

        assertFalse("graph does not contain edge", g.contains(B, A));
        assertFalse("graph does not contain edge", g.contains(A, C));
        assertFalse("graph does not contain edge", g.contains(A, G));
        assertFalse("graph does not contain edge", g.contains(E, D));
        assertFalse("graph does not contain edge", g.contains(G, E));
        assertFalse("graph does not contain edge", g.contains(C, A));
        assertFalse("graph does not contain edge", g.contains(B, D));
    }

    @Test
    public void testContains() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        g.add(A, B);
        g.add(B, C);
        g.add(C, C);
        g.add(C, B);

        g.add(A, D);
        g.add(D, E);
        g.add(E, G);
        g.add(G, D);

        assertTrue("graph does contain edge", g.contains(A, B));
        assertTrue("graph does contain edge", g.contains(B, C));
        assertTrue("graph does contain edge", g.contains(C, C));
        assertTrue("graph does contain edge", g.contains(C, B));
        assertTrue("graph does contain edge", g.contains(A, D));
        assertTrue("graph does contain edge", g.contains(D, E));
        assertTrue("graph does contain edge", g.contains(E, G));
        assertTrue("graph does contain edge", g.contains(G, D));
    }

    @Test
    public void testContainsEdgeLabel() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        g.add(A, B, "A-B");
        g.add(B, C, "B-C");
        g.add(C, C, "C-C");
        g.add(C, B, "C-B");

        g.add(A, D, "A-D");
        g.add(D, E, "D-E");
        g.add(E, G, "E-G");
        g.add(G, D, "G-D");

        assertTrue("graph does contain edge", g.contains(A, B, "A-B"));
        assertTrue("graph does contain edge", g.contains(B, C, "B-C"));
        assertTrue("graph does contain edge", g.contains(C, C, "C-C"));
        assertTrue("graph does contain edge", g.contains(C, B, "C-B"));
        assertTrue("graph does contain edge", g.contains(A, D, "A-D"));
        assertTrue("graph does contain edge", g.contains(D, E, "D-E"));
        assertTrue("graph does contain edge", g.contains(E, G, "E-G"));
        assertTrue("graph does contain edge", g.contains(G, D, "G-D"));

        assertFalse("graph does not contain edge", g.contains(A, B, "B-A"));
        assertFalse("graph does not contain edge", g.contains(B, C, "C-C"));
        assertFalse("graph does not contain edge", g.contains(C, C, "A-C"));
        assertFalse("graph does not contain edge", g.contains(C, B, "K-B"));
        assertFalse("graph does not contain edge", g.contains(A, D,
                                                              "Hilfinger"));
        assertFalse("graph does not contain edge", g.contains(D, E, "Amy"));
        assertFalse("graph does not contain edge", g.contains(E, G, ""));
        assertFalse("graph does not contain edge", g.contains(G, D, ""));
    }

    @Test
    public void testRemoveVertex() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");
        Graph<String, String>.Vertex F = g.add("F");

        g.add(A, B, "A-B");
        g.add(B, A, "B-A");
        g.add(B, C, "B-C");
        g.add(C, C, "C-C");
        g.add(C, B, "C-B");

        g.add(A, D, "A-D");
        g.add(D, E, "D-E");
        g.add(E, G, "E-G");
        g.add(G, D, "G-D");

        g.remove(A);
        g.remove(F);

        assertEquals("incorrect vertex size", 5, g.vertexSize());
        assertEquals("incorrect edge size", 6, g.edgeSize());

        assertFalse("Graph does not contain edge from A to B",
                    g.contains(A, B));
        assertFalse("Graph does not contain edge from B to A",
                    g.contains(B, A));
        assertFalse("Graph does not contain edge from A to D",
                    g.contains(A, D));

        assertTrue("Graph does contain edge B to C", g.contains(B, C));
    }

    @Test
    public void testRemoveEdge() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        Graph<String, String>.Edge e5 = g.add(A, B, "A-B");
        Graph<String, String>.Edge e6 = g.add(B, C, "B-C");
        Graph<String, String>.Edge e7 = g.add(C, C, "C-C");
        Graph<String, String>.Edge e8 = g.add(C, B, "C-B");

        Graph<String, String>.Edge e1 = g.add(A, D, "A-D");
        Graph<String, String>.Edge e2 = g.add(D, E, "D-E");
        Graph<String, String>.Edge e3 = g.add(E, G, "E-G");
        Graph<String, String>.Edge e4 = g.add(G, D, "G-D");

        g.remove(e6);

        assertEquals("incorrect number of vertices", 6, g.vertexSize());
        assertEquals("incorrect number of edges", 7, g.edgeSize());

        assertFalse("Graph does not contain edge from B to C",
                    g.contains(B, C));

        assertTrue("Graph does contain edge from A to B", g.contains(A, B));
        assertTrue("Graph does contain edge from C to B", g.contains(C, B));
    }

    @Test
    public void testRemoveVertices() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        Graph<String, String>.Edge e5 = g.add(A, B, "A-B");
        Graph<String, String>.Edge e6 = g.add(B, C, "B-C");
        Graph<String, String>.Edge e7 = g.add(C, C, "C-C");
        Graph<String, String>.Edge e8 = g.add(C, B, "C-B");

        Graph<String, String>.Edge e1 = g.add(A, D, "A-D");
        Graph<String, String>.Edge e2 = g.add(D, E, "D-E");
        Graph<String, String>.Edge e3 = g.add(E, G, "E-G");
        Graph<String, String>.Edge e4 = g.add(G, D, "G-D");

        g.remove(B, A);

        assertEquals("incorrect vertex size", 6, g.vertexSize());
        assertEquals("incorrect edge size", 8, g.edgeSize());

        assertTrue("Graph does contain edge A to B", g.contains(A, B));

        g.remove(B, C);

        assertEquals("incorrect vertex size", 6, g.vertexSize());
        assertEquals("incorrect edge size", 7, g.edgeSize());

        assertTrue("Graph does contain edge C to B", g.contains(C, B));
        assertFalse("Graph does not contain edge B to C", g.contains(B, C));
    }

    @Test
    public void testVerticesIteration() {
        Graph<String, String> g = new DirectedGraph<String, String>();

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
    public void testSuccessors() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        g.add(A, B, "A-B");
        g.add(B, C, "B-C");
        g.add(C, C, "C-C");
        g.add(C, B, "C-B");

        g.add(A, D, "A-D");
        g.add(D, E, "D-E");
        g.add(E, G, "E-G");
        g.add(G, D, "G-D");

        Set<Graph<String, String>.Vertex> expectedSuccessors =
            new HashSet<Graph<String, String>.Vertex>();

        expectedSuccessors.add(B);
        expectedSuccessors.add(C);

        Set<Graph<String, String>.Vertex> successors =
            new HashSet<Graph<String, String>.Vertex>();

        for (Graph<String, String>.Vertex e: g.successors(C)) {
            successors.add(e);
        }

        assertEquals("incorrect successors", expectedSuccessors,
                     successors);

        expectedSuccessors = new HashSet<Graph<String, String>.Vertex>();
        expectedSuccessors.add(B);
        expectedSuccessors.add(D);

        successors = new HashSet<Graph<String, String>.Vertex>();
        for (Graph<String, String>.Vertex e: g.successors(A)) {
            successors.add(e);
        }

        assertEquals("incorrect successors", expectedSuccessors,
                     successors);
    }

    @Test
    public void testPredecessors() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        g.add(A, B, "A-B");
        g.add(B, C, "B-C");
        g.add(C, C, "C-C");
        g.add(C, B, "C-B");

        g.add(A, D, "A-D");
        g.add(D, E, "D-E");
        g.add(E, G, "E-G");
        g.add(G, D, "G-D");

        Set<Graph<String, String>.Vertex> expectedPredecessors =
            new HashSet<Graph<String, String>.Vertex>();

        expectedPredecessors.add(B);
        expectedPredecessors.add(C);

        Set<Graph<String, String>.Vertex> predecessors =
            new HashSet<Graph<String, String>.Vertex>();

        for (Graph<String, String>.Vertex e: g.predecessors(C)) {
            predecessors.add(e);
        }

        assertEquals("incorrect predecessors", expectedPredecessors,
                     predecessors);

        expectedPredecessors = new HashSet<Graph<String, String>.Vertex>();
        predecessors = new HashSet<Graph<String, String>.Vertex>();

        for (Graph<String, String>.Vertex e: g.predecessors(A)) {
            predecessors.add(e);
        }

        assertEquals("A should have no predecesssors",
                     expectedPredecessors, predecessors);

        expectedPredecessors.add(A);
        expectedPredecessors.add(G);

        predecessors = new HashSet<Graph<String, String>.Vertex>();
        for (Graph<String, String>.Vertex e: g.predecessors(D)) {
            predecessors.add(e);
        }

        assertEquals("incorrect predecessors", expectedPredecessors,
                     predecessors);
    }

    @Test
    public void testOutEdge() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        Graph<String, String>.Edge e5 = g.add(A, B, "A-B");
        Graph<String, String>.Edge e6 = g.add(B, C, "B-C");
        Graph<String, String>.Edge e7 = g.add(C, C, "C-C");
        Graph<String, String>.Edge e8 = g.add(C, B, "C-B");

        Graph<String, String>.Edge e1 = g.add(A, D, "A-D");
        Graph<String, String>.Edge e2 = g.add(D, E, "D-E");
        Graph<String, String>.Edge e3 = g.add(E, G, "E-G");
        Graph<String, String>.Edge e4 = g.add(G, D, "G-D");

        Set<Graph<String, String>.Edge> expectedEdges =
            new HashSet<Graph<String, String>.Edge>();

        expectedEdges.add(e1);
        expectedEdges.add(e5);

        Set<Graph<String, String>.Edge> edges =
            new HashSet<Graph<String, String>.Edge>();

        for (Graph<String, String>.Edge e: g.outEdges(A)) {
            edges.add(e);
        }

        assertEquals("incorrect outedges", expectedEdges, edges);

        expectedEdges = new HashSet<Graph<String, String>.Edge>();
        edges = new HashSet<Graph<String, String>.Edge>();
    }

    @Test
    public void testInEdges() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        Graph<String, String>.Edge e5 = g.add(A, B, "A-B");
        Graph<String, String>.Edge e6 = g.add(B, C, "B-C");
        Graph<String, String>.Edge e7 = g.add(C, C, "C-C");
        Graph<String, String>.Edge e8 = g.add(C, B, "C-B");

        Graph<String, String>.Edge e1 = g.add(A, D, "A-D");
        Graph<String, String>.Edge e2 = g.add(D, E, "D-E");
        Graph<String, String>.Edge e3 = g.add(E, G, "E-G");
        Graph<String, String>.Edge e4 = g.add(G, D, "G-D");

        Set<Graph<String, String>.Edge> expectedEdges =
            new HashSet<Graph<String, String>.Edge>();

        Set<Graph<String, String>.Edge> edges =
            new HashSet<Graph<String, String>.Edge>();
        for (Graph<String, String>.Edge e: g.inEdges(A)) {
            edges.add(e);
        }

        assertEquals("A has no inedges", expectedEdges, edges);
        expectedEdges = new HashSet<Graph<String, String>.Edge>();
        edges = new HashSet<Graph<String, String>.Edge>();

        expectedEdges.add(e6);
        expectedEdges.add(e7);

        for (Graph<String, String>.Edge e: g.inEdges(C)) {
            edges.add(e);
        }

        assertEquals("incorrect inedges", expectedEdges, edges);
    }

    @Test
    public void testAllEdges() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        Graph<String, String>.Edge e5 = g.add(A, B, "A-B");
        Graph<String, String>.Edge e6 = g.add(B, C, "B-C");
        Graph<String, String>.Edge e7 = g.add(C, C, "C-C");
        Graph<String, String>.Edge e8 = g.add(C, B, "C-B");

        Graph<String, String>.Edge e1 = g.add(A, D, "A-D");
        Graph<String, String>.Edge e2 = g.add(D, E, "D-E");
        Graph<String, String>.Edge e3 = g.add(E, G, "E-G");
        Graph<String, String>.Edge e4 = g.add(G, D, "G-D");

        Set<Graph<String, String>.Edge> expectedEdges =
            new HashSet<Graph<String, String>.Edge>();

        expectedEdges.add(e1);
        expectedEdges.add(e2);
        expectedEdges.add(e3);
        expectedEdges.add(e4);
        expectedEdges.add(e5);
        expectedEdges.add(e6);
        expectedEdges.add(e7);
        expectedEdges.add(e8);

        Set<Graph<String, String>.Edge> edges =
            new HashSet<Graph<String, String>.Edge>();

        for (Graph<String, String>.Edge e: g.edges()) {
            edges.add(e);
        }

        assertEquals("incorrect inedges", expectedEdges, edges);
    }

    @Test
    public void testOrderEdges() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex G = g.add("G");
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");

        Graph<String, String>.Edge e5 = g.add(A, B, "5");
        Graph<String, String>.Edge e6 = g.add(B, C, "6");
        Graph<String, String>.Edge e7 = g.add(C, C, "7");
        Graph<String, String>.Edge e8 = g.add(C, B, "8");

        Graph<String, String>.Edge e1 = g.add(A, D, "1");
        Graph<String, String>.Edge e2 = g.add(D, E, "2");
        Graph<String, String>.Edge e3 = g.add(E, G, "3");
        Graph<String, String>.Edge e4 = g.add(G, D, "4");

        g.orderEdges(g.<String>naturalOrder());

        List<Graph<String, String>.Edge> expectedEdges =
            new ArrayList<Graph<String, String>.Edge>();

        expectedEdges.add(e1);
        expectedEdges.add(e2);
        expectedEdges.add(e3);
        expectedEdges.add(e4);
        expectedEdges.add(e5);
        expectedEdges.add(e6);
        expectedEdges.add(e7);
        expectedEdges.add(e8);

        List<Graph<String, String>.Edge> edges =
            new ArrayList<Graph<String, String>.Edge>();

        for (Graph<String, String>.Edge e: g.edges()) {
            edges.add(e);
        }

        assertEquals("incorrect inedges", expectedEdges, edges);
    }
}
