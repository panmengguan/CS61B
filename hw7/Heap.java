import java.util.ArrayList;

/** A Heap of T ordered with largest element first.
 *  @author Kiet Lam*/
class Heap<T extends Comparable<? super T>> {
    /** The heap data.  The children of element # K in data are at
     *  locations 2K+1 and 2K+2, when those locations are valid (K>=0).
     */
    private final ArrayList<T> data = new ArrayList<T>();

    /** Add X to the heap. */
    void add(T x) {
        data.add(x);
        reheapifyUp(data.size() - 1);
    }

    /** Print all values in THIS that are >= X on the
     *  standard output, one per line. */
    void printLarger(T x) {
        printHelper(0, x);
    }

    /** Print the all the content beginning at INDEX that is >= X..*/
    void printHelper(int index, T x) {
        if (index >= data.size()) {
            return;
        }

        T t = data.get(index);

        if (t.compareTo(x) >= 0) {
            System.out.println(t);
        }

        printLeft(index, x);
        printRight(index, x);
    }

    /** Print the left children of INDEX >= X.*/
    void printLeft(int index, T x) {
        printHelper(2 * index + 1, x);
    }

    /** Print the right children of INDEX >= X.*/
    void printRight(int index, T x) {
        printHelper(2 * index + 2, x);
    }

    /** Restore the heap property, assuming that it may be violated only
     *  at K (a position in the array 'data') and K's parent. */
    private void reheapifyUp(int k) {
        T v = data.get(k);
        for (int p = k; k > 0; k = p) {
            p = (k - 1) / 2;
            if (v.compareTo(data.get(p)) > 0) {
                data.set(k, data.get(p));
            } else {
                break;
            }
        }
        data.set(k, v);
    }

}
