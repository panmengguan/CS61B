package graph;

import java.util.Iterator;

/** An Iteration<TYPE> is an Iterator<TYPE> that may also be used in a foreach
 *  loop.  That is, it implements the Interable<TYPE> interface by simply
 *  returning itself.  For example, this allows one to write
 *      for (Edge<String, NoLabel> e: G.edges()) {
 *           ...
 *      }
 *  @author P. N. Hilfinger
 */
public abstract class Iteration<Type>
    implements Iterator<Type>, Iterable<Type> {

    @Override
    public Iterator<Type> iterator() {
        return this;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }
}
