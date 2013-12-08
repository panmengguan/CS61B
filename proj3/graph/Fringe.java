package graph;

/** Fringe class for graph traversal.
 *  @author Kiet Lam*/
interface Fringe<Vertex> {

    /** Push V onto the fringe.*/
    void push(Vertex v);

    /** Pop and returns a vertex from the fringe.*/
    Vertex pop();

    /** Returns if the fringe is empty.*/
    boolean isEmpty();

    /** Empty our fringe.*/
    void empty();
}
