import java.util.Scanner;
import java.util.regex.MatchResult;


/** Solution to #3.
 *  @author Kiet Lam*/
public class P3 {

    /** Solves #3.*/
    public static void main(String... ignored) {

        String regex = "([0-9]+)\\s+";

        Scanner scanner = new Scanner(System.in);

        while (scanner.findWithinHorizon(regex, 0) != null) {
            MatchResult match = scanner.match();

            int n = Integer.parseInt(match.group(1));

            int start = 0;
            int end = 0;

            for (int i = 0; i < n; i += 1) {
                start += 1 * (int) Math.pow(10, i);
                end   += 3 * (int) Math.pow(10, i);
            }

            for (int i = start; i < end; i += 1) {
                i += 1;

                if (!containsAdjacentRepeat(i)) {
                    System.out.printf("The smallest good numeral of length %d "
                                      + "is %d\n\n", n, i);
                }
            }
        }
    }

    /** Returns true iff N contains adjacent repeated substrings.*/
    private static boolean containsAdjacentRepeat(int n) {
        return true;
    }
}
