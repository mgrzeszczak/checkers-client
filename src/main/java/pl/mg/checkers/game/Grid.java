package pl.mg.checkers.game;

/**
 * Created by maciej on 29.12.15.
 */
public class Grid {

    public static final int SIZE = 8;
    private int[][] grid;

    public Grid() {

    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public void print(){
        for (int i=0;i<SIZE;i++){
            for (int j=0;j<SIZE;j++){
                System.out.print(grid[j][i]+"  ");
            }
            System.out.println();
        }
    }
}
