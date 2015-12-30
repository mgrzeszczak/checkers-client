package pl.mg.checkers.service;

import org.springframework.stereotype.Service;
import pl.mg.checkers.game.Grid;

import java.util.*;

/**
 * Created by maciej on 30.12.15.
 */
@Service
public class GameLogicService {

    public Map<Integer,List<Integer>> calculateMoves(int[][] grid, int color){
        System.out.println("My color: "+color);
        List<Integer> pawns = new ArrayList<>();
        List<Integer> capturePawns = new ArrayList<>();
        for (int i = 0; i< Grid.SIZE; i++){
            for (int j=0;j<Grid.SIZE;j++){
                if (grid[j][i]!=color) continue;
                int ind = pawnIndex(j,i);
                pawns.add(ind);
                if (canCapture(grid,ind,color)) capturePawns.add(ind);
            }
        }
        if (capturePawns.isEmpty()){
            System.out.println("Capture pawns empty!!");
            System.out.println("MovePawns size: "+pawns.size());
            return calculateMoves(grid,color,pawns);
        } else {
            return calculateCaptureMoves(grid,color,capturePawns);
        }
    }

    private Map<Integer,List<Integer>> calculateCaptureMoves(int[][] grid, int color, List<Integer> capturePawns){
        int otherColor = (color%2)+1;
        Map<Integer,List<Integer>> moveMap = new HashMap<>();
        Map<Integer,Integer> pawnCaptureLength = new HashMap<>();
        for (int i : capturePawns) {
            List<Integer> moves = new ArrayList<>();
            int[] coords = pawnCoords(i);
            int length = calculateMaxCaptureLength(coords[0],coords[1],color,otherColor,grid,moves,true);
            moveMap.put(i,moves);
            pawnCaptureLength.put(i,length);
        }
        int max = 0;
        for (int i : pawnCaptureLength.values()){
            if (i>max) max = i;
        }
        final int m = max;
        pawnCaptureLength.forEach((ind,len)->{
            if (len<m && moveMap.containsKey(ind)) moveMap.remove(ind);
        });
        return moveMap;
    }

    private Integer calculateMaxCaptureLength(int x, int y, int color, int otherColor,
                                              int[][] grid, List<Integer> moves, boolean store){
        int length = 0;
        int maxLength = 0;
        int[][] gridCopy = new int[Grid.SIZE][Grid.SIZE];

        if (x-2>=0 && y-2>=0){
            if (grid[x-1][y-1]==otherColor && grid[x-2][y-2]==0) {
                copyArr(grid,gridCopy);
                //gridCopy = Arrays.copyOf(grid,grid.length);
                gridCopy[x-1][y-1] = 0;
                length = 1+calculateMaxCaptureLength(x-2,y-2,color,otherColor,gridCopy,moves,false);
                if (length>=maxLength){
                    if (store){
                        if (length>maxLength){
                            maxLength = length;
                            moves.clear();
                        }
                        moves.add(pawnIndex(x-2,y-2));
                    } else {
                        maxLength = length;
                    }
                }
            }
        }
        if (x+2<Grid.SIZE && y-2>=0){
            if (grid[x+1][y-1]==otherColor && grid[x+2][y-2]==0) {
                copyArr(grid,gridCopy);
                //gridCopy = Arrays.copyOf(grid,grid.length);
                gridCopy[x+1][y-1] = 0;
                length = 1+calculateMaxCaptureLength(x+2,y-2,color,otherColor,gridCopy,moves,false);
                if (length>=maxLength){
                    if (store){
                        if (length>maxLength){
                            maxLength = length;
                            moves.clear();
                        }
                        moves.add(pawnIndex(x+2,y-2));
                    } else {
                        maxLength = length;
                    }
                }
            }
        }
        if (x-2>=0 && y+2<Grid.SIZE){
            if (grid[x-1][y+1]==otherColor && grid[x-2][y+2]==0) {
                copyArr(grid,gridCopy);
                //gridCopy = copyArr(grid,grid.length);
                gridCopy[x-1][y+1] = 0;
                length = 1+calculateMaxCaptureLength(x-2,y+2,color,otherColor,gridCopy,moves,false);
                if (length>=maxLength){
                    if (store){
                        if (length>maxLength){
                            maxLength = length;
                            moves.clear();
                        }
                        moves.add(pawnIndex(x-2,y+2));
                    } else {
                        maxLength = length;
                    }
                }
            }
        }
        if (x+2<Grid.SIZE && y+2<Grid.SIZE){
            if (grid[x+1][y+1]==otherColor && grid[x+2][y+2]==0) {
                copyArr(grid,gridCopy);
                //gridCopy = Arrays.copyOf(grid,grid.length);
                gridCopy[x+1][y+1] = 0;
                length = 1+calculateMaxCaptureLength(x+2,y+2,color,otherColor,gridCopy,moves,false);
                if (length>=maxLength){
                    if (store){
                        if (length>maxLength){
                            maxLength = length;
                            moves.clear();
                        }
                        moves.add(pawnIndex(x+2,y+2));
                    } else {
                        maxLength = length;
                    }
                }
            }
        }
        return maxLength;
    }

