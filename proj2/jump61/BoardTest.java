package jump61;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Set;
import java.util.HashSet;
import java.util.List;


/** Unit test of Board.
 *  @author Kiet Lam
 */
public class BoardTest {

    private static final String NL = System.getProperty("line.separator");

    @Test
    public void testRowSquareN() {
        Board b = new MutableBoard(5);

        assertEquals("bad row", 5, b.row(24));
        assertEquals("bad row", 1, b.row(0));
        assertEquals("bad row", 1, b.row(4));
        assertEquals("bad row", 4, b.row(15));
    }

    @Test
    public void testColumnSquareN() {
        Board b = new MutableBoard(5);

        assertEquals("bad column", 5, b.col(24));
        assertEquals("bad column", 5, b.col(9));
        assertEquals("bad column", 3, b.col(12));
        assertEquals("bad column", 1, b.col(0));
    }

    @Test
    public void testGetSquareN() {
        Board b = new MutableBoard(5);

        assertEquals("bad square number", 24, b.sqNum(5, 5));
        assertEquals("bad square number", 14, b.sqNum(3, 5));
        assertEquals("bad square number", 0, b.sqNum(1, 1));
        assertEquals("bad square number", 22, b.sqNum(5, 3));
    }

    @Test
    public void testWhoseMove() {
        Board b = new MutableBoard(5);

        assertEquals("bad player color", Color.RED, b.whoseMove());
        b.addSpot(Color.RED, 12);
        assertEquals("bad player color", Color.BLUE, b.whoseMove());
        b.addSpot(Color.BLUE, 13);
        assertEquals("bad player color", Color.RED, b.whoseMove());
    }

    @Test
    public void testWhoseMoveWonBoard() {
        Board b = new MutableBoard(2);
        b.set(0, 1, Color.BLUE);
        b.set(1, 1, Color.BLUE);
        b.set(2, 1, Color.BLUE);
        b.set(3, 1, Color.BLUE);

        assertEquals("bad player color", Color.RED, b.whoseMove());

        b = new MutableBoard(2);
        b.set(0, 1, Color.RED);
        b.set(1, 1, Color.RED);
        b.set(2, 1, Color.RED);
        b.set(3, 1, Color.RED);

        assertEquals("bad player color", Color.BLUE, b.whoseMove());
    }

    @Test
    public void testSpots() {
        MutableBoard b = new MutableBoard(5);

        assertEquals("wrong spot count", 0, b.spots(0));
        assertEquals("wrong spot count", 0, b.spots(1, 1));

        int redSpot = 0;
        int blueSpot1 = 1;
        int blueSpot2 = 2;

        b.addSpot(Color.RED, redSpot);
        b.addSpot(Color.BLUE, blueSpot1);
        b.addSpot(Color.RED, redSpot);
        b.addSpot(Color.BLUE, blueSpot2);

        for (int i = 0; i < b.size() * b.size(); i += 1) {
            int numSpots;

            if (i == blueSpot1 || i == blueSpot2) {
                numSpots = 1;
            } else if (i == redSpot) {
                numSpots = 2;
            } else {
                numSpots = 0;
            }

            int row = b.row(i);
            int col = b.col(i);
            assertEquals("wrong spot count", numSpots, b.spots(i));
            assertEquals("wrong spot count", numSpots, b.spots(row, col));
        }
    }

    @Test
    public void testSize() {
        Board B = new MutableBoard(5);
        assertEquals("bad length", 5, B.size());
        ConstantBoard C = new ConstantBoard(B);
        assertEquals("bad length", 5, C.size());
        Board D = new MutableBoard(C);
        assertEquals("bad length", 5, C.size());
    }

    @Test
    public void testCopy() {
        Board b = new MutableBoard(5);
        b.addSpot(Color.RED, 2, 2);
        b.addSpot(Color.BLUE, 13);
        b.addSpot(Color.RED, 6);

        Board b1 = new MutableBoard(5);
        b1.copy(b);

        for (int i = 0; i < b.size() * b.size(); i += 1) {
            assertEquals("wrong square color", b.color(i),
                         b1.color(i));
            assertEquals("wrong spot count", b.spots(i),
                         b1.spots(i));
        }

        assertEquals("wrong num moves", 3, b1.numMoves());
        assertEquals("wrong player color", Color.BLUE, b1.whoseMove());
    }

