package tex61;

import java.io.PrintWriter;

import java.util.List;
import java.util.ArrayList;

import static tex61.FormatException.error;


/** Receives (partial) words and commands, performs commands, and
 *  accumulates and formats words into lines of text, which are sent to a
 *  designated PageAssembler.  At any given time, a Controller has a
 *  current word, which may be added to by addText, a current list of
 *  words that are being accumulated into a line of text, and a list of
 *  lines of endnotes.
 *  @author Kiet Lam
 */
class Controller {

    /** PrintWriter out.*/
    private PrintWriter _out;

    /** True iff we are currently processing an endnote. */
    private boolean _endnoteMode;

    /** Number of next endnote. */
    private int _refNum;

    /** A page printer.*/
    private PagePrinter _printer;

    /** A line assembler.*/
    private LineAssembler _lineAssembler;

    /** An end note assembler.*/
    private LineAssembler _endnoteAssembler;

    /** Current assembler to use.*/
    private LineAssembler _assembler;

    /** List of end notes strings.*/
    private List<String> _endNotes = new ArrayList<String>();

    /** Current end note number.*/
    private int _currentEndnoteNum = 0;

    /** A new Controller that sends formatted output to OUT. */
    Controller(PrintWriter out) {
        _out = out;
        _printer = new PagePrinter(out);

        _lineAssembler    = LineAssembler.createLineAssembler(_printer);
        _endnoteAssembler = LineAssembler.createEndnoteAssembler(_printer);

        _assembler = _lineAssembler;
    }

    /** Add TEXT to the end of the word of formatted text currently
     *  being accumulated. */
    void addText(String text) {
        _assembler.addText(text);
    }

    /** Finish any current word of text and, if present, add to the
     *  list of words for the next line.  Has no effect if no unfinished
     *  word is being accumulated. */
    void endWord() {
        _assembler.finishWord();
    }

    /** Finish any current word of formatted text and process an end-of-line
     *  according to the current formatting parameters. */
    void addNewline() {
        _assembler.newLine();
    }

    /** Finish any current word of formatted text, format and output any
     *  current line of text, and start a new paragraph. */
    void endParagraph() {
        _assembler.endParagraph();
    }

    /** If valid, process TEXT into an endnote, first appending a reference
     *  to it to the line currently being accumulated. */
    void formatEndnote(String text) {
        if (_assembler == _endnoteAssembler) {
            throw error("Cannot have endnote within endnotes");
        }

        _currentEndnoteNum += 1;

        _endNotes.add(text);
        addText("[" + _currentEndnoteNum + "]");
    }

    /** Set the current text height (number of lines per page) to VAL, if
     *  it is a valid setting.  Ignored when accumulating an endnote. */
    void setTextHeight(int val) {
        _assembler.setTextHeight(val);
    }

    /** Set the current text width (width of lines including indentation)
     *  to VAL, if it is a valid setting. */
    void setTextWidth(int val) {
        _assembler.setTextWidth(val);
    }

    /** Set the current text indentation (number of spaces inserted before
     *  each line of formatted text) to VAL, if it is a valid setting. */
    void setIndentation(int val) {
        _assembler.setIndentation(val);
    }

    /** Set the current paragraph indentation (number of spaces inserted before
     *  first line of a paragraph in addition to indentation) to VAL, if it is
     *  a valid setting. */
    void setParIndentation(int val) {
        _assembler.setParIndentation(val);
    }

    /** Set the current paragraph skip (number of blank lines inserted before
     *  a new paragraph, if it is not the first on a page) to VAL, if it is
     *  a valid setting. */
    void setParSkip(int val) {
        _assembler.setParSkip(val);
    }

    /** Iff ON, begin filling lines of formatted text. */
    void setFill(boolean on) {
        _assembler.setFill(on);
    }

    /** Iff ON, begin justifying lines of formatted text whenever filling is
     *  also on. */
    void setJustify(boolean on) {
        _assembler.setJustify(on);
    }

    /** Finish the current formatted document or endnote (depending on mode).
     *  Formats and outputs all pending text. */
    void close() {
        endWord();
        endParagraph();

        if (_assembler != _endnoteAssembler) {
            setEndnoteMode();
            writeEndnotes();
        }
    }

    /** Start directing all formatted text to the endnote assembler. */
    private void setEndnoteMode() {
        _assembler = _endnoteAssembler;
    }

    /** Return to directing all formatted text to _mainText. */
    private void setNormalMode() {
        _assembler = _lineAssembler;
    }

    /** Write all accumulated endnotes to _mainText. */
    private void writeEndnotes() {
        for (String endNote: _endNotes) {
            new InputParser(endNote, this).process();
        }
    }
}
