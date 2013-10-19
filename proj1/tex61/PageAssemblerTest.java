package tex61;

import java.util.List;
import java.util.ArrayList;
import java.io.StringWriter;
import java.io.PrintWriter;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests of PageAssemblers.
 *  @author Kiet Lam
 */
public class PageAssemblerTest {

    private static final String NL = System.getProperty("line.separator");

    /** Collects output to a PrintWriter. */
    private StringWriter output;

    /** Collects output from a PageAssembler. */
    private PrintWriter writer;

    /** Lines of test data. */
    private List<String> testLines;

    /** Lines from a PageCollector. */
    private List<String> outList;

    /** Target PageAssembler. */
    private PageAssembler pages;


    private void setupWriter() {
        output = new StringWriter();
        writer = new PrintWriter(output);
    }

    private void setupCollector() {
        outList = new ArrayList<>();
    }

    private void makeTestLines(int n) {
        testLines = new ArrayList<>();
        for (int i = 0; i < n; i += 1) {
            testLines.add("Line " + i);
        }
    }

    private void writeTestLines() {
        for (String L : testLines) {
            pages.addLine(L);
        }
    }

    private String joinLines() {
        StringBuilder S = new StringBuilder();
        for (String L : testLines) {
            S.append(L);
            S.append(NL);
        }
        return S.toString();
    }

    @Test
    public void testPrinterContents1() {
        makeTestLines(20);
        setupWriter();
        pages = new PagePrinter(writer);
        writeTestLines();
        writer.close();
        assertEquals("wrong contents: printer", joinLines(), output.toString());
    }

    @Test
    public void testCollectorContents1() {
        makeTestLines(20);
        setupCollector();
        pages = new PageCollector(outList);
        writeTestLines();
        assertEquals("wrong contents: collector", testLines, outList);
    }

    @Test public void testPageIncrement() {
        setupWriter();
        pages = new PagePrinter(writer);
        pages.setTextHeight(3);

        pages.addLine("1");
        pages.addLine("2");
        pages.addLine("3");
        pages.addLine("4");
        pages.addLine("5");

        String expected = "1\n2\n3\n\f4\n5\n";

        assertEquals("Page increment failed", expected,
                     output.toString());
    }

    @Test public void testNullLines() {
        setupWriter();

        pages = new PagePrinter(writer);

        pages.addLine("3");
        pages.addLine(null);
        pages.addLine(null);
        pages.addLine("2");

        String expected = "3\n\n\n2\n";

        assertEquals("Null lines are not being skipped",
                     expected, output.toString());
    }
}
