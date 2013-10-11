/** An alternative addition procedure.
 *  @author Kiet Lam
 */
public class Adder {
    /** Returns X+Y. */
    public static int add(int x, int y) {
        int c = x & y;
        int r = x ^ y;
        int result = r;
        for (int i = 0; i < 32; i += 1) {
            c = c << 1;
            result = c ^ r;
            c = r & c;
            r = result;
        }

        return result;
    }
}
