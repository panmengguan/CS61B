import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/** A simple test framework for playing around with Patterns.
 *  @author P. N. Hilfinger.
 */
public class Matching {
    /** java Matching reads in pairs of potentially multi-line
     *  strings, with each string terminated by the sequence
     *  "///<newline>".  For each pair, S and P, it interprets P as a
     *  Pattern and reports whether it matches the string S.  If they
     *  match, it additionally prints out the captured groups,
     *  specified in P by parentheses.  Input terminates at the
     *  string "QUIT///<newline>" */
    public static void main(String[] ignored) {
        Scanner inp = new Scanner(System.in);
        inp.useDelimiter("///\r?\n");
        System.out.println("Enter QUIT/// to end the program.");
        while (true) {
            System.out.println("One or more lines, ending with "
                               + "/// and a newline.");
            if (!inp.hasNext() || inp.hasNext("QUIT")) {
                break;
            }
            String str = inp.next();
            System.out.println("Enter a pattern, ending with "
                               + "/// and a newline.");
            if (!inp.hasNext() || inp.hasNext("QUIT")) {
                break;
            }
            String patn = inp.next();
            Matcher mat = Pattern.compile(patn).matcher(str);
            if (mat.matches()) {
                System.out.println("Matches.");
                for (int i = 1; i <= mat.groupCount(); i += 1) {
                    System.out.printf("  Group %d: '%s'%n",
                                      i, mat.group(i));
                }
            } else {
                System.out.println("No match.");
            }
        }
    }
}
