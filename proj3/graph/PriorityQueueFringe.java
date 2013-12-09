package graph;

import java.util.PriorityQueue;
import java.util.Comparator;

/** A vertex fringe based on a priority queue with a custom comparator.
 *  @author Kiet Lam.*/
class PriorityQueueFringe<V> implements Fringe<V> {

    /** The priority queue for our fringe.*/
    private PriorityQueue<V> pQueue;

    /** The comparator to order our priority queue.*/
    private Comparator<V> comparator;

    /** The initial capacity for our priority queue.*/
    private static final int INITIAL_CAPACITY = 100;

    /** Create a priority queue fringe using COMP.*/
    PriorityQueueFringe(final Comparator<V> comp) {
        Comparator<V> vComp = new Comparator<V>() {
            @Override
            public int compare(V v1, V v2) {
                return comp.compare(v1, v2);
            }
        };

        pQueue = new PriorityQueue<V>(INITIAL_CAPACITY, vComp);
    }

    @Override
    public void push(V v) {
        pQueue.add(v);
    }

    @Override
    public V pop() {
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
