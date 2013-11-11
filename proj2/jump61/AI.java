package jump61;

import java.util.List;
import java.util.ArrayList;

/** An automated Player.
 *  @author Kiet Lam
 */
class AI extends Player {

    /** Evaluation function. */
    public interface EvaluationFunction {

        /** Returns an evaluation for color P for board B.*/
        int value(Color p, Board b);
    }

    /** Transition function.*/
    public interface TransitionFunction {

        /** Returns the list of legal moves for player P for board B.*/
        List<Integer> legalMoves(Color p, Board b);
    }

    /** The standard transition for a board.*/
    static class StandardTransition implements TransitionFunction {

        /** Returns the list of legal moves for player P for board B.*/
        public List<Integer> legalMoves(Color p, Board b) {

            List<Integer> re = new ArrayList<Integer>();

            if (b.numOfColor(p) == b.size() * b.size()
                || b.numOfColor(p.opposite()) == b.size() * b.size()) {
                return re;
            }

            for (int i = 0; i < b.size() * b.size(); i += 1) {
                if (b.isLegal(p, i)) {
                    re.add(i);
                }
            }

            return re;
        }
    }

    /** Class to store the value of a game tree node,
     *  and he current alpha and beta values. */
    static class ValueAlphaBeta {

        /** The current value for the node.*/
        private int _value;

        /** The alpha value. */
        private int _alpha;

        /** The beta value. */
        private int _beta;

        /** Store value VAL, alpha ALP, and beta BET into this class. */
        ValueAlphaBeta(int val, int alp, int bet) {
            _value = val;
            _alpha = alp;
            _beta = bet;
        }

        /** Returns the value of this node. */
        public int value() {
            return _value;
        }

        /** Returns the alpha of this node. */
        public int alpha() {
            return _alpha;
        }

        /** Returns the beta of this node. */
        public int beta() {
            return _beta;
        }
    }

    /** Class to store the action and its value of a game tree node. */
    static class ActionValue {

        /** The action. */
        private int _action;

        /** The value of this action. */
        private int _value;

        /** Store action ACT and its value VAL into this class. */
        ActionValue(int act, int val) {
            _action = act;
            _value = val;
        }

        /** Returns the action. */
        public int action() {
            return _action;
        }

        /** Returns the value of the action. */
        public int value() {
            return _value;
        }
    }

    /** Negative infinity for us.*/
    public static final int NEGATIVE_INF = Integer.MIN_VALUE;

    /** Positive infinity for us.*/
    public static final int POSITIVE_INF = Integer.MAX_VALUE;

    /** Our evaluation function.*/
    private EvaluationFunction _evalFn;

    /** Our transition function.*/
    private TransitionFunction _tranFn;

    /** How deep we will go in our game tree search.*/
    private int _depth = 3;


    /** The evaluation function for our minimax algorithm
     *  Uses the color of square, the position of the square
     *  and the opposing's color and position of the square to evaluate. */
    static class DeltaSquareEvaluation implements EvaluationFunction {

        /** Returns the value of this node for player PLAYER for board BOARD. */
        @Override
        public int value(Color player, Board board) {
            if (board.numOfColor(player) == board.size() * board.size()) {
                return POSITIVE_INF;
            }

            if (board.numOfColor(player.opposite())
                == board.size() * board.size()) {
                return NEGATIVE_INF;
            }

            int playerValue = evaluateColor(player, board);
            int oppValue = evaluateColor(player.opposite(), board);

            return playerValue - oppValue;
        }

        /** Returns the evaluation of COLOR for BOARD. */
        int evaluateColor(Color color, Board board) {
            int value = 0;

            for (int i = 1; i <= board.size(); i += 1) {
                for (int j = 1; j <= board.size(); j += 1) {
                    if (!board.color(i, j).equals(color)) {
                        continue;
                    }

                    if ((i == 1 && j == 1)
                        || (i == board.size() && j == 1)
                        || (i == 1 && j == board.size())
                        || (i == board.size() && j == board.size())) {
                        value += board.spots(i, j);
                        continue;

                    }

                    if (i > 1 && i < board.size()
                        && j > 1 && j < board.size()) {
                        value += 3 * board.spots(i, j);
                        continue;
                    }

                    value += 2 * board.spots(i, j);
                }
            }

            return value;
        }
    }


