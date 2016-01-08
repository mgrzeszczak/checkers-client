package pl.mg.checkers.message.msgs;

/**
 * Created by maciej on 02.01.16.
 */
public class GameContinueTurnMessage {

    private int pawnIndex;
    private boolean playerTurn;

    public GameContinueTurnMessage(int pawnIndex, boolean playerTurn) {
        this.pawnIndex = pawnIndex;
        this.playerTurn = playerTurn;
    }

    public GameContinueTurnMessage() {
    }

    public int getPawnIndex() {
        return pawnIndex;
    }

    public void setPawnIndex(int pawnIndex) {
        this.pawnIndex = pawnIndex;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }
}
