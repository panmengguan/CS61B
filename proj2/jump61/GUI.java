package jump61;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.ListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JList;

import java.util.List;
import java.util.Random;

/** CS61B jump61 GUI.
 *  @author Kiet Lam
 */
public class GUI extends JFrame {

    /** Maximum board size.*/
    private static final int MAX_BOARD_SIZE = 15;

    /** Min width.*/
    private static final int MIN_WIDTH = 650;

    /** Min height.*/
    private static final int MIN_HEIGHT = 650;

    /** The y location for the buttons.*/
    private static final int BUTTON_Y = 11;

    /** X location for red player button.*/
    private static final int RED_X = 0;
    /** X location for blue player button.*/
    private static final int BLUE_X = 3;
    /** X location for moves button.*/
    private static final int MOVES_X = 6;
    /** X location for random button.*/
    private static final int RANDOM_X = 9;
    /** X location for start button.*/
    private static final int START_X = 12;
    /** X location for clear button.*/
    private static final int CLEAR_X = 15;
    /** X location for size button.*/
    private static final int SIZE_X = 18;
    /** X location for seed button.*/
    private static final int SEED_X = 21;
    /** X location for quit button.*/
    private static final int QUIT_X = 24;

    /** Inset top.*/
    private static final int INSET_TOP = 9;
    /** Inset left.*/
    private static final int INSET_LEFT = 20;
    /** Inset bottom.*/
    private static final int INSET_BOTTOM = 9;
    /** Inset right.*/
    private static final int INSET_RIGHT = 20;

    /** Weight for the board.*/
    private static final double BOARD_WEIGHT = 0.8;

    /** Width for our board for GridBagLayout.*/
    private static final int BOARD_WIDTH = 25;

    /** Height for our board for GridBagLayout.*/
    private static final int BOARD_HEIGHT = 10;

    /** Button weight.*/
    private static final double BUTTON_WEIGHT = 0.1;

    /** JPanel for the board.*/
    private JPanel boardPanel;

    /** JButotn for set moves.*/
    private JButton setMovesButton;
    /** JButotn for blue player.*/
    private JButton blueButton;
    /** JButotn for start.*/
    private JButton startButton;
    /** JButotn for red.*/
    private JButton redButton;
    /** JButotn for quit.*/
    private JButton quitButton;
    /** JButotn for clear.*/
    private JButton clearButton;
    /** JButotn for seed.*/
    private JButton seedButton;
    /** JButotn for size.*/
    private JButton sizeButton;
    /** JButotn for random.*/
    private JButton randomButton;

    /** Our board for the game.*/
    private Board _board;

    /** Whether we are playing or not.*/
    private boolean isPlaying = false;

    /** AI player for red.*/
    private AI redAI;

    /** AI player for blue.*/
    private AI blueAI;

    /** Convience square matrix.*/
    private Square[][] squares;

    /** Random number generator for seeding the board.*/
    private final Random random = new Random();

    /** Sampling rate for red.*/
    private static final double RED_SAMPLING = 0.08;

    /** Sampling rate for blue.*/
    private static final double BLUE_SAMPLING = 0.08;

    /** Construct our GUI.*/
    public GUI() {
        blueAI = new AI(new GUIGame(), Color.BLUE);
        _board = new MutableBoard(Defaults.BOARD_SIZE);
        initComponents();
    }

    /** Initialize our GUI components.*/
    private void initComponents() {
        redButton = new JButton();
        blueButton = new JButton();
        quitButton = new JButton();
        setMovesButton = new JButton();
        clearButton = new JButton();
        seedButton = new JButton();
        sizeButton = new JButton();
        randomButton = new JButton();

        boardPanel = new JPanel();

        startButton = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("CS61B: jump61");
        setMinimumSize(new java.awt.Dimension(MIN_WIDTH, MIN_HEIGHT));
        getContentPane().setLayout(new GridBagLayout());

        addRed();
        addBlue();
        addMovesButton();
        addSeedButton();
        addRandomButton();
        addSizeButton();
        addClearButton();
        addStartButton();
        addQuitButton();

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = BOARD_WIDTH;
        gridBagConstraints.gridheight = BOARD_HEIGHT;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = BOARD_WEIGHT;
        gridBagConstraints.weighty = BOARD_WEIGHT;
        gridBagConstraints.insets = new Insets(INSET_TOP, INSET_LEFT,
                                               INSET_BOTTOM, INSET_RIGHT);
        getContentPane().add(boardPanel, gridBagConstraints);

        setupBoard();

        pack();

        setLocationRelativeTo(null);
    }

