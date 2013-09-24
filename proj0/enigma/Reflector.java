package enigma;

import java.util.Map;

/** Class that represents a reflector in the enigma.
 *  @author Kiet Lam
 */
class Reflector extends FixedRotor {

    /** Construct a Reflector using only a FORWARDMAP.*/
    public Reflector(Map<Integer, Integer> forwardMap) {
        super(forwardMap, null);
    }

    /** Don't do anything here because Reflector
     *  do not have settings (POSN is meaningless).*/
    @Override
    void setSetting(int posn) {
    }

    @Override
    boolean hasInverse() {
        return false;
    }

    /** Returns a useless value; should never be called.
     * @param unused
     */
    @Override
    int convertBackward(int unused) {
        throw new UnsupportedOperationException();
    }
}
