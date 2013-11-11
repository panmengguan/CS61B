import java.util.Arrays;


/** HW #8, Problem 5.
 * @author Kiet Lam
 */
public class Sum {

    /** Returns true iff A[i]+B[j] = M for some i and j. */
    public static boolean sumsTo(int[] A, int[] B, int m) {
        Arrays.sort(A);
        Arrays.sort(B);

        for (int i = 0; i < A.length; i += 1) {
            int numToFind = m - A[i];

            int index = Arrays.binarySearch(B, numToFind);

            if (index >= 0) {
                return true;
            }
        }

        return false;
    }
}
