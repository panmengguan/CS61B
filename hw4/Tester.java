import java.io.IOException;
import java.io.FileReader;

import java.util.Scanner;
import java.util.regex.MatchResult;

import static java.lang.System.out;

/** Test answers to HW4.  [Any changes you make to this file will be
 *  ignored during testing.]
 *  @author P. N. Hilfinger
 */

public class Tester {

    /** Run HW4 tests. */
    public static void main(String... unused) throws IOException {
        p1("tests/p1-2.inp", HW4.DELIM_P1, HW4.OKSTRING_P1);
        p2("tests/p1-2.inp", HW4.DELIM_P1, HW4.OKSTRING_P1,
            HW4.DOUBLE_P2, HW4.ANY_STRING_P2);
        p3("tests/p3.inp", HW4.HTML_P3);
        p4("tests/p4.inp", HW4.FORMAT_P4);
    }

    /** Problem 1 test on file NAME. DELIM is a delimiter pattern
     * specifying what separates items.  OKSTRINGPATN is a pattern
     * matching acceptable strings (other than doubles). */
    public static void p1(String name, String delimPatn, String okStringPatn)
        throws IOException {
        out.println("Problem #1.");
        Scanner inp = new Scanner(new FileReader(name));
        inp.useDelimiter(delimPatn);
        while (inp.hasNext()) {
            if (inp.hasNextDouble()) {
                out.printf("Double: %g%n", inp.nextDouble());
            } else if (inp.hasNext(okStringPatn)) {
                inp.next();
                out.printf("String: '%s'%n", inp.match().group(0));
            } else {
                inp.next();
                out.printf("Bad string%n");
            }
        }
        out.println();
    }

    /** Problem 2 test on file NAME. DELIM is a delimiter pattern
     * specifying what separate items.  OKSTRINGPATN is a pattern
     * matching acceptable strings (other than doubles). DOUBLEPATN matches
     * signed floating-point numbers (with decimal point or
     * trailing 'e' exponent).
     * STRINGPATN matches any string that does not contain commas. */
    public static void p2(String name, String delimPatn, String okStringPatn,
                          String doublePatn, String stringPatn)
        throws IOException {
        out.println("Problem #2.");
        Scanner inp = new Scanner(new FileReader(name));
        while (true) {
            String item;
            if (inp.findWithinHorizon(doublePatn, 0) != null) {
                out.printf("Double: %g%n",
                           Double.parseDouble(inp.match().group(0)));
            } else if (inp.findWithinHorizon("\\G" + stringPatn, 0) != null) {
                out.printf("String: '%s'%n", inp.match().group(0));
            } else if (inp.hasNext()) {
                out.printf("Bad string");
            } else {
                break;
            }
            if (inp.findWithinHorizon(delimPatn, 0) == null)  {
                break;
            }
        }
        out.println();
    }

    /** Probem 3 test on file NAME.  MARKUP is a pattern that matches an HTML
     *  markup as specified in the problem.  For each match, the set of groups
     *  that are non-null indicates the form of markup (A-E) matched:
     *     Case A: Groups 2-5
     *     Case B: Groups 2-4
     *     Case C: Groups 2 and 5
     *     Case D: Group 2
     *     Case E: Groups 1 and 2
     */
    public static void p3(String name, String markup) throws IOException {
        out.println("Problem #3.");
        Scanner inp = new Scanner(new FileReader(name));
        while (inp.findWithinHorizon(markup, 0) != null) {
            MatchResult mat = inp.match();
            if (mat.group(1) != null
                && (mat.group(5) != null || mat.group(3) != null)) {
                out.printf("Bad markup.%n");
                continue;
            }
            out.printf("Tag: %s", mat.group(2));
            if (mat.group(3) != null) {
                out.printf(", Attribute: %s, Value: \"%s\"",
                            mat.group(3), mat.group(4));
            }
            if (mat.group(5) != null || mat.group(1) != null) {
                out.print(" end");
            }
            out.println();
        }
        out.println();
    }

    /** Probem 4 test on file NAME.  FORMAT is a format in the style
     *  of printf that prints a string and a double. */
    public static void p4(String name, String format) throws IOException {
        out.println("Problem #4.");
        Scanner inp = new Scanner(new FileReader(name));
        while (inp.hasNext()) {
            out.printf(format, inp.next(), inp.nextDouble() * 1.0e-6);
        }
        out.println();
    }

}

