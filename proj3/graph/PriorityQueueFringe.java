package graph;

import java.util.PriorityQueue;
import java.util.Comparator;

/** A fringe based on a priority queue with a custom comparator
 *  @author Kiet Lam.*/
class PriorityQueueFringe<VLabel, ELabel>
    implements Fringe<Graph<VLabel, ELabel>.Vertex> {

    /** The priority queue for our fringe.*/
    private PriorityQueue<Graph<VLabel, ELabel>.Vertex> pQueue;

    /** The comparator to order our priority queue.*/
    private Comparator<VLabel> comparator;

    /** The initial capacity for our priority queue.*/
    private static final int INITIAL_CAPACITY = 100;

    /** Create a priority queue fringe using COMP.*/
    PriorityQueueFringe(final Comparator<VLabel> comp) {
        Comparator<Graph<VLabel, ELabel>.Vertex> vComp =
            new Comparator<Graph<VLabel, ELabel>.Vertex>() {
            @Override
            public int compare(Graph<VLabel, ELabel>.Vertex v1,
                               Graph<VLabel, ELabel>.Vertex v2) {
                return comp.compare(v1.getLabel(), v2.getLabel());
            }
        };

        pQueue = new PriorityQueue<Graph<VLabel, ELabel>.Vertex>(100, vComp);
    }

    @Override
        public void push(Graph<VLabel, ELabel>.Vertex v) {
        pQueue.add(v);
    }

    @Override
        public Graph<VLabel, ELabel>.Vertex pop() {
        return pQueue.remove();
    }

    @Override
    public boolean isEmpty() {
        return pQueue.isEmpty();
    }

    @Override
    public void empty() {
        pQueue.clear();
    }

    @Override
    public String toString() {
        return pQueue.toString();
    }
}
