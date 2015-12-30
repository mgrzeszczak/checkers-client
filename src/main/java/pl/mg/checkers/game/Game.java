package pl.mg.checkers.game;

import pl.mg.checkers.message.msgs.StartGameMessage;
import pl.mg.checkers.representation.ClientRepresentation;

import java.util.List;
import java.util.Map;

/**
 * Created by maciej on 29.12.15.
 */
public class Game {

    private ClientRepresentation opponent;
    private Grid grid;
    private int color;
    private Map<Integer,List<Integer>> moves;
    private boolean selected = false;
    private Integer selectedIndex;

    public Game(StartGameMessage message) {
        this.grid = message.getGameRepresentation().getGrid();
        this.opponent = message.getGameRepresentation().getOpponent();
        this.color = message.getColor();
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Game{" +
                "opponent=" + opponent +
                ", grid=" + grid +
                ", color=" + color +
                '}';
    }

    public ClientRepresentation getOpponent() {
        return opponent;
    }

    public void setOpponent(ClientRepresentation opponent) {
        this.opponent = opponent;
    }

    public Map<Integer, List<Integer>> getMoves() {
        return moves;
    }

    public void setMoves(Map<Integer, List<Integer>> moves) {
        this.moves = moves;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Integer getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(Integer selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public void select(int index){
        this.selected = true;
        this.selectedIndex = index;
    }

    public void deselect(){
        this.selected = false;
    }
}
