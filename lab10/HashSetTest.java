import java.util.Random;

/** Test of HashSets. Generates a large set of random distinct
 *  integers, adds them * to the set, removes some of them, and then
 *  checks the result.
 *  @author P. N. Hilfinger
 */
class HashSetTest {

    /** Assuming ARGS consists of decimal numerals N and S, generates
     *  N random Integers, adds them to a a HashSet<Integer>, then removes
     *  half of them and makes sure the right ones remain.  S is a random
     *  seed value (for reproducibility). */
    public static void main(String... args) {
        if (args.length != 2) {
            System.err.println("Usage: java HashSetTest N SEED");
            System.exit(1);
        }

        HashSet<Integer> H = new HashSet<Integer>(2.0, 64);

        int N = Integer.parseInt(args[0]);
        long seed = Long.parseLong(args[1]);

        Random gen = new Random(seed);

        Integer[] A = randomArray(N, gen);
        boolean[] include = randomBits(N, gen);

        for (Integer a : A) {
            H.add(a);
        }

        boolean wrong;
        wrong = false;

        if (H.size() != N) {
            System.err.printf("Size of set = %d after %d additions: %d.%n",
                              H.size(), N);
            wrong = true;
        }

        int removed;
        removed = 0;
        for (int i = 0; i < N; i += 1) {
            if (!include[i]) {
                removed += 1;
                H.remove(A[i]);
            }
        }

        if (H.size() != N - removed) {
            System.err.printf("Size of set = %d after %d removals: %d.%n",
                              H.size(), removed);
            wrong = true;
        }

        for (int i = 0; i < N; i += 1) {
            if (H.contains(A[i]) != include[i]) {
                wrong = true;
                System.err.printf("element %s should%s be in H.%n",
                                  A[i], include[i] ? "" : " not");
            }
        }
        if (wrong) {
            System.exit(1);
        }
    }

    /** Return an array of size N containing distinct random integers in
     *  random order, where randomness is determined by GEN. */
    static Integer[] randomArray(int N, Random gen) {
        Integer[] A = new Integer[N];
        A[0] = gen.nextInt(100);
        for (int i = 1; i < N; i += 1) {
            A[i] = A[i - 1] + gen.nextInt(100) + 1;
        }
        for (int i = N - 1; i > 0; i -= 1) {
            Integer tmp = A[i];
            int k = gen.nextInt(i);
            A[i] = A[k];
            A[k] = tmp;
        }
        return A;
    }

    /** Return an array of N random bits, as determined by GEN. */
    static boolean[] randomBits(int N, Random gen) {
        boolean[] B = new boolean[N];
        for (int i = 0; i < N; i += 1) {
            B[i] = gen.nextBoolean();
        }
        return B;
    }

}
