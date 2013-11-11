package jump61;

import java.io.Reader;
import java.io.Writer;


/** Stub for Game to ease testing. Single player
 *  This is needed to separate testing of HumanPlayer and Game
  *  @author Kiet Lam */
class GameStub extends Game {

    /** Next move.*/
    private int[] _nextMove;

    /** Current board.*/
    private Board _board;

    /** The only player of this board.*/
    private Color _player;

    /** Create a Game stub with BOARD, IS, OS, ERR and optional PLAYER.*/
    GameStub(Board board, Reader is, Writer os,
             Writer err, Color player) {
        super(is, os, os, err);
        _board = board;
        _player = player;
    }

    /** Create a Game stub with BOARD, IS, OS, ERR.*/
    GameStub(Board board, Reader is, Writer os,
             Writer err) {
        this(board, is, os, err, null);
    }

    /** Set the output of getMove() to be NEXTMOVE.
     *  Assumes NEXTMOVE is int[2]*/
    void setNextMove(int[] nextMove) {
        _nextMove = nextMove;
    }

    @Override
    void makeMove(int r, int c) {
        _board.set(r, c, _board.spots(r, c) + 1, _player);
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