    @Test
    public void testColor() {
        Board b = new MutableBoard(5);

        int redSquare = 6;
        int blueSquare = 9;

        b.addSpot(Color.RED, redSquare);
        b.addSpot(Color.BLUE, blueSquare);

        for (int i = 0; i < b.size() * b.size(); i += 1) {
            Color color;

            if (i == redSquare) {
                color = Color.RED;
            } else if (i == blueSquare) {
                color = Color.BLUE;
            } else {
                color = Color.WHITE;
            }

            int row = b.row(i);
            int col = b.col(i);
            assertEquals("wrong square color", color, b.color(i));
            assertEquals("wrong square color", color, b.color(row, col));
        }
    }

    @Test
    public void testExists() {
        Board b = new MutableBoard(5);

        for (int i = 0; i < b.size() * b.size(); i += 1) {
            int row = b.row(i);
            int col = b.col(i);
            assertTrue("Square does exist", b.exists(i));
            assertTrue("Square does exist", b.exists(row, col));
        }

        assertFalse("Square does not exist", b.exists(100));
        assertFalse("Square does not exist", b.exists(-1));
        assertFalse("Square does not exist", b.exists(0, 0));
    }

    @Test
    public void testIsLegal() {
        Board b = new MutableBoard(5);

        b.set(3, 1, Color.RED);
        b.set(14, 1, Color.RED);
        b.set(18, 1, Color.RED);

        Set<Integer> redSquares = new HashSet<Integer>();
        redSquares.add(3);
        redSquares.add(14);
        redSquares.add(18);

        b.set(0, 1, Color.BLUE);
        b.set(16, 1, Color.BLUE);
        b.set(20, 1, Color.BLUE);

        Set<Integer> blueSquares = new HashSet<Integer>();

        blueSquares.add(0);
        blueSquares.add(16);
        blueSquares.add(20);

        for (int i = 0; i < b.size() * b.size(); i += 1) {
            boolean redLegal = !blueSquares.contains(i);
            boolean blueLegal = !redSquares.contains(i);

            int row = b.row(i);
            int col = b.col(i);

            assertEquals("isLegal for Color.RED incorrect", redLegal,
                         b.isLegal(Color.RED, i));
            assertEquals("isLegal for Color.BLUE incorrect", blueLegal,
                         b.isLegal(Color.BLUE, i));
            assertEquals("isLegal for Color.RED incorrect", redLegal,
                         b.isLegal(Color.RED, row, col));
            assertEquals("isLegal for Color.BLUE incorrect", blueLegal,
                         b.isLegal(Color.BLUE, row, col));
        }
    }

    @Test
    public void testWinner() {
        Board b = new MutableBoard(2);

        assertNull("No winner at beginning!", b.getWinner());

        b.set(1, 1, Color.BLUE);

        assertNull("No winner yet!", b.getWinner());

        b.set(0, 1, Color.BLUE);

        b.set(2, 1, Color.BLUE);

        assertNull("No winner yet!", b.getWinner());
        b.set(3, 1, Color.BLUE);

        assertEquals("Color.BLUE should have won!", Color.BLUE,
                     b.getWinner());
    }

    @Test
    public void testNumOfColor() {
        Board b = new MutableBoard(5);

        b.set(1, 1, Color.RED);
        b.set(3, 1, Color.RED);
        b.set(14, 1, Color.BLUE);
        b.set(19, 1, Color.BLUE);

        assertEquals("wrong number of squares", 2, b.numOfColor(Color.RED));
        assertEquals("wrong number of squares", 2, b.numOfColor(Color.BLUE));
        assertEquals("wrong number of squares", 25 - 2 - 2,
                     b.numOfColor(Color.WHITE));
    }

