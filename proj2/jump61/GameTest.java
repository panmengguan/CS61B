package jump61;

import org.junit.Test;
import static org.junit.Assert.*;


import java.io.StringReader;
import java.io.StringWriter;


/** Unit test of Game.
 *  @author Kiet Lam
 */
public class GameTest {

    private StringReader iReader = new StringReader("");
    private StringWriter oWriter = new StringWriter();
    private StringWriter eWriter = new StringWriter();


    @Test
    public void testSizeCommand() {
        String input = "size 3\n"
            + "quit\n";

        iReader = new StringReader(input);

        Game game = new Game(iReader, oWriter, oWriter, eWriter);
        game.play();

        Board b = game.getBoard();

        assertEquals("incorrect board size",
                     3, b.size());
    }

    @Test
    public void testClear() {
        String input = "size 3\n"
            + "manual Red\n"
            + "manual Blue\n"
            + "start\n"
            + "2 2\n"
            + "2 3\n"
            + "clear\n"
            + "quit\n";

        iReader = new StringReader(input);

        Game game = new Game(iReader, oWriter, oWriter, eWriter);
        game.play();

        Board b = game.getBoard();

        for (int i = 0; i < b.size() * b.size(); i += 1) {
            assertEquals("incorrect square color",
                         Color.WHITE, b.color(i));
            assertEquals("incorrect square spots",
                         0, b.spots(i));
        }
    }

    @Test
    public void testMoves() {
        String input = "size 3\n"
            + "manual Red\n"
            + "manual Blue\n"
            + "start\n"
            + "2 2\n"
            + "2 3\n"
            + "quit\n";

        iReader = new StringReader(input);

        Game game = new Game(iReader, oWriter, oWriter, eWriter);
        game.play();

        Board b = game.getBoard();

        assertEquals("incorrect square color",
                     Color.RED, b.color(2, 2));
        assertEquals("incorrect square spots",
                     1, b.spots(2, 2));

        assertEquals("incorrect square color",
                     Color.BLUE, b.color(2, 3));
        assertEquals("incorrect square spots",
                     1, b.spots(2, 3));
    }
}