    /** Add the red player button.*/
    private void addRed() {
        GridBagConstraints constraint = new GridBagConstraints();
        redButton.setText("Auto Red");
        redButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    onRedClicked();
                }
            });
        constraint.gridx = RED_X;
        constraint.gridy = BUTTON_Y;
        constraint.weightx = BUTTON_WEIGHT;
        constraint.weighty = BUTTON_WEIGHT;
        getContentPane().add(redButton, constraint);
    }

    /** Add the blue player button.*/
    private void addBlue() {
        GridBagConstraints constraint = new GridBagConstraints();
        blueButton.setText("Manual Blue");
        blueButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    onBlueClicked();
                }
            });
        constraint.gridx = BLUE_X;
        constraint.gridy = BUTTON_Y;
        constraint.weightx = BUTTON_WEIGHT;
        constraint.weighty = BUTTON_WEIGHT;
        getContentPane().add(blueButton, constraint);
    }

    /** Add the set moves button.*/
    private void addMovesButton() {
        GridBagConstraints constraint = new GridBagConstraints();
        setMovesButton.setText("Moves");
        setMovesButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    askForMoves();
                }
            });
        constraint.gridx = MOVES_X;
        constraint.gridy = BUTTON_Y;
        constraint.weightx = BUTTON_WEIGHT;
        constraint.weighty = BUTTON_WEIGHT;
        getContentPane().add(setMovesButton, constraint);
    }

    /** Add the seed button.*/
    private void addSeedButton() {
        GridBagConstraints constraint = new GridBagConstraints();
        seedButton.setText("Seed");
        seedButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    askForSeed();
                }
            });
        constraint.gridx = SEED_X;
        constraint.gridy = BUTTON_Y;
        constraint.weightx = BUTTON_WEIGHT;
        constraint.weighty = BUTTON_WEIGHT;
        getContentPane().add(seedButton, constraint);
    }

    /** Add the random button.*/
    private void addRandomButton() {
        GridBagConstraints constraint = new GridBagConstraints();
        randomButton.setText("Random");
        randomButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    randomize();
                }
            });
        constraint.gridx = RANDOM_X;
        constraint.gridy = BUTTON_Y;
        constraint.weightx = BUTTON_WEIGHT;
        constraint.weighty = BUTTON_WEIGHT;
        getContentPane().add(randomButton, constraint);
    }

    /** Add the size button.*/
    private void addSizeButton() {
        GridBagConstraints constraint = new GridBagConstraints();
        sizeButton.setText("Size");
        sizeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    askForSize();
                }
            });
        constraint.gridx = SIZE_X;
        constraint.gridy = BUTTON_Y;
        constraint.weightx = BUTTON_WEIGHT;
        constraint.weighty = BUTTON_WEIGHT;
        getContentPane().add(sizeButton, constraint);
    }

    /** Add the clear button.*/
    private void addClearButton() {
        GridBagConstraints constraint = new GridBagConstraints();
        clearButton.setText("Clear");
        clearButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    stopPlaying();
                    _board.clear(_board.size());
                    updateBoard();
                }
            });
        constraint.gridx = CLEAR_X;
        constraint.gridy = BUTTON_Y;
        constraint.weightx = BUTTON_WEIGHT;
        constraint.weighty = BUTTON_WEIGHT;
        getContentPane().add(clearButton, constraint);
    }

    /** Add the start button.*/
    private void addStartButton() {
        GridBagConstraints constraint = new GridBagConstraints();
        startButton.setText("Start");
        startButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if (isPlaying) {
                        stopPlaying();
                    } else {
                        startPlaying();
                    }
                }
            });
        constraint.gridx = START_X;
        constraint.gridy = BUTTON_Y;
        constraint.weightx = BUTTON_WEIGHT;
        constraint.weighty = BUTTON_WEIGHT;
        getContentPane().add(startButton, constraint);
    }

    /** Add the quit button.*/
    private void addQuitButton() {
        GridBagConstraints constraint = new GridBagConstraints();
        quitButton.setText("Quit");
        quitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    System.exit(0);
                }
            });
        constraint.gridx = QUIT_X;
        constraint.gridy = BUTTON_Y;
        constraint.weightx = BUTTON_WEIGHT;
        constraint.weighty = BUTTON_WEIGHT;
        getContentPane().add(quitButton, constraint);
    }

    /** Setup our board and squares.*/
    private void setupBoard() {
        stopPlaying();
        boardPanel.removeAll();
        boardPanel.setLayout(new GridLayout(_board.size(), _board.size(),
                                            2, 2));

        squares = new Square[_board.size()][_board.size()];

        for (int i = 0; i < _board.size(); i += 1) {
            for (int j = 0; j < _board.size(); j += 1) {
                Square sq = new Square(i + 1, j + 1,
                                       _board.spots(i + 1, j + 1),
                                       _board.color(i + 1, j + 1));
                squares[i][j] = sq;
                addListener(sq);
                boardPanel.add(sq);
            }
        }
    }

    /** Update our squares from board.*/
    private void updateBoard() {
        for (int i = 0; i < _board.size(); i += 1) {
            for (int j = 0; j < _board.size(); j += 1) {
                Square sq = squares[i][j];

                sq.update(_board.spots(i + 1, j + 1),
                          _board.color(i + 1, j + 1));
                sq.repaint();
            }
        }
    }

    /** Add a mouse listener to SQ.*/
    private void addListener(Square sq) {
        sq.addMouseListener(new MouseListener() {
                @Override
                public void mouseReleased(MouseEvent event) {
                }

                @Override
                public void mousePressed(MouseEvent event) {
                }

                @Override
                public void mouseExited(MouseEvent event) {
                }

                @Override
                public void mouseEntered(MouseEvent event) {
                }

                @Override
                public void mouseClicked(MouseEvent event) {
                    Square square = (Square) event.getSource();
                    handleSquareClicked(square);
                }
            });
    }

    /** Handle a mouse click for SQUARE.*/
    private void handleSquareClicked(Square square) {
        if (!isPlaying) {
            showSquareOption(square);
        } else {
            addSpotSquare(square);
        }
    }

    /** Show the options to choose color and spots for SQUARE.*/
    private void showSquareOption(Square square) {
        JList<String> colorList = new JList<String>(new String[] {"RED", "BLUE",
                                                                  "WHITE"});
        colorList.setSelectionMode(ListSelectionModel.
                                   SINGLE_INTERVAL_SELECTION);

        List<Integer> neighbors = _board.getNeighbors(square.getRow(),
                                                      square.getColumn());
        Integer[] spots = new Integer[neighbors.size() + 1];

        spots[0] = 0;
        for (int i = 0; i < neighbors.size(); i += 1) {
            spots[i + 1] = i + 1;
        }

        JList<Integer> spotsList = new JList<Integer>(spots);
        spotsList.setSelectionMode(ListSelectionModel.
                                   SINGLE_INTERVAL_SELECTION);

        JOptionPane.showMessageDialog(square, colorList,
                                      "Select color for square",
                                      JOptionPane.PLAIN_MESSAGE);

        int colorChoice = colorList.getSelectedIndices()[0];

        Color color = null;
        if (colorChoice == 0) {
            color = Color.RED;
        } else if (colorChoice == 1) {
            color = Color.BLUE;
        } else {
            color = Color.WHITE;
        }

        int spotNum = 0;

        if (!color.equals(Color.WHITE)) {
            JOptionPane.showMessageDialog(square, spotsList,
                                          "Select spots for square",
                                          JOptionPane.PLAIN_MESSAGE);
            spotNum = spotsList.getSelectedIndices()[0];
        }

        _board.set(square.getRow(), square.getColumn(), spotNum, color);
        updateBoard();
    }

    /** Add spot to SQUARE and get the AI move (if needed).*/
    private void addSpotSquare(Square square) {
        if (_board.isLegal(_board.whoseMove(), square.getRow(),
                           square.getColumn())) {
            _board.addSpot(_board.whoseMove(), square.getRow(),
                           square.getColumn());
        }

        updateBoard();

        if (_board.getWinner() != null) {
            stopPlaying();
            return;
        }

        switch (_board.whoseMove()) {
        case RED:
            if (redAI != null) {
                SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            redAI.makeMove();
                        }
                    });
            }
            break;
        case BLUE:
            if (blueAI != null) {
                SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            blueAI.makeMove();
                        }
                    });
            }
            break;
        default:
            break;
        }
    }

    /** Start playing the game, get the AI to move (if needed).*/
    private void startPlaying() {
        if (_board.getWinner() != null) {
            stopPlaying();
            return;
        }

        isPlaying = true;
        startButton.setText("Stop");

        switch (_board.whoseMove()) {
        case RED:
            if (redAI != null) {
                redAI.makeMove();
            }

            break;
        case BLUE:
            if (blueAI != null) {
                blueAI.makeMove();
            }

            break;
        default:
            break;
        }
    }

    /** Check for a win and update our internal state.*/
    private void checkForWin() {
        isPlaying = _board.getWinner() == null;

        if (!isPlaying) {
            startButton.setText("Start");
        }
    }

    /** Game stub for ease of use.*/
    private class GUIGame extends Game {

        @Override
        void makeMove(int r, int c) {
            addSpotSquare(squares[r - 1][c - 1]);
        }

        @Override
        void makeMove(int n) {
            makeMove(_board.row(n), _board.col(n));
        }

        @Override
        Board getBoard() {
            return _board;
        }
    }

    /** Handle when user clicked on red player button.*/
    private void onRedClicked() {
        stopPlaying();

        if (redAI == null) {
            redButton.setText("Manual Red");
            redAI = new AI(new GUIGame(), Color.RED);
        } else {
            redAI = null;
            redButton.setText("Auto Red");
        }
    }

    /** Handle when user clicked on blue player button.*/
    private void onBlueClicked() {
        stopPlaying();

        if (blueAI == null) {
            blueButton.setText("Manual Blue");
            blueAI = new AI(new GUIGame(), Color.BLUE);
        } else {
            blueAI = null;
            blueButton.setText("Auto Blue");
        }
    }

    /** Stop playing.*/
    private void stopPlaying() {
        isPlaying = false;
        startButton.setText("Start");
    }

    /** Ask for the number of moves.*/
    private void askForMoves() {
        String move = JOptionPane.showInputDialog(this, "Enter moves > 1",
                                                  null);

        try {
            int moves = Integer.parseInt(move);
            if (moves > 1) {
                _board.setMoves(moves);
            }
        } catch (NumberFormatException e) {
            int k = 3;
        }
    }

    /** Ask for a seed for our random number generator.*/
    private void askForSeed() {
        String seedStr = JOptionPane.showInputDialog(this, "Enter seed",
                                                  null);

        try {
            random.setSeed(Integer.parseInt(seedStr));
        } catch (NumberFormatException e) {
            int k = 3;
        }
    }

    /** Ask for a size for our game and then update.*/
    private void askForSize() {
        String sizeStr = JOptionPane.showInputDialog(this,
                                                     "Enter size > 1 and < 15");
        try {
            int n = Integer.parseInt(sizeStr);
            if (n > 1 && n < MAX_BOARD_SIZE) {
                _board.clear(n);
                setupBoard();
                revalidate();
                repaint();
            }
        } catch (NumberFormatException e) {
            int k = 3;
        }
    }

    /** Randomize our board.*/
    private void randomize() {
        for (int i = 0; i < _board.size() * _board.size(); i += 1) {
            if (random.nextDouble() < RED_SAMPLING) {
                _board.set(i, 1, Color.RED);
            } else if (random.nextDouble() < RED_SAMPLING + BLUE_SAMPLING) {
                _board.set(i, 1, Color.BLUE);
            } else {
                _board.set(i, 0, Color.WHITE);
            }
        }

        updateBoard();
    }
}
