package graph;

/** Fringe class for graph traversal.
 *  @author Kiet Lam*/
interface Fringe<V> {

    /** Push V onto the fringe.*/
    void push(V v);

    /** Pop and returns a vertex from the fringe.*/
    V pop();

    /** Returns if the fringe is empty.*/
    boolean isEmpty();

    /** Empty our fringe.*/
    void empty();
}
