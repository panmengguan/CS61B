package graph;

import java.util.Stack;

/** A fringe backed by a stack
 * @author Kiet Lam */
class StackFringe<Vertex> implements Fringe<Vertex> {

    /** Stack of vertices.*/
    private Stack<Vertex> stack;

    /** Construct a new stack fringe.*/
    StackFringe() {
        stack = new Stack<Vertex>();
    }

    @Override
    public void push(Vertex v) {
        stack.push(v);
    }

    @Override
    public Vertex pop() {
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    @Override
    public void empty() {
        stack.clear();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
