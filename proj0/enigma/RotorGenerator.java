package enigma;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/** Rotors generator.
 *  @author Kiet Lam
 */
class RotorGenerator {

    /** Set of Rotors that have notches.*/
    public static final Set<String> NORMAL_ROTORS = new HashSet<String>() {
        {
            add("I");
            add("II");
            add("III");
            add("IV");
            add("V");
            add("VI");
            add("VII");
            add("VIII");
        }
    };

    /** Set of Rotors that do not have notches.*/
    public static final Set<String> FIXED_ROTORS = new HashSet<String>() {
        {
            add("BETA");
            add("GAMMA");
        }
    };

    /** Set of reflector Rotors.*/
    public static final Set<String> REFLECTORS = new HashSet<String>() {
        {
            add("B");
            add("C");
        }
    };

    /** Returns the Rotor with the ROTORID.*/
    static Rotor getRotor(String rotorID) {
        String[] config = null;

        int i = 0;

        for (; i < PermutationData.ROTOR_SPECS.length; i += 1) {
            if (PermutationData.ROTOR_SPECS[i][0].equals(rotorID)) {
                config = PermutationData.ROTOR_SPECS[i];
                break;
            }
        }

        if (config == null) {
            return null;
        }

        String[] spec = PermutationData.ROTOR_SPECS[i];

        return generateFromSpec(spec, rotorID);
    }

    /** Returns a Rotor from a SPEC line following PermutationData's
     *  template for ROTORID.*/
    static Rotor generateFromSpec(String[] spec, String rotorID) {
        Set<Integer> notches = new HashSet<Integer>();
        char[] forwardChars = spec[1].toCharArray();

        Map<Integer, Integer> forwardMap = getMapping(forwardChars);

        if (NORMAL_ROTORS.contains(rotorID)) {

            char[] backwardChars = spec[2].toCharArray();

            Map<Integer, Integer> backwardMap = getMapping(backwardChars);
            return new Rotor(forwardMap, backwardMap, notches);

        } else if (FIXED_ROTORS.contains(rotorID)) {

            char[] backwardChars = spec[2].toCharArray();

            Map<Integer, Integer> backwardMap = getMapping(backwardChars);
            return new FixedRotor(forwardMap, backwardMap);

        } else if (REFLECTORS.contains(rotorID)) {
            return new Reflector(forwardMap);
        }

        return null;
    }

    /** Returns a mapping given a char[] CHARS cycle representation.*/
    static Map<Integer, Integer> getMapping(char[] chars) {
        Map<Integer, Integer> mapping = new HashMap<Integer, Integer>();

        for (int i = 0; i < chars.length; i += 1) {
            mapping.put(i, Rotor.toIndex(chars[i]));
        }

        return mapping;
    }
}
