import java.util.Iterator;
import utils.Filter;

/** A kind of Filter that lets through every other VALUE element of
 *  its input sequence, starting with the first.
 *  @author Kiet Lam
 */
class AlternatingFilter<Value> extends Filter<Value> {

    /** A filter of values from INPUT that lets through every other
     *  value. */
    AlternatingFilter(Iterator<Value> input) {
        super(input);
        _current = false;
    }

    @Override
    protected boolean keep() {
        _current = !_current;
        return _current;
    }

    /** Stores the current boolean.*/
    private boolean _current;
}