    /** An AI player of GAME initially playing COLOR that chooses
     *  moves automatically. DEPTH is used to limit the depth of the
     *  minimax search. EVALFN is used to evaluate a board given a color.
     *  TRANFN is to get the transition given a board and a color.
     */
    AI(Game game, Color color, int depth, EvaluationFunction evalFn,
       TransitionFunction tranFn) {
        super(game, color);
        _evalFn = evalFn;
        _tranFn = tranFn;
        _depth = depth;
    }

    /** An AI player of GAME initially playing COLOR that uses minimax.*/
    AI(Game game, Color color) {
        this(game, color, Defaults.MINIMAX_DEPTH, new DeltaSquareEvaluation(),
             new StandardTransition());
    }

    @Override
    void makeMove() {
        getGame().makeMove(bestAction()._action);
    }

    /** Returns the best square # in the board for this player to play.*/
    ActionValue bestAction() {
        int value = NEGATIVE_INF;

        int alpha = NEGATIVE_INF;
        int beta = POSITIVE_INF;

        List<Integer> actions = _tranFn.legalMoves(getColor(), getBoard());

        int act = actions.get(0);

        for (Integer action: actions) {
            Board nextBoard = successor(getBoard(), action, getColor());

            ValueAlphaBeta vab = value(nextBoard, getColor().opposite(),
                                       1, alpha, beta);

            if (vab._value >= value) {
                act = action;
                value = vab._value;
            }

            alpha = Math.max(alpha, value);
        }

        return new ActionValue(act, alpha);
    }

    /** Returns the minimax value for current BOARD for player PLAYER.
     *  The game tree evaluation will be limited to _depth.
     *  Evaluate immediately if DEPTH == _depth, otherwise pass
     *  ALPHA and BETA further down to evaluate children nodes. */
    ValueAlphaBeta value(Board board, Color player, int depth,
                                 int alpha, int beta) {
        if (depth == _depth) {
            return new ValueAlphaBeta(_evalFn.value(player, board),
                                      alpha, beta);
        }

        if (player.equals(getColor())) {
            return maxValue(board, player, depth, alpha, beta);
        } else {
            return minValue(board, player, depth + 1, alpha, beta);
        }
    }

    /** Returns the maximum value for this BOARD for PLAYER.
     *  Stores current depth, alpha and beta in DEPTH, ALPHA, and BETA
     *  respectively.*/
    private ValueAlphaBeta maxValue(Board board, Color player, int depth,
                                     int alpha, int beta) {
        int tempAlpha = NEGATIVE_INF;

        List<Integer> actions = _tranFn.legalMoves(player, board);

        if (actions.size() == 0) {
            return new ValueAlphaBeta(_evalFn.value(player.opposite(), board),
                                      alpha, beta);
        }

        for (Integer action : actions) {
            Board nextBoard = successor(board, action, player);
            int val = value(nextBoard, player.opposite(),
                            depth, alpha, beta)._value;

            tempAlpha = Math.max(tempAlpha, val);

            if (tempAlpha > beta) {
                return new ValueAlphaBeta(tempAlpha, alpha, beta);
            }

            alpha = Math.max(alpha, tempAlpha);
        }

        return new ValueAlphaBeta(tempAlpha, alpha, beta);
    }

    /** Returns the minimum value for this BOARD for PLAYER.
     *  Stores current depth, alpha and beta in DEPTH, ALPHA, and BETA
     *  respectively.*/
    private ValueAlphaBeta minValue(Board board, Color player, int depth,
                                     int alpha, int beta) {
        int tempBeta = POSITIVE_INF;

        List<Integer> actions = _tranFn.legalMoves(player, board);

        if (actions.size() == 0) {
            return new ValueAlphaBeta(_evalFn.value(player.opposite(), board),
                                      alpha, beta);
        }

        for (Integer action : actions) {
            Board nextBoard = successor(board, action, player);
            int val = value(nextBoard, player.opposite(),
                            depth, alpha, beta)._value;

            tempBeta = Math.min(tempBeta, val);

            if (tempBeta < alpha) {
                return new ValueAlphaBeta(tempBeta, alpha, beta);
            }

            beta = Math.min(beta, tempBeta);
        }

        return new ValueAlphaBeta(tempBeta, alpha, beta);
    }

    /** Returns the successor for PLAYER adding spot at SQ for BOARD.*/
    private Board successor(Board board, int sq, Color player) {
        Board newBoard = new MutableBoard(board);
        newBoard.addSpot(player, sq);
        return newBoard;
    }
}