    @Test
    public void testNumMoves() {
        Board b = new MutableBoard(5);

        assertEquals("Num moves is 0 at beginning!",
                     0, b.numMoves());

        b.addSpot(Color.RED, 3);

        assertEquals("Num moves incorrect",
                     1, b.numMoves());

        b.addSpot(Color.BLUE, 5);

        assertEquals("Num moves incorrect",
                     2, b.numMoves());

        b.addSpot(Color.RED, 1);

        assertEquals("Num moves incorrect",
                     3, b.numMoves());

        b.addSpot(Color.BLUE, 5);

        assertEquals("Num moves incorrect",
                     4, b.numMoves());
    }

    @Test
    public void testAddSpot() {
        Board b = new MutableBoard(5);

        b.addSpot(Color.RED, 3);
        b.addSpot(Color.BLUE, 4);
        b.addSpot(Color.RED, 3);

        assertEquals("wrong number of spots", 2, b.spots(3));
        assertEquals("wrong number of spots", 1, b.spots(4));
        assertEquals("wrong square color", Color.RED, b.color(3));
        assertEquals("wrong square color", Color.BLUE, b.color(4));
    }

    @Test
    public void testClear() {
        Board b = new MutableBoard(5);

        b.addSpot(Color.RED, 3);
        b.addSpot(Color.BLUE, 4);
        b.addSpot(Color.RED, 3);

        b.clear(3);

        assertFalse("Square number does not exist", b.exists(18));

        checkBlankBoard(b);
    }

    @Test
    public void testNeighbors() {
        Board b = new MutableBoard(5);

        assertEquals("corners have two neighbors",
                     2, b.neighbors(0));
        assertEquals("corners have two neighbors",
                     2, b.neighbors(4));
        assertEquals("corners have two neighbors",
                     2, b.neighbors(20));
        assertEquals("corners have two neighbors",
                     2, b.neighbors(24));
        assertEquals("corners have two neighbors",
                     2, b.neighbors(5, 5));

        assertEquals("non-corner side squares have three neighbors",
                     3, b.neighbors(2));
        assertEquals("non-corner side squares have three neighbors",
                     3, b.neighbors(1, 3));
        assertEquals("non-corner side squares have three neighbors",
                     3, b.neighbors(5));
        assertEquals("non-corner side squares have three neighbors",
                     3, b.neighbors(9));
        assertEquals("non-corner side squares have three neighbors",
                     3, b.neighbors(22));

        assertEquals("non-side squares have four neighbors",
                     4, b.neighbors(8));
        assertEquals("non-side squares have four neighbors",
                     4, b.neighbors(17));
        assertEquals("non-side squares have four neighbors",
                     4, b.neighbors(3, 4));
    }

    @Test
    public void testUndoBeginning() {
        Board b = new MutableBoard(5);

        b.addSpot(Color.RED, 4);
        b.undo();

        checkBlankBoard(b);
        assertEquals("Num moves should be 0", 0,
                     b.numMoves());
    }

    @Test
    public void testMultipleUndo() {
        Board b = new MutableBoard(5);

        b.addSpot(Color.RED, 7);
        b.addSpot(Color.BLUE, 8);
        b.addSpot(Color.RED, 7);
        b.addSpot(Color.BLUE, 9);
        b.addSpot(Color.RED, 7);

        b.undo();

        assertEquals("incorrect spots", 2, b.spots(7));
        assertEquals("incorrect spots", 1, b.spots(9));

        b.undo();

        assertEquals("incorrect spots", 0, b.spots(9));
        assertEquals("incorrect square color", Color.WHITE, b.color(9));

        b.undo();
        b.undo();
        b.undo();

        checkBlankBoard(b);
    }

