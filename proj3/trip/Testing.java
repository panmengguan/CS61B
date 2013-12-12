package trip;

/* You MAY add public @Test methods to this class.  You may also add
 * additional public classes containing "Testing" in their name. These
 * may not be part of your trip package per se (that is, it must be
 * possible to remove them and still have your package work). */

import org.junit.Test;
import org.junit.Before;
import ucb.junit.textui;
import static org.junit.Assert.*;

import java.io.StringWriter;
import java.io.PrintWriter;

import java.util.List;
import java.util.ArrayList;

/** Unit tests for the trip package. */
public class Testing {

    /** Run all JUnit tests in the graph package. */
    public static void main(String[] ignored) {
        System.exit(textui.runClasses(trip.Testing.class));
    }

    /** Collects output to StringWriter .*/
    private StringWriter output;

    /** Output printer.*/
    private PrintWriter outPrinter;

    /** Collects err to StringWriter .*/
    private StringWriter err;

    /** Trip planner.*/
    private Planner planner;

    /** Output printer.*/
    private PrintWriter errPrinter;

    /** Setup the output and writer.*/
    private void setupWriter() {
        output = new StringWriter();
        err = new StringWriter();
        outPrinter = new PrintWriter(output);
        errPrinter = new PrintWriter(err);
        planner = new Planner(outPrinter, errPrinter);
    }

    @Before
    public void setUp() {
        setupWriter();
    }

    @Test
    public void testSimpleTrip() {
        Location lA = new Location("A", 0.0, 0.0);
        Location lB = new Location("B", 1.0, 1.0);
        Location lC = new Location("C", 2.0, 2.0);

        planner.addLocation(lA);
        planner.addLocation(lB);
        planner.addLocation(lC);

        Distance d1 = new Distance("R A R1 1.0 NS B");
        Distance d2 = new Distance("R B R2 1.0 NS C");

        planner.addDistance(d1);
        planner.addDistance(d2);

        List<String> locations = new ArrayList<String>();

        locations.add("A");
        locations.add("C");

        List<String> directions = planner.planTrip(locations);

        List<String> expected = new ArrayList<String>();
        expected.add("1. Take R1 south for 1.0 miles.");
        expected.add("2. Take R2 south for 1.0 miles to C.");

        assertEquals("incorrect directions", expected, directions);
    }

    @Test
    public void testNoPath() {
        Location lA = new Location("A", 0.0, 0.0);
        Location lB = new Location("B", 1.0, 1.0);
        Location lC = new Location("C", 2.0, 2.0);

        planner.addLocation(lA);
        planner.addLocation(lB);
        planner.addLocation(lC);

        Distance d1 = new Distance("R A R1 1.0 NS B");
        Distance d2 = new Distance("R B R2 1.0 NS C");

        planner.addDistance(d1);

        List<String> locations = new ArrayList<String>();

        locations.add("A");
        locations.add("C");

        List<String> directions = planner.planTrip(locations);
        List<String> expected = new ArrayList<String>();

        assertTrue("there was an error", err.toString().length() > 0);
    }

    @Test
    public void testTwoLocations() {
        Location lA = new Location("A", 0.0, 0.0);
        Location lB = new Location("B", 1.0, 1.0);

        List<String> locations = new ArrayList<String>();
        locations.add("A");
        locations.add("B");

        planner.addLocation(lA);
        planner.addLocation(lB);

        Distance d1 = new Distance("R A R1 1.0 NS B");

        planner.addDistance(d1);

        List<String> directions = planner.planTrip(locations);
        List<String> expected = new ArrayList<String>();

        expected.add("1. Take R1 south for 1.0 miles to B.");
        assertEquals("incorrect directions", expected, directions);
    }

    @Test
    public void testInvalidLocation() {
        Location lA = new Location("A", 0.0, 0.0);
        Location lB = new Location("B", 1.0, 1.0);

        List<String> locations = new ArrayList<String>();
        locations.add("B");
        locations.add("K");

        planner.addLocation(lA);
        planner.addLocation(lB);

        Distance d1 = new Distance("R A R1 1.0 NS B");

        planner.addDistance(d1);
        planner.planTrip(locations);

        assertTrue("need error message", err.toString().length() > 0);
    }
}
