/** java HW1Test should test the methods defined in Progs.
 *  @author
 */
public class HW1Test {

    /** Run all tests. */
    public static void main(String[] args) {
        report("factorSum", testFactorSum());
        report("printSociablePairs", testPrintSociablePairs());
        report("dcatenate", testDcatenate());
        report("sublist", testSublist());
        report("dsublist", testDsublist());
    }

    /** Print message that test NAME has (if ISOK) or else has not
     *  passed. */
    private static void report(String name, boolean isOK) {
        if (isOK) {
            System.out.printf("%s OK.%n", name);
        } else {
            System.out.printf("%s FAILS.%n", name);
        }
    }


    /** Return true iff factorSum passes its tests. */
    private static boolean testFactorSum() {

        if (Progs.factorSum(2) != 1) {
            return false;
        }

        if (Progs.factorSum(20) != 22) {
            return false;
        }

        if (Progs.factorSum(49) != 8) {
            return false;
        }

        if (Progs.factorSum(55) != 17) {
            return false;
        }

        if (Progs.factorSum(54) != 66) {
            return false;
        }

        return true;
    }

    /** Return true iff printSociablePairs passes its tests. */
    private static boolean testPrintSociablePairs() {
        Progs.printSociablePairs(5);
        Progs.printSociablePairs(300);
        Progs.printSociablePairs(1250);
        Progs.printSociablePairs(284);
        Progs.printSociablePairs(3000);
        return true;
    }

    /** Return true iff dcantenate passes its tests. */
    private static boolean testDcatenate() {
        IntList l1 = IntList.list(1, 3, 5);
        IntList l2 = IntList.list(2, 4);

        IntList l3 = IntList.list(1, 3, 5, 2, 4);

        if (!Progs.dcatenate(l1, l2).equals(l3)) {
            return false;
        }

        IntList emptyL = IntList.list();

        if (!Progs.dcatenate(emptyL, l1).equals(l1)) {
            return false;
        }

        if (!Progs.dcatenate(l1, emptyL).equals(l1)) {
            return false;
        }


        return true;
    }

    /** Return true iff sublist passes its tests. */
    private static boolean testSublist() {

        if (!testSubListArguments()) {
            return false;
        }

        IntList l1 = IntList.list(1, 2, 3, 4);
        IntList l2 = IntList.list(1, 2, 3, 4);
        IntList l3 = IntList.list(2, 3);
        IntList l4 = IntList.list(3, 4);
        IntList l5 = IntList.list(1, 2);

        if (!Progs.sublist(l1, 1, 2).equals(l3)) {
            return false;
        }

        if (!Progs.sublist(l1, 0, 2).equals(l5)) {
            return false;
        }

        if (!Progs.sublist(l1, 2, 2).equals(l4)) {
            return false;
        }

        if (!l1.equals(l2)) {
            return false;
        }

        return true;
    }

    /** Return true iff dsublist passes its tests. */
    private static boolean testDsublist() {

        if (!testSubListArguments()) {
            return false;
        }

        IntList l1 = IntList.list(1, 2, 3, 4);
        IntList l2 = IntList.list(1, 2, 3, 4);
        IntList l3 = IntList.list(2, 3);
        IntList l4 = IntList.list(3, 4);
        IntList l5 = IntList.list(1, 2);

        if (!Progs.dsublist(l1, 1, 2).equals(l3)) {
            return false;
        }

        l1 = IntList.list(1, 2, 3, 4);

        if (!Progs.dsublist(l1, 0, 2).equals(l5)) {
            return false;
        }

        l1 = IntList.list(1, 2, 3, 4);

        if (!Progs.dsublist(l1, 2, 2).equals(l4)) {
            return false;
        }

        return true;
    }


    /** Return true iff the edge cases for sublist
     *  are handled.
     */
    private static boolean testSubListArguments() {
        try {
            Progs.sublist(null, 0, 1);
            return false;
        } catch (IllegalArgumentException exception) {
            Exception e = exception;
        }

        try {
            Progs.sublist(IntList.list(1, 2, 3), 3, 1);
            return false;

        } catch (IllegalArgumentException exception) {
            Exception e = exception;
        }

        try {
            Progs.sublist(IntList.list(1, 2, 3), -1, 1);
            return false;
        } catch (IllegalArgumentException exception) {
            Exception e = exception;
        }

        try {
            Progs.sublist(IntList.list(1, 2, 3), 1, -1);
            return false;
        } catch (IllegalArgumentException exception) {
            Exception e = exception;
        }

        try {
            Progs.sublist(IntList.list(1, 2), 0, 4);
            return false;
        } catch (IllegalArgumentException exception) {
            Exception e = exception;
        }

        return true;
    }
}