    @Test
    public void testNeighborsList() {
        Board b = new MutableBoard(5);

        List<Integer> n1 = b.getNeighbors(6);

        assertEquals("incorrect neighbors num", 4, n1.size());
        assertTrue("incorrect neighbors", n1.contains(1));
        assertTrue("incorrect neighbors", n1.contains(5));
        assertTrue("incorrect neighbors", n1.contains(7));
        assertTrue("incorrect neighbors", n1.contains(11));

        List<Integer> n2 = b.getNeighbors(0);

        assertEquals("incorrect neighbors num", 2, n2.size());
        assertTrue("incorrect neighbors", n2.contains(1));
        assertTrue("incorrect neighbors", n2.contains(5));

        List<Integer> n3 = b.getNeighbors(4);
        assertEquals("incorrect neighbors num", 2, n3.size());
        assertTrue("incorrect neighbors", n3.contains(3));
        assertTrue("incorrect neighbors", n3.contains(9));

        List<Integer> n4 = b.getNeighbors(20);
        assertEquals("incorrect neighbors num", 2, n4.size());
        assertTrue("incorrect neighbors", n4.contains(15));
        assertTrue("incorrect neighbors", n4.contains(21));

        List<Integer> n5 = b.getNeighbors(24);
        assertEquals("incorrect neighbors num", 2, n5.size());
        assertTrue("incorrect neighbors", n5.contains(19));
        assertTrue("incorrect neighbors", n5.contains(23));

        List<Integer> n6 = b.getNeighbors(2);
        assertEquals("incorrect neighbors num", 3, n6.size());
        assertTrue("incorrect neighbors", n6.contains(1));
        assertTrue("incorrect neighbors", n6.contains(3));
        assertTrue("incorrect neighbors", n6.contains(7));

        List<Integer> n7 = b.getNeighbors(10);
        assertEquals("incorrect neighbors num", 3, n7.size());
        assertTrue("incorrect neighbors", n7.contains(5));
        assertTrue("incorrect neighbors", n7.contains(11));
        assertTrue("incorrect neighbors", n7.contains(15));

        List<Integer> n8 = b.getNeighbors(14);
        assertEquals("incorrect neighbors num", 3, n8.size());
        assertTrue("incorrect neighbors", n8.contains(9));
        assertTrue("incorrect neighbors", n8.contains(13));
        assertTrue("incorrect neighbors", n8.contains(19));

        List<Integer> n9 = b.getNeighbors(22);
        assertEquals("incorrect neighbors num", 3, n9.size());
        assertTrue("incorrect neighbors", n9.contains(21));
        assertTrue("incorrect neighbors", n9.contains(17));
        assertTrue("incorrect neighbors", n9.contains(23));
    }

    @Test
    public void testSimpleJumpSquares() {
        Board b = new MutableBoard(3);
        b.set(4, 4, Color.RED);

        b.addSpot(Color.RED, 4);

        assertEquals("incorrect square color", Color.RED,
                     b.color(3));
        assertEquals("incorrect spots", 1, b.spots(3));
        assertEquals("incorrect spots", 1, b.spots(4));
    }

    @Test
    public void testJumpSquares1() {
        Board b = new MutableBoard(3);

        b.set(4, 4, Color.RED);
        b.set(3, 3, Color.BLUE);

        b.addSpot(Color.RED, 4);

        assertEquals("incorrect square color", Color.RED,
                     b.color(0));
        assertEquals("incorrect square color", Color.RED,
                     b.color(1));
        assertEquals("incorrect square color", Color.WHITE,
                     b.color(2));
        assertEquals("incorrect square color", Color.RED,
                     b.color(3));
        assertEquals("incorrect square color", Color.RED,
                     b.color(4));
        assertEquals("incorrect square color", Color.RED,
                     b.color(5));
        assertEquals("incorrect square color", Color.RED,
                     b.color(6));
        assertEquals("incorrect square color", Color.RED,
                     b.color(7));
        assertEquals("incorrect square color", Color.WHITE,
                     b.color(8));

        assertEquals("incorrect num spots", 1, b.spots(0));
        assertEquals("incorrect num spots", 1, b.spots(1));
        assertEquals("incorrect num spots", 0, b.spots(2));
        assertEquals("incorrect num spots", 1, b.spots(3));
        assertEquals("incorrect num spots", 2, b.spots(4));
        assertEquals("incorrect num spots", 1, b.spots(5));
        assertEquals("incorrect num spots", 1, b.spots(6));
        assertEquals("incorrect num spots", 1, b.spots(7));
        assertEquals("incorrect num spots", 0, b.spots(8));
    }

