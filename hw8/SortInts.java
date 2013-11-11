import java.util.List;
import java.util.ArrayList;

/** HW #8, Problem 3.
 *  @author Kiet Lam
 */
public class SortInts {

    /** Sort A into ascending order.  Assumes that 0 <= A[i] < n*n for all
     *  i, and that the A[i] are distinct. */
    static void sort(long[] A) {
        for (int digit = 0; digit < Math.log10(A.length * A.length);
             digit += 1) {
            ArrayList<List<Long>> ls = new ArrayList<List<Long>>(10);

            for (int i = 0; i < 10; i += 1) {
                ls.add(i, new ArrayList<Long>());
            }

            for (int i = 0; i < A.length; i += 1) {
                long num = A[i];
                int numDigit = -1;

                if (digit == 0) {
                    numDigit = (int) num % 10;
                } else {
                    numDigit = (int) (num / (10 * digit)) % 10;
                }

                ls.get(numDigit).add(num);
            }

            int counter = 0;
            for (int i = 0; i < ls.size(); i += 1) {
                for (int j = 0; j < ls.get(i).size(); j += 1) {
                    A[counter] = ls.get(i).get(j);
                    counter += 1;
                }
            }
        }
    }
}
