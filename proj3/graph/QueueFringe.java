package graph;

import java.util.LinkedList;
import java.util.Queue;

/** A fringe backed by a queue
 * @author Kiet Lam */
class QueueFringe<Vertex> implements Fringe<Vertex> {

    /** Stack of vertices.*/
    private Queue<Vertex> queue;

    /** Construct a new stack fringe.*/
    public QueueFringe() {
         queue = new LinkedList<Vertex>();
    }

    @Override
    public void push(Vertex v) {
        queue.add(v);
    }

    @Override
    public Vertex pop() {
        return queue.remove();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
