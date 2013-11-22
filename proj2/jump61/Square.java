package jump61;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.SwingUtilities;


/** Custom Square JPanel class.
 *  @author KietLam. */
public class Square extends JPanel {

    /** The number of spots for this square.*/
    private int _spots;

    /** The color of this square.*/
    private Color _color;

    /** The row on the board for this square.*/
    private final int _row;

    /** The column on the board for this square.*/
    private final int _col;

    /** Construct a square from ROW, COL, SPOTS and COLOR.*/
    public Square(int row, int col, int spots, Color color) {
        _spots = spots;
        _row = row;
        _col = col;
        _color = color;
        setBorder(BorderFactory.createLineBorder(java.awt.Color.black));
        updateUI();
    }

    /** Update this square to have SPOTS spots and to be COLOR color.*/
    public void update(int spots, Color color) {
        _spots = spots;
        _color = color;
        updateInterface();
    }

    /** Returns the number of spots.*/
    public int getSpots() {
        return _spots;
    }

    /** Returns the row.*/
    public int getRow() {
        return _row;
    }

    /** Returns the column.*/
    public int getColumn() {
        return _col;
    }

    /** Update the user interface of this square.*/
    private void updateInterface() {
        switch (_color) {
        case RED:
            setBackground(java.awt.Color.RED);
            break;
        case BLUE:
            setBackground(java.awt.Color.BLUE);
            break;
        case WHITE:
            setBackground(java.awt.Color.WHITE);
            break;
        default:
            break;
        }

        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    repaint();
                }
            });
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(java.awt.Color.BLACK);

        int radius = Math.min(getWidth() / 5, getHeight() / 5) / 2;

        if (_spots == 1) {
            draw(g, getWidth() / 2, getHeight() / 2, radius);
        } else if (_spots == 2) {
            draw(g, getWidth() / 4, getHeight() / 4, radius);
            draw(g, getWidth() / 2 + getWidth() / 4,
                 getHeight() / 2 + getHeight() / 4, radius);
        } else if (_spots == 3) {
            draw(g, getWidth() / 4, getHeight() / 4, radius);
            draw(g, getWidth() / 2, getHeight() / 2, radius);
            draw(g, getWidth() / 2 + getWidth() / 4,
                 getHeight() / 2 + getHeight() / 4, radius);
        } else if (_spots >= 4) {
            draw(g, getWidth() / 4, getHeight() / 4, radius);
            draw(g, getWidth() / 4,
                 getHeight() / 2 + getHeight() / 4, radius);
            draw(g, getWidth() / 2 + getWidth() / 4,
                 getHeight() / 4, radius);
            draw(g, getWidth() / 2 + getWidth() / 4,
                 getHeight() / 2 + getHeight() / 4, radius);
        }
    }

    /** Draw and fill a circle using G with center at X, Y with RADIUS.*/
    private void draw(Graphics2D g, int x, int y, int radius) {
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }
}
