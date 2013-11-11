package jump61;

import org.junit.Test;
import static org.junit.Assert.*;


import java.io.StringWriter;
import java.io.StringReader;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


/** Unit test for AI
 *  Use custom evaluation and transition functions so we can
 *  independently test our Alpha-Beta agent.
 *
 *  Generate custom evaluation and transition mappings
 *  Use an extremely simple board of 2x2. Though the board is
 *  small, it is still good enough to test the correctness of
 *  the alpha-beta AI agent. The cutoff depth is 2.
 *
 *  All of this was worked on pencil and paper.
 *
 *  Also includes some tests for the evaluation function.
 *
 *  @author Kiet Lam.*/
public class AITest {

    private class BoardColor {

        private Board _board;

        private Color _color;

        BoardColor(Board bo, Color color) {
            _board = bo;
            _color = color;
        }

        @Override
        public boolean equals(Object other) {
            BoardColor bc = (BoardColor) other;

            return _board.equals(bc._board) && _color.equals(bc._color);
        }

        @Override
        public int hashCode() {
            return _board.hashCode() + _color.hashCode();
        }
    }

    /** Custom evaluation map.*/
    private Map<BoardColor, Integer> evalMap
        = new HashMap<BoardColor, Integer>();

    /** Transition map.*/
    private Map<BoardColor, List<Integer>> tranMap = new HashMap<BoardColor,
        List<Integer>>();


    private StringReader iReader = new StringReader("");
    private StringWriter oWriter = new StringWriter();
    private StringWriter eWriter = new StringWriter();

    private Board _board = new MutableBoard(2);
    private Game game = new GameStub(_board, iReader, oWriter, eWriter);

    private int _numEvalCalled = 0;

    private AI.EvaluationFunction customEval = new AI.EvaluationFunction() {

            @Override
            public int value(Color p, Board b) {
                _numEvalCalled += 1;
                return evalMap.get(new BoardColor(b, p));
            }
        };

    private AI.TransitionFunction customTran = new AI.StandardTransition() {

            @Override
            public List<Integer> legalMoves(Color p, Board b) {
                BoardColor bc = new BoardColor(b, p);

                if (tranMap.containsKey(bc)) {
                    return tranMap.get(bc);
                }

                return new ArrayList<Integer>();
            }
        };


    public void setup() {
        buildEvalMap();
        buildTranMap();
    }


    @Test
    public void testBestAction() {
        setup();
        AI ai = new AI(game, Color.RED, 2, customEval, customTran);
        int action = ai.bestAction().action();

        assertEquals("Action was not the best",
                     1, action);
    }

    /** Test whether we are correctly pruning the nodes
     *  If alpha-beta pruning is incorrect, the number of evaluated
     *  nodes will be greater than 5 or the overall value of the
     *  game tree will be incorrect (not equal to 3).*/
    @Test
    public void testAlphaBetaEvaluatedNodes() {
        setup();
        AI ai = new AI(game, Color.RED, 2, customEval, customTran);
        int minimax = ai.bestAction().value();
        assertEquals("minimax value incorrect",
                     3, minimax);
        assertEquals("# of evaluated nodes incorrect, alpha-beta pruning "
                     + "is incorrect", 5, _numEvalCalled);
    }

    @Test
    public void testEvaluationEmptyBoard() {
        AI.EvaluationFunction evalF = new AI.DeltaSquareEvaluation();

        Board board = new MutableBoard(8);

        assertEquals("incorrect board evaluation",
                     0, evalF.value(Color.RED, board));
        assertEquals("incorrect board evaluation",
                     0, evalF.value(Color.BLUE, board));
    }

    @Test
    public void testEvaluationWin() {
        AI.EvaluationFunction evalF = new AI.DeltaSquareEvaluation();

        Board board = new MutableBoard(2);

        board.set(0, 1, Color.RED);
        board.set(1, 1, Color.RED);
        board.set(2, 1, Color.RED);
        board.set(3, 1, Color.RED);

        assertEquals("incorrect board evaluation",
                     AI.POSITIVE_INF, evalF.value(Color.RED, board));
        assertEquals("incorrect board evaluation",
                     AI.NEGATIVE_INF, evalF.value(Color.BLUE, board));
    }

    @Test
    public void testEvaluationGeneral() {
        AI.EvaluationFunction evalF = new AI.DeltaSquareEvaluation();

        Board board = new MutableBoard(3);

        board.set(0, 2, Color.RED);
        board.set(1, 3, Color.BLUE);
        board.set(4, 2, Color.RED);
        board.set(6, 1, Color.BLUE);
        board.set(8, 2, Color.BLUE);

        assertEquals("incorrect board evaluation",
                     -1, evalF.value(Color.RED, board));
        assertEquals("incorrect board evaluation",
                     1, evalF.value(Color.BLUE, board));
    }

