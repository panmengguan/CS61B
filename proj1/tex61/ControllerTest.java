package tex61;

import java.io.StringWriter;
import java.io.PrintWriter;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;


/** Unit tests for Controller.
 *  @author Kiet Lam*/
public class ControllerTest {

    /** Collects output to a PrintWriter. */
    private StringWriter output;

    /** Collects output from a PageAssembler. */
    private PrintWriter writer;

    /** Controller.*/
    private Controller controller;

    /** Setup the output and writer.*/
    private void setupWriter() {
        output = new StringWriter();
        writer = new PrintWriter(output);
    }

    /** Common setup for tests.*/
    private void commonSetup() {
        setupWriter();

        controller = new Controller(writer);
    }

    /** Assemble the words from ARGS and end the paragraph iff END.*/
    private void assemblerAddWords(boolean end, String... args) {
        for (String s : args) {
            controller.addText(s);
            controller.endWord();
        }

        if (end) {
            controller.endParagraph();
        }
    }

    @Before
    public void initialize() {
        commonSetup();
    }

    @Test
    public void testEndNote1() {
        assemblerAddWords(false, "The", "following", "quotation", "about",
                          "writing", "test", "programs", "for", "a", "document",
                          "compiler", "from", "an", "article", "by", "Donald",
                          "Knuth", "conveys", "some", "of", "the", "right",
                          "frame", "of");

        controller.addText("mind");
        controller.formatEndnote("(D. E. Knuth, ``The Errors of TEX'',\n"
                                 + "Software Practice & Experience, 19 (7) "
                                 + "(July, 1989), pp. 625-626).");
        controller.addText(":");
        controller.endWord();
        controller.endParagraph();

        controller.close();

        String expected = "   The following  quotation about writing  test "
            + "programs for  a document\n"
            + "compiler from an article by Donald Knuth conveys some of the "
            + "right frame\n"
            + "of mind[1]:\n"
            + "[1] (D. E. Knuth, ``The Errors of TEX'', Software Practice & "
            + "Experience,\n"
            + "    19 (7) (July, 1989), pp. 625-626).\n";

        assertEquals("End note formatting failed",
                     expected, output.toString());
    }

    @Test
    public void testEndNote2() {
        controller.setParIndentation(0);
        controller.setIndentation(0);

        assemblerAddWords(false, "You", "should", "also", "read", "Column", "3",
                          "of", "_More", "Programming");

        controller.addText("Pearls_");
        controller.formatEndnote("J. Bentley, More Programming Pearls: "
                                 + "Confessions of a Coder,\n"
                                 + "AT&T Bell Telephone Laboratories, 1988.");
        controller.endWord();
        assemblerAddWords(true, "for", "more", "advice.", "Among", "other",
                          "things,", "this", "column", "discusses",
                          "scaffolding.");

        controller.close();

        String expected = "You should also  read Column 3 of _More "
            + "Programming  Pearls_[1] for more\n"
            + "advice. Among other things, this column discusses "
            + "scaffolding.\n"
            + "[1] J. Bentley,  More Programming Pearls:  Confessions of a  "
            + "Coder, AT&T\n"
            + "    Bell Telephone Laboratories, 1988.\n";

        assertEquals("End note formatting failed",
                     expected, output.toString());
    }
}
