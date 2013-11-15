package jump61;

import java.io.Reader;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringReader;

import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import static jump61.Color.*;
import static jump61.GameException.error;

/** Main logic for playing (a) game(s) of Jump61.
 *  @author Kiet Lam
 */
class Game {

    /** EOF character.*/
    private static final char EOF = 4;

    /** Name of resource containing help message. */
    private static final String HELP = "jump61/Help.txt";

    /** Writer on which to print prompts for input. */
    private final PrintWriter _prompter;
    /** Scanner from current game input.  Initialized to return
     *  newlines as tokens. */
    private final Scanner _inp;
    /** Outlet for responses to the user. */
    private final PrintWriter _out;
    /** Outlet for error responses to the user. */
    private final PrintWriter _err;

    /** The board on which I record all moves. */
    private final Board _board;
    /** A readonly view of _board. */
    private final Board _readonlyBoard;

    /** A pseudo-random number generator used by players as needed. */
    private final Random _random = new Random();

    /** True iff a game is currently in progress. */
    private boolean _playing;

    /** True iff quit command was seen.*/
    private boolean _quit = false;

    /** Command input .*/
    private String _cmd = "";

    /** String delimimter pattern.*/
    private static final String DELIMITER_PATTERN
        = "(?m)\\p{Blank}*$|^\\p{Blank}*|\\p{Blank}+";

    /** Command Scanner.*/
    private Scanner _cmdScanner;

    /** Red player.*/
    private Player _red;

    /** Blue player.*/
    private Player _blue;

    /** Sampling rate for red.*/
    private static final double RED_SAMPLING = 0.08;

    /** Sampling rate for blue.*/
    private static final double BLUE_SAMPLING = 0.08;

   /** Used to return a move entered from the console.  Allocated
     *  here to avoid allocations. */
    private final int[] _move = new int[2];

    /** Previous move. This is so that we are able to display for AI*/
    private final int[] _previousMove = new int[2];

    /** Automatically dumps the board after every move.*/
    private boolean _autodump = false;

    /** A new Game that takes command/move input from INPUT, prints
     *  normal output on OUTPUT, prints prompts for input on PROMPTS,
     *  and prints error messages on ERROROUTPUT. The Game now "owns"
     *  INPUT, PROMPTS, OUTPUT, and ERROROUTPUT, and is responsible for
     *  closing them when its play method returns. */
    Game(Reader input, Writer prompts, Writer output, Writer errorOutput) {
        _board = new MutableBoard(Defaults.BOARD_SIZE);
        _readonlyBoard = new ConstantBoard(_board);
        _prompter = new PrintWriter(prompts, true);
        _inp = new Scanner(input);
        _out = new PrintWriter(output, true);
        _err = new PrintWriter(errorOutput, true);
        _red = new HumanPlayer(this, Color.RED);
        _blue = new AI(this, Color.BLUE);
    }

    /** Default constructor. */
    Game() {
        _prompter = null;
        _board = null;
        _readonlyBoard = null;
        _inp = null;
        _out = null;
        _err = null;
        _red = null;
        _blue = null;
    }

    /** Returns a readonly view of the game board.  This board remains valid
     *  throughout the session. */
    Board getBoard() {
        return _board;
    }

    /** Play a session of Jump61.  This may include multiple games,
     *  and proceeds until the user exits.  Returns an exit code: 0 is
     *  normal; any positive quantity indicates an error.  */
    int play() {
        while (!_quit) {
            if (promptForNext()) {
                try {
                    readExecuteCommand();
                } catch (GameException e) {
                    reportError(e.getMessage());
                } catch (IllegalArgumentException e) {
                    reportError(e.getMessage());
                } catch (InputMismatchException e) {
                    reportError(e.getMessage());
                }
            }
        }

        return 0;
    }

    /** Get a move from my input and place its row and column in
     *  MOVE. Returns true if this is successful, false if game stops
     *  or ends first. */
    boolean getMove(int[] move) {
        while (_playing && _move[0] == 0 && !_quit && promptForNext()) {
            readExecuteCommand();
        }

        if (_move[0] > 0) {
            move[0] = _move[0];
            move[1] = _move[1];
            _move[0] = 0;
            return true;
        } else {
            return false;
        }
    }

    /** Add a spot to R C, if legal to do so. */
    void makeMove(int r, int c) {
        if (_board.isLegal(_board.whoseMove(), r, c)) {
            _board.addSpot(_board.whoseMove(), r, c);

            if (_autodump) {
                dump();
            }

            _previousMove[0] = r;
            _previousMove[1] = c;
        } else {
            _move[0] = 0;
            throw error("Invalid move");
        }
    }

    /** Add a spot to square #N, if legal to do so.
     *  Then check for winner. */
    void makeMove(int n) {
        makeMove(_board.row(n), _board.col(n));
    }

    /** Return a random integer in the range [0 .. N), uniformly
     *  distributed.  Requires N > 0. */
    int randInt(int n) {
        return _random.nextInt(n);
    }

    /** Return a random double in the range [0..1], uniformly distributed.*/
    double random() {
        return _random.nextDouble();
    }

