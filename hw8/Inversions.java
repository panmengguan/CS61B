import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/** HW #8, Problem 4.
 *  @author Kiet Lam
 */
public class Inversions {

    /** A main program for testing purposes.  Prints the number of inversions
     *  in the sequence ARGS. */
    public static void main(String[] args) {
        System.out.println(inversions(Arrays.asList(args)));
    }

    /** Return the number of inversions of T objects in ARGS. */
    public static <T extends Comparable<? super T>> int inversions(List<T> args)
    {
        int inversions = 0;

        while (args.size() > 0) {
            List<T> temp = new ArrayList<T>(args);
            Collections.sort(temp);

            T elem = args.get(0);
            int index = temp.indexOf(elem);
            inversions += index;

            args.remove(0);
        }

        return inversions;
    }
}
