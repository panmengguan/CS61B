package graph;

import java.util.Comparator;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/* Do not add or remove public or protected members, or modify the signatures of
 * any public methods.  You may make changes that don't affect the API as seen
 * from outside the graph package:
 *   + You may make methods in Graph abstract, if you want different
 *     implementations in DirectedGraph and UndirectedGraph.
 *   + You may add bodies to abstract methods, modify existing bodies,
 *     or override inherited methods.
 *   + You may change parameter names, or add 'final' modifiers to parameters.
 *   + You may private and package private members.
 *   + You may add additional non-public classes to the graph package.
 */

/** Represents a general graph whose vertices are labeled with a type
 *  VLABEL and whose edges are labeled with a type ELABEL. The
 *  vertices are represented by the inner type Vertex and edges by
 *  inner type Edge.  A graph may be directed or undirected.  For
 *  an undirected graph, outgoing and incoming edges are the same.
 *  Graphs may have self edges and may have multiple edges between vertices.
 *
 *  The vertices and edges of the graph, the edges incident on a
 *  vertex, and the neighbors of a vertex are all accessible by
 *  iterators.  Changing the graph's structure by adding or deleting
 *  edges or vertices invalidates these iterators (subsequent use of
 *  them is undefined.)
 *  @author Kiet Lam
 */
public abstract class Graph<VLabel, ELabel> {

    /** Represents one of my vertices. */
    public class Vertex {

        /** A new vertex with LABEL as the value of getLabel(). */
        Vertex(VLabel label) {
            _label = label;
        }

        /** Returns the label on this vertex. */
        public VLabel getLabel() {
            return _label;
        }

        @Override
        public String toString() {
            return String.valueOf(_label);
        }

        /** The label on this vertex. */
        private final VLabel _label;

    }

    /** Represents one of my edges. */
    public class Edge {

        /** An edge (V0,V1) with label LABEL.  It is a directed edge (from
         *  V0 to V1) in a directed graph. */
        Edge(Vertex v0, Vertex v1, ELabel label) {
            _label = label;
            _v0 = v0;
            _v1 = v1;
        }

        /** Returns the label on this edge. */
        public ELabel getLabel() {
            return _label;
        }

        /** Return the vertex this edge exits. For an undirected edge, this is
         *  one of the incident vertices. */
        public Vertex getV0() {
            return _v0;
        }

        /** Return the vertex this edge enters. For an undirected edge, this is
         *  the incident vertices other than getV1(). */
        public Vertex getV1() {
            return _v1;
        }

        /** Returns the vertex at the other end of me from V.  */
        public final Vertex getV(Vertex v) {
            if (v == _v0) {
                return _v1;
            } else if (v == _v1) {
                return _v0;
            } else {
                throw new
                    IllegalArgumentException("vertex not incident to edge");
            }
        }

        @Override
        public String toString() {
            return String.format("(%s,%s):%s", _v0, _v1, _label);
        }

        /** Endpoints of this edge.  In directed edges, this edge exits _V0
         *  and enters _V1. */
        private final Vertex _v0, _v1;

        /** The label on this edge. */
        private final ELabel _label;

    }

    /*=====  Methods and variables of Graph =====*/

    /** Returns the number of vertices in me. */
    public int vertexSize() {
        // FIXME
        return 0;
    }

    /** Returns the number of edges in me. */
    public int edgeSize() {
        // FIXME
        return 0;
    }

    /** Returns true iff I am a directed graph. */
    public abstract boolean isDirected();

    /** Returns the number of outgoing edges incident to V. Assumes V is one of
     *  my vertices.  */
    public int outDegree(Vertex v) {
        // FIXME
        return 0;
    }

    /** Returns the number of incoming edges incident to V. Assumes V is one of
     *  my vertices. */
    public int inDegree(Vertex v) {
        // FIXME
        return 0;
    }

    /** Returns outDegree(V). This is simply a synonym, intended for
     *  use in undirected graphs. */
    public final int degree(Vertex v) {
        return outDegree(v);
    }

    /** Returns true iff there is an edge (U, V) in me with any label. */
    public boolean contains(Vertex u, Vertex v) {
        // FIXME
        return false;
    }

    /** Returns true iff there is an edge (U, V) in me with label LABEL. */
    public boolean contains(Vertex u, Vertex v,
                            ELabel label) {
        // FIXME
        return false;
    }

    /** Returns a new vertex labeled LABEL, and adds it to me with no
     *  incident edges. */
    public Vertex add(VLabel label) {
        Vertex vertex = new Vertex(label);

        Map<Vertex, Integer> edgeCounts = new HashMap<Vertex, Integer>();

        for (Map.Entry<Vertex, Integer> entry: edgeCounts.entrySet()) {
            edgeCounts.put(entry.getKey(), 0);
        }

        edgeCounts.put(vertex, 0);
        matrix.put(vertex, edgeCounts);

        return vertex;
    }

    /** Returns an edge incident on FROM and TO, labeled with LABEL
     *  and adds it to this graph. If I am directed, the edge is directed
     *  (leaves FROM and enters TO). */
    public Edge add(Vertex from, Vertex to, ELabel label) {
        if (!matrix.containsKey(from)) {
            return null;
        }

        if (!matrix.get(from).containsKey(to)) {
            return null;
        }

        if (isDirected()) {
            addEdge(to, from, label);
        }

        return addEdge(from, to, label);
    }

