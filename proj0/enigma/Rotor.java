package enigma;

import java.util.Set;
import java.util.Map;


/** Class that represents a rotor in the enigma machine.
 *  @author Kiet Lam
 */
class Rotor {

    /** Size of alphabet used for plaintext and ciphertext. */
    static final int ALPHABET_SIZE = 26;

    /** Assuming that P is an integer in the range 0..25, returns the
     *  corresponding upper-case letter in the range A..Z. */
    static char toLetter(int p) {
        return (char) (p + 'A');
    }

    /** Assuming that C is an upper-case letter in the range A-Z, return the
     *  corresponding index in the range 0..25. Inverse of toLetter. */
    static int toIndex(char c) {
        return (int) (c - 'A');
    }

    /** My current setting (index 0..25, with 0 indicating that 'A'
     *  is showing). */
    private int _setting;

    /** The notches for this rotor.*/
    private Set<Integer> _notches;

    /** The callback for when this rotor advances the pawl.*/
    private OnNotchAdvance _onNotchAdvance;

    /** Forward mapping for this rotor.*/
    private Map<Integer, Integer> _forwardMapping;

    /** Backward mapping for this rotor.*/
    private Map<Integer, Integer> _backwardMapping;

    /** Callback interface for when this rotor advances the notch.*/
    public interface OnNotchAdvance {

        /** Callback for when an advance() causes
         *  the notch to move.
         */
        void onNotchAdvance();
    }

    /** Constructs a Rotor given a FORWARDMAP, BACKWARDMAP,
     *  and a set of NOTCHES.*/
    public Rotor(Map<Integer, Integer> forwardMap,
                 Map<Integer, Integer> backwardMap,
                 Set<Integer> notches) {
        _setting = 0;
        _notches = notches;
        _forwardMapping = forwardMap;
        _backwardMapping = backwardMap;
    }

    /** Advance me one position. */
    void advance() {

        if (atNotch() && _onNotchAdvance != null) {
            _onNotchAdvance.onNotchAdvance();
        }

        _setting += 1;

        if (_setting >= ALPHABET_SIZE) {
            _setting -= ALPHABET_SIZE;
        }
    }

    /** Returns the notches for this Rotor.*/
    Set<Integer> getNotches() {
        return _notches;
    }

    /** Set the notches for this Rotor to be NOTCHES.*/
    void setNotches(Set<Integer> notches) {
        _notches = notches;
    }

    /** Returns true iff this rotor has a ratchet and can advance. */
    boolean advances() {
        return true;
    }

    /** Returns true iff this rotor has a left-to-right inverse. */
    boolean hasInverse() {
        return true;
    }

    /** Return my current rotational setting as an integer between 0
     *  and 25 (corresponding to letters 'A' to 'Z').  */
    int getSetting() {
        return _setting;
    }

    /** Set getSetting() to POSN.  */
    void setSetting(int posn) {
        assert 0 <= posn && posn < ALPHABET_SIZE;
        _setting = posn;
    }

    /** Set the callback for when the rotor advances the notch
     *  to be ONADVANCE.*/
    void setOnNotchAdvance(OnNotchAdvance onAdvance) {
        _onNotchAdvance = onAdvance;
    }

    /** Return the conversion of P (an integer in the range 0..25)
     *  according to my permutation. */
    int convertForward(int p) {
        return convert(p, _forwardMapping);
    }

    /** Return the conversion of E (an integer in the range 0..25)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        return convert(e, _backwardMapping);
    }

    /** Returns a converted P using MAP.*/
    int convert(int p, Map<Integer, Integer> map) {
        p += _setting;

        if (p >= ALPHABET_SIZE) {
            p -= ALPHABET_SIZE;
        }

        int i = map.get(p);

        i -= _setting;

        if (i < 0) {
            i += ALPHABET_SIZE;
        }

        return i;
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        return _notches.contains(_setting);
    }
}
