package tex61;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.ArrayDeque;

import static tex61.FormatException.error;

/** An object that receives a sequence of words of text and formats
 *  the words into filled and justified text lines that are sent to a receiver.
 *  @author Kiet Lam
 */
class LineAssembler {

    /** Boolean to fill or not.*/
    private boolean _fill;

    /** Boolean to justify or not.*/
    private boolean _justify;

    /** Destination given in constructor for formatted lines. */
    private final PageAssembler _pages;

    /** Width of text.*/
    protected int _textWidth;

    /** Paragraph indentation.*/
    protected int _parIndent;

    /** Indentation.*/
    protected int _indentation;

    /** Paragraph skip.*/
    protected int _parSkip;

    /** Current word.*/
    private String _currentWord;

    /** Lines .*/
    protected Queue<Line> _lines;

    /** Current line.*/
    private Line _currentLine;

    /** Regular space constant between each word, suggested by the guideline.*/
    private static final int REGULAR_SPACE_CONSTANT = 3;

    /** Boolean of whether we are still on the first paragraph.*/
    private boolean isFirstParagraph = true;

    /** A new, empty line assembler with default settings of all
     *  parameters, sending finished lines to PAGES.
     *
     *  Parameters: PAGES, TEXTWIDTH, PARINDENT, INDENTATION, PARSKIP,
     *              TEXTHEIGHT, FILL, JUSTIFY
     */
    LineAssembler(PageAssembler pages, int textWidth, int parIndent,
                  int indentation, int parSkip, int textHeight,
                  boolean fill, boolean justify) {
        _pages       = pages;
        _textWidth   = textWidth;
        _parIndent   = parIndent;
        _indentation = indentation;
        _parSkip     = parSkip;
        _fill        = fill;
        _justify     = justify;

        _lines = new ArrayDeque<Line>();
        _pages.setTextHeight(Defaults.TEXT_HEIGHT);

        _currentLine = new Line();
        _currentWord = "";
    }

    /** Returns a regular line assembler using PRINTER.*/
    public static LineAssembler createLineAssembler(PageAssembler printer) {
        return new LineAssembler(printer, Defaults.TEXT_WIDTH,
                                 Defaults.PARAGRAPH_INDENTATION,
                                 Defaults.INDENTATION,
                                 Defaults.PARAGRAPH_SKIP,
                                 Defaults.TEXT_HEIGHT, true,
                                 true);
    }

    /** Returns an endnote assembler using PRINTER.*/
    public static LineAssembler createEndnoteAssembler(PageAssembler printer) {
        return new EndnoteAssembler(printer,
                                    Defaults.ENDNOTE_TEXT_WIDTH,
                                    Defaults.ENDNOTE_PARAGRAPH_INDENTATION,
                                    Defaults.ENDNOTE_INDENTATION,
                                    Defaults.ENDNOTE_PARAGRAPH_SKIP,
                                    PageAssembler.INFINITE_HEIGHT,
                                    true, true);
    }

    /** Add TEXT to the word currently being built. */
    void addText(String text) {
        _currentWord += text;
    }

    /** Finish the current word, if any, and add to words being accumulated. */
    void finishWord() {
        if (!_currentWord.equals("")) {
            addWord(_currentWord);
        }

        _currentWord = "";
    }

    /** Add WORD to the formatted text. */
    void addWord(String word) {
        if (!_fill) {
            _currentLine.addWord(word);
            return;
        }

        int len = 0;

        if (_lines.size() == 0) {
            len += _parIndent;
        }

        len += _indentation;
        len += _currentLine.getWordsLength();
        len += _currentLine.getNumWords();
        len += word.length();

        if (len > _textWidth && _currentLine.isEmpty()) {
            _currentLine.addWord(word);
            _lines.add(_currentLine);
            _currentLine = new Line();
        } else if (len > _textWidth) {
            _lines.add(_currentLine);
            _currentLine = new Line();
            _currentLine.addWord(word);
        } else if (len < _textWidth) {
            _currentLine.addWord(word);
        } else {
            _currentLine.addWord(word);
            _lines.add(_currentLine);
            _currentLine = new Line();
        }
    }

    /** Add LINE to our output, with no preceding paragraph skip.  There must
     *  not be an unfinished line pending. */
    void addLine(String line) {
        if (!_currentLine.isEmpty()) {
            throw error("Cannot add new line while current is not finished");
        }

        _lines.add(new Line(line));
    }

    /** Set the current indentation to VAL. VAL >= 0. */
    void setIndentation(int val) {
        if (val < 0) {
            throw error("Indentation needs to be >= 0");
        }

        _indentation = val;
    }

    /** Set the current paragraph indentation to VAL. VAL >= 0. */
    void setParIndentation(int val) {
        if (val + _indentation < 0) {
            throw error("Paragraph indentation + indentation must be >= 0");
        }

        _parIndent = val;
    }

    /** Set the text width to VAL, where VAL >= 0. */
    void setTextWidth(int val) {
        if (val < 0) {
            throw error("Text width needs to be >= 0");
        }

        _textWidth = val;
    }

    /** Iff ON, set fill mode. */
    void setFill(boolean on) {
        _fill = on;
    }

    /** Iff ON, set justify mode (which is active only when filling is
     *  also on). */
    void setJustify(boolean on) {
        _justify = on && _fill;
    }

