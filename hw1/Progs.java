/** HW #1 solutions.
 *
 *  @author Kiet Lam
 */
import java.util.Map;
import java.util.HashMap;

/** @author Kiet Lam. Progs class for HW1.*/
class Progs {

    /** Cache the factorSum so we don't have to re-compute.*/
    private static Map<Integer, Integer> factorCache
        = new HashMap<Integer, Integer>();

    /* 1a. */
    /** Returns the sum of all integers, k, such that 1 <= k < N and
     *  N is evenly divisible by k. */
    static int factorSum(int N) {

        if (factorCache.containsKey(N)) {
            return factorCache.get(N);
        }

        int factorTotal = 0;

        for (int i = 1; i < N; i += 1) {
            if (N % i == 0) {
                factorTotal += i;
            }
        }

        factorCache.put(N, factorTotal);

        return factorTotal;
    }


    /* 1b. */
    /** Print the set of all sociable pairs whose members are all
     *  between 1 and N>=0 (inclusive) on the standard output (one pair per
     *  line, smallest member of each pair first, with no repetitions). */
    static void printSociablePairs(int N) {
        for (int i = 0; i <= N; i += 1) {
            for (int j = i + 1; j <= N; j += 1) {
                if (factorSum(i) == j
                    && factorSum(j) == i) {
                    System.out.println(i + " " + j);
                }
            }
        }
    }

    /* 2a. */
    /** Returns a list consisting of the elements of A followed by the
     *  elements of B.  May modify items of A. Don't use 'new'. */
    static IntList dcatenate(IntList A, IntList B) {

        if (A == null) {
            return B;
        }

        if (B == null) {
            return A;
        }

        A.tail = dcatenate(A.tail, B);
        return A;
    }

    /* 2b. */
    /** Returns the sublist consisting of LEN items from list L,
     *  beginning with item #START (where the first item is #0).
     *  Does not modify the original list elements.
     *  It is an error if the desired items don't exist. */
    static IntList sublist(IntList L, int start, int len) {
        verifyArguments(L, start, len);

        if (len == 0) {
            return null;
        }

        if (start == 0) {
            return new IntList(L.head, sublist(L.tail, start, len - 1));
        }

        return sublist(L.tail, start - 1, len);
    }

    /* 2c. */
    /** Returns the sublist consisting of LEN items from list L,
     *  beginning with item #START (where the first item is #0).
     *  May modify the original list elements. Don't use 'new'.
     *  It is an error if the desired items don't exist. */
    static IntList dsublist(IntList L, int start, int len) {
        verifyArguments(L, start, len);

        if (len == 0) {
            return null;
        }

        if (start == 0) {
            L.tail = dsublist(L.tail, start, len - 1);
            return L;
        }

        return dsublist(L.tail, start - 1, len);
    }

    /**
     * Verify whether the arguments are valid for generating a
     * sublist from list L, START, and LEN.
     */
    static void verifyArguments(IntList L, int start, int len) {
        if (L == null && len > 0) {
            String exceptionMessage = "IntList does not have enough elements";
            throw new IllegalArgumentException(exceptionMessage);
        }

        if (len < 0) {
            throw new IllegalArgumentException("LEN cannot be negative");
        }

        if (start < 0) {
            throw new IllegalArgumentException("START cannot be negative");
        }
    }
}
