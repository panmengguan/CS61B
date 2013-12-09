package graph;

import java.util.Comparator;

/** A fringe based on a priority queue with a custom comparator.
 *  @author Kiet Lam.*/
class VertexPriorityQueueFringe<VLabel, ELabel>
    extends PriorityQueueFringe<Graph<VLabel, ELabel>.Vertex> {

    /** Create a priority queue fringe using COMP.*/
    VertexPriorityQueueFringe(final Comparator<VLabel> comp) {
        super(new Comparator<Graph<VLabel, ELabel>.Vertex>() {
                @Override
                public int compare(Graph<VLabel, ELabel>.Vertex v1,
                                   Graph<VLabel, ELabel>.Vertex v2) {
                    return comp.compare(v1.getLabel(), v2.getLabel());
                }
            });
    }
}