    private Map<Integer,List<Integer>> calculateMoves(int[][] grid, int color, List<Integer> pawns){
        int otherColor = (color%2)+1;
        Map<Integer,List<Integer>> moveMap = new HashMap<>();
        for (Integer i : pawns){
            int[] coords = pawnCoords(i);
            int x = coords[0];
            int y = coords[1];
            List<Integer> moves = new ArrayList<>();

            if (color==2) {
                if (x - 1 >= 0 & y - 1 >= 0) {
                    if (grid[x - 1][y - 1] == 0) moves.add(pawnIndex(x - 1, y - 1));
                }
                if (x + 1 < Grid.SIZE & y - 1 >= 0) {
                    if (grid[x + 1][y - 1] == 0) moves.add(pawnIndex(x + 1, y - 1));
                }
            }

            if (color == 1) {
                if (x - 1 >= 0 & y + 1 < Grid.SIZE) {
                    if (grid[x - 1][y + 1] == 0) moves.add(pawnIndex(x - 1, y + 1));
                }
                if (x + 1 < Grid.SIZE & y + 1 < Grid.SIZE) {
                    if (grid[x + 1][y + 1] == 0) moves.add(pawnIndex(x + 1, y + 1));
                }
            }
            if (!moves.isEmpty()) moveMap.put(i,moves);
        }
        return moveMap;
    }

    public boolean canCapture(int[][] grid, int pawn, int color){
        int[] coords = pawnCoords(pawn);
        int x = coords[0];
        int y = coords[1];

        int otherColor = (color%2)+1;
        if (x-2>=0 && y-2>=0){
            if (grid[x-1][y-1]==otherColor && grid[x-2][y-2]==0) return true;
        }
        if (x+2<Grid.SIZE && y-2>=0){
            if (grid[x+1][y-1]==otherColor && grid[x+2][y-2]==0) return true;
        }
        if (x-2>=0 && y+2<Grid.SIZE){
            if (grid[x-1][y+1]==otherColor && grid[x-2][y+2]==0) return true;
        }
        if (x+2<Grid.SIZE && y+2<Grid.SIZE){
            if (grid[x+1][y+1]==otherColor && grid[x+2][y+2]==0) return true;
        }
        return false;
    }

    public int[] pawnCoords(int index){
        return new int[]{index/Grid.SIZE,index%Grid.SIZE};
    }

    public Integer pawnIndex(int x, int y){
        return x*Grid.SIZE+y;
    }



    public List<int[]> getAvailableMoves(int[][] grid, int x, int y, int color){
        if (grid[x][y]!=color) return Collections.emptyList();
        List<int[]> out = new ArrayList<>();

        return null;
    }

    private void copyArr(int[][] from, int[][] to){
        for (int i=0;i<Grid.SIZE;i++){
            for (int j=0;j<Grid.SIZE;j++){
                to[j][i] = from[j][i];
            }
        }
    }
}
