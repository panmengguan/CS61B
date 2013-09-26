/** A WeirdList holds a sequence of integers. */
public class WeirdList {
    /** The empty sequence of integers. */
    public static final WeirdList EMPTY = new EmptyList();

    private int _head;
    private WeirdList _tail;

    /** A new WeirdList whose head is HEAD and tail is TAIL. */
    public WeirdList(int head, WeirdList tail) {
        _head = head;
        _tail = tail;
    }

    /** Returns the number of elements in the sequence that
     *  starts with THIS. */
    public int length() {
        return 1 + _tail.length();
    }

    /** Apply FUNC.apply to every element of THIS WeirdList in
     *  sequence, and return a WeirdList of the resulting values. */
    public WeirdList map(IntUnaryFunction func) {
        return new WeirdList(func.apply(_head), _tail.map(func));
    }

    /** Print the contents of THIS WeirdList on the standard output
     *  (on one line, each followed by a blank).  Does not print
     *  an end-of-line. */
    public void print() {
        System.out.print(_head + " ");
        _tail.print();
    }

    private static class EmptyList extends WeirdList {

        /** Creates an empty list with HEAD and TAIL.*/
        public EmptyList() {
            super(0, null);
        }

        /** Returns 0 for length.*/
        public int length() {
            return 0;
        }

        /** Returns an EmptyList for mapping FUNC.*/
        public WeirdList map(IntUnaryFunction func) {
            return new EmptyList();
        }

        /** Do nothing since we can't print empty list'.*/
        public void print() {
        }
    }
}
