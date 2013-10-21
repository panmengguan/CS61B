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
    @Override
    void addWord(String word) {
        if (_isBeginningWord) {
            word = "[" + _currentEndnoteNum + "] " + word;
        }

        _isBeginningWord = false;
        super.addWord(word);
    }

    /** End the paragraph.*/
    @Override
    void endParagraph() {
        _isBeginningWord = true;
        _currentEndnoteNum += 1;
        super.endParagraph();
    }

    /** Ignores HEIGHT.*/
    @Override
    void setTextHeight(int height) {
        _pages.setEndnoteMode(true);
    }

    @Override
    protected void setPageTextHeight() {
        _pages.setEndnoteMode(true);
    }
}
