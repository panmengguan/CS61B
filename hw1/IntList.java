import java.util.Formatter;

/** Scheme-like pairs that can be used to form a list of integers.
 *  @author P. N. Hilfinger
 *  [Do not modify this file.]
 */
public class IntList {
    /** First element of list. */
    public int head;
    /** Remaining elements of list. */
    public IntList tail;

    /** A List with head HEAD0 and tail TAIL0. */
    public IntList(int head0, IntList tail0) {
        head = head0;
        tail = tail0;
    }

    /** A List with null tail, and head = 0. */
    public IntList() {
        /* NOTE: public IntList () { }  would also work. */
        this (0, null);
    }

    /** Returns a new IntList containing the ints in ARGS. */
    public static IntList list(Integer ... args) {
        IntList result, p;

        if (args.length > 0) {
            result = new IntList(args[0], null);
        } else {
            return null;
        }

        int k;
        for (k = 1, p = result; k < args.length; k += 1, p = p.tail) {
            p.tail = new IntList(args[k], null);
        }
        return result;
    }

    /** Returns true iff X is an IntList containing the same sequence of ints
     *  as THIS. */
    public boolean equals(Object x) {
        if (!(x instanceof IntList)) {
            return false;
        }
        IntList L = (IntList) x;
        IntList p;
        for (p = this; p != null && L != null; p = p.tail, L = L.tail) {
            if (p.head != L.head) {
                return false;
            }
        }
        if (p != null || L != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return head;
    }

    @Override
    public String toString() {
        Formatter out = new Formatter();
        String sep;
        sep = "(";
        for (IntList p = this; p != null; p = p.tail) {
            out.format("%s%d", sep, p.head);
            sep = ", ";
        }
        out.format(")");
        return out.toString();
    }

}
