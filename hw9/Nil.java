/** Represents the value () in Scheme.
 *  @author P. N. Hilfinger */
class Nil extends Value {

    /** The single Nil value. */
    static final Value NIL = new Nil();

    /** There is only one instance of me; I can't be instantiated outside this
     *  class. */
    private Nil() {
    }

    /** Return true iff I am an atom. */
    boolean atomp() {
        return true;
    }

    /** Return true iff I am the atom nil. */
    boolean nullp() {
        return true;
    }

    @Override
    public String toString() {
        return "()";
    }

}

