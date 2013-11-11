import java.util.LinkedList;
import java.util.ListIterator;

/** Class for exploring properties of LinkedLists.
 *  @author Kiet Lam
 */
class ListTesting {

    /** Carry out a test on a list of size ARGS[0] (converted to an
     *  integer), or 1000 by default. */
    public static void main(String... args) {
        int N;
        N = 1000;
        if (args.length > 0) {
            N = Integer.parseInt(args[0]);
        }

        LinkedList<Integer> ls = new LinkedList<Integer>();

        for (int i = 0; i < N; i += 1) {
            ls.add(i);
        }

        ListIterator<Integer> iter = ls.listIterator(ls.size());
        timeBackwards(iter, N);
    }

    /** Times how long to set the entire list backwards using ITER and K.*/
    static void timeBackwards(ListIterator<Integer> iter, int k) {
        long start = System.currentTimeMillis();

        double ops = 0;

        while (iter.hasPrevious()) {
            iter.previous();
            iter.set(k);
            ops += 1;
        }

        long end = System.currentTimeMillis();

        System.out.printf("Performed %d backwards operations in "
                          + "%.2f seconds (%.2g msec/operation)%n",
                          (int) ops, (end - start) * 0.001,
                          (end - start) / ops);
    }
}
