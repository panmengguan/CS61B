package tex61;

import java.util.List;
import java.util.ArrayList;
import java.io.StringWriter;
import java.io.PrintWriter;

import org.junit.Test;
import static org.junit.Assert.*;


/** Unit tests of LineAssembler.
 *  @author Kiet Lam
 */
public class LineAssemblerTest {

    /** Collects output to a PrintWriter. */
    private StringWriter output;

    /** Collects output from a PageAssembler. */
    private PrintWriter writer;

    /** Target PageAssembler. */
    private PageAssembler pages;

    /** Line Assembler.*/
    private LineAssembler lineAssembler;

    /** Paragraph indent.*/
    private static final String PAR_INDENT = "   ";

    /** Common test text width.*/
    private static final int COMMON_TEST_WIDTH = 13;

    /** Extra test text width.*/
    private static final int EXTRA_TEST_WIDTH = 30;

    private void setupWriter() {
        output = new StringWriter();
        writer = new PrintWriter(output);
    }

    private void commonSetup() {
        setupWriter();
        pages = new PagePrinter(writer);
        lineAssembler = new LineAssembler(pages);
        lineAssembler.setParIndentation(Defaults.PARAGRAPH_INDENTATION);
        lineAssembler.setFill(true);
        lineAssembler.setTextWidth(COMMON_TEST_WIDTH);
    }

    private void assemblerAddWords(String... args) {
        assemblerAddWords(true, args);
    }

    private void assemblerAddWords(boolean end, String... args) {
        for (String s : args) {
            lineAssembler.addWord(s);
        }

        lineAssembler.endParagraph();
    }

    @Test
    public void testEmpty() {
        commonSetup();

        lineAssembler.endParagraph();

        String expected = "";

        assertEquals("Empty test failed", expected,
                     output.toString());
    }

    @Test
    public void testFill() {
        commonSetup();

        lineAssembler.setJustify(false);
        lineAssembler.addWord("High");
        lineAssembler.addWord("Dare");
        lineAssembler.addWord("AJT");

        lineAssembler.endParagraph();

        String expected = PAR_INDENT + "High Dare\nAJT\n";

        assertEquals("God damn fill error", expected,
                     output.toString());
    }

    @Test
    public void testFillAndEndParagraph() {
        commonSetup();

        lineAssembler.setJustify(false);
        lineAssembler.addWord("High");
        lineAssembler.addWord("Dare");
        lineAssembler.addWord("AJT");

        lineAssembler.endParagraph();
        lineAssembler.endParagraph();

        String expected = PAR_INDENT + "High Dare\nAJT\n";

        assertEquals("God damn fill error", expected,
                     output.toString());
    }

    @Test
    public void testIndentation() {
        commonSetup();

        lineAssembler.setJustify(false);
        lineAssembler.setIndentation(2);

        lineAssembler.addWord("High");
        lineAssembler.addWord("NP");
        lineAssembler.addWord("AJT");

        lineAssembler.endParagraph();

        String expected = PAR_INDENT + "  " + "High NP\n"
            + "  " + "AJT\n";

        assertEquals("God damn indentation test failed",
                     expected, output.toString());
    }

    @Test
    public void testFillOverflow() {
        commonSetup();

        String excessiveWord = "OMGHOWAREYOUREADINGTHIS";

        lineAssembler.addWord(excessiveWord);
        lineAssembler.endParagraph();

        String expected = PAR_INDENT + excessiveWord + "\n";

        assertEquals("Excessive test failed", expected,
                     output.toString());
    }

    @Test
    public void testNoFill1() {
        commonSetup();

        lineAssembler.setFill(false);
        assemblerAddWords("BLADITY", "BLAH", "BLAH", "FD", "BLAH",
                          "BLAH");

        String expected = PAR_INDENT + "BLADITY BLAH BLAH FD BLAH BLAH\n";

        assertEquals("Should be no fill", expected,
                     output.toString());
    }

    @Test
    public void testNoFill2() {
        commonSetup();

        lineAssembler.setFill(false);

        assemblerAddWords("WHA", "IS", "MARK", "????");

        String expected = PAR_INDENT + "WHA IS MARK ????\n";

        assertEquals("Should be no fill", expected,
                     output.toString());
    }

    @Test
    public void testNoJustify() {
        commonSetup();

        lineAssembler.setFill(false);

        assemblerAddWords("OMG", "DDD");

        String expected = PAR_INDENT + "OMG DDD\n";

        assertEquals("Should be no justification",
                     expected, output.toString());
    }

