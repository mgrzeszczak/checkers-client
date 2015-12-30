package pl.mg.checkers.message.msgs;

/**
 * Created by maciej on 30.12.15.
 */
public class GameTurnMessage {

    private boolean playerTurn;

    public GameTurnMessage(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public GameTurnMessage() {

    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }
}
