package graph;

import java.util.Comparator;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;
import java.util.LinkedList;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Deque;

/** Implements a generalized traversal of a graph.  At any given time,
 *  there is a particular set of untraversed vertices---the "fringe."
 *  Traversal consists of repeatedly removing an untraversed vertex
 *  from the fringe, visting it, and then adding its untraversed
 *  successors to the fringe.  The client can dictate an ordering on
 *  the fringe, determining which item is next removed, by which kind
 *  of traversal is requested.
 *     + A depth-first traversal treats the fringe as a list, and adds
 *       and removes vertices at one end.  It also revisits the node
 *       itself after traversing all successors by calling the
 *       postVisit method on it.
 *     + A breadth-first traversal treats the fringe as a list, and adds
 *       and removes vertices at different ends.  It also revisits the node
 *       itself after traversing all successors as for depth-first
 *       traversals.
 *     + A general traversal treats the fringe as an ordered set, as
 *       determined by a Comparator argument.  There is no postVisit
 *       for this type of traversal.
 *  As vertices are added to the fringe, the traversal calls a
 *  preVisit method on the vertex.
 *
 *  Generally, the client will extend Traversal, overriding the visit,
 *  preVisit, and postVisit methods, as desired (by default, they do nothing).
 *  Any of these methods may throw StopException to halt the traversal
 *  (temporarily, if desired).  The preVisit method may throw a
 *  RejectException to prevent a vertex from being added to the
 *  fringe, and the visit method may throw a RejectException to
 *  prevent its successors from being added to the fringe.
 *  @author Kiet Lam
 */
public class Traversal<VLabel, ELabel> {

    /** Perform a traversal of G over all vertices reachable from V.
     *  ORDER determines the ordering in which the fringe of
     *  untraversed vertices is visited.  The effect of specifying an
     *  ORDER whose results change as a result of modifications made during the
     *  traversal is undefined. */
    public void traverse(Graph<VLabel, ELabel> G,
                         Graph<VLabel, ELabel>.Vertex v,
                         Comparator<VLabel> order) {
        _graph = G;
        _fringe = new PriorityQueueFringe<VLabel, ELabel>(order);
        _fringe.push(v);
        universalTraverse(_graph, _fringe);
    }

    /** Performs a depth-first traversal of G over all vertices
     *  reachable from V.  That is, the fringe is a sequence and
     *  vertices are added to it or removed from it at one end in
     *  an undefined order.  After the traversal of all successors of
     *  a node is complete, the node itself is revisited by calling
     *  the postVisit method on it. */
    public void depthFirstTraverse(Graph<VLabel, ELabel> G,
                                   Graph<VLabel, ELabel>.Vertex v) {
        _graph = G;
        _fringe = new StackFringe<Graph<VLabel, ELabel>.Vertex>();
        _fringe.push(v);

        while (!_fringe.isEmpty()) {
            Graph<VLabel, ELabel>.Vertex vert = _fringe.pop();
            if (_closed.contains(vert) && !_postVisited.contains(vert)) {
                postVisit(vert);
                _postVisited.add(vert);
            } else if (!_closed.contains(vert)) {
                _closed.add(vert);
                visit(vert);

                Queue<Graph<VLabel, ELabel>.Edge> edges
                    = new ArrayDeque<Graph<VLabel, ELabel>.Edge>();

                Stack<Graph<VLabel, ELabel>.Vertex> vertices
                    = new Stack<Graph<VLabel, ELabel>.Vertex>();

                for (Graph<VLabel, ELabel>.Edge e: G.outEdges(vert)) {
                    Graph<VLabel, ELabel>.Vertex vertex = e.getV(vert);

                    if (!_closed.contains(vertex)) {
                        edges.add(e);
                        vertices.add(vertex);
                    }
                }

                _fringe.push(vert);

                while (!edges.isEmpty()) {
                    Graph<VLabel, ELabel>.Edge edge = edges.remove();
                    Graph<VLabel, ELabel>.Vertex otherVert = edge.getV(vert);

                    if (!_closed.contains(otherVert)) {
                        preVisit(edge, vert);
                    }
                }

                while (!vertices.empty()) {
                    Graph<VLabel, ELabel>.Vertex ve = vertices.pop();
                    _fringe.push(ve);
                }
            }
        }
    }