    @Test
    public void testJumpToWin() {
        Board b = new MutableBoard(3);

        b.set(1, 4, Color.RED);
        b.set(3, 3, Color.BLUE);
        b.set(4, 4, Color.RED);
        b.set(5, 3, Color.BLUE);
        b.set(7, 3, Color.BLUE);

        b.addSpot(Color.RED, 4);

        assertEquals("incorrect square color", Color.RED,
                     b.color(0));
        assertEquals("incorrect square color", Color.RED,
                     b.color(1));
        assertEquals("incorrect square color", Color.RED,
                     b.color(2));
        assertEquals("incorrect square color", Color.RED,
                     b.color(3));
        assertEquals("incorrect square color", Color.RED,
                     b.color(4));
        assertEquals("incorrect square color", Color.RED,
                     b.color(5));
        assertEquals("incorrect square color", Color.RED,
                     b.color(6));
        assertEquals("incorrect square color", Color.RED,
                     b.color(7));
        assertEquals("incorrect square color", Color.RED,
                     b.color(8));

        assertEquals("incorrect player color", Color.BLUE,
                     b.whoseMove());
        assertEquals("incorrect player", Color.RED,
                     b.getWinner());
    }

    @Test
    public void testJumpMassive() {
        Board b = new MutableBoard(6);

        b.set(0, 2, Color.RED);
        b.set(1, 1, Color.BLUE);
        b.set(2, 2, Color.RED);
        b.set(3, 2, Color.RED);
        b.set(4, 1, Color.BLUE);
        b.set(5, 2, Color.BLUE);
        b.set(6, 1, Color.BLUE);
        b.set(7, 1, Color.BLUE);
        b.set(8, 1, Color.BLUE);
        b.set(9, 1, Color.BLUE);
        b.set(10, 3, Color.RED);
        b.set(11, 1, Color.BLUE);
        b.set(12, 2, Color.RED);
        b.set(13, 1, Color.BLUE);
        b.set(14, 4, Color.RED);
        b.set(15, 1, Color.BLUE);
        b.set(16, 1, Color.BLUE);
        b.set(17, 2, Color.BLUE);
        b.set(18, 2, Color.RED);
        b.set(19, 4, Color.RED);
        b.set(20, 4, Color.BLUE);
        b.set(21, 4, Color.RED);
        b.set(22, 1, Color.BLUE);
        b.set(23, 2, Color.BLUE);
        b.set(24, 2, Color.BLUE);
        b.set(25, 3, Color.BLUE);
        b.set(26, 1, Color.BLUE);
        b.set(27, 4, Color.BLUE);
        b.set(28, 2, Color.BLUE);
        b.set(29, 2, Color.BLUE);
        b.set(30, 2, Color.BLUE);
        b.set(31, 2, Color.BLUE);
        b.set(32, 3, Color.BLUE);
        b.set(33, 1, Color.BLUE);
        b.set(34, 3, Color.BLUE);
        b.set(35, 1, Color.BLUE);

        b.addSpot(Color.RED, 3, 3);

        assertEquals("incorrect square color", Color.RED, b.color(4, 3));
        assertEquals("incorrect square color", Color.RED, b.color(5, 2));
        assertEquals("incorrect square color", Color.RED, b.color(5, 3));
        assertEquals("incorrect square color", Color.RED, b.color(5, 4));
        assertEquals("incorrect square color", Color.RED, b.color(5, 5));
        assertEquals("incorrect square color", Color.RED, b.color(6, 4));
        assertEquals("incorrect square color", Color.RED, b.color(6, 4));
        assertEquals("incorrect num spots", 2, b.spots(2, 3));
        assertEquals("incorrect num spots", 3, b.spots(3, 2));
        assertEquals("incorrect num spots", 2, b.spots(3, 3));
        assertEquals("incorrect num spots", 3, b.spots(3, 4));
        assertEquals("incorrect num spots", 3, b.spots(4, 1));
        assertEquals("incorrect num spots", 1, b.spots(4, 2));
        assertEquals("incorrect num spots", 3, b.spots(4, 3));
        assertEquals("incorrect num spots", 2, b.spots(4, 4));
        assertEquals("incorrect num spots", 2, b.spots(4, 5));
        assertEquals("incorrect num spots", 4, b.spots(5, 2));
    }


