package jump61;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class GUI extends JFrame {

    private static final int WIDTH = 600;

    private static final int HEIGHT = 600;

    private JPanel boardPanel;

    private JButton setMovesButton;
    private JButton blueButton;
    private JButton startButton;
    private JButton redButton;
    private JButton quitButton;

    private Board _board;

    private boolean isPlaying = false;

    private AI redAI;
    private AI blueAI;

    private Square[][] squares;

    public GUI() {
        blueAI = new AI(new GUIGame(), Color.BLUE);
        _board = new MutableBoard(Defaults.BOARD_SIZE);
        initComponents();
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        redButton = new JButton();
        blueButton = new JButton();
        quitButton = new JButton();
        setMovesButton = new JButton();

        boardPanel = new JPanel();

        startButton = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CS61B: jump61");
        setMinimumSize(new java.awt.Dimension(WIDTH, HEIGHT));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        redButton.setText("Auto Red");
        redButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    onRedClicked();
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(redButton, gridBagConstraints);

        blueButton.setText("Manual Blue");
        blueButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    onBlueClicked();
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(blueButton, gridBagConstraints);

        setMovesButton.setText("Moves");
        setMovesButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(setMovesButton, gridBagConstraints);


        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if (isPlaying) {
                        isPlaying = false;
                        startButton.setText("Start");
                    } else {
                        start();
                    }
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(startButton, gridBagConstraints);


        quitButton.setText("Quit");
        quitButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    System.exit(0);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(quitButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 13;
        gridBagConstraints.gridheight = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.weighty = 0.8;
        gridBagConstraints.insets = new java.awt.Insets(9, 20, 9, 20);
        getContentPane().add(boardPanel, gridBagConstraints);

        setupBoard();

        pack();

        setLocationRelativeTo(null);
    }

    private void setupBoard() {
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

    private void handleSquareClicked(Square square) {
        if (!isPlaying) {
            showSquareOption(square);
        } else {
            addSpotSquare(square);
        }
    }

    private void showSquareOption(Square square) {

    }

    private void addSpotSquare(Square square) {
        if (_board.isLegal(_board.whoseMove(), square.getRow(),
                           square.getColumn())) {
            _board.addSpot(_board.whoseMove(), square.getRow(),
                           square.getColumn());
        }

        updateBoard();

        if (_board.getWinner() != null) {
            isPlaying = false;
            startButton.setText("Start");
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
        }
    }

    private void start() {
        if (_board.getWinner() != null) {
            isPlaying = false;
            startButton.setText("Start");
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
        }
    }

    private void checkForWin() {
        isPlaying = _board.getWinner() == null;

        if (!isPlaying) {
            startButton.setText("Start");
        }
    }

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

    private void onRedClicked() {
        isPlaying = false;

        if (redAI == null) {
            redButton.setText("Manual Red");
            redAI = new AI(new GUIGame(), Color.RED);
        } else {
            redAI = null;
            redButton.setText("Auto Red");
        }
    }

    private void onBlueClicked() {
        isPlaying = false;

        if (blueAI == null) {
            blueButton.setText("Manual Blue");
            blueAI = new AI(new GUIGame(), Color.BLUE);
        } else {
            blueAI = null;
            blueButton.setText("Auto Blue");
        }
    }
}
