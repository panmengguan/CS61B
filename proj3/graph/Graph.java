package graph;

import java.util.Comparator;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;
import java.util.Collection;
import java.util.Collections;

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
        return matrix.keySet().size();
    }

    /** Returns the number of edges in me. */
    public int edgeSize() {
        Set<Edge> edges = new LinkedHashSet<Edge>();

        for (Map.Entry<Vertex, Map<Vertex, Set<Edge>>> entry: matrix.entrySet())
        {
            for (Vertex vertex: entry.getValue().keySet()) {
                Set<Edge> eds = entry.getValue().get(vertex);
                edges.addAll(eds);
            }
        }

        return edges.size();
    }

    /** Returns true iff I am a directed graph. */
    public abstract boolean isDirected();

    /** Returns the number of outgoing edges incident to V. Assumes V is one of
     *  my vertices.  */
    public int outDegree(Vertex v) {
        Collection<Edge> edges = createCollection(outEdges(v));

        if (!isDirected()) {
            return edges.size() + matrix.get(v).get(v).size();
        }

        return edges.size();
    }

    /** Returns the number of incoming edges incident to V. Assumes V is one of
     *  my vertices. */
    public int inDegree(Vertex v) {
        Collection<Edge> edges = createCollection(inEdges(v));
        return edges.size();
    }

    /** Returns outDegree(V). This is simply a synonym, intended for
     *  use in undirected graphs. */
    public final int degree(Vertex v) {
        return outDegree(v);
    }

    /** Returns true iff there is an edge (U, V) in me with any label. */
    public boolean contains(Vertex u, Vertex v) {
        if (!matrix.containsKey(u)) {
            return false;
        }

        if (!matrix.get(u).containsKey(v)) {
            return false;
        }

        return matrix.get(u).get(v).size() > 0;
    }

    /** Returns true iff there is an edge (U, V) in me with label LABEL. */
    public boolean contains(Vertex u, Vertex v,
                            ELabel label) {
        if (!matrix.containsKey(u)) {
            return false;
        }

        if (!matrix.get(u).containsKey(v)) {
            return false;
        }

        Set<Edge> edges = matrix.get(u).get(v);

        for (Edge edge: edges) {
            if (edge.getLabel().equals(label)) {
                return true;
            }
        }

        return false;
    }

    /** Returns a new vertex labeled LABEL, and adds it to me with no
     *  incident edges. */
    public Vertex add(VLabel label) {
        Vertex vertex = new Vertex(label);

        for (Map.Entry<Vertex, Map<Vertex, Set<Edge>>> entry: matrix.entrySet())
        {
            Map<Vertex, Set<Edge>> edgeMap = entry.getValue();
            edgeMap.put(vertex, new LinkedHashSet<Edge>());
        }

        Map<Vertex, Set<Edge>> edgeSetMap =
            new LinkedHashMap<Vertex, Set<Edge>>();

        for (Vertex v: matrix.keySet()) {
            edgeSetMap.put(v, new LinkedHashSet<Edge>());
        }

        edgeSetMap.put(vertex, new LinkedHashSet<Edge>());
        matrix.put(vertex, edgeSetMap);
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

        Edge edge =  addEdge(from, to, label);

        if (!isDirected()) {
            addEdge(to, from, edge);
        }

        return edge;
    }

    /** Returns an edge created from FROM to TO with LABEL.*/
    private Edge addEdge(Vertex from, Vertex to, ELabel label) {
        Edge edge = new Edge(from, to, label);
        addEdge(from, to, edge);
        return edge;
    }

    /** Add an edge from FROM to TO with EDGE edge.*/
    private void addEdge(Vertex from, Vertex to, Edge edge) {
        matrix.get(from).get(to).add(edge);
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
        if (matrix.containsKey(v)) {
            matrix.remove(v);
        }

        for (Vertex vertex: matrix.keySet()) {
            Map<Vertex, Set<Edge>> edgeMap = matrix.get(vertex);
            if (edgeMap.containsKey(v)) {
                edgeMap.remove(v);
            }
        }
    }

    /** Remove E from me, if present.  E must be between my vertices,
     *  or the result is undefined.  */
    public void remove(Edge e) {
        remove(e.getV0(), e.getV1(), e);

        if (!isDirected()) {
            remove(e.getV1(), e.getV0(), e);
        }
    }

    /** Remove an EDGE with vertice FROM, TO.*/
    private void remove(Vertex from, Vertex to, Edge edge) {
        if (matrix.containsKey(from)) {
            Map<Vertex, Set<Edge>> edgeMap = matrix.get(from);
            if (edgeMap.containsKey(to)) {
                Set<Edge> edges = edgeMap.get(to);
                edges.remove(edge);
            }
        }
    }

    /** Remove all edges in EDGESTOREMOVE on FROM and TO.*/
    private void removeEdges(Vertex from, Vertex to) {
        if (matrix.containsKey(from)) {
            Map<Vertex, Set<Edge>> edgeMap = matrix.get(from);
            if (edgeMap.containsKey(to)) {
                Set<Edge> edges = edgeMap.get(to);
                edges.clear();
            }
        }
    }

    /** Remove all edges from V1 to V2 from me, if present.  The result is
     *  undefined if V1 and V2 are not among my vertices.  */
    public void remove(Vertex v1, Vertex v2) {
        removeEdges(v1, v2);

        if (!isDirected()) {
            removeEdges(v2, v1);
        }
    }

    /** Returns an Iterator over all vertices in arbitrary order. */
    public Iteration<Vertex> vertices() {
        Set<Vertex> vertices = new LinkedHashSet<Vertex>();

        for (Vertex vertex: matrix.keySet()) {
            vertices.add(vertex);
        }

        return Iteration.iteration(vertices.iterator());
    }

    /** Returns an iterator over all successors of V. */
    public Iteration<Vertex> successors(Vertex v) {
        Set<Edge> outEdges =
            new LinkedHashSet<Edge>(createCollection(outEdges(v)));
        Set<Vertex> vertices = new LinkedHashSet<Vertex>();

        for (Edge e: outEdges) {
            vertices.add(e.getV(v));
        }

        return Iteration.iteration(vertices.iterator());
    }

    /** Returns an iterator over all predecessors of V. */
    public Iteration<Vertex> predecessors(Vertex v) {
        Set<Edge> inEdges =
            new LinkedHashSet<Edge>(createCollection(inEdges(v)));
        Set<Vertex> vertices = new LinkedHashSet<Vertex>();

        for (Edge e: inEdges) {
            vertices.add(e.getV(v));
        }

        return Iteration.iteration(vertices.iterator());
    }

    /** Returns successors(V).  This is a synonym typically used on
     *  undirected graphs. */
    public final Iteration<Vertex> neighbors(Vertex v) {
        return successors(v);
    }

    /** Returns an iterator over all edges in me. */
    public Iteration<Edge> edges() {
        Set<Edge> inEdges = new LinkedHashSet<Edge>();

        for (Vertex vertex: matrix.keySet()) {
            inEdges.addAll(createCollection(inEdges(vertex)));
        }

        Set<Edge> outEdges = new LinkedHashSet<Edge>();

        for (Vertex vertex: matrix.keySet()) {
            outEdges.addAll(createCollection(outEdges(vertex)));
        }

        inEdges.addAll(outEdges);

        List<Edge> edges = new ArrayList<Edge>(inEdges);

        if (comparator != null) {
            Collections.sort(edges, new Comparator<Edge>() {
                    @Override
                    public int compare(Edge e1, Edge e2) {
                        return comparator.compare(e1.getLabel(), e2.getLabel());
                    }
                });
        }

        return Iteration.iteration(edges.iterator());
    }

    /** Returns iterator over all outgoing edges from V. */
    public Iteration<Edge> outEdges(Vertex v) {
        Set<Edge> edges = new LinkedHashSet<Edge>();
        Map<Vertex, Set<Edge>> edgeMap = matrix.get(v);

        for (Map.Entry<Vertex, Set<Edge>> entry: edgeMap.entrySet()) {
            edges.addAll(entry.getValue());
        }

        return Iteration.iteration(edges.iterator());
    }

    /** Returns iterator over all incoming edges to V. */
    public Iteration<Edge> inEdges(Vertex v) {
        Set<Edge> edges = new LinkedHashSet<Edge>();

        for (Vertex vertex: matrix.keySet()) {
            Map<Vertex, Set<Edge>> edgeMap = matrix.get(vertex);

            if (edgeMap.containsKey(v)) {
                edges.addAll(edgeMap.get(v));
            }
        }

        return Iteration.iteration(edges.iterator());
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
     *  edges in sorted order, according to COMP. Subsequent
     *  addition of edges may cause the edges to be reordered
     *  arbitrarily.  */
    public void orderEdges(Comparator<ELabel> comp) {
        comparator = comp;
    }

    /** Class representing a vertices coming to.*/
    /** Matrix of which node has an edge to another node.*/
    private Map<Vertex, Map<Vertex, Set<Edge>>> matrix =
        new LinkedHashMap<Vertex, Map<Vertex, Set<Edge>>>();

    /** Comparator for the edges.*/
    private Comparator<ELabel> comparator;

    /** Returns a collection from ITERABLE<T>.*/
    private static <T> Collection<T> createCollection(Iterable<T> iterable) {
        Collection<T> collection = new ArrayList<T>();

        for (T t: iterable) {
            collection.add(t);
        }

        return collection;
    }
}
