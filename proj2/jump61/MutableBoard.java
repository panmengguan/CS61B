package jump61;


import static jump61.Color.*;

import java.util.Stack;
import java.util.ArrayList;

import java.util.List;


/** A Jump61 board state.
 *  @author Kiet Lam
 */
class MutableBoard extends Board {

    /** Class representing a square.*/
    private class Square {
        /** Color of this square.*/
        private Color _color = Color.WHITE;

        /** Number of spots for this square.*/
        private int _spots = 0;

        /** Create a Square with COLOR color and SPOTS spots.*/
        Square(Color color, int spots) {
            _color = color;
            _spots = spots;
        }

        /** Create default square with WHITE and 0 spots.*/
        Square() {}
    }

    /** Total combined number of moves by both sides. */
    protected int _moves;
    /** Convenience variable: size of board (squares along one edge). */
    private int _N;

    /** 2D array representation of the board.*/
    private Square[][] _board;

    /** History.*/
    private Stack<Board> _history = new Stack<Board>();

    /** An N x N board in initial configuration. */
    MutableBoard(int N) {
        _N = N;
        initializeBoard();
    }

    /** A board whose initial contents are copied from BOARD0. Clears the
     *  undo history. */
    MutableBoard(Board board0) {
        copy(board0);
        clearHistory();
    }

    @Override
    void clear(int N) {
        _N = N;
        _moves = 0;

        clearHistory();
        initializeBoard();
    }

    @Override
    void copy(Board board) {
        _N = board.size();
        _moves = board.numMoves();

        initializeBoard();

        for (int i = 0; i < board.size(); i += 1) {
            for (int j = 0; j < board.size(); j += 1) {
                Color color = board.color(i + 1, j + 1);
                int spots = board.spots(i + 1, j + 1);

                _board[i][j] = new Square(color, spots);
            }
        }

        setNextPlayer(board.whoseMove());
    }

    @Override
    int size() {
        return _N;
    }

    @Override
    int spots(int r, int c) {
        return _board[r - 1][c - 1]._spots;
    }

    @Override
    int spots(int n) {
        return spots(row(n), col(n));
    }

    @Override
    Color color(int r, int c) {
        return _board[r - 1][c - 1]._color;
    }

    @Override
    Color color(int n) {
        return color(row(n), col(n));
    }

    @Override
    int numMoves() {
        return _moves;
    }

    @Override
    int numOfColor(Color color) {
        int numSquares = 0;

        for (int i = 0; i < _N; i += 1) {
            for (int j = 0; j < _N; j += 1) {
                if (_board[i][j]._color.equals(color)) {
                    numSquares += 1;
                }
            }
        }

        return numSquares;
    }

    @Override
    void addSpot(Color player, int r, int c) {
        Board cp = new MutableBoard(this);

        Square sq = _board[r - 1][c - 1];
        sq._spots += 1;
        sq._color = player;

        if (spots(r, c) > neighbors(r, c)) {
            jump(sqNum(r, c));
        }

        if (getWinner() == null) {
            flipNextPlayer();
            _moves += 1;
        }

        _history.push(cp);
    }

    @Override
    void addSpot(Color player, int n) {
        addSpot(player, row(n), col(n));
    }

    @Override
    void set(int r, int c, int num, Color player) {
        Square sq = new Square(player, num);
        _board[r - 1][c - 1] = sq;
        clearHistory();
    }

    @Override
    void set(int n, int num, Color player) {
        set(row(n), col(n), num, player);
    }

    @Override
    void setMoves(int num) {
        assert num > 0;
        _moves = num;
    }

    @Override
    void undo() {
        if (!_history.empty()) {
            Board board = _history.pop();
            copy(board);
        }
    }

    /** Do all jumping (if necessary) on this board, starting with square S.*/
    private void jump(int s) {
        if (s == size() * size()) {
            return;
        }

        if (spots(s) <= neighbors(s)) {
            jump(s + 1);
            return;
        }

        if (getWinner() != null) {
            return;
        }

        int r = row(s);
        int c = col(s);

        List<Integer> neighbors = getNeighbors(r, c);

        _board[r - 1][c - 1]._spots = 1;

        for (Integer i: neighbors) {

            int r1 = row(i);
            int c1 = col(i);

            _board[r1 - 1][c1 - 1]._spots += 1;
            _board[r1 - 1][c1 - 1]._color = color(s);
        }

        jump(0);
    }

    /** Clear the board's history.*/
    private void clearHistory() {
        _history = new Stack<Board>();
    }

    /** Initialize the board.*/
    private void initializeBoard() {
        _board = new Square[_N][_N];

        for (int i = 0; i < _N; i += 1) {
            for (int j = 0; j < _N; j += 1) {
                _board[i][j] = new Square();
            }
        }
    }
}
