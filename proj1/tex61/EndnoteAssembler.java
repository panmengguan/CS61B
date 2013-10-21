package tex61;


/** End note assembler.
 *  @author Kiet Lam
 */
class EndnoteAssembler extends LineAssembler {

    /** The current end note number.*/
    private int _currentEndnoteNum;

    /** Boolean for if we are the beginning word.*/
    private boolean _isBeginningWord;

    /** Construct a line assembler out of
     *  PAGES, TEXTWIDTH, PARINDENT, INDENTATION, PARSKIP, TEXTHEIGHT,
     *  FILL and JUSTIFY.*/
    EndnoteAssembler(PageAssembler pages, int textWidth, int parIndent,
                  int indentation, int parSkip, int textHeight,
                  boolean fill, boolean justify) {
        super(pages, textWidth, parIndent, indentation, parSkip, textHeight,
              fill, justify);

        _currentEndnoteNum = 1;
        _isBeginningWord = true;
    }

    /** Add WORD pre-pended by [<x>], if needed.*/
    void addWord(String word) {
        if (_isBeginningWord) {
            word = "[" + _currentEndnoteNum + "] " + word;
        }

        _isBeginningWord = false;
        super.addWord(word);
    }

    /** End the paragraph.*/
    void endParagraph() {
        _isBeginningWord = true;
        _currentEndnoteNum += 1;
        super.endParagraph();
    }

    /** Ignores HEIGHT.*/
    void setTextHeight(int height) {
        super.setTextHeight(PageAssembler.INFINITE_HEIGHT);
    }
}
