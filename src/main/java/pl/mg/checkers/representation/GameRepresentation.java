package pl.mg.checkers.representation;


import pl.mg.checkers.game.Grid;

/**
 * Created by maciej on 29.12.15.
 */
public class GameRepresentation {

    private Grid grid;
    private ClientRepresentation opponent;

    public GameRepresentation() {

    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public ClientRepresentation getOpponent() {
        return opponent;
    }

    public void setOpponent(ClientRepresentation opponent) {
        this.opponent = opponent;
    }
}
