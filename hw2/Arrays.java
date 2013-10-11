/* NOTE: The file ArrayUtil.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2, Problem #2. */

/** Array utilities.
 *  @author Kiet Lam
 */
class Arrays {
    /* 2a. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        int[] c = new int[A.length + B.length];

        int i = 0;

        for ( ; i < A.length; i += 1) {
            c[i] = A[i];
        }

        for (int k = 0; k < B.length; i += 1, k += 1) {
            c[i] = B[k];
        }

        return c;
    }

    /* 2b. */
    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. */
    static int[] remove(int[] A, int start, int len) {
        if (len <= 0) {
            return A;
        }

        if (A.length == 0) {
            return A;
        }

        int numLength = 0;

        if (start + len > A.length) {
            numLength = A.length - start;
        } else {
            numLength = len;
        }

        int[] b = new int[A.length - numLength];

        int k = 0;

        for ( ; k < start; k += 1) {
            b[k] = A[k];
        }

        for (int i = start + len; i < A.length; i += 1, k += 1) {
            b[k] = A[i];
        }

        return b;
    }

    /* 2c. */
    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}. */
    static int[][] naturalRuns(int[] A) {

        int numLists = 0;

        int k = A[0];

        for (int i = 0; i < A.length; i += 1) {
            if (k > A[i]) {
                numLists += 1;
            }
        }

        int[][] matrix = new int[numLists][];
        int[] indices = new int[numLists];
        int[] lengths = new int[numLists + 1];

        int j = 0;
        for (int i = 0; i < numLists; i += 1) {
            int h = A[j];

            for ( ; j < A.length; j += 1) {
                if (h > A[j]) {
                    indices[i] = j;
                }
            }
        }

        return matrix;
    }
}
