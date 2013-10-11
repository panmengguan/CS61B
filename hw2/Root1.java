/** Square root implementation on floating-point numbers.
 *  @author P. N. Hilfinger
 */
public class Root1 {

    /** Tolerance of approximation. */
    private static final double EPS = 1.0e-6;

    /** Return the Kth root of X, where X >= 0, K >= 1. */
    static double root(double x, int k) {
        /* Strategy: A Newton-Raphson iteration, organized so that all
         * corrections are negative.  We continue until the estimated
         * magnitude of the relative error is less than that of EPS. */

        if (x < 0.0) {
            throw new IllegalArgumentException("x must be non-negative");
        }
        if (k < 1.0) {
            throw new IllegalArgumentException("k must be >= 1");
        }
        if (x == 0.0) {
            return 0.0;
        }

        double y, err;
        double threshold = -k * EPS * x;
        y = Math.max(1.0, x);

        do {
            double yk1 = power(y, k - 1);
            err = x - yk1 * y;
            y = y + err / (k * yk1);
        } while (err < threshold);

        return y;
    }

    /** Returns X ** K, for K >= 0, with 0 ** 0 == 1. */
    static double power(double x, int k) {
        /* Strategy: Exponentiation via the Russian Peasant's Algorithm.
         * Java Note: m&1 is the units bit of m, hence 1 if m is odd and 0
         * if it is even. */

        if (x == 0.0 || x == 1.0 || k == 1) {
            return x;
        }
        if (k == 0) {
            return 1.0;
        }
        double z;
        z = 1.0;
        while (k != 0) {
            if ((k & 1) == 1) {
                z *= x;
            }
            k /= 2;
            x *= x;
        }
        return z;
    }

    /** The command
     *       java Root ARGS
     *  where ARGS has the form x k, prints an approximation of the kth root
     *  of x, assuming x >= 0, k > 0. */
    public static void main(String... args) {
        double x = Double.parseDouble(args[0]);
        int k = Integer.parseInt(args[1]);
        System.out.printf("%g ** (1/%d) = %g%n", x, k, root(x, k));
    }

}
