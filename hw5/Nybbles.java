/** Represents an array of integers each in the range -8..7.
 *  Such integers may be represented in 4 bits (called nybbles).
 *  @author Kiet Lam
 */
public class Nybbles {
    /** Return an array of size N. */
    public Nybbles(int N) {
        _data = new int[(N + 7) / 8];
        _n = N;
    }

    /** Return the size of THIS. */
    public int size() {
        return _n;
    }

    /** Return the Kth integer in THIS array, numbering from 0.
     *  Assumes 0 <= K < N. */
    public int get(int k) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else {
            int index = k / 8;
            int num = _data[index];

            int c = 0;

            for (int i = 0; i < 3; i += 1) {
                c += ((1 << (i + 4 * (k % 8))) & num) >> (4 * (k % 8));
            }

            int sign = (1 << (3 + 4 * (k % 8))) & num;

            if (sign > 0 && c == 0) {
                c = -8;
            } else if (sign > 0) {
                c ^= 1 << 0;
                c ^= 1 << 1;
                c ^= 1 << 2;
                c = (-1) * (c + 1);
            }

            return c;
        }
    }

    /** Set the Kth integer in THIS array to VAL.  Assumes
     *  0 <= K < N and -8 <= VAL < 8. */
    public void set(int k, int val) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else if (val < -8 || val >= 8) {
            throw new IllegalArgumentException();
        } else {
            int index = k / 8;
            int c = _data[index];

            int clearer = (1 + 2 + 4 + 8) << (4 * (k % 8));

            c &= ~clearer;

            int bit1 = (val & 1) << (4 * (k % 8));
            int bit2 = (val & 2) << (4 * (k % 8));
            int bit3 = (val & 4) << (4 * (k % 8));
            int bit4 = (1 << (3 + 4 * (k % 8))) & val;

            int b1 = c & (1 << (0 + 4 * (k % 8)));
            int b2 = c & (2 << (1 + 4 * (k % 8)));
            int b3 = c & (4 << (2 + 4 * (k % 8)));
            int b4 = c & (8 << (3 + 4 * (k % 8)));

            if (val == -8) {
                bit1 = 0;
                bit2 = 0;
                bit3 = 0;
            } else if (val < 0) {
                bit4 = (1 << (3 + 4 * (k % 8)));
            }

            c |= bit1;
            c |= bit2;
            c |= bit3;
            c |= bit4;

            _data[index] = c;
        }
    }

    /** Size of current array (in nybbles). */
    private int _n;
    /** The array data, packed 8 nybbles to an int. */
    private int[] _data;
}
