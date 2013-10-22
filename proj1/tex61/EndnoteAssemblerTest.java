package tex61;

import java.io.StringWriter;
import java.io.PrintWriter;

import org.junit.Test;
import static org.junit.Assert.*;


/** Unit tests of EndnoteAssembler.
 *  @author Kiet Lam
 */
public class EndnoteAssemblerTest {

    /** Collects output to a PrintWriter. */
    private StringWriter output;

    /** Collects output from a PageAssembler. */
    private PrintWriter writer;

    /** Target PageAssembler. */
    private PageAssembler pages;

    /** .*/
    private LineAssembler assembler;

    private void setupWriter() {
        output = new StringWriter();
        writer = new PrintWriter(output);
    }

    private void commonSetup() {
        setupWriter();
        pages = new PagePrinter(writer);
        assembler = LineAssembler.createEndnoteAssembler(pages);
    }

    @Test
    public void testEndnote() {
        commonSetup();

        assembler.addWord("ads");
        assembler.endParagraph();

        String expected = "[1] ads\n";

        assertEquals("End note did not pre-pend reference",
                     expected, output.toString());
    }

    @Test
    public void testIgnoreHeight() {
        commonSetup();

        assembler.setTextHeight(1);
        assembler.setTextWidth(4);

        assembler.addWord("HIGH");
        assembler.addWord("DARE");
        assembler.addWord("NEGIN");

        assembler.endParagraph();

        String expected = "[1] HIGH\n    DARE\n    NEGIN\n";

        assertEquals("Text height not ignored",
                     expected, output.toString());
    }
}
