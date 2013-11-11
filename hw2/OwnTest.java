public class OwnTest {

    public static void main(String[] args) {

        IntList[] R = new IntList[6];
        IntList L = IntList.list(1, 2, 3, 4, 5, 6, 7);

        helper(R, L, 1, 0);

        // for (int i = 0; i < R.length; i += 1) {
        //     System.out.println(R[i].toString());
        // }

        IntList l1 = IntList.list(1, 7, 13, 15, 20, 25);
        IntList l2 = IntList.list(6, 9, 13, 14, 17, 20, 21);

        System.out.println(removeAll(l1, l2));
    }

    static void helper(IntList[] R, IntList L, int k, int h) {
        if (L == null) return;
        if (k > 3) {
            k = 1;
        }

        R[h] = L;
        IntList t = L;
        for (int i = 0; i < k; i += 1) {
            if (L == null) return;

            t = L;
            L = L.tail;
        }

        t.tail = null;

        helper(R, L, k + 1, h + 1);
    }

    static IntList dmask(IntList P, IntList L) {
        IntList head = P;
        IntList t = P;

        while (P != null) {
            if (L.head == 0 && P == head) {
            }

            if (L.head == 0) {
                t.tail = P.tail;
                P = P.tail;
            }

            t = P;
            P = P.tail;
        }

        return head;
    }

    public static IntList removeAll(IntList fromList, IntList remove) {
        IntList theList, result, last;

        result = null;
        last = fromList;
        theList = fromList;

        while (theList != null) {
            System.out.println(last);
            if (remove.head < theList.head) {
                remove = remove.tail;
            } else {
                if (result == null) {
                    result = last = theList;
                } else if (theList.head > remove.head) {
                    last = last.tail = theList;
                } else {y
                    theList = theList.tail;
                }
            }
        }

        if (result == null) {
            result = theList;
        }

        return result;
    }
}
