package graph;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

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
        Graph<String, String>.Vertex F = g.add("F");

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
        allVertices.add(B);
        allVertices.add(C);
        allVertices.add(E);
        allVertices.add(E);
        allVertices.add(D);
        allVertices.add(D);
        allVertices.add(F);
        allVertices.add(F);
        allVertices.add(F);
        allVertices.add(D);
        allVertices.add(E);
        allVertices.add(C);
        allVertices.add(A);

        ListTraversal traverser = new ListTraversal();
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
        Graph<String, String>.Vertex F = g.add("F");

        g.add(A, B);
        g.add(A, C);
        g.add(C, E);
        g.add(A, D);
        g.add(D, E);
        g.add(D, F);

        List<Graph<String, String>.Vertex> preVertices =
            new ArrayList<Graph<String, String>.Vertex>();

        preVertices.add(B);
        preVertices.add(C);
        preVertices.add(D);
        preVertices.add(E);
        preVertices.add(D);
        preVertices.add(F);

        ListTraversal traverser = new ListTraversal();
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
        Graph<String, String>.Vertex F = g.add("F");

        g.add(A, B);
        g.add(A, C);
        g.add(C, E);
        g.add(A, D);
        g.add(D, E);
        g.add(D, F);

        List<Graph<String, String>.Vertex> vertices =
            new ArrayList<Graph<String, String>.Vertex>();

        vertices.add(A);
        vertices.add(B);
        vertices.add(C);
        vertices.add(E);
        vertices.add(D);
        vertices.add(F);

        ListTraversal traverser = new ListTraversal();
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
        Graph<String, String>.Vertex F = g.add("F");

        g.add(A, B);
        g.add(A, C);
        g.add(A, D);
        g.add(C, E);
        g.add(D, E);
        g.add(D, F);

        List<Graph<String, String>.Vertex> postVertices =
            new ArrayList<Graph<String, String>.Vertex>();

        postVertices.add(B);
        postVertices.add(F);
        postVertices.add(D);
        postVertices.add(E);
        postVertices.add(C);
        postVertices.add(A);

        ListTraversal traverser = new ListTraversal();
        traverser.depthFirstTraverse(g, A);

        assertEquals("visit vertices incorrect", postVertices,
                     traverser.getPostvisited());
    }

    @Test
    public void testDFSPostVisitDirected() {
        Graph<String, String> g = new DirectedGraph<String, String>();
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");
        Graph<String, String>.Vertex F = g.add("F");

        g.add(A, B);
        g.add(A, C);
        g.add(A, D);
        g.add(C, E);
        g.add(D, E);
        g.add(D, F);

        List<Graph<String, String>.Vertex> postVertices =
            new ArrayList<Graph<String, String>.Vertex>();

        postVertices.add(B);
        postVertices.add(E);
        postVertices.add(C);
        postVertices.add(F);
        postVertices.add(D);
        postVertices.add(A);

        ListTraversal traverser = new ListTraversal();
        traverser.depthFirstTraverse(g, A);

        assertEquals("visit vertices incorrect", postVertices,
                     traverser.getPostvisited());
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

        ListTraversal traverser = new ListTraversal();
        traverser.breadthFirstTraverse(g, A);

        assertEquals("incorrect BFS visit", allVertices,
                     traverser.getAllVertices());
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

        ListTraversal traverser = new ListTraversal();
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

        ListTraversal traverser = new ListTraversal();
        traverser.breadthFirstTraverse(g, A);

        assertEquals("incorrect BFS previsit", preVertices,
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

        ListTraversal traverser = new ListTraversal();
        traverser.breadthFirstTraverse(g, A);

        assertEquals("incorrect BFS postvisit", postVertices,
                     traverser.getPostvisited());
    }

    @Test
    public void testStopException() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex C = g.add("C");
        final Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");
        Graph<String, String>.Vertex F = g.add("F");

        g.add(A, B);
        g.add(A, C);
        g.add(A, D);

        g.add(D, E);
        g.add(D, F);

        ListTraversal traverser = new ListTraversal() {
                @Override
                protected void visit(Graph<String, String>.Vertex v) {
                    if (v.equals(D)) {
                        throw new StopException();
                    } else {
                        super.visit(v);
                    }
                }
            };

        traverser.breadthFirstTraverse(g, A);

        List<Graph<String, String>.Vertex> vertices =
            new ArrayList<Graph<String, String>.Vertex>();

        vertices.add(A);
        vertices.add(B);
        vertices.add(C);

        assertEquals("incorrect stoppage", vertices,
                     traverser.getVertices());
    }

    @Test
    public void testStopResume() {
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

        final List<Integer> sentinel = new ArrayList<Integer>();

        ListTraversal traverser = new ListTraversal() {
                @Override
                protected void visit(Graph<String, String>.Vertex v) {
                    if (sentinel.isEmpty()) {
                        throw new StopException();
                    } else {
                        super.visit(v);
                    }
                }
            };

        traverser.breadthFirstTraverse(g, A);

        sentinel.add(1);

        traverser.continueTraversing(A);

        List<Graph<String, String>.Vertex> vertices =
            new ArrayList<Graph<String, String>.Vertex>();

        vertices.add(A);
        vertices.add(B);
        vertices.add(C);
        vertices.add(D);
        vertices.add(E);
        vertices.add(F);

        assertEquals("incorrect stoppage", vertices,
                     traverser.getVertices());
    }

    @Test
    public void testRejectPrevisit() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex D = g.add("D");
        final Graph<String, String>.Vertex E = g.add("E");
        Graph<String, String>.Vertex F = g.add("F");

        g.add(A, B);
        g.add(A, C);
        g.add(A, D);

        g.add(D, E);
        g.add(D, F);

        ListTraversal traverser = new ListTraversal() {
                @Override
                protected void preVisit(Graph<String, String>.Edge e,
                                        Graph<String, String>.Vertex v0) {
                    Graph<String, String>.Vertex v1 = e.getV(v0);
                    if (v1.equals(E)) {
                        throw new RejectException();
                    } else {
                        super.preVisit(e, v0);
                    }
                }
            };

        traverser.breadthFirstTraverse(g, A);

        List<Graph<String, String>.Vertex> vertices =
            new ArrayList<Graph<String, String>.Vertex>();

        vertices.add(A);
        vertices.add(B);
        vertices.add(C);
        vertices.add(D);
        vertices.add(F);

        assertEquals("incorrect traverse rejection", vertices,
                     traverser.getVertices());
    }

    @Test
    public void testRejectSuccessors() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex B = g.add("B");
        Graph<String, String>.Vertex C = g.add("C");
        final Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");
        Graph<String, String>.Vertex F = g.add("F");

        g.add(A, B);
        g.add(A, C);
        g.add(A, D);

        g.add(D, E);
        g.add(D, F);

        ListTraversal traverser = new ListTraversal() {
                @Override
                protected void visit(Graph<String, String>.Vertex v) {
                    super.visit(v);

                    if (v.equals(D)) {
                        throw new RejectException();
                    }
                }
            };

        traverser.breadthFirstTraverse(g, A);

        List<Graph<String, String>.Vertex> vertices =
            new ArrayList<Graph<String, String>.Vertex>();

        vertices.add(A);
        vertices.add(B);
        vertices.add(C);
        vertices.add(D);

        assertEquals("incorrect traverse rejection", vertices,
                     traverser.getVertices());
    }

    @Test
    public void testCustomTraverse() {
        Graph<String, String> g = new UndirectedGraph<String, String>();
        Graph<String, String>.Vertex A = g.add("A");
        Graph<String, String>.Vertex D = g.add("D");
        Graph<String, String>.Vertex E = g.add("E");
        Graph<String, String>.Vertex F = g.add("F");
        Graph<String, String>.Vertex C = g.add("C");
        Graph<String, String>.Vertex B = g.add("B");

        g.add(A, D);
        g.add(D, E);
        g.add(D, F);

        g.add(A, C);
        g.add(A, B);

        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;

        List<Graph<String, String>.Vertex> vertices =
            new ArrayList<Graph<String, String>.Vertex>();

        vertices.add(A);
        vertices.add(B);
        vertices.add(C);
        vertices.add(D);
        vertices.add(E);
        vertices.add(F);

        ListTraversal traverser = new ListTraversal();
        traverser.traverse(g, A, comparator);

        assertEquals("incorrect custom visit", vertices,
                     traverser.getVertices());

        List<Graph<String, String>.Vertex> preVertices =
            new ArrayList<Graph<String, String>.Vertex>();

        preVertices.add(D);
        preVertices.add(C);
        preVertices.add(B);
        preVertices.add(E);
        preVertices.add(F);

        assertEquals("incorrect custom previsit", preVertices,
                     traverser.getPrevisited());
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
            preVertices = new ArrayList<Graph<String, String>.Vertex>();
            postVertices = new ArrayList<Graph<String, String>.Vertex>();
            allVertices = new ArrayList<Graph<String, String>.Vertex>();
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
        protected void preVisit(Graph<String, String>.Edge e,
                                Graph<String, String>.Vertex v0) {
            if (e.getV0().equals(v0)) {
                preVertices.add(e.getV1());
                allVertices.add(e.getV1());
            } else {
                preVertices.add(e.getV0());
                allVertices.add(e.getV0());
            }
        }

        @Override
        protected void postVisit(Graph<String, String>.Vertex v) {
            postVertices.add(v);
            allVertices.add(v);
        }
    }
}
