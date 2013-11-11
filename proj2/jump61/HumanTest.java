package jump61;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.StringWriter;
import java.io.StringReader;


/** Unit test of HumanPlayer.
 *  @author Kiet Lam
 */
public class HumanTest {

    private StringReader iReader = new StringReader("");
    private StringWriter oWriter = new StringWriter();
    private StringWriter eWriter = new StringWriter();

    @Test
    public void testNoMove() {
        Board b = new MutableBoard(5);
        GameStub game = new GameStub(b, iReader, oWriter, eWriter, Color.RED);
        Player player = new HumanPlayer(game, Color.RED);

        player.makeMove();

        for (int i = 0; i < b.size() * b.size(); i += 1) {
            assertEquals("incorrect square color",
                         Color.WHITE, b.color(i));
            assertEquals("incorrect number of spots",
                         0, b.spots(i));
        }
    }

    @Test
    public void testMakeMove() {
        Board b = new MutableBoard(5);
        GameStub game = new GameStub(b, iReader, oWriter, eWriter, Color.RED);
        Player player = new HumanPlayer(game, Color.RED);

        game.setNextMove(new int[] {3, 3});
        player.makeMove();

        assertEquals("incorrect square color",
                     Color.RED, b.color(3, 3));
        assertEquals("incorrect number of spots",
                     1, b.spots(3, 3));
    }

    @Test
    public void testMultipleMoves() {
        Board b = new MutableBoard(5);
        GameStub game = new GameStub(b, iReader, oWriter, eWriter, Color.RED);
        Player player = new HumanPlayer(game, Color.RED);

        game.setNextMove(new int[] {3, 3});
        player.makeMove();

        game.setNextMove(new int[] {3, 3});
        player.makeMove();

        game.setNextMove(new int[] {5, 4});
        player.makeMove();

        assertEquals("incorrect square color",
                     Color.RED, b.color(3, 3));
        assertEquals("incorrect number of spots",
                     2, b.spots(3, 3));

        assertEquals("incorrect square color",
                     Color.RED, b.color(5, 4));
        assertEquals("incorrect number of spots",
                     1, b.spots(5, 4));
    }
}