    /** Send a message to the user as determined by FORMAT and ARGS, which
     *  are interpreted as for String.format or PrintWriter.printf. */
    void message(String format, Object... args) {
        _out.printf(format, args);
        _out.println();
        _out.flush();
    }

    /** Check whether we are playing and there is an unannounced winner.
     *  If so, announce and stop play. */
    private void checkForWin() {
        if (_board.getWinner() != null) {
            _playing = false;
            _board.resetNextPlayer();
            announceWinner();
        }
    }

    /** Send announcement of winner to my user output. */
    private void announceWinner() {
        switch (_board.getWinner()) {
        case BLUE:
            message("Blue wins.");
            break;
        case RED:
            message("Red wins.");
            break;
        default:
            break;
        }
    }

    /** Make PLAYER an AI for subsequent moves. */
    private void setAuto(Color player) {
        _playing = false;
        switch (player) {
        case RED:
            _red = new AI(this, Color.RED);
            break;
        case BLUE:
            _blue = new AI(this, Color.BLUE);
            break;
        default:
            break;
        }
    }

    /** Make PLAYER take manual input from the user for subsequent moves. */
    private void setManual(Color player) {
        _playing = false;
        switch (player) {
        case RED:
            _red = new HumanPlayer(this, Color.RED);
            break;
        case BLUE:
            _blue = new HumanPlayer(this, Color.BLUE);
            break;
        default:
            break;
        }
    }

    /** Stop any current game and clear the board to its initial
     *  state. */
    private void clear() {
        _playing = false;
        _board.clear(_board.size());
        _board.resetNextPlayer();
    }

    /** Print the current board using standard board-dump format. */
    private void dump() {
        message(_board.toDisplayString());
    }

    /** Print a help message. */
    private void help() {
        Main.printHelpResource(HELP, _out);
    }

    /** Stop any current game and set the move number to N. */
    private void setMoveNumber(int n) {
        _playing = false;

        if (n < 1) {
            throw error("Move number needs to be >= 1");
        }

        _board.setMoves(n);
    }

    /** Seed the random-number generator with SEED. */
    private void setSeed(long seed) {
        _random.setSeed(seed);
    }

    /** Place SPOTS spots on square R:C and color the square red or
     *  blue depending on whether COLOR is "r" or "b".  If SPOTS is
     *  0, clears the square, ignoring COLOR.  SPOTS must be less than
     *  the number of neighbors of square R, C. */
    private void setSpots(int r, int c, int spots, String color) {
        Color co;

        try {
            if (spots <= 0) {
                co = Color.WHITE;
            } else {
                co = Color.parseColor(color);
            }
        } catch (IllegalArgumentException e) {
            throw error("Failed to parse %s into color", color);
        }

        _board.set(r, c, spots, co);
    }

    /** Stop any current game and set the board to an empty N x N board
     *  with numMoves() == 0.  */
    private void setSize(int n) {
        if (n <= 1) {
            throw error("Board size needs to be > 1");
        }

        _playing = false;
        _board.clear(n);
    }

    /** Begin accepting moves for game.  If the game is won,
     *  immediately print a win message and end the game. */
    private void restartGame() {
        _playing = true;

        checkForWin();

        while (_playing && !_quit) {
            checkForWin();

            try {
                switch (_board.whoseMove()) {
                case RED:
                    _red.makeMove();

                    if (_red instanceof AI) {
                        message("Red moves %d %d.", _previousMove[0],
                                _previousMove[1]);
                    }

                    checkForWin();

                    if (!_playing) {
                        break;
                    }

                    break;
                case BLUE:
                    _blue.makeMove();

                    if (_blue instanceof AI) {
                        message("Blue moves %d %d.", _previousMove[0],
                                _previousMove[1]);
                    }

                    checkForWin();

                    if (!_playing) {
                        break;
                    }

                    break;
                default:
                    break;
                }
            } catch (GameException e) {
                reportError(e.getMessage());
            } catch (IllegalArgumentException e) {
                reportError(e.getMessage());
            } catch (InputMismatchException e) {
                reportError(e.getMessage());
            }
        }
    }


    /** Save move R C in _move.  Error if R and C do not indicate an
     *  existing square on the current board. */
    private void saveMove(int r, int c) {
        if (!_board.exists(r, c)) {
            throw error("move %d %d out of bounds", r, c);
        }
        _move[0] = r;
        _move[1] = c;
    }

    /** Returns a color (player) name from READER: either RED or BLUE.
     *  Throws an exception if not present. */
    private Color readColor() {
        try {
            String colorName = _cmdScanner.next("[rR][eE][dD]|[Bb][Ll][Uu]"
                                                + "[Ee]");
            return Color.parseColor(colorName);
        } catch (NoSuchElementException e) {
            throw error("Cannot parse color");
        }
    }

    /** Read and execute one command.  Leave the input at the start of
     *  a line, if there is more input. */
    private void readExecuteCommand() {
        String command;

        try {
            _cmdScanner = getScanner(_cmd);
            command = _cmdScanner.next();
        } catch (NoSuchElementException e) {
            return;
        }

        executeCommand(command);
    }

