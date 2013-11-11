import java.util.Random;

/** An implementation of Bitsort on integers.
 *  @author Kiet Lam
 */
class Bitsort {

    /** Unit of time. */
    static final double MILLISEC = 0.001;

    /** When run with ARGS containing the numerml N (java SortTesting
     *  N), creates a pseudo-random permutation of the integers (
     *  .. N-1 and uses bitsort (radix sort where the "characters" are
     *  the bits of the integer) to sort them.  It reports the
     *  time taken to do this. For each  value of N, the pseudo-random
     *  numbers used are the same each time for reproducible results. */
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);

        int[] nums = createRandomPermutation(N);

        long start = System.currentTimeMillis();
        bitsort(nums);
        long end = System.currentTimeMillis();
        check(nums);
        System.out.printf("Sorted %d numbers in %.3f seconds%n",
                          N, (end - start) * MILLISEC);
    }

    /** Sort A via bitsort. */
    static void bitsort(int[] A) {
        int B = maxOneBits(A);
        for (int p = 0; p < B; p += 1) {
            sortOnBit(A, p);
        }
    }

    /** A seed value that determines the pseudo-random number sequence. */
    private static final long SEED = 314159265L;

    /** Exchange A[I] and A[J]. */
    private static void swap(int[] A, int i, int j) {
        int t = A[i]; A[i] = A[j]; A[j] = t;
    }

    /** Returns an array containing a pseudo-random permutation of 0 .. N-1. */
    private static int[] createRandomPermutation(int N) {
        Random gen = new Random(SEED);
        int[] result = new int[N];
        for (int i = 0; i < N; i += 1) {
            result[i] = i;
        }
        for (int k = N - 1; k > 0; k -= 1) {
            swap(result, k, gen.nextInt(k + 1));
        }
        return result;
    }

    /** Check that A is 0 .. N-1. */
    private static void check(int[] A) {
        for (int i = 0; i < A.length; i += 1) {
            if (A[i] != i) {
                throw new AssertionError("Element " + i + " is out of order");
            }
        }
    }

    /** Return the maximum number of significant bits over all values
     *  in A, treating all numbers as unsigned. */
    private static int maxOneBits(int[] A) {
        int r = 0;
        int mask = ~0;
        for (int x : A) {
            while ((mask & x) != 0) {
                r += 1;
                mask <<= 1;
            }
        }
        return r;
    }

    /** Sort A stably on bit #P of A (P==0 is least significant). */
    private static void sortOnBit(int[] A, int p) {
        int mask = 1 << p;

        int numZeros = 0;
        for (int i = 0; i < A.length; i += 1) {
            if ((A[i] & mask) == 0) {
                numZeros += 1;
            }
        }

        int numOnes = A.length - numZeros;

        int[] zeros = new int[numZeros];
        int[] ones = new int[numOnes];

        int zeroCounter = 0;
        int oneCounter = 0;

        for (int i = 0; i < A.length; i += 1) {
            if ((A[i] & mask) == 0) {
                zeros[zeroCounter] = A[i];
                zeroCounter += 1;
            } else {
                ones[oneCounter] = A[i];
                oneCounter += 1;
            }
        }

        oneCounter = 0;
        for (int i = 0; i < A.length; i += 1) {
            if (i < numZeros) {
                A[i] = zeros[i];
            } else {
                A[i] = ones[oneCounter];
                oneCounter += 1;
            }
        }
    }
}
