/* A possible source of useful stuff:
import java.util.Collections;
*/

import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/** HW #8, Problem 2.
 *  @author Kiet Lam
  */
class Ranges {
    /** Assuming that RANGES contains two-element arrays of integers,
     *  <x,y> with x <= y, representing intervals of ints, this returns
     *  a List of the intervals of [0 .. MAX] that are NOT covered by one of
     *  the intervals in RANGES. The resulting list consists of
     *  non-overlapping intervals ordered in increasing order of
     *  their starting points. */
    static List<int[]> uncovered(List<int[]> ranges, int max) {

        Collections.sort(ranges, new Comparator<int[]>() {
                public int compare(int[] a1, int[] a2) {
                    return Integer.compare(a1[0], a2[0]);
                }
            });

        List<int[]> ls = new ArrayList<int[]>();

        int begin = 0;
        int counter = 0;

        while (counter < ranges.size()) {
            int[] ars = ranges.get(counter);

            int x1 = ars[0];
            int x2 = ars[1];

            int[] range = new int[2];

            if (begin < x1) {
                range[0] = begin;
                range[1] = x1 - 1;
            }

            ls.add(range);

            begin = x2 + 1;
            counter += 1;
        }

        if (begin < max) {
            int[] ars = new int[2];
            ars[0] = begin;
            ars[1] = max;

            ls.add(ars);
        }

        return ls;
    }
}
