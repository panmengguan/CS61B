/** Represents a Scheme value (an "atom" (string) or a pair).
 *  @author P. N. Hilfinger */
abstract class Value {
    /** Return true iff I am an atom. */
    boolean atomp() {
        return false;
    }

    /** Return true iff I am a pair. */
    boolean pairp() {
        return false;
    }

    /** Return true iff I am the atom nil. */
    boolean nullp() {
        return false;
    }

    /** Return my car (head) value, if a pair (otherwise error). */
    Value car() {
        throw new IllegalStateException("not a pair");
    }

    /** Return my cdr (tail) value, if a pair (otherwise error). */
    Value cdr() {
        throw new IllegalStateException("not a pair");
    }

    /** Set car() to V, if I am a pair (otherwise error). */
    void setcar(Value v) {
        throw new IllegalStateException("not a pair");
    }

    /** Set cdr() to V, if I am a pair (otherwise error). */
    void setcdr(Value v) {
        throw new IllegalStateException("not a pair");
    }

}
