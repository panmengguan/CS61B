package tex61;

import java.io.PrintWriter;

/** A PageAssembler that sends lines immediately to a PrintWriter, with
 *  terminating newlines.
 *  @author Kiet Lam
 */
class PagePrinter extends PageAssembler {

    /** Out .*/
    private PrintWriter _out;

    /** A new PagePrinter that sends lines to OUT. */
    PagePrinter(PrintWriter out) {
        _out = out;
    }

    /** Print LINE to my output. */
    @Override
    void write(String line) {
        _out.println(line);
    }
}
