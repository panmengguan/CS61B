import java.util.Iterator;
import utils.Filter;

/** A kind of Filter that lets all the VALUE elements of its input sequence
 *  that are larger than all the preceding values to go through the
 *  Filter.  So, if its input delivers (1, 2, 3, 3, 2, 1, 5), then it
 *  will produce (1, 2, 3, 5).
 *  @author Kiet Lam
 */
class MonotonicFilter<Value extends Comparable<Value>> extends Filter<Value> {

    /** A filter of values from INPUT that delivers a monotonic
     *  subsequence.  */
    MonotonicFilter(Iterator<Value> input) {
        super(input);
    }

    @Override
    protected boolean keep() {
        Value candidate = candidateNext();
        if (!hasStarted) {
            hasStarted = true;
            current = candidate;
            return true;
        } else {
            if (candidate.compareTo(current) > 0) {
                current = candidate;
                return true;
            } else {
                return false;
            }
        }
    }

    /** Boolean of whether we have started the filter.*/
    private boolean hasStarted = false;

    /** Stores the current integer.*/
    private Value current;
}
