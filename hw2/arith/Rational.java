package arith;

/** A rational number.  Members of this class, like the Integer and Double
 *  wrapper classes, are immutable.  Operations create new Rational objects
 *  rather than modifying existing ones.
 *  @author Kiet Lam
 */
public class Rational {

    /** Return the rational number NUM/DEN, where DEN is non-zero. */
    public static Rational frac(long num, long den) {

        if (den == 0) {
            throw new IllegalArgumentException("Denominator is 0!");
        }

        if (num == 0) {
            return new Rational(0L, 1L);
        }

        Rational rat = new Rational(num, den);
        long d = rat.gcd(rat.numer(), rat.denom());

        if (num < 0 && den < 0) {
            return new Rational((-1) * num / d, (-1) * den / d);
        }

        return new Rational(num / d, den / d);
    }

    /** Returns the rational number X. */
    public static Rational frac(long x) {
        return frac(x, 1);
    }

    /** Returns the rational number denoted by VAL, which must be of the form
     *  NUM/DEN, +NUM/DEN, -NUM/DEN, +NUM, or -NUM for NUM and DEN
     *  integer numerals and DEN a non-zero integer numeral. */
    public static Rational frac(String val) {
        String[] strs = val.split("/");

        long num = 0L;
        long den = 1L;

        String numerStr = "";
        String denomStr = "1";

        numerStr = strs[0];

        if (strs.length > 1) {
            denomStr = strs[1];
        }

        num = sanitizeParse(numerStr);
        den = sanitizeParse(denomStr);

        return frac(num, den);
    }

    /** Sanitize the string @param num and returns
     *  the correctly parsed long. */
    private static long sanitizeParse(String num) {
        if (num.charAt(0) == '+') {
            return Long.parseLong(num.substring(1));
        } else if (num.charAt(0) == '-') {
            return (-1L) * Long.parseLong(num.substring(1));
        } else {
            return Long.parseLong(num);
        }
    }


    /** Returns the rationl multiplied by K.*/
    public Rational mul(long k) {
        long n = numer() * k;
        return frac(n, denom());
    }

    /** Returns the rational mulitplied by OTHER.*/
    public Rational mul(Rational other) {
        long n = numer() * other.numer();
        long d = denom() * other.denom();
        return frac(n, d);
    }

    /** Returns the rational divided by OTHER.*/
    public Rational div(Rational other) {

        if (other.numer() == 0L) {
            throw new IllegalArgumentException("Cannot divide by 0");
        }

        return mul(frac(other.denom(), other.numer()));
    }

    /** Returns the rational divided by K.*/
    public Rational div(long k) {
        return frac(numer(), denom() * k);
    }

    /** Returns the rational with OTHER.*/
    public Rational add(Rational other) {
        long n = numer() * other.denom() + other.numer() * denom();
        long d = denom() * other.denom();
        return frac(n, d);
    }

    /** Returns the rationl with a K.*/
    public Rational add(long k) {
        return add(Rational.frac(k, 1L));
    }

    /** Returns the rational subtracted by OTHER.*/
    public Rational sub(Rational other) {
        return add(other.mul(-1L));
    }

    /** Returns the rational subtracted by K.*/
    public Rational sub(long k) {
        return add(Rational.frac((-1L) * k));
    }

    /** Returns whether the rational is less than OTHER.*/
    public boolean lessThan(Rational other) {
        return numer() * other.denom() < denom() * other.numer();
    }

    /** Returns whether the rational equals to OTHER.*/
    public boolean equal(Rational other) {
        return numer() * other.denom() == denom() * other.numer();
    }

    /** Returns the maximum of RAT1 and RAT2.*/
    public static Rational max(Rational rat1, Rational rat2) {
        if (rat1.lessThan(rat2)) {
            return rat2;
        } else {
            return rat1;
        }
    }


    /** Returns the value N, where THIS, in lowest terms, is N/D, and D>0. */
    public long numer() {
        return _num;
    }

    /** Returns the value D, where THIS, in lowest terms, is N/D, and D>0. If N
     *  is 0, returns 1. */
    public long denom() {
        return _den;
    }

    /** Returns my representation as a String.  Returns a String of the form
     *  N/D or -N/D, where N/D is a fraction in lowest terms, leaving off /D
     *  when D is 1. */
    public String toString() {
        if (_den == 1) {
            return String.format("%d", _num);
        } else {
            return String.format("%d/%d", _num, _den);
        }
    }

    /** I represent NUM/DEN, which are kept in lowest terms. */
    private final long _num, _den;

    /** A new Rational number whose value is NUM/DEN. */
    private Rational(long num, long den) {
        _num = num;
        _den = den;
    }

    /** Returns the positive greatest common divisor (X,Y) if X!=0 or
     *  Y!=0, or 0 if both X and Y are 0.  (X,Y) is defined as the
     *  largest positive integer that divides both X and Y. */
    private long gcd(long x, long y) {
        x = Math.abs(x);
        y = Math.abs(y);
        if (x > y) {
            x %= y;
        }
        while (x != 0) {
            long t = x;
            x = y % x;
            y = t;
        }
        return y;
    }
}
