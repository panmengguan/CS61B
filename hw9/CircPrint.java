import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

/** Demonstration of circular graph printing.
 *  @author Kiet Lam.
 */
public class CircPrint {

    /** Inputs an S expression from the standard input in the form of numbered
     *  objects.  Each object is either an identifier in the form:
     *       N: IDENTIFIER
     *  where N>0 is the object number and IDENTIFIER is a string starting
     *  with a non-digit and containing no whitespace, or a pair in the form
     *       N: M1 M2
     *  where M1>=0 and M2>= are object numbers (the car and cdr of object N).
     *  Object 0 represents null.
     *
     *  Prints out the S-expression represented by object #1 on a single line,
     *  using the Scheme notation (#n#, #n=) to indicate shared structure and
     *  using the given object numbers for labeling all pairs that are shared
     *  (and only those).
     *
     *  For example, given the input
     *       1: 2 3
     *       2: A
     *       4: B
     *       3: 4 1
     *  we print #1=(A . (B . #1#))
     */
    public static void main(String... ignored) {
        Scanner inp = new Scanner(System.in);
        Value g = readGraph(inp);
        write(g);
        System.out.println();
    }

    /** Pattern for input line. */
    private static final Pattern GRAPH_LINE =
        Pattern.compile("(\\d+):\\s*"
                        + "(?:([a-zA-Z_][a-zA-Z_0-9]*)|(\\d+)\\s+(\\d+))");

    /** The NIL object. */
    static final Value NIL = Nil.NIL;

    /** Returns a directed graph read from INP in the syntax described in
     *  main. */
    private static Value readGraph(Scanner inp) {
        ArrayList<int[]> pairs = new ArrayList<int[]>();
        ArrayList<Value> vals = new ArrayList<Value>();
        pairs.add(null);
        vals.add(NIL);

        while (inp.findWithinHorizon(GRAPH_LINE, 0) != null) {
            MatchResult mat = inp.match();
            Integer n = Integer.parseInt(mat.group(1));
            while (pairs.size() < n + 1) {
                pairs.add(null);
                vals.add(null);
            }
            if (mat.group(2) != null) {
                vals.set(n, new Atom(mat.group(2)));
            } else {
                vals.set(n, new Pair(NIL, NIL));
                pairs.set(n, new int[] { Integer.parseInt(mat.group(3)),
                                         Integer.parseInt(mat.group(4))});
            }
        }
        for (int i = 0; i < pairs.size(); i += 1) {
            if (pairs.get(i) != null) {
                Value p = vals.get(i);
                int[] links = pairs.get(i);
                p.setcar(vals.get(links[0]));
                p.setcdr(vals.get(links[1]));
            }
        }

        return vals.get(1);
    }

    /** Print potentially circular structure V, marking only shared nodes with
     *  #n= notation. */
    static void write(Value v) {
        Set<Value> traversed = new HashSet<Value>();
        List<Shared> ls = new ArrayList<Shared>();

        while (v.cdr() != null) {
            v = v.cdr();

            if (traversed.contains(v)) {
                ls.add(new Shared(v, true));
                break;
            } else {
                ls.add(new Shared(v, false));
            }

            traversed.add(v);

            if (v.atomp()) {
                System.out.println(v.toString());
            }
        }


        String str = ls.get(0)._value + "=";
        for (Shared sh: ls.subList(1, ls.size() - 1)) {
            if (sh._value.atomp()) {
                str += "(" + sh._value.toString() + "";
            }

            if (sh._isShared) {
                str += "(" + " . " + sh._value.toString();
            }

            str += ")";
        }

        System.out.println(str);
    }

    /** Class to represent if we are shared or not.*/
    private static class Shared {

        /** The value.*/
        private Value _value;

        /** Marking whether we are shared or not.*/
        private boolean _isShared;

        /** Create a Shared, marking if VALUE is ISSHARED or not.*/
        Shared(Value value, boolean isShared) {
            _value = value;
            _isShared = isShared;
        }
    }
}
