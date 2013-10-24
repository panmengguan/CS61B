import ucb.junit.textui;

import org.junit.Test;

import static org.junit.Assert.*;

/** BigMat unit test
 *  @author Kiet Lam
 */
public class BigMatTest {


    /** Run the unit test.*/
    public static void main(String[] args) {
        textui.runClasses(BigMatTest.class);
    }


    @Test
    public void testMostOnes() {
        BitMatrix matrix = new BitMatrix(4);

        matrix.set(0, 0, 0);
        matrix.set(0, 1, 0);
        matrix.set(0, 2, 0);
        matrix.set(0, 3, 0);


        matrix.set(1, 0, 1);
        matrix.set(1, 1, 0);
        matrix.set(1, 2, 0);
        matrix.set(1, 3, 0);


        matrix.set(2, 0, 1);
        matrix.set(2, 1, 1);
        matrix.set(2, 2, 1);
        matrix.set(2, 3, 0);


        matrix.set(3, 0, 1);
        matrix.set(3, 1, 1);
        matrix.set(3, 2, 0);
        matrix.set(3, 3, 0);

        assertEquals("Row with most ones should be 2",
                     2, BigMat.mostOnes(matrix));
    }
}
