/** HW 6, Problem 3.
 *  @author Kiet Lam
 */
class BigMat {

    /** Returns the number, r, of the row of A that contains the most 1's.
     *  When there is more than one such row, the smallest index. */
    public static int mostOnes(BitMatrix A) {
        int row = 0;

        int zeroIndex = 0;

        for (int i = 0; i < A.size(); i += 1) {
            if (A.get(i, zeroIndex) != 0) {
                for (int j = 0; j < A.size(); j += 1) {
                    if (A.get(i, j) == 0) {
                        zeroIndex = j;
                        break;
                    }
                }

                row = i;
            } else {
                continue;
            }
        }

        return row;
    }
}