    @Test
    public void testDisplayString() {
        Board b = new MutableBoard(3);

        b.set(0, 2, Color.BLUE);
        b.set(2, 1, Color.RED);
        b.set(4, 4, Color.RED);
        b.set(7, 1, Color.RED);

        String expected =
            "===\n"
            + "    2b -- 1r\n"
            + "    -- 4r --\n"
            + "    -- 1r --\n"
            + "===";

        assertEquals("incorrect board display", expected,
                     b.toDisplayString());
    }


    @Test
    public void testSet() {
        Board b = new MutableBoard(5);
        b.set(2, 2, 1, Color.RED);
        b.setMoves(1);
        assertEquals("wrong number of spots", 1, b.spots(2, 2));
        assertEquals("wrong color", Color.RED, b.color(2, 2));
        assertEquals("wrong count", 1, b.numOfColor(Color.RED));
        assertEquals("wrong count", 0, b.numOfColor(Color.BLUE));
        assertEquals("wrong count", 24, b.numOfColor(Color.WHITE));
    }

    @Test
    public void testMove() {
        Board B = new MutableBoard(6);
        B.addSpot(Color.RED, 1, 1);
        checkBoard("#1", B, 1, 1, 1, Color.RED);
        B.addSpot(Color.BLUE, 2, 1);
        checkBoard("#2", B, 1, 1, 1, Color.RED, 2, 1, 1, Color.BLUE);
        B.addSpot(Color.RED, 1, 1);
        checkBoard("#3", B, 1, 1, 2, Color.RED, 2, 1, 1, Color.BLUE);
        B.addSpot(Color.BLUE, 2, 1);
        checkBoard("#4", B, 1, 1, 2, Color.RED, 2, 1, 2, Color.BLUE);
        B.addSpot(Color.RED, 1, 1);
        checkBoard("#5", B, 1, 1, 1, Color.RED, 2, 1, 3,
                   Color.RED, 1, 2, 1, Color.RED);
        B.undo();
        checkBoard("#4U", B, 1, 1, 2, Color.RED, 2, 1, 2, Color.BLUE);
        B.undo();
        checkBoard("#3U", B, 1, 1, 2, Color.RED, 2, 1, 1, Color.BLUE);
        B.undo();
        checkBoard("#2U", B, 1, 1, 1, Color.RED, 2, 1, 1, Color.BLUE);
        B.undo();
        checkBoard("#1U", B, 1, 1, 1, Color.RED);
    }

    private void checkBoard(String msg, Board B, Object... contents) {
        for (int k = 0; k < contents.length; k += 4) {
            String M = String.format("%s at %d %d", msg, contents[k],
                                     contents[k + 1]);
            assertEquals(M, (int) contents[k + 2],
                         B.spots((int) contents[k], (int) contents[k + 1]));
            assertEquals(M, contents[k + 3],
                         B.color((int) contents[k], (int) contents[k + 1]));
        }
        int c;
        c = 0;
        for (int i = B.size() * B.size() - 1; i >= 0; i -= 1) {
            assertTrue("bad white square #" + i,
                       (B.color(i) == Color.WHITE) == (B.spots(i) == 0));
            if (B.color(i) != Color.WHITE) {
                c += 1;
            }
        }
        assertEquals("extra squares filled", contents.length / 4, c);
    }

    private void checkBlankBoard(Board b) {
        for (int i = 0; i < b.size() * b.size(); i += 1) {
            assertEquals("Initial squares should be white",
                         Color.WHITE, b.color(i));
            assertEquals("Initial squares have no spots",
                         0, b.spots(i));
        }
    }
}