    /** Returns an edge created from FROM to TO with LABEL.*/
    private Edge addEdge(Vertex from, Vertex to, ELabel label) {
        Edge edge = new Edge(from, to, label);
        Map<Vertex, Integer> edgesTo = matrix.get(from);
        edgesTo.put(to, edgesTo.get(to) + 1);
        VertexFromTo vft = new VertexFromTo(from, to);

        List<Edge> edges = new ArrayList<Edge>();

        if (edgeMap.containsKey(vft)) {
            edges = edgeMap.get(vft);
        }

        edges.add(edge);
        edgeMap.put(vft, edges);
        return edge;
    }

    /** Returns an edge incident on FROM and TO with a null label
     *  and adds it to this graph. If I am directed, the edge is directed
     *  (leaves FROM and enters TO). */
    public Edge add(Vertex from,
                    Vertex to) {
        return add(from, to, null);
    }

    /** Remove V and all adjacent edges, if present. */
    public void remove(Vertex v) {
        matrix.remove(v);

        for (Vertex vertex: matrix.keySet()) {
            Map<Vertex, Integer> edgeCount = matrix.get(vertex);
            edgeCount.remove(v);
        }

        for (VertexFromTo vft: edgeMap.keySet()) {
            if (vft.containsV1(v)) {
                remove(v, vft.getV2());
            }

            if (vft.containsV2(v)) {
                remove(vft.getV1(), v);
            }
        }
    }

    /** Remove E from me, if present.  E must be between my vertices,
     *  or the result is undefined.  */
    public void remove(Edge e) {
        remove(e.getV0(), e.getV1());

        if (isDirected()) {
            remove(e.getV1(), e.getV0());
        }
    }

    /** Remove all edges from V1 to V2 from me, if present.  The result is
     *  undefined if V1 and V2 are not among my vertices.  */
    public void remove(Vertex v1, Vertex v2) {
        // FIXME
    }

    /** Returns an Iterator over all vertices in arbitrary order. */
    public Iteration<Vertex> vertices() {
        // FIXME
        return null;
    }

    /** Returns an iterator over all successors of V. */
    public Iteration<Vertex> successors(Vertex v) {
        // FIXME
        return null;
    }

    /** Returns an iterator over all predecessors of V. */
    public Iteration<Vertex> predecessors(Vertex v) {
        return null;
        // FIXME
    }

    /** Returns successors(V).  This is a synonym typically used on
     *  undirected graphs. */
    public final Iteration<Vertex> neighbors(Vertex v) {
        return successors(v);
    }

    /** Returns an iterator over all edges in me. */
    public Iteration<Edge> edges() {
        return null;
        // FIXME
    }

    /** Returns iterator over all outgoing edges from V. */
    public Iteration<Edge> outEdges(Vertex v) {
        return null;
        // FIXME
    }

    /** Returns iterator over all incoming edges to V. */
    public Iteration<Edge> inEdges(Vertex v) {
        return null;
        // FIXME
    }

    /** Returns outEdges(V). This is a synonym typically used
     *  on undirected graphs. */
    public final Iteration<Edge> edges(Vertex v) {
        return outEdges(v);
    }

    /** Returns the natural ordering on T, as a Comparator.  For
     *  example, if stringComp = Graph.<Integer>naturalOrder(), then
     *  stringComp.compare(x1, y1) is <0 if x1<y1, ==0 if x1=y1, and >0
     *  otherwise. */
    public static <T extends Comparable<? super T>> Comparator<T> naturalOrder()
    {
        return new Comparator<T>() {
            @Override
            public int compare(T x1, T x2) {
                return x1.compareTo(x2);
            }
        };
    }

    /** Cause subsequent calls to edges() to visit or deliver
     *  edges in sorted order, according to COMPARATOR. Subsequent
     *  addition of edges may cause the edges to be reordered
     *  arbitrarily.  */
    public void orderEdges(Comparator<ELabel> comparator) {
        // FIXME
    }

    /** Class representing a vertices coming to.*/
    private class VertexFromTo {

        /** Vertex from.*/
        private Vertex _v1;

        /** Vertex to.*/
        private Vertex _v2;

        /** Create a vertices representing from V1 to V2.*/
        VertexFromTo(Vertex v1, Vertex v2) {
            _v1 = v1;
            _v2 = v2;
        }

        /** Returns vertex 1.*/
        Vertex getV1() {
            return _v1;
        }

        /** Returns vertex 2.*/
        Vertex getV2() {
            return _v2;
        }

        /** Returns true iff contains V1 as v1.*/
        boolean containsV1(Vertex v1) {
            return _v1.equals(v1);
        }

        /** Returns true iff contains V2 as v2.*/
        boolean containsV2(Vertex v2) {
            return _v2.equals(v2);
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }

            if (other == this) {
                return true;
            }

            if (other instanceof Graph<?, ?>.VertexFromTo) {
                Graph<?, ?>.VertexFromTo pair =
                    (Graph<?, ?>.VertexFromTo) other;
                return _v1.equals(pair._v1) && _v2.equals(pair._v2);
            }

            return false;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + _v1.hashCode();
            result = prime * result + _v2.hashCode();
            return result;
        }
    }

    /** Creates a VertexFromTo object from V1 and V2.*/
    private VertexFromTo createFromTo(Vertex v1, Vertex v2) {
        return new VertexFromTo(v1, v2);
    }

    /** Matrix of which node has an edge to another node.*/
    private Map<Vertex, Map<Vertex, List<Edge>>> matrix =
        new HashMap<Vertex, Map<Vertex, List<Edge>>>();

    /** Map of vertex-from-to to a list of edges.*/
    private Map<VertexFromTo, List<Edge>> edgeMap =
        new HashMap<VertexFromTo, List<Edge>>();
}
