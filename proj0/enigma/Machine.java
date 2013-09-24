package enigma;

/** Class that represents a complete enigma machine.
 *  @author Kiet Lam
 */
class Machine {

    /** Rotors for the machine, going from 0-4 (left-to-right).*/
    private Rotor[] rotors;

    /** The GROUPING number for printing.*/
    public static final int GROUPING = 5;

    /** Construct a machine using 5 rotors.
     *  REFLECTOR goes to 0 position
     *  FIXED goes to 1 position
     *  ROT3 goes to 2 position
     *  ROT4 goes to 3 position
     *  ROT5 goes to 4 position
     */
    public Machine(Rotor reflector, Rotor fixed,
                   final Rotor rot3, final Rotor rot4, Rotor rot5) {

        rotors = new Rotor[5];

        rotors[0] = reflector;
        rotors[1] = fixed;
        rotors[2] = rot3;
        rotors[3] = rot4;
        rotors[4] = rot5;

        rot4.setOnNotchAdvance(new Rotor.OnNotchAdvance() {

                @Override
                public void onNotchAdvance() {
                    rot3.advance();
                }
            });

        rot5.setOnNotchAdvance(new Rotor.OnNotchAdvance() {

                @Override
                public void onNotchAdvance() {
                    rot4.advance();
                }
            });
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        String content = "";

        char[] chs = msg.toCharArray();

        for (int i = 0; i < chs.length; i += 1) {
            content += convert(chs[i]);
            if ((i + 1) % GROUPING == 0) {
                content += " ";
            }
        }

        return content;
    }

    /** Returns the converted character from CH.*/
    char convert(char ch) {
        boolean initialNotch = rotors[4].atNotch();

        rotors[4].advance();

        if (rotors[3].atNotch() && !initialNotch) {
            rotors[3].advance();
        }

        int c = Rotor.toIndex(ch);

        for (int i = rotors.length - 1; i >= 0; i -= 1) {
            c = rotors[i].convertForward(c);
        }

        for (int i = 1; i < rotors.length; i += 1) {
            c = rotors[i].convertBackward(c);
        }

        return Rotor.toLetter(c);
    }
}
