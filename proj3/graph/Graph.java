package graph;

import java.util.Comparator;

/* Do not add or remove public or protected members, or modify the signatures of
 * any public methods.  You may add bodies to abstract methods, modify
 * existing bodies, or override inherited methods. */

/** Represents a general graph whose vertices are labeled with a type
 *  VLABEL and whose edges are labeled with a type ELABEL. The
 *  vertices are represented by the type Vertex<VLabel> and edges by
 *  Edge<VLabel, ELabel>.  A graph may be directed or undirected.  For
 *  an undirected graph, outgoing and incoming edges are the same.
 *  The vertices and edges of the graph, the edges incident on a
 *  vertex, and the neighbors of a vertex are all accessible by
 *  iterators.  Changing the graph's structure by adding or deleting
 *  edges or vertices invalidates these iterators (subsequent use of
 *  them is undefined.)
 *  @author
 */
public abstract class Graph<VLabel, ELabel> {

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
    public int outDegree(Vertex<VLabel> v) {
        // FIXME
        return 0;
    }

    /** Returns the number of incoming edges incident to V. Assumes V is one of
     *  my vertices. */
    public int inDegree(Vertex<VLabel> v) {
        // FIXME
        return 0;
    }

    /** Returns outDegree(V). This is simply a synonym, intended for
     *  use in undirected graphs. */
    public final int degree(Vertex<VLabel> v) {
        return outDegree(v);
    }

    /** Returns true iff there is an edge (U, V) in me with any label. */
    public boolean contains(Vertex<VLabel> u, Vertex<VLabel> v) {
        // FIXME
        return false;
    }

    /** Returns true iff there is an edge (U, V) in me with label LABEL. */
    public boolean contains(Vertex<VLabel> u, Vertex<VLabel> v,
                                     ELabel label) {
        // FIXME
        return false;
    }

    /** Returns a new vertex labeled LABEL, and adds it to me with no
     *  incident edges. */
    public Vertex<VLabel> add(VLabel label) {
        // FIXME
        return null;
    }

    /** Returns an edge incident on FROM and TO, labeled with LABEL
     *  and adds it to this graph. If I am directed, the edge is directed
     *  (leaves FROM and enters TO). */
    public Edge<VLabel, ELabel> add(Vertex<VLabel> from,
                                             Vertex<VLabel> to,
                                             ELabel label) {
        // FIXME
        return null;
    }

    /** Returns an edge incident on FROM and TO with a null label
     *  and adds it to this graph. If I am directed, the edge is directed
     *  (leaves FROM and enters TO). */
    public Edge<VLabel, ELabel> add(Vertex<VLabel> from,
                                             Vertex<VLabel> to) {
        // FIXME
        return null;
    }

    /** Remove V and all adjacent edges, if present. */
    public void remove(Vertex<VLabel> v) {
        // FIXME
    }

    /** Remove E from me, if present.  E must be between my vertices,
     *  or the result is undefined.  */
    public void remove(Edge<VLabel, ELabel> e) {
        // FIXME
    }

    /** Remove all edges from V1 to V2 from me, if present.  The result is
     *  undefined if V1 and V2 are not among my vertices.  */
    public void remove(Vertex<VLabel> v1, Vertex<VLabel> v2) {
        // FIXME
    }

    /** Returns an Iterator over all vertices in arbitrary order. */
    public Iteration<Vertex<VLabel>> vertices() {
        // FIXME
        return null;
    }

    /** Returns an iterator over all successors of V. */
    public Iteration<Vertex<VLabel>> successors(Vertex<VLabel> v) {
        // FIXME
        return null;
    }

    /** Returns an iterator over all predecessors of V. */
    public Iteration<Vertex<VLabel>> predecessors(Vertex<VLabel> v) {
        return null;
        // FIXME
    }

    /** Returns successors(V).  This is a synonym typically used on
     *  undirected graphs. */
    public final Iteration<Vertex<VLabel>> neighbors(Vertex<VLabel> v) {
        return successors(v);
    }

    /** Returns an iterator over all edges in me. */
    public Iteration<Edge<VLabel, ELabel>> edges() {
        return null;
        // FIXME
    }

    /** Returns iterator over all outgoing edges from V. */
    public Iteration<Edge<VLabel, ELabel>> outEdges(Vertex<VLabel> v) {
        return null;
        // FIXME
    }

    /** Returns iterator over all incoming edges to V. */
    public Iteration<Edge<VLabel, ELabel>> inEdges(Vertex<VLabel> v) {
        return null;
        // FIXME
    }

    /** Returns outEdges(V). This is a synonym typically used
     *  on undirected graphs. */
    public final Iteration<Edge<VLabel, ELabel>> edges(Vertex<VLabel> v) {
        return outEdges(v);
    }

    /** Returns the natural ordering on ELabel<T>, as a Comparator.  For
     *  example, if stringComp = Graph.<String>naturalOrder(), then
     *  stringComp.compare(S1, S2) is S1.compareTo(S2) for Strings S1
     *  and S2. */
    public static <T extends Comparable<? super T>> Comparator<T>
    naturalOrder() {
        return new Comparator<T>() {
            @Override
            public int compare(T x1, T x2) {
                return x1.compareTo(x2);
            }
        };
    }

    /** Cause subsequent traversals and calls to edges() to visit or deliver
     *  edges in sorted order, according to COMPARATOR. Subsequent
     *  addition of edges may cause the edges to be reordered
     *  arbitrarily.  */
    public void orderEdges(Comparator<ELabel> comparator) {
        // FIXME
    }

}
