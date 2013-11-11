package jump61;

import java.util.Formatter;
import java.util.ArrayList;
import java.util.List;

import static jump61.Color.*;

/** Represents the state of a Jump61 game.  Squares are indexed either by
 *  row and column (between 1 and size()), or by square number, numbering
 *  squares by rows, with squares in row 1 numbered 0 - size()-1, in
 *  row 2 numbered size() - 2*size() - 1, etc.
 *  @author Kiet Lam
 */
abstract class Board {

    /** Next player to move.*/
    private Color _nextPlayer = Color.RED;

    /** The length of an end of line on this system. */
    private static final int NL_LENGTH =
        System.getProperty("line.separator").length();

    /** (Re)initialize me to a cleared board with N squares on a side. Clears
     *  the undo history and sets the number of moves to 0. */
    void clear(int N) {
        unsupported("clear");
    }

    /** Copy the contents of BOARD into me. */
    void copy(Board board) {
        unsupported("copy");
    }

    @Override
    public boolean equals(Object other) {
        Board board = (Board) other;

        if (size() * size() != board.size() * board.size()) {
            return false;
        }

        for (int i = 0; i < size() * size(); i += 1) {
            if (spots(i) != board.spots(i)
                || !color(i).equals(board.color(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        for (int i = 0; i < size() * size(); i += 1) {
            hash += spots(i) + color(i).hashCode();
        }

        return hash;
    }

    /** Resets the next player to be red. */
    public void resetNextPlayer() {
        _nextPlayer = Color.RED;
    }

    /** Return the number of rows and of columns of THIS. */
    abstract int size();

    /** Returns the number of spots in the square at row R, column C,
     *  1 <= R, C <= size (). */
    abstract int spots(int r, int c);

    /** Returns the number of spots in square #N. */
    abstract int spots(int n);

    /** Returns the color of square #N, numbering squares by rows, with
     *  squares in row 1 number 0 - size()-1, in row 2 numbered
     *  size() - 2*size() - 1, etc. */
    abstract Color color(int n);

    /** Returns the color of the square at row R, column C,
     *  1 <= R, C <= size(). */
    abstract Color color(int r, int c);

    /** Returns the total number of moves made (red makes the odd moves,
     *  blue the even ones). */
    abstract int numMoves();

    /** Returns the Color of the player who would be next to move.  If the
     *  game is won, this will return the loser (assuming legal position). */
    Color whoseMove() {
        Color winner = getWinner();

        if (winner != null) {
            return winner.opposite();
        }

        return _nextPlayer;
    }

    /** Return true iff row R and column C denotes a valid square. */
    final boolean exists(int r, int c) {
        return 1 <= r && r <= size() && 1 <= c && c <= size();
    }

    /** Return true iff S is a valid square number. */
    final boolean exists(int s) {
        int N = size();
        return 0 <= s && s < N * N;
    }

    /** Return the row number for square #N. */
    final int row(int n) {
        return (n / size()) + 1;
    }

    /** Return the column number for square #N. */
    final int col(int n) {
        return (n % size()) + 1;
    }

    /** Return the square number of row R, column C. */
    final int sqNum(int r, int c) {
        return (r - 1) * size() + c - 1;
    }

    /** Returns true iff it would currently be legal for PLAYER to add a spot
        to square at row R, column C. */
    boolean isLegal(Color player, int r, int c) {
        if (r < 1 || r > size() || c < 1 || r > size()) {
            return false;
        }

        return isLegal(player, sqNum(r, c));
    }

    /** Returns true iff it would currently be legal for PLAYER to add a spot
     *  to square #N. */
    boolean isLegal(Color player, int n) {
        return color(n).equals(Color.WHITE)
            || color(n).equals(player);
    }

    /** Returns true iff PLAYER is allowed to move at this point. */
    boolean isLegal(Color player) {
        return player.equals(_nextPlayer);
    }

    /** Returns the winner of the current position, if the game is over,
     *  and otherwise null. */
    final Color getWinner() {
        Color winner = null;

        for (int i = 0; i < size() * size(); i += 1) {
            if (color(i).equals(Color.WHITE)) {
                winner = null;
                break;
            }

            if (winner == null) {
                winner = color(i);
            }

            if (!winner.equals(color(i))) {
                winner = null;
                break;
            }
        }

        return winner;
    }

    /** Return the number of squares of given COLOR. */
    abstract int numOfColor(Color color);

    /** Add a spot from PLAYER at row R, column C.  Assumes
     *  isLegal(PLAYER, R, C). */
    void addSpot(Color player, int r, int c) {
        unsupported("addSpot");
    }

    /** Add a spot from PLAYER at square #N.  Assumes isLegal(PLAYER, N). */
    void addSpot(Color player, int n) {
        unsupported("addSpot");
    }

    /** Set the square at row R, column C to NUM spots (0 <= NUM), and give
     *  it color PLAYER if NUM > 0 (otherwise, white).  Clear the undo
     *  history. */
    void set(int r, int c, int num, Color player) {
        unsupported("set");
    }

    /** Set the square #N to NUM spots (0 <= NUM), and give it color PLAYER
     *  if NUM > 0 (otherwise, white).  Clear the undo history. */
    void set(int n, int num, Color player) {
        unsupported("set");
    }

    /** Set the current number of moves to N.  Clear the undo history. */
    void setMoves(int n) {
        unsupported("setMoves");
    }

    /** Undo the effects one move (that is, one addSpot command).  One
     *  can only undo back to the last point at which the undo history
     *  was cleared, or the construction of this Board. */
    void undo() {
        unsupported("undo");
    }

    /** Returns the neighbors of square R:C.*/
    List<Integer> getNeighbors(int r, int c) {
        List<Integer> neighbors = new ArrayList<Integer>();

        int prevRow = r - 1;
        int nextRow = r + 1;
        int prevCol = c - 1;
        int nextCol = c + 1;

        if (prevRow >= 1) {
            neighbors.add(sqNum(prevRow, c));
        }

        if (prevCol >= 1) {
            neighbors.add(sqNum(r, prevCol));
        }

        if (nextRow <= size()) {
            neighbors.add(sqNum(nextRow, c));
        }

        if (nextCol <= size()) {
            neighbors.add(sqNum(r, nextCol));
        }

        return neighbors;
    }

    /** Returns the neighbors of square SQNUM.*/
    List<Integer> getNeighbors(int sqNum) {
        return getNeighbors(row(sqNum), col(sqNum));
    }

    /** Returns my dumped representation. */
    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("size:%i nextPlayer:%s \board:%s", size(),
                   whoseMove().toString(), toDisplayString());
        return out.toString();
    }

    /** Returns an external rendition of me, suitable for
     *  human-readable textual display.  This is distinct from the dumped
     *  representation (returned by toString). */
    public String toDisplayString() {
        String re = "";

        re += "===\n";

        for (int i = 0; i < size(); i += 1) {
            re += "    ";
            for (int j = 0; j < size(); j += 1) {
                int sq = sqNum(i + 1, j + 1);

                switch (color(sq)) {
                case WHITE:
                    re += "-- ";
                    break;
                case RED:
                    re += spots(sq) + "r ";
                    break;
                case BLUE:
                    re += spots(sq) + "b ";
                    break;
                default:
                    re += "-- ";
                }
            }

            re = re.substring(0, re.length() - 1);
            re += "\n";
        }

        re += "===";

        return re;
    }

    /** Returns the number of neighbors of the square at row R, column C. */
    int neighbors(int r, int c) {
        if ((r == 1 && c == 1)
            || (r == size() && c == 1)
            || (r == 1 && c == size())
            || (r == size() && c == size())) {
            return 2;
        }

        if (r == 1 || r == size() || c == 1 || c == size()) {
            return 3;
        }

        return 4;
    }

    /** Returns the number of neighbors of square #N. */
    int neighbors(int n) {
        return neighbors(row(n), col(n));
    }

    /** Flip next player to the opposite color.*/
    protected void flipNextPlayer() {
        _nextPlayer = _nextPlayer.opposite();
    }

    /** Sets the next player.*/
    protected void setNextPlayer(Color player) {
        _nextPlayer = player;
    }

    /** Indicate fatal error: OP is unsupported operation. */
    private void unsupported(String op) {
        String msg = String.format("'%s' operation not supported", op);
        throw new UnsupportedOperationException(msg);
    }
}
