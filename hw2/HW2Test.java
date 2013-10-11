/** java HW2Test should test the methods defined in Lists and Arrays.
 *  @author Kiet Lam
 */
public class HW2Test {

    /** Run all tests. */
    public static void main(String[] args) {
        int[] ars = {1, 3, 7, 5, 4, 6, 9, 10};
        System.out.println(Lists.naturalRuns(IntList.list(ars)));

        int[] blank = {};

        int[] a1 = {1, 2, 3};
        int[] a2 = {4, 5, 6};
        int[] a3 = {1, 2, 3, 4, 5, 6};

        Utils.print(Arrays.catenate(a1, a2));
        System.out.println();

        Utils.print(Arrays.catenate(a1, blank));
        System.out.println();

        Utils.print(Arrays.catenate(blank, blank));
        System.out.println();

        Utils.print(Arrays.catenate(blank, a1));
        System.out.println();

        Utils.print(Arrays.remove(a3, 1, 3));
        System.out.println();

        Utils.print(Arrays.remove(a3, 1, 10));
        System.out.println();

        Utils.print(Arrays.remove(a3, 1, 0));
        System.out.println();

        Utils.print(Arrays.remove(blank, 1, 3));
        System.out.println();
    }
}
