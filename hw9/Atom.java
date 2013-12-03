/** Represents a Scheme symbol (a kind of atom).
 *  @author P. N. Hilfinger */
class Atom extends Value {

    /** A symbol whose text is SYMBOL. */
    Atom(String symbol) {
        _symbol = symbol;
    }

    @Override
    boolean atomp() {
        return true;
    }

    @Override
    public String toString() {
        return _symbol;
    }

    /** My text. */
    private String _symbol;
}
