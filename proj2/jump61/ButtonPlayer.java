package jump61;

import javax.swing.JButton;

public class ButtonPlayer extends JButton {

    private Player _currentPlayer;

    private Player _playerWhenPressed;

    public ButtonPlayer(String label, Player currentPlayer,
                        Player playerWhenPressed) {
        super(label);
        _currentPlayer = currentPlayer;
        _playerWhenPressed = playerWhenPressed;
    }

    public Player getPressedPlayer() {
        return _playerWhenPressed;
    }

    public Player getCurrentPlayer() {
        return _currentPlayer;
    }
}
