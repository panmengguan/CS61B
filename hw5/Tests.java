import org.junit.Test;
import static org.junit.Assert.*;


public class Tests {

    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(Tests.class));
    }

    @Test public void add1() {
        assertEquals(5, Adder.add(2, 3));
        assertEquals(0, Adder.add(0, 0));
        assertEquals(-7, Adder.add(-2, -5));
    }

    @Test public void nybbles1() {
        Nybbles nyb1 = new Nybbles(9);

        nyb1.set(0, -2);
        nyb1.set(1, -2);
        nyb1.set(2, -4);
        nyb1.set(3, -8);
        nyb1.set(5, 3);
        nyb1.set(5, -8);
        nyb1.set(7, 7);

        assertEquals(-2, nyb1.get(0));
        assertEquals(-2, nyb1.get(1));
        assertEquals(-4, nyb1.get(2));
        assertEquals(-8, nyb1.get(3));
        assertEquals(-8, nyb1.get(5));
        assertEquals(7, nyb1.get(7));
    }
}
