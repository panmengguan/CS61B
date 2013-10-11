import arith.Rational;
import static arith.Rational.frac;


/** Square root implementation on rational numbers.
 *  @author Kiet Lam
 */
public class Root2 {

    /** Tolerance of approximation. */
    private static final Rational EPS = Rational.frac(1, 1000000L);

    /** Return the Kth root of X, where X >= 0, K >= 1. */
    static Rational root(Rational x, int k) {
        /* Strategy: A Newton-Raphson iteration, organized so that all
         * corrections are negative.  We continue until the estimated
         * magnitude of the relative error is less than that of EPS. */

        if (x.lessThan(Rational.frac(0))) {
            throw new IllegalArgumentException("x must be non-negative");
        }
        if (k < 1.0) {
            throw new IllegalArgumentException("k must be >= 1");
        }
        if (x.numer() == 0) {
            return frac(0);
        }

        if (x.equal(frac(1, 1))) {
            return x;
        }

        Rational y, err;
        Rational threshold = x.mul(-k).mul(EPS);
        y = Rational.max(Rational.frac(1), x);

        do {
            Rational yk1 = power(y, k - 1);
            err = x.sub(yk1).mul(y);
            y = y.add(err).div(yk1.mul(k));
        } while (err.lessThan(threshold));

        return y;
    }

    /** Returns X ** K, for K >= 0, with 0 ** 0 == 1. */
    static Rational power(Rational x, int k) {
        /* Strategy: Exponentiation via the Russian Peasant's Algorithm.
         * Java Note: m&1 is the units bit of m, hence 1 if m is odd and 0
         * if it is even. */

        if (x.numer() == 1L && x.denom() == 1L) {
            return frac(1L, 1L);
        }

        if (x.equal(Rational.frac(0L))
            || x.equal(Rational.frac(1L)) || k == 1) {
            return x;
        }
        if (k == 0) {
            return Rational.frac(1L);
        }
        Rational z;
        z = Rational.frac(1L);
        while (k != 0) {
            if ((k & 1) == 1) {
                z = z.mul(x);
            }
            k /= 2;
            x = x.mul(x);
        }
        return z;
    }

    /** The command
     *       java Root ARGS
     *  where ARGS has the form x k, prints an approximation of the kth root
     *  of x, assuming x >= 0, k > 0. */
    public static void main(String... args) {
        Rational x = frac(args[0]);
        int k = Integer.parseInt(args[1]);
        System.out.printf("%s ** (1/%d) = %s%n", x, k, root(x, k));
    }

}
