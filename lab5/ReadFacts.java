import java.util.Scanner;

import java.io.File;
import java.io.IOException;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/** Demonstration of sentence reading for Project 1.
 *  @author Kiet Lam
 */
class ReadFacts {

    /** Pattern describing sentences "<Name> is [not] the <Occupation>". */
    static final Pattern NAME_OCC_PATN =
        Pattern.compile("\\A\\s+([a-zA-Z]*)*?\\s+(is|is\\s+not)\\s+the\\s+"
                        + "([a-zA-Z]*)*?\\.\\s*");

    /** Print out the sentences in the file named ARGS[0]. */
    public static void main(String... args) {
        if (args.length != 1) {
            System.err.println("Usage: java ReadFacts FILENAME");
            System.exit(1);
        }

        Scanner inp;
        try {
            inp = new Scanner(new File(args[0]));
        } catch (IOException e) {
            System.err.printf("Could not read %s.%n", args[0]);
            System.exit(1);
            return;
        }

        while (true) {
            if (inp.findInLine(NAME_OCC_PATN) != null) {
                MatchResult mat = inp.match();
                String name = mat.group(1);
                boolean negated = !mat.group(2).equals("is");
                String occupation = mat.group(3);

                System.out.printf("%s is%s the %s.%n",
                                  name, negated ? " not" : "", occupation);
            } else if (!inp.hasNext()) {
                break;
            } else {
                String rest = inp.nextLine();

                if (!rest.equals("")) {
                    System.out.println("<TRAILING GARBAGE ON LINE>");
                }
            }
        }
    }
}
