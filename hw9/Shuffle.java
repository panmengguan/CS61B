import java.util.List;
import java.util.Random;

import java.util.ArrayList;

/** HW10, problem #3.
 *  @author Kiet Lam
 */
public class Shuffle {
    /** Returns the result of non-destructively riffle-shuffling the
     *  two lists of T L1 and L2 together, using the
     *  Gilbert-Shannon-Reeds model, with R supplying pseudo-random
     *  numbers (so that shuffling the same lists twice with a
     *  generator starting in the same state both times results in the
     *  same list).
     */
    static <T> List<T> riffle(List<T> L1, List<T> L2, Random R) {
        List<T> ls = new ArrayList<T>();
        return helper(L1, L2, R, ls);
    }

    /** Recursively riffle-shuffle L1 and L2 and returns a shuffled List of T
     *  from R and ACCUMULATOR.*/
    static <T> List<T> helper(List<T> L1, List<T> L2, Random R,
                              List<T> accumulator) {
        if (L1.size() == 0 && L2.size() == 0) {
            return accumulator;
        }

        if (L1.size() == 0) {
            accumulator.addAll(L2);
            return accumulator;
        }

        if (L2.size() == 0) {
            accumulator.addAll(L1);
            return accumulator;
        }

        double r1 = ((double) L1.size()) / (L1.size() + L2.size());
        double r2 = ((double) L2.size()) / (L1.size() + L2.size());

        double ran = R.nextDouble();

        T nexVal;

        if (ran <= r1) {
            nexVal = L1.get(0);
            L1.remove(0);
        } else {
            nexVal = L2.get(0);
            L2.remove(0);
        }

        accumulator.add(nexVal);

        return helper(L1, L2, R, accumulator);
    }
}
