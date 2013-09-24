package enigma;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.HashSet;
import java.util.Set;

/** Enigma simulator.
 *  @author Kiet Lam
 */
public final class Main {

    /** The expected number of space delimited strings in a config line.*/
    static final int EXPECTED_STRS_CONFIG_NUM = 7;

    /** The expected number of rotors.*/
    static final int EXPECTED_ROTORS_NUM = 5;

    /** Process a sequence of encryptions and decryptions, as
     *  specified in the input from the standard input.  Print the
     *  results on the standard output. Exits normally if there are
     *  no errors in the input; otherwise with code 1. */
    public static void main(String[] unused) {
        Machine M;
        BufferedReader input =
            new BufferedReader(new InputStreamReader(System.in));

        M = null;

        try {
            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }

                if (isConfigurationLine(line)) {
                    M = readConfiguration(line);
                } else {
                    if (M == null) {
                        System.exit(1);
                    }

                    printMessageLine(M.convert(standardize(line)));
                }
            }
        } catch (IOException excp) {
            System.err.printf("Input error: %s%n", excp.getMessage());
            System.exit(1);
        }
    }

    /** Return true iff LINE is an Enigma configuration line. */
    private static boolean isConfigurationLine(String line) {
        if (line.equals("") || line.equals("\n")
            || line.length() <= 0) {
            return false;
        }

        return line.charAt(0) == '*';
    }

    /** Returns a Machine given a valid configuration LINE.*/
    private static Machine readConfiguration(String line) {
        String[] strs = line.split(" ");
        Set<String> rotors = new HashSet<String>();

        if (strs.length != EXPECTED_STRS_CONFIG_NUM) {
            return null;
        }

        if (!RotorGenerator.REFLECTORS.contains(strs[1])) {
            return null;
        }

        Rotor reflector = RotorGenerator.getRotor(strs[1]);
        rotors.add(strs[1]);

        if (!RotorGenerator.FIXED_ROTORS.contains(strs[2])) {
            return null;
        }

        Rotor fixed = RotorGenerator.getRotor(strs[2]);
        rotors.add(strs[2]);

        if (!RotorGenerator.NORMAL_ROTORS.contains(strs[3])) {
            return null;
        }

        Rotor rotor3 = RotorGenerator.getRotor(strs[3]);
        rotors.add(strs[3]);

        if (!RotorGenerator.NORMAL_ROTORS.contains(strs[4])) {
            return null;
        }

        Rotor rotor4 = RotorGenerator.getRotor(strs[4]);
        rotors.add(strs[4]);

        if (!RotorGenerator.NORMAL_ROTORS.contains(strs[5])) {
            return null;
        }

        Rotor rotor5 = RotorGenerator.getRotor(strs[5]);
        rotors.add(strs[5]);

        if (!strs[6].matches("[A-Z]+") || strs[6].length() != 4) {
            return null;
        }

        char[] inits = strs[6].toCharArray();

        fixed.setSetting(Rotor.toIndex(inits[0]));
        rotor3.setSetting(Rotor.toIndex(inits[1]));
        rotor4.setSetting(Rotor.toIndex(inits[2]));
        rotor5.setSetting(Rotor.toIndex(inits[3]));

        if (rotors.size() != EXPECTED_ROTORS_NUM) {
            return null;
        }

        return new Machine(reflector, fixed, rotor3, rotor4, rotor5);
    }

    /** Return the result of converting LINE to all upper case,
     *  removing all blanks and tabs.  It is an error if LINE contains
     *  characters other than letters and blanks. */
    private static String standardize(String line) {
        line = line.replaceAll("\t", "");
        line = line.replaceAll(" ", "");
        line = line.replaceAll("\n", "");
        line = line.toUpperCase();

        if (!line.matches("[A-Z]+")) {
            return "";
        }

        return line;
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private static void printMessageLine(String msg) {
        System.out.println(msg);
    }
}