    @Test
    public void testRawJustification1() {
        commonSetup();

        lineAssembler.setJustify(true);
        lineAssembler.setTextWidth(Defaults.TEXT_WIDTH);
        lineAssembler.setParIndentation(Defaults.PARAGRAPH_INDENTATION);

        assemblerAddWords("The", "following", "quotation", "about",
                          "writing", "test", "programs",
                          "for", "a", "document");

        String expected = PAR_INDENT
            + "The following  quotation about"
            + " writing  test programs for  a document\n";

        assertEquals("God damn it, why won't justice work",
                     expected, output.toString());
    }

    @Test
    public void testJustification2() {
        commonSetup();

        lineAssembler.setJustify(true);

        lineAssembler.setTextWidth(18);
        assemblerAddWords("SAM", "I", "AM", "NOT");

        String expected = PAR_INDENT + "SAM  I  AM  NOT\n";

        assertEquals("Justification failed", expected, output.toString());
    }

    @Test
    public void testUltimateJustification() {
        commonSetup();

        lineAssembler.setJustify(true);
        lineAssembler.setTextWidth(Defaults.TEXT_WIDTH);
        lineAssembler.setParIndentation(4);

        assemblerAddWords("The", "standard", "C", "header", "file", "assert.h",
                          "provides", "a", "macro", "\"assert\".",
                          "When", "this", "file", "is", "included", "in", "a",
                          "source", "file,", "and", "the", "file", "is",
                          "compiled", "without", "the", "option", "-DNDEBUG,",
                          "each", "call", "assert(C),",
                          "where", "C", "is", "any", "boolean", "expression,",
                          "will", "terminate", "the", "program", "with", "an",
                          "error", "message", "if", "C", "turns", "out", "to",
                          "be", "false", "at", "that", "point.");

        String expected = "    The standard C header file  assert.h provides a"
            + " macro \"assert\". When\n"
            + "this file is included in a source file,"
            + " and the file is compiled without\nthe  option  -DNDEBUG, "
            + "each  call  assert(C),  where  C is  any  boolean\n"
            + "expression, will terminate the program with  an error message "
            + "if C turns\n"
            + "out to be false at that point.\n";

        assertEquals("Ultimate justification failed", expected,
                     output.toString());
    }

    @Test
    public void testJustificationTriplets() {
        commonSetup();

        lineAssembler.setJustify(true);
        lineAssembler.setTextWidth(EXTRA_TEST_WIDTH);

        assemblerAddWords("I", "CHOOSE", "YOU!");

        String expected = PAR_INDENT + "I   CHOOSE   YOU!\n";

        assertEquals("Justification failed for B>=(N-1)",
                     expected, output.toString());
    }

    @Test(expected = FormatException.class)
    public void testNegativeTextWidth() {
        commonSetup();

        lineAssembler.setTextWidth(-1);
    }

    @Test(expected = FormatException.class)
    public void testNegativeParSkip() {
        commonSetup();

        lineAssembler.setParSkip(-1);
    }

    @Test(expected = FormatException.class)
    public void testZeroTextHeight() {
        commonSetup();

        lineAssembler.setTextHeight(0);
    }

    @Test(expected = FormatException.class)
    public void testNegativeTextHeight() {
        commonSetup();

        lineAssembler.setTextHeight(-1);
    }

    @Test
    public void testNewLine() {
        commonSetup();

        lineAssembler.setFill(false);

        lineAssembler.addWord("HIGH");
        lineAssembler.newLine();
        lineAssembler.addWord("DARE");

        lineAssembler.endParagraph();

        String expected = PAR_INDENT + "HIGH\nDARE\n";

        assertEquals("Newline() failed", expected, output.toString());
    }

    @Test
    public void test2NewLines() {
        commonSetup();

        lineAssembler.setFill(false);

        lineAssembler.addWord("HIGH");
        lineAssembler.newLine();
        lineAssembler.newLine();
        lineAssembler.addWord("DARE");

        lineAssembler.endParagraph();

        String expected = PAR_INDENT + "HIGH\nDARE\n";

        assertEquals("Newline() failed", expected, output.toString());
    }

    @Test
    public void testNoNewLine() {
        commonSetup();

        lineAssembler.addWord("HIGH");
        lineAssembler.newLine();
        lineAssembler.addWord("DARE");

        lineAssembler.endParagraph();

        String expected = PAR_INDENT + "HIGH DARE\n";

        assertEquals("No newline failed", expected,
                     output.toString());
    }
}
