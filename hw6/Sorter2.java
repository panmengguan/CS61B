import java.util.Random;
import java.util.ArrayList;

import ucb.util.Stopwatch;

/** Insertion sort with timing.
 *  @author P. N. Hilfinger
 */
public class Sorter2 {

    /** Sort VALUES into non-decreasing order. */
    public static void sort(ArrayList<Double> values) {
        /* Loop invariant:
         * Elements 0 through k-1 are in nondecreasing order:
         *   values[0] <= values[1] <= ... <= values[k-1].
         * Insert element k into its correct position, so that
         *   values[0] <= values[1] <= ... <= values[k-1]. */
        for (int k = 1; k < values.size(); k += 1) {
            double temp = values.get(k);
            int j;
            for (j = k - 1; j >= 0 && values.get(j) > temp; j -= 1) {
                values.set(j + 1, values.get(j));
            }
            values.set(j + 1, temp);
        }
    }

    /** Returns an N-element vector, V, filled with random numbers, with
     *  V[k] = C0 * k + C1 * R, where R is uniform over 0.0 .. 1.0.
     */
    static ArrayList<Double> randomVector(int n, double c0, double c1,
                                          Random R) {
        int j;
        ArrayList<Double> values = new ArrayList<Double>();
        for (j = 0; j < n; j += 1) {
            values.add(c0 * j + c1 * R.nextDouble());
        }
        return values;
    }

    /** Print VALUES, one item per line. */
    static void printArr(ArrayList<Double> values) {
        for (double x : values) {
            System.out.println(x);
        }
    }

    /** ARGS is SIZE [ TYPE [SEED]].  Generates random data of
     *  given SIZE using SEED and times a call on sort over this
     *  data.  TYPE is either "A" or "B", selecting one of two
     *  formulas for transforming random numbers into data.*/
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java Sorter SIZE [ TYPE [SEED]]");
            System.exit(1);
        }
        int N = Integer.parseInt(args[0]);
        String type = args.length >= 2 ? args[1] : "A";
        long seed = args.length >= 3 ? Long.parseLong(args[2]) : 42;
        Random R = new Random(seed);

        ArrayList<Double> values;
        if (type.equals("A")) {
            values = randomVector(N, 0.0, 1.0, R);
        } else {
            values = randomVector(N, 1.0, 20.0, R);
        }

        Stopwatch t = new Stopwatch();
        t.start();
        sort(values);
        System.out.printf("%5.3f seconds elapsed.%n",
                           t.stop() * 1.0e-3);
        if (N < 20) {
            printArr(values);
        }
    }
}
