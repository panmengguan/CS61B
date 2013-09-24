package enigma;

import org.junit.Test;
import static org.junit.Assert.*;


/** Unit tests for Machine.
 * @author Kiet Lam
 */
public class MachineTest {

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(MachineTest.class));
    }

    @Test public void advance() {
        Rotor ref = RotorGenerator.getRotor("B");
        Rotor fix = RotorGenerator.getRotor("BETA");
        Rotor ro3 = RotorGenerator.getRotor("III");
        Rotor ro4 = RotorGenerator.getRotor("IV");
        Rotor ro5 = RotorGenerator.getRotor("I");

        ro4.setSetting(Rotor.toIndex('A'));
        ro5.setSetting(Rotor.toIndex('Q'));

        Machine m1 = new Machine(ref, fix, ro3, ro4, ro5);

        m1.convert('C');

        assertEquals(Rotor.toIndex('B'), ro4.getSetting());
    }

    @Test public void advance2() {
        Rotor ref = RotorGenerator.getRotor("B");
        Rotor fix = RotorGenerator.getRotor("BETA");
        Rotor ro3 = RotorGenerator.getRotor("III");
        Rotor ro4 = RotorGenerator.getRotor("II");
        Rotor ro5 = RotorGenerator.getRotor("I");

        fix.setSetting(Rotor.toIndex('B'));
        ro3.setSetting(Rotor.toIndex('C'));
        ro4.setSetting(Rotor.toIndex('D'));
        ro5.setSetting(Rotor.toIndex('Z'));

        Machine m1 = new Machine(ref, fix, ro3, ro4, ro5);

        m1.convert('C');

        assertEquals(Rotor.toIndex('B'), fix.getSetting());
        assertEquals(Rotor.toIndex('C'), ro3.getSetting());
        assertEquals(Rotor.toIndex('D'), ro4.getSetting());
        assertEquals(Rotor.toIndex('A'), ro5.getSetting());
    }

    @Test public void doubleStep() {
        Rotor ref = RotorGenerator.getRotor("B");
        Rotor fix = RotorGenerator.getRotor("BETA");
        Rotor ro3 = RotorGenerator.getRotor("III");
        Rotor ro4 = RotorGenerator.getRotor("IV");
        Rotor ro5 = RotorGenerator.getRotor("I");

        fix.setSetting(Rotor.toIndex('A'));
        ro3.setSetting(Rotor.toIndex('X'));
        ro4.setSetting(Rotor.toIndex('J'));
        ro5.setSetting(Rotor.toIndex('R'));

        Machine m1 = new Machine(ref, fix, ro3, ro4, ro5);
        m1.convert('C');

        assertEquals(Rotor.toIndex('A'), fix.getSetting());
        assertEquals(Rotor.toIndex('Y'), ro3.getSetting());
        assertEquals(Rotor.toIndex('K'), ro4.getSetting());
        assertEquals(Rotor.toIndex('S'), ro5.getSetting());
    }

    @Test public void advanceDoubleStep() {
        Rotor ref = RotorGenerator.getRotor("B");
        Rotor fix = RotorGenerator.getRotor("BETA");
        Rotor ro3 = RotorGenerator.getRotor("III");
        Rotor ro4 = RotorGenerator.getRotor("IV");
        Rotor ro5 = RotorGenerator.getRotor("I");

        fix.setSetting(Rotor.toIndex('A'));
        ro3.setSetting(Rotor.toIndex('X'));
        ro4.setSetting(Rotor.toIndex('I'));
        ro5.setSetting(Rotor.toIndex('Q'));

        Machine m1 = new Machine(ref, fix, ro3, ro4, ro5);
        m1.convert('C');
        m1.convert('C');

        assertEquals(Rotor.toIndex('A'), fix.getSetting());
        assertEquals(Rotor.toIndex('Y'), ro3.getSetting());
        assertEquals(Rotor.toIndex('K'), ro4.getSetting());
        assertEquals(Rotor.toIndex('S'), ro5.getSetting());
    }

    @Test public void convertChar() {
        Rotor ref = RotorGenerator.getRotor("B");
        Rotor fix = RotorGenerator.getRotor("BETA");
        Rotor ro3 = RotorGenerator.getRotor("III");
        Rotor ro4 = RotorGenerator.getRotor("IV");
        Rotor ro5 = RotorGenerator.getRotor("I");

        fix.setSetting(Rotor.toIndex('A'));
        ro3.setSetting(Rotor.toIndex('X'));
        ro4.setSetting(Rotor.toIndex('L'));
        ro5.setSetting(Rotor.toIndex('E'));

        Machine m1 = new Machine(ref, fix, ro3, ro4, ro5);

        assertEquals('H', m1.convert('F'));
    }

    @Test public void convertLine() {
        Rotor ref = RotorGenerator.getRotor("B");
        Rotor fix = RotorGenerator.getRotor("BETA");
        Rotor ro3 = RotorGenerator.getRotor("III");
        Rotor ro4 = RotorGenerator.getRotor("IV");
        Rotor ro5 = RotorGenerator.getRotor("I");

        fix.setSetting(Rotor.toIndex('A'));
        ro3.setSetting(Rotor.toIndex('X'));
        ro4.setSetting(Rotor.toIndex('L'));
        ro5.setSetting(Rotor.toIndex('E'));

        Machine m1 = new Machine(ref, fix, ro3, ro4, ro5);

        String mesg = "FROMHISSHOULDERHIAWATHA";
        String output = m1.convert(mesg);

        assertEquals("HYIHL BKOML IUYDC MPPSF SZW", output);

        mesg = "TOOKTHECAMERAOFROSEWOOD";
        output = m1.convert(mesg);

        assertEquals("SQCNJ EXNUO JYRZE KTCNB DGU", output);
    }
}