    /** Performs a breadth-first traversal of G over all vertices
     *  reachable from V.  That is, the fringe is a sequence and
     *  vertices are added to it at one end and removed from it at the
     *  other in an undefined order.  After the traversal of all successors of
     *  a node is complete, the node itself is revisited by calling
     *  the postVisit method on it. */
    public void breadthFirstTraverse(Graph<VLabel, ELabel> G,
                                     Graph<VLabel, ELabel>.Vertex v) {
        _graph = G;
        _fringe = new QueueFringe<Graph<VLabel, ELabel>.Vertex>();
        _fringe.push(v);

        while (!_fringe.isEmpty()) {
            Graph<VLabel, ELabel>.Vertex vert = _fringe.pop();
            if (_closed.contains(vert) && !_postVisited.contains(vert)) {
                postVisit(vert);
                _postVisited.add(vert);
            } else if (!_closed.contains(vert)) {
                _closed.add(vert);
                visit(vert);

                Queue<Graph<VLabel, ELabel>.Edge> edges
                    = new ArrayDeque<Graph<VLabel, ELabel>.Edge>();

                Queue<Graph<VLabel, ELabel>.Vertex> vertices
                    = new ArrayDeque<Graph<VLabel, ELabel>.Vertex>();

                for (Graph<VLabel, ELabel>.Edge e: G.outEdges(vert)) {
                    Graph<VLabel, ELabel>.Vertex vertex = e.getV(vert);

                    if (!_closed.contains(vertex)) {
                        edges.add(e);
                        vertices.add(vertex);
                    }
                }

                while (!edges.isEmpty()) {
                    Graph<VLabel, ELabel>.Edge edge = edges.remove();
                    Graph<VLabel, ELabel>.Vertex otherVert = edge.getV(vert);

                    if (!_closed.contains(otherVert)) {
                        preVisit(edge, vert);
                    }
                }

                while (!vertices.isEmpty()) {
                    Graph<VLabel, ELabel>.Vertex ve = vertices.remove();
                    _fringe.push(ve);
                }

                _fringe.push(vert);
            }
        }
    }

    /** Continue the previous traversal starting from V.
     *  Continuing a traversal means that we do not traverse
     *  vertices that have been traversed previously. */
    public void continueTraversing(Graph<VLabel, ELabel>.Vertex v) {
        if (_fringe == null || _graph == null) {
            return;
        }

        _fringe.empty();
        _fringe.push(v);

        universalTraverse(_graph, _fringe);
    }

    /** If the traversal ends prematurely, returns the Vertex argument to
     *  preVisit, visit, or postVisit that caused a Visit routine to
     *  return false.  Otherwise, returns null. */
    public Graph<VLabel, ELabel>.Vertex finalVertex() {
        return _finalVertex;
    }

    /** If the traversal ends prematurely, returns the Edge argument to
     *  preVisit that caused a Visit routine to return false. If it was not
     *  an edge that caused termination, returns null. */
    public Graph<VLabel, ELabel>.Edge finalEdge() {
        return _finalEdge;
    }

    /** Returns the last graph argument to a traverse routine, or null if none
     *  of these methods have been called. */
    protected Graph<VLabel, ELabel> theGraph() {
        return _graph;
    }

    /** Method to be called when adding the node at the other end of E from V0
     *  to the fringe. If this routine throws a StopException,
     *  the traversal ends.  If it throws a RejectException, the edge
     *  E is not traversed. The default does nothing.
     */
    protected void preVisit(Graph<VLabel, ELabel>.Edge e,
                            Graph<VLabel, ELabel>.Vertex v0) {
    }

    /** Method to be called when visiting vertex V.  If this routine throws
     *  a StopException, the traversal ends.  If it throws a RejectException,
     *  successors of V do not get visited from V. The default does nothing. */
    protected void visit(Graph<VLabel, ELabel>.Vertex v) {
    }

    /** Method to be called immediately after finishing the traversal
     *  of successors of vertex V in pre- and post-order traversals.
     *  If this routine throws a StopException, the traversal ends.
     *  Throwing a RejectException has no effect. The default does nothing.
     */
    protected void postVisit(Graph<VLabel, ELabel>.Vertex v) {
    }

    /** Universal graph traversal using graph G, starting from
     *  vertex V and using fringe FRINGE to keep track of which nodes to
     *  expand.*/
    private void universalTraverse(Graph<VLabel, ELabel> g,
                                   Fringe<Graph<VLabel, ELabel>.Vertex> fringe)
    {
    }

    /** The Vertex (if any) that terminated the last traversal. */
    protected Graph<VLabel, ELabel>.Vertex _finalVertex;
    /** The Edge (if any) that terminated the last traversal. */
    protected Graph<VLabel, ELabel>.Edge _finalEdge;
    /** The last graph traversed. */
    protected Graph<VLabel, ELabel> _graph;

    /** Set of vertices that have been visited.*/
    private Set<Graph<VLabel, ELabel>.Vertex> _closed =
        new HashSet<Graph<VLabel, ELabel>.Vertex>();
    /** Set of vertices that have been post-visited.*/
    private Set<Graph<VLabel, ELabel>.Vertex> _postVisited =
        new HashSet<Graph<VLabel, ELabel>.Vertex>();
    /** Our current fringe.*/
    private Fringe<Graph<VLabel, ELabel>.Vertex> _fringe;
}
