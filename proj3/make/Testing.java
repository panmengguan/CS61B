package make;

import graph.Graph;
import graph.NoLabel;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.StringWriter;
import java.io.PrintWriter;

/* You MAY add public @Test methods to this class.  You may also add
 * additional public classes containing "Testing" in their name. These
 * may not be part of your make package per se (that is, it must be
 * possible to remove them and still have your package work). */

import org.junit.Test;
import org.junit.Before;
import ucb.junit.textui;
import static org.junit.Assert.*;

/** Unit tests for the make package. */
public class Testing {

    /** Run all JUnit tests in the make package. */
    public static void main(String[] ignored) {
        textui.runClasses(make.Testing.class);
    }

    /** Collects output to StringWriter .*/
    private StringWriter output;

    /** Output printer.*/
    private PrintWriter outPrinter;

    /** Collects err to StringWriter .*/
    private StringWriter err;

    /** Output printer.*/
    private PrintWriter errPrinter;

    /** Setup the output and writer.*/
    private void setupWriter() {
        output = new StringWriter();
        err = new StringWriter();
        outPrinter = new PrintWriter(output);
        errPrinter = new PrintWriter(err);
    }

    @Before
    public void initialize() {
        setupWriter();
    }

    @Test
    public void testAddRule() {
        Make make = new Make(100, getCreationMap(), outPrinter, errPrinter);
        make.addRule(getRuleFooO());
        make.addRule(getRuleFoo());
        make.addRule(getRuleFooC());

        Graph<String, NoLabel> graph = make.getGraph();

        Map<String, Graph<String, NoLabel>.Vertex> vertices =
            make.getVerticesMap();

        assertTrue("incorrect dependency graph",
                   graph.contains(vertices.get("foo"), vertices.get("foo.o")));
        assertTrue("incorrect dependency graph",
                   graph.contains(vertices.get("foo.o"),
                                  vertices.get("foo.c")));
        assertTrue("incorrect dependency graph",
                   graph.contains(vertices.get("foo.o"),
                                  vertices.get("foo.h")));
        assertTrue("incorrect dependency graph",
                   graph.contains(vertices.get("foo.c"),
                                  vertices.get("foo.y")));
        assertFalse("incorrect dependency graph",
                    graph.contains(vertices.get("foo"), vertices.get("foo.c")));
    }

    @Test
    public void testSimpleRule() {
        Make make = new Make(100, getCreationMap(), outPrinter, errPrinter);
        make.addRule(getRuleFooO());
        make.addRule(getRuleFoo());
        make.addRule(getRuleFooC());

        make.build("foo.c");

        String expected = "yacc -o\n";
        String out = output.toString();

        assertEquals("incorrect make build", expected, out);
    }

    @Test
    public void testComplexMake() {
        Map<String, Integer> creationMap = getCreationMap();
        Make make = new Make(100, creationMap, outPrinter, errPrinter);
        make.addRule(getRuleFooO());
        make.addRule(getRuleFoo());
        make.addRule(getRuleFooC());
        make.addRule(getExtraneous());

        make.build("foo");
        String expected = "yacc -o\ngcc -g\ngcc -o\n";
        String out = output.toString();

        assertEquals("incorrect make build", expected, out);
    }

    @Test
    public void testNoMake() {
        Make make = new Make(0, getCreationMap(), outPrinter, errPrinter);
        make.addRule(getRuleFooO());
        make.addRule(getRuleFoo());
        make.addRule(getRuleFooC());
        make.addRule(getExtraneous());

        make.build("foo");
        String expected = "";
        String out = output.toString();

        assertEquals("incorrect make build", expected, out);
    }

    @Test
    public void testEmpty() {
        Make make = new Make(0, getCreationMap(), outPrinter, errPrinter);

        make.build("asds");
        String expected = "";
        String out = output.toString();

        assertEquals("incorrect make build", expected, out);
    }

    @Test
    public void testCircularDependency() {
        Make make = new Make(100, getCreationMap(), outPrinter, errPrinter);
        make.addRule(getRuleFooO());
        make.addRule(getRuleFoo());
        make.addRule(getRuleFooC());
        make.addRule(getRuleCircular());
        make.addRule(getExtraneous());

        make.build("foo");
        String expected = "";
        String errOut = err.toString();

        assertTrue("error must be reported", errOut.length() > 0);
    }

    @Test
    public void testFalseTarget() {
        Make make = new Make(100, getCreationMap(), outPrinter, errPrinter);
        make.addRule(getRuleFooO());
        make.addRule(getRuleFoo());
        make.addRule(getRuleFooC());

        make.build("amy");
        String expected = "";
        String errOut = err.toString();

        assertTrue("error must be reported", errOut.length() > 0);
    }

    private static Rule getExtraneous() {
        List<String> dependencies = new ArrayList<String>();
        dependencies.add("bar");
        dependencies.add("germany");

        List<String> commands = new ArrayList<String>();
        commands.add("go go canada");

        return new Rule("amy", dependencies, commands);
    }

    private static Rule getRuleCircular() {
        List<String> dependencies = new ArrayList<String>();
        dependencies.add("foo");
        List<String> commands = new ArrayList<String>();

        return new Rule("foo.h", dependencies, commands);
    }

    private static Rule getRuleFooO() {
        List<String> dependencies = new ArrayList<String>();
        dependencies.add("foo.c");
        dependencies.add("foo.h");

        List<String> commands = new ArrayList<String>();
        commands.add("gcc -g");

        return new Rule("foo.o", dependencies, commands);
    }

    private static Rule getRuleFoo() {
        List<String> dependencies = new ArrayList<String>();
        dependencies.add("foo.o");

        List<String> commands = new ArrayList<String>();
        commands.add("gcc -o");

        return new Rule("foo", dependencies, commands);
    }

    private static Rule getRuleFooC() {
        List<String> dependencies = new ArrayList<String>();
        dependencies.add("foo.y");

        List<String> commands = new ArrayList<String>();
        commands.add("yacc -o");

        return new Rule("foo.c", dependencies, commands);
    }

    private Map<String, Integer> getCreationMap() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("foo", 90);
        map.put("foo.y", 50);
        map.put("foo.h", 10);
        return map;
    }
}