    /** Gather arguments and execute command CMND.  Throws GameException
     *  on errors.*/
    private void executeCommand(String cmnd) {
        switch (cmnd) {
        case "\n": case "\r\n":
            return;
        case "#":
            break;
        case "clear":
            clear();
            break;
        case "start":
            restartGame();
            break;
        case "quit":
            _quit = true;
            break;
        case "auto":
            setAuto(readColor());
            break;
        case "manual":
            setManual(readColor());
            break;
        case "size":
            if (!_cmdScanner.hasNextInt()) {
                throw error("size needs an integer as argument");
            }
            setSize(_cmdScanner.nextInt());
            break;
        case "move":
            if (!_cmdScanner.hasNextInt()) {
                throw error("move needs an integer as argument");
            }
            setMoveNumber(_cmdScanner.nextInt());
            break;
        case "set":
            _playing = false;
            readExecuteSet();
            break;
        case "dump":
            dump();
            break;
        case "autodump":
            _autodump = !_autodump;
            break;
        case "seed":
            if (!_cmdScanner.hasNextInt()) {
                throw error("seed needs an integer as argument");
            }
            setSeed(_cmdScanner.nextInt());
            break;
        case "random":
            seedBoard();
            break;
        case "help":
            help();
            break;
        default:
            readOrThrowMove(cmnd);
        }
    }

    /** Try to parse a move from CMND, if failed, throw an error.*/
    private void readOrThrowMove(String cmnd) {
        if (_playing) {
            attemptReadMove(cmnd);
        } else {
            throw error("bad command: '%s'", cmnd);
        }
    }

    /** Randomly seed the board.*/
    private void seedBoard() {
        for (int i = 0; i < _board.size() * _board.size(); i += 1) {
            if (random() < RED_SAMPLING) {
                _board.set(i, 1, Color.RED);
            } else if (random() < RED_SAMPLING + BLUE_SAMPLING) {
                _board.set(i, 1, Color.BLUE);
            } else {
                _board.set(i, 0, Color.WHITE);
            }
        }
    }

    /** Try to parse a move of the form R C given an initial ROWSTR
     *  Throws an IllegalArgumentException if R is an integer and C is not.
     *  Throws a GameException if the move is not legal or game is not
     *      playing or R C is out of range or if N is negative.*/
    private void attemptReadMove(String rowStr) {
        int r, c;

        try {
            r = Integer.parseInt(rowStr);
        } catch (NumberFormatException e) {
            throw error("R C both need to be integers for a move");
        }

        if (!_cmdScanner.hasNextInt()) {
            throw new IllegalArgumentException("R C both need to be integers "
                                               + "for a move");
        }

        c = _cmdScanner.nextInt();

        if (!_playing) {
            throw error("Cannot move, game is not playing");
        }

        if (!_board.isLegal(_board.whoseMove(), r, c)) {
            throw error("Invalid move, please enter a valid move");
        }

        verifyRowColumn(r, c);
        saveMove(r, c);
    }

    /** Read and execute a set command.
     *  Throws a GameException if the move is not legal or game is not
     *      playing or R C is out of range or if N is negative.*/
    private void readExecuteSet() {
        int r, c, n;

        if (!_cmdScanner.hasNextInt()) {
            throw error("set needs R C N to be integers");
        }

        r = _cmdScanner.nextInt();

        if (!_cmdScanner.hasNextInt()) {
            throw error("set needs R C N to be integers");
        }

        c = _cmdScanner.nextInt();

        if (!_cmdScanner.hasNextInt()) {
            throw error("set needs R C N to be integers");
        }

        n = _cmdScanner.nextInt();

        verifyRowColumn(r, c);

        if (n < 0) {
            throw error("set needs N to be >= 0");
        }

        if (n == 0) {
            setSpots(r, c, n, "");
        } else {
            try {
                String str = _cmdScanner.next();
                setSpots(r, c, n, str);
            } catch (NoSuchElementException e) {
                throw error("Failed to read color");
            }
        }
    }

    /** Verifies row R and column C is valid.
     *  Throws error if they are not. */
    private void verifyRowColumn(int r, int c) {
        if (!_board.exists(r, c)) {
            throw error("R C both need to be within 1 and " + _board.size());
        }
    }

    /** Print a prompt and wait for input. Returns true iff there is another
     *  token. */
    private boolean promptForNext() {
        String prompt = "> ";

        if (_playing) {
            prompt = _board.whoseMove() + prompt;
        }

        _prompter.print(prompt);
        _prompter.flush();

        try {
            _cmd = _inp.nextLine();
        } catch (NoSuchElementException e) {
            _quit = true;
            return false;
        }

        return !_cmd.equals("");
    }

    /** Returns a delimited Scanner from STR.*/
    private Scanner getScanner(String str) {
        Reader reader = new StringReader(str);
        Scanner scanner = new Scanner(str);
        scanner.useDelimiter(DELIMITER_PATTERN);
        return scanner;
    }

    /** Send an error message to the user formed from arguments FORMAT
     *  and ARGS, whose meanings are as for printf. */
    void reportError(String format, Object... args) {
        _err.print("Error: ");
        _err.printf(format, args);
        _err.println();
    }
}
