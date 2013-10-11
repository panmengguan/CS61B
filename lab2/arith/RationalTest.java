package arith;

import org.junit.Test;
import static org.junit.Assert.*;

import static arith.Rational.*;

/** Unit tests for class Rational.
 *  @author
 */
public class RationalTest {

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(RationalTest.class));
    }

    @Test public void frac1() {
        Rational r = frac(36, 48);
        assertEquals("numer() should be in lowest terms",
                     3, r.numer());
        assertEquals("denom() should be in lowest terms",
                     4, r.denom());
    }

    @Test public void frac2() {
        Rational r = frac(0, 20);
        assertTrue("0 should be 0/1", r.numer() == 0 && r.denom() == 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void frac3() {
        frac(3, 0);
    }

    @Test public void frac4() {
        Rational r = frac(2, 4);
        assertTrue("Result should be 1/2",
                   (r.numer() == 1L) && (r.denom() == 2L));
    }

    @Test public void frac5() {
        Rational r = frac(-1, -1);
        assertTrue("Result should be 1/1",
                   (r.numer() == 1L) && (r.denom() == 1L));
    }

    @Test public void mul1() {
        Rational r1 = frac(1, 2);
        Rational r2 = frac(2, 4);
        Rational r3 = r1.mul(r2);

        assertTrue("Result should be 1/4",
                   (r3.numer() == 1L) && (r3.denom() == 4L));
    }

    @Test public void mul2() {
        Rational r1 = frac(4, 3);
        Rational r2 = frac(5, 2);
        Rational r3 = r1.mul(r2);

        assertTrue("Result should be 10/3",
                   (r3.numer() == 10L) && (r3.denom() == 3L));
    }

    @Test public void div1() {
        Rational r1 = frac(4, 3);
        Rational r2 = frac(5, 2);
        Rational r3 = r1.div(r2);

        assertTrue("Result should be 10/3",
                   (r3.numer() == 8L) && (r3.denom() == 15L));
    }

    @Test public void div2() {
        Rational r1 = frac(4, 3);
        Rational r2 = frac(5, 2);
        Rational r3 = r1.div(r2);

        assertTrue("Result should be 8/15",
                   (r3.numer() == 8L) && (r3.denom() == 15L));
    }

    @Test public void div3() {
        Rational r1 = frac(4, 2);
        Rational r2 = frac(2, 4);
        Rational r3 = r1.div(r2);

        assertTrue("Result should be 4/1, got: " + r3,
                   (r3.numer() == 4L) && (r3.denom() == 1L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void div4() {
        Rational r1 = frac(4, 2);
        Rational r2 = frac(0, 4);
        Rational r3 = r1.div(r2);
    }

    @Test public void add1() {
        Rational r1 = frac(4, 2);
        Rational r2 = frac(2, 4);
        Rational r3 = r1.add(r2);

        assertTrue("Result should be 5/2",
                   (r3.numer() == 5L) && (r3.denom() == 2L));
    }

    @Test public void add2() {
        Rational r1 = frac(4, 2);
        Rational r2 = frac(-2, 4);
        Rational r3 = r1.add(r2);

        assertTrue("Result should be 3/2",
                   (r3.numer() == 3L) && (r3.denom() == 2L));
    }

    @Test public void add3() {
        Rational r1 = frac(1, 2);
        Rational r2 = frac(-2, 4);
        Rational r3 = r1.add(r2);

        assertTrue("Result should be 0/1",
                   (r3.numer() == 0L) && (r3.denom() == 1L));
    }

    @Test public void sub1() {
        Rational r1 = frac(4, 2);
        Rational r2 = frac(-2, 4);
        Rational r3 = r1.sub(r2);

        assertTrue("Result should be 5/2",
                   (r3.numer() == 5L) && (r3.denom() == 2L));
    }

    @Test public void sub2() {
        Rational r1 = frac(4, 2);
        Rational r2 = frac(2, 4);
        Rational r3 = r1.sub(r2);

        assertTrue("Result should be 3/2",
                   (r3.numer() == 3L) && (r3.denom() == 2L));
    }

    @Test public void less1() {
        Rational r1 = frac(4, 2);
        Rational r2 = frac(-2, 4);

        assertTrue("Result should be false", !r1.lessThan(r2));
        assertTrue("Result should be true", r2.lessThan(r1));
    }

    @Test public void max1() {
        Rational r1 = frac(4, 2);
        Rational r2 = frac(-2, 4);
        Rational r3 = Rational.max(r1, r2);

        assertTrue("Result should be 2/1",
                   r3.numer() == 2 && r3.denom() == 1);
    }

    @Test public void equal1() {
        Rational r1 = frac(4, 2);
        Rational r2 = frac(2, 1);

        assertTrue("Result should be true", r1.equal(r2));
    }

    @Test public void equal2() {
        Rational r1 = frac(1, 1);
        Rational r2 = frac(1, 2);

        assertTrue("Result should be false", !r1.equal(r2));
    }
}
