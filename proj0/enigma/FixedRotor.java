package enigma;

import java.util.Map;
import java.util.HashSet;

/** Class that represents a rotor that has no ratchet and does not advance.
 *  @author Kiet Lam
 */
class FixedRotor extends Rotor {

    /** Construct a FixedRotor from FORWARDMAP and
     *  BACKWARDMAP.*/
    public FixedRotor(Map<Integer, Integer> forwardMap,
                      Map<Integer, Integer> backwardMap) {
        super(forwardMap, backwardMap, new HashSet<Integer>());
    }

    @Override
    boolean advances() {
        return false;
    }

    @Override
    boolean atNotch() {
        return false;
    }

    /** Fixed rotors do not advance. */
    @Override
    void advance() {
    }
}