    /** Set paragraph skip to VAL.  VAL >= 0. */
    void setParSkip(int val) {
        if (val < 0) {
            throw error("Paragraph skip must be >= 0");
        }

        _parSkip = val;
    }

    /** Set page height to VAL > 0. */
    void setTextHeight(int val) {
        if (val <= 0) {
            throw error("Text height must be > 0");
        }

        _pages.setTextHeight(val);
    }

    /** Process the end of the current input line.  No effect if
     *  current line accumulator is empty or in fill mode.  Otherwise,
     *  adds a new complete line to the finished line queue and clears
     *  the line accumulator. */
    void newLine() {
        if (_fill || _currentLine.isEmpty()) {
            return;
        }

        _lines.add(_currentLine);
        _currentLine = new Line();
    }

    /** If there is a current unfinished paragraph pending, close it
     *  out and start a new one. */
    void endParagraph() {
        if (!_currentLine.isEmpty()) {
            _lines.add(_currentLine);
            _currentLine = new Line();
        }

        if (_lines.isEmpty()) {
            return;
        }

        if (!isFirstParagraph) {
            for (int i = 0; i < _parSkip; i += 1) {
                emitLine("");
            }
        } else {
            isFirstParagraph = false;
        }

        int totalIndentation = _parIndent + _indentation;

        if (!_fill) {
            Line line = _lines.remove();

            outputConstantLine(line, totalIndentation, 1);

            for (Line l: _lines) {
                outputConstantLine(l, _indentation, 1);
            }
            return;
        }

        emitFormattedLines();
    }

    /** Emit formatted (if needed) lines.*/
    private void emitFormattedLines() {
        int totalIndentation = _parIndent + _indentation;

        Line l = _lines.remove();

        if (!l.isEmpty()) {
            if (_justify) {
                outputJustifiedLine(l, totalIndentation, _textWidth
                                    - l.getWordsLength() - totalIndentation);
            } else {
                outputConstantLine(l, totalIndentation, 1);
            }
        }

        if (_lines.isEmpty()) {
            return;
        }

        Line currentLine = _lines.remove();

        while (!_lines.isEmpty()) {
            int spaces = _textWidth - currentLine.getWordsLength()
                - _indentation;

            if (_justify) {
                outputJustifiedLine(currentLine, _indentation,
                                    spaces);
            } else {
                outputConstantLine(currentLine, _indentation, 1);
            }

            currentLine = _lines.remove();
        }

        if (currentLine != null) {
            outputConstantLine(currentLine, _indentation, 1);
        }
    }

    /** Output LINE, adding INDENT characters of indentation, and a total of
     *  SPACES spaces between words, evenly distributed.*/
    private void outputJustifiedLine(Line line, int indent, int spaces) {

        int numWords = line.getNumWords();

        if (spaces >= REGULAR_SPACE_CONSTANT * (numWords - 1)) {
            outputConstantLine(line, indent, REGULAR_SPACE_CONSTANT);
            return;
        }

        String str = indentStr("", indent);

        int spacesSoFar = 0;

        for (int i = 0; i < line.getNumWords(); i += 1) {
            str += line.getWord(i);
            int spacesNeeded = (int) Math.floor(0.5 + ((float) (spaces
                                                                * (i + 1))
                                                       / (numWords - 1)))
                - spacesSoFar;

            if (i != line.getNumWords() - 1) {
                for (int j = 0; j < spacesNeeded; j += 1) {
                    str += " ";
                }
            }

            spacesSoFar += spacesNeeded;
        }

        emitLine(str);
    }

    /** Output LINE, adding INDENT characters of indentation, and exactly
     *  SPACES spaces between each word.*/
    private void outputConstantLine(Line line, int indent, int spaces) {
        String str = indentStr("", indent);

        for (int i = 0; i < line.getNumWords(); i += 1) {
            str += line.getWord(i);

            if (i != line.getNumWords() - 1) {
                for (int j = 0; j < spaces; j += 1) {
                    str += " ";
                }
            }
        }

        emitLine(str);
    }

    /** Returns indented STR by INDENT of indentation characters.*/
    private String indentStr(String str, int indent) {
        for (int i = 0; i < indent; i += 1) {
            str = " " + str;
        }

        return str;
    }

    /** Emit STR to _pages.*/
    private void emitLine(String str) {
        _pages.addLine(str);
    }

    /** Class representing a line.*/
    private static class Line {

        /** Stores the current words on a line.*/
        private List<String> _words = new ArrayList<String>();

        /** The string of the line.*/
        private String _line = "";

        /** Construct an empty line.*/
        Line() {
        }

        /** Construct a line using a string LINE.*/
        Line(String line) {
            _line = line;
        }

        /** Add WORD to a line.*/
        void addWord(String word) {
            _words.add(word);
        }

        /** Returns the length of all the words.*/
        int getWordsLength() {
            int len = 0;

            for (String w: _words) {
                len += w.length();
            }

            return len;
        }

        /** Returns the string of this line.*/
        String getLine() {
            return _line;
        }

        /** Returns the number of words on this line.*/
        int getNumWords() {
            return _words.size();
        }

        /** Returns true iff this line is empty.*/
        boolean isEmpty() {
            return _words.size() == 0;
        }

        /** Returns the list of words on this line.*/
        List<String> getWords() {
            return _words;
        }

        /** Returns the word at index I on this line.*/
        String getWord(int i) {
            return _words.get(i);
        }
    }
}
