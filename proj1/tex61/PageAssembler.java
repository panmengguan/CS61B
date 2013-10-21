package tex61;


/** A PageAssembler accepts complete lines of text (minus any
 *  terminating newlines) and turns them into pages, adding form
 *  feeds as needed.  It prepends a form feed (Control-L  or ASCII 12)
 *  to the first line of each page after the first.  By overriding the
 *  'write' method, subtypes can determine what is done with
 *  the finished lines.
 *  @author Kiet Lam
 */
abstract class PageAssembler {

    /** Current page.*/
    private int _currentPage = 0;

    /** Infinite text height.*/
    public static final int INFINITE_HEIGHT = Integer.MAX_VALUE;

    /** Text height.*/
    private int _textHeight = INFINITE_HEIGHT;

    /** Current text height.*/
    private int _currentTextHeight = 0;

    /** Boolean for is end node mode.*/
    private boolean _isEndnote = false;

    /** Boolean for whether we have page broken once for end notes.*/
    private boolean _hasPageBreakEndnote = false;

    /** Create a new PageAssembler that sends its output to OUT.
     *  Initially, its text height is unlimited. It prepends a form
     *  feed character to the first line of each page except the first. */
    PageAssembler() {
    }

    /** Add LINE to the current page, starting a new page with it if
     *  the previous page is full. A null LINE indicates a skipped line,
     *  and has no effect at the top of a page. */
    void addLine(String line) {
        if ((line == null || line.equals(""))
            && _currentTextHeight == 0) {
            return;
        }

        if ((line == null || line.equals(""))
            && _currentTextHeight == _textHeight) {
            return;
        }

        if (_isEndnote) {
            if (_currentTextHeight == _textHeight
                && !_hasPageBreakEndnote) {
                line = "\f" + line;
                _currentTextHeight = 1;
                _hasPageBreakEndnote = true;
            } else if (!_hasPageBreakEndnote) {
                _currentTextHeight += 1;
            }
        } else {
            if (_currentTextHeight >= _textHeight && line != null) {
                line = "\f" + line;
                _currentTextHeight = 1;

            } else if (_currentTextHeight >= _textHeight) {
                line = "\f";
                _currentTextHeight = 0;
            } else {
                _currentTextHeight += 1;
            }
        }

        if (line == null) {
            write("");
        } else {
            write(line);
        }
    }

    /** Set text height to VAL. */
    void setTextHeight(int val) {
        _textHeight = val;
    }

    /** Set end note mode based on ISENDNOTE.*/
    void setEndnoteMode(boolean isEndnote) {
        _isEndnote = isEndnote;
    }

    /** Perform final disposition of LINE, as determined by the
     *  concrete subtype. */
    abstract void write(String line);
}
