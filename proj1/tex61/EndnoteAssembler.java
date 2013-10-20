package tex61;


/** End note assembler.
 *  @author Kiet Lam
 */
class EndnoteAssembler extends LineAssembler {

    /** Construct a line assembler out of
     *  PAGES, TEXTWIDTH, PARINDENT, INDENTATION, PARSKIP, TEXTHEIGHT,
     *  FILL and JUSTIFY.*/
    EndnoteAssembler(PageAssembler pages, int textWidth, int parIndent,
                  int indentation, int parSkip, int textHeight,
                  boolean fill, boolean justify) {
        super(pages, textWidth, parIndent, indentation, parSkip, textHeight,
              fill, justify);
    }

    /** Dont allow the text height to be changed to VAL.*/
    void setTextHeight(int val) {
    }
}
