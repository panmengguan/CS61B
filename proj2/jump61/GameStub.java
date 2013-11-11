package jump61;

import java.io.Reader;
import java.io.Writer;


/** Stub for Game to ease testing.
 *  @author Kiet Lam*/
class GameStub extends Game {

    /** Next move.*/
    private int[] _nextMove;

    /** Current board.*/
    private Board _board;

    /** Create a Game stub with beginning BOARD.*/
    GameStub(Board board, Reader is, Writer os,
             Writer err) {
        super(is, os, os, err);
        _board = board;
    }

    /** Set the output of getMove() to be NEXTMOVE.
     *  Assumes NEXTMOVE is int[2]*/
    void setNextMove(int[] nextMove) {
        _nextMove = nextMove;
    }

    /** Returns true iff _nextMove is not null.
     *  Sets MOVE to be _nextMove*/
    @Override
    boolean getMove(int[] move) {
        if (_nextMove == null) {
            return false;
        }

        move[0] = _nextMove[0];
        move[1] = _nextMove[1];

        return true;
    }

    /** Returns the current board.*/
    @Override
    Board getBoard() {
        return _board;
    }
}