    @Test
    public void testStandardTransitionBeginning() {
        Board board = new MutableBoard(2);

        int[] transitions = new int[] {0, 1, 2, 3};
        List<Integer> ls = arrayToList(transitions);

        AI.TransitionFunction tranF = new AI.StandardTransition();

        assertEquals("incorrect transitions",
                     ls, tranF.legalMoves(Color.RED, board));
        assertEquals("incorrect transitions",
                     ls, tranF.legalMoves(Color.BLUE, board));
    }

    @Test
    public void testNoTransition() {
        Board board = new MutableBoard(2);

        board.set(0, 1, Color.RED);
        board.set(1, 1, Color.RED);
        board.set(2, 1, Color.RED);
        board.set(3, 1, Color.RED);

        AI.TransitionFunction tranF = new AI.StandardTransition();

        List<Integer> trans = new ArrayList<Integer>();

        assertEquals("incorrect transitions",
                     trans, tranF.legalMoves(Color.BLUE, board));
    }

    @Test
    public void testTransitions() {
        Board board = new MutableBoard(2);

        board.set(0, 1, Color.RED);
        board.set(2, 1, Color.RED);

        int[] redTransitions = new int[] {0, 1, 2, 3};
        int[] blueTransitions = new int[] {1, 3};

        AI.TransitionFunction tranF = new AI.StandardTransition();

        assertEquals("incorrect transitions",
                     arrayToList(redTransitions),
                     tranF.legalMoves(Color.RED, board));
        assertEquals("incorrect transitions",
                     arrayToList(blueTransitions),
                     tranF.legalMoves(Color.BLUE, board));
    }


    private void buildEvalMap() {
        Board b1 = new MutableBoard(2);
        Board b2 = new MutableBoard(2);
        Board b3 = new MutableBoard(2);
        Board b4 = new MutableBoard(2);
        Board b5 = new MutableBoard(2);
        Board b6 = new MutableBoard(2);
        Board b7 = new MutableBoard(2);

        b1.set(1, 1, Color.RED);
        b1.set(0, 1, Color.BLUE);
        b2.set(1, 1, Color.RED);
        b2.set(2, 1, Color.BLUE);
        b3.set(1, 1, Color.RED);
        b3.set(3, 1, Color.BLUE);
        b4.set(2, 1, Color.RED);
        b4.set(0, 1, Color.BLUE);
        b5.set(2, 1, Color.RED);
        b5.set(1, 1, Color.BLUE);
        b6.set(3, 1, Color.RED);
        b6.set(0, 1, Color.BLUE);
        b7.set(3, 1, Color.RED);
        b7.set(1, 1, Color.BLUE);

        evalMap.put(new BoardColor(b1, Color.RED), 5);
        evalMap.put(new BoardColor(b2, Color.RED), 3);
        evalMap.put(new BoardColor(b3, Color.RED), 8);
        evalMap.put(new BoardColor(b4, Color.RED), 1);
        evalMap.put(new BoardColor(b5, Color.RED), 3);
        evalMap.put(new BoardColor(b6, Color.RED), 0);
        evalMap.put(new BoardColor(b7, Color.RED), 4);
    }

    private void buildTranMap() {
        Board board = new MutableBoard(2);
        BoardColor maxTranBC = new BoardColor(board, Color.RED);

        int[] transitions = new int[] {1, 2, 3};
        tranMap.put(maxTranBC, arrayToList(transitions));

        buildMinTranMap();
    }

    private void buildMinTranMap() {
        Board b1 = new MutableBoard(2);
        Board b2 = new MutableBoard(2);
        Board b3 = new MutableBoard(2);

        b1.set(1, 1, Color.RED);
        b2.set(2, 1, Color.RED);
        b3.set(3, 1, Color.RED);

        int[] t1 = new int[] {0, 2, 3};
        int[] t2 = new int[] {0, 1};
        int[] t3 = new int[] {0, 1};

        tranMap.put(new BoardColor(b1, Color.BLUE), arrayToList(t1));
        tranMap.put(new BoardColor(b2, Color.BLUE), arrayToList(t2));
        tranMap.put(new BoardColor(b3, Color.BLUE), arrayToList(t3));
    }

    private List<Integer> arrayToList(int[] ars) {
        List<Integer> ls = new ArrayList<Integer>();

        for (int i = 0; i < ars.length; i += 1) {
            ls.add(ars[i]);
        }

        return ls;
    }
}
