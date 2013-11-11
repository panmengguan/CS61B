import java.util.Random;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


/** HW #8, Problem 1.
 *  @author Kiet Lam
 */
public class StableSort {

    /** A type of functional object for comparing ints (analogous to the
     *  Comparator<Integer> interface, but operates on the primitive type
     *  int.). */
    interface IntComparator {
        /** Defines an ordering on ints by returning an integer that is <, ==,
         *  or > 0 depending on whether, X comes before, at the same place, or
         *  after Y in the ordering.  */
        int compare(int x, int y);
    }

    /** Sort the array A according to the given ORDERING.  The sort is not
     *  stable. */
    static void sort(int[] A, IntComparator ordering) {
        sort(A, 0, A.length - 1, ordering);
    }

    /** Generator for creating random pivots. */
    private static Random gen = new Random();

    /** Swap elements A[I] and A[J]. */
    static void swap(int[] A, int i, int j) {
        int t = A[i]; A[i] = A[j]; A[j] = t;
    }

    /** Sort elements A[L .. U], according to ORDERING. */
    static void sort(int[] A, int L, int U, IntComparator ordering) {
        if (L >= U) {
            return;
        } else {
            int m;
            swap(A, L, L + gen.nextInt(U - L + 1));
            int T = A[L];
            m = L;
            for (int i = L + 1; i <= U; i += 1) {
                if (ordering.compare(A[i], T) < 0) {
                    m += 1;
                    swap(A, i, m);
                }
            }
            swap(A, L, m);
            sort(A, L, m - 1, ordering);
            sort(A, m + 1, U, ordering);
        }
    }

    /** Given that D is an Nx2 array of Strings (that is, each D[i] is
     *  a String[] with length 2), returns an array p such that
     *  D[p[0]], D[p[1]], D[p[2]], ... D[p[D.length-1]] is a stable sorting
     *  of D by the first strings.  D is not modified.   That is,
     *  produce a p that sorts D[0], D[1], etc., by D[0][0], D[1][0], D[2][0],
     *  etc., and where two D[i] have the same first element, leaves them
     *  in the same order they have in D.  */
    static int[] sortStably(final String[][] D) {
        int[] p = new int[D.length];
        for (int i = 0; i < p.length; i += 1) {
            p[i] = i;
        }

        final Map<String, List<Integer>> sortMap =
            new HashMap<String, List<Integer>>();

        for (int i = 0; i < D.length; i += 1) {
            String s1 = D[i][0];

            if (sortMap.containsKey(s1)) {
                List<Integer> ls = sortMap.get(s1);
                ls.add(i);
            } else {
                List<Integer> ls = new ArrayList<Integer>();
                ls.add(i);
                sortMap.put(s1, ls);
            }
        }

        sort(p, new IntComparator() {
                public int compare(int i, int j) {
                    String s1 = D[i][0];
                    String s2 = D[j][0];

                    String inner1 = D[i][1];
                    String inner2 = D[j][1];

                    if (s1.compareTo(s2) != 0) {
                        return s1.compareTo(s2);
                    } else {
                        List<Integer> ls = sortMap.get(s1);

                        int p1 = ls.indexOf(i);
                        int p2 = ls.indexOf(j);

                        return Integer.compare(p1, p2);
                    }
                }
            });
        return p;
    }
}
