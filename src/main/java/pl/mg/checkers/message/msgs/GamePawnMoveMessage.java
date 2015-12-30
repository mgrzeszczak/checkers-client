package pl.mg.checkers.message.msgs;

/**
 * Created by maciej on 30.12.15.
 */
public class GamePawnMoveMessage {

    private int[][] grid;
    private int pawnIndex;
    private int destinationIndex;

    public GamePawnMoveMessage(int[][] grid) {
        this.grid = grid;
    }

    public GamePawnMoveMessage(int pawnIndex, int destinationIndex) {
        this.pawnIndex = pawnIndex;
        this.destinationIndex = destinationIndex;
    }

    public GamePawnMoveMessage() {
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public int getPawnIndex() {
        return pawnIndex;
    }

    public void setPawnIndex(int pawnIndex) {
        this.pawnIndex = pawnIndex;
    }

    public int getDestinationIndex() {
        return destinationIndex;
    }

    public void setDestinationIndex(int destinationIndex) {
        this.destinationIndex = destinationIndex;
    }
}
