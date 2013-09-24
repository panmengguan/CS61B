package enigma;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Map;
import java.util.HashSet;
import java.util.Set;


/** Unit tests for Rotor.
 * @author Kiet Lam
 */
public class RotorTest {

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(RotorTest.class));
    }

    @Test public void toLetter() {
        assertEquals('A', Rotor.toLetter(0));
        assertEquals('B', Rotor.toLetter(1));
        assertTrue('B' != Rotor.toLetter(2));
        assertEquals('Z', Rotor.toLetter(25));
    }

    @Test public void toIndex() {
        assertEquals(0, Rotor.toIndex('A'));
        assertEquals(1, Rotor.toIndex('B'));
        assertEquals(25, Rotor.toIndex('Z'));
        assertTrue(0 != Rotor.toIndex('B'));
    }

    @Test public void advance() {
        Rotor rotor = RotorGenerator.getRotor("I");

        assertTrue(rotor.advances());
        assertEquals(0, rotor.getSetting());

        rotor.advance();
        assertEquals(1, rotor.getSetting());

        rotor.setSetting(25);
        rotor.advance();
        assertEquals(0, rotor.getSetting());
    }

    @Test public void advanceCallbacks() {

        final int[] dummy = {0};

        Rotor.OnNotchAdvance callback = new
            Rotor.OnNotchAdvance() {

                @Override
                public void onNotchAdvance(Rotor rotor) {
                    dummy[0] += 1;
                }
            };

        Rotor rotor = RotorGenerator.getRotor("I");
        rotor.setSetting(11);
        rotor.setOnNotchAdvance(callback);

        Set<Integer> notches0 = new HashSet<Integer>();
        notches0.add(11);

        rotor.setNotches(notches0);

        rotor.advance();
        rotor.advance();

        assertEquals(1, dummy[0]);

        dummy[0] = 0;
        Set<Integer> notches = new HashSet<Integer>();
        notches.add(12);
        notches.add(13);

        rotor.setSetting(12);
        rotor.setNotches(notches);

        rotor.advance();
        rotor.advance();

        assertEquals(2, dummy[0]);
    }

    @Test public void generatorMap() {
        String spec = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";

        Map<Integer, Integer> map =
            RotorGenerator.getMapping(spec.toCharArray());

        assertTrue(4 == map.get(Rotor.toIndex('A')));
        assertTrue(10 == map.get(Rotor.toIndex('B')));
        assertTrue(12 == map.get(Rotor.toIndex('C')));
        assertTrue(9 == map.get(Rotor.toIndex('Z')));
    }

    @Test public void rotorI() {
        Rotor rotorI = RotorGenerator.getRotor("I");
        rotorI.setSetting(Rotor.toIndex('A'));

        assertEquals(Rotor.toIndex('L'),
                     rotorI.convertForward(Rotor.toIndex('E')));
        assertEquals(Rotor.toIndex('G'),
                     rotorI.convertForward(Rotor.toIndex('F')));
        assertEquals(Rotor.toIndex('K'),
                     rotorI.convertForward(Rotor.toIndex('B')));
        assertEquals(Rotor.toIndex('Z'),
                     rotorI.convertForward(Rotor.toIndex('J')));

        rotorI.setSetting(Rotor.toIndex('N'));

        assertEquals(Rotor.toIndex('J'),
                     rotorI.convertForward(Rotor.toIndex('A')));
        assertEquals(Rotor.toIndex('S'),
                     rotorI.convertForward(Rotor.toIndex('Q')));

        assertEquals(Rotor.toIndex('T'),
                     rotorI.convertBackward(Rotor.toIndex('Q')));
        assertEquals(Rotor.toIndex('N'),
                     rotorI.convertBackward(Rotor.toIndex('R')));
    }

    @Test public void fixedRotor() {
        Rotor fixed = RotorGenerator.getRotor("BETA");
        fixed.setSetting(Rotor.toIndex('B'));
        fixed.advance();

        assertEquals(Rotor.toIndex('E'),
                     fixed.convertForward(Rotor.toIndex('B')));
    }
}
