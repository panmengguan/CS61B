/** Represents a Scheme pair.
 *  @author P. N. Hilfinger */
class Pair extends Value {

    /** A pair whose car is CAR and whose cdr is CDR. */
    Pair(Value car, Value cdr) {
        _car = car;
        _cdr = cdr;
    }

    @Override
    boolean pairp() {
        return true;
    }

    @Override
    Value car() {
        return _car;
    }

    @Override
    Value cdr() {
        return _cdr;
    }

    @Override
    void setcar(Value v) {
        _car = v;
    }

    @Override
    void setcdr(Value v) {
        _cdr = v;
    }

    /** My head and tail values. */
    private Value _car, _cdr;

}
