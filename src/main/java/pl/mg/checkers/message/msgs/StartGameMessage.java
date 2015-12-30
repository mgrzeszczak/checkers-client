package pl.mg.checkers.message.msgs;

import pl.mg.checkers.representation.GameRepresentation;

/**
 * Created by maciej on 29.12.15.
 */
public class StartGameMessage {

    private int color;
    private GameRepresentation gameRepresentation;

    public StartGameMessage() {
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public GameRepresentation getGameRepresentation() {
        return gameRepresentation;
    }

    public void setGameRepresentation(GameRepresentation gameRepresentation) {
        this.gameRepresentation = gameRepresentation;
    }
}
