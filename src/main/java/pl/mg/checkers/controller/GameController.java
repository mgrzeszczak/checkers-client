package pl.mg.checkers.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.mg.checkers.game.Game;
import pl.mg.checkers.game.Grid;
import pl.mg.checkers.message.MsgType;
import pl.mg.checkers.message.TypedMessage;
import pl.mg.checkers.message.msgs.GamePawnMoveMessage;
import pl.mg.checkers.message.msgs.StartGameMessage;
import pl.mg.checkers.service.GameLogicService;
import pl.mg.checkers.service.MessengerService;
import pl.mg.checkers.service.SceneService;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by maciej on 29.12.15.
 */
@Component
@Scope("singleton")
public class GameController implements Initializable {

    @FXML
    private Canvas canvas;
    @FXML
    private Button leaveButton;
    @FXML
    private AnchorPane anchorPane;
    @Autowired
    private MessengerService messengerService;
    @Autowired
    private GameLogicService gameLogicService;
    @Autowired
    private SceneService sceneService;


    private double height;
    private double width;
    private double xOffset;
    private double yOffset;
    private double fieldSize;

    private GraphicsContext graphics;

    private Game game;

    private void drawGrid(){
        graphics.setFill(Color.BLACK);
        graphics.fillRect(0,0,width,height);

        int[][] grid = game.getGrid().getGrid();

        for (int i=0;i<Grid.SIZE;i++){
            for (int j=0;j<Grid.SIZE;j++){
                graphics.setFill((i+j)%2==1 ? Color.rgb(139,69,19) : Color.WHITE);
                graphics.fillRect(xOffset+j*fieldSize,yOffset+i*fieldSize,fieldSize,fieldSize);
            }
        }
    }

    private void drawPawns(int[][] grid){
        System.out.println("DRAWING PAWNS");
        for (int i=0;i<Grid.SIZE;i++){
            for (int j=0;j<Grid.SIZE;j++){

                if (grid[j][i]==0) {
                    System.out.print("0 ");
                    continue;
                }
                else{
                    System.out.print(grid[j][i]+" ");
                }

                graphics.setFill(grid[j][i]==1 ? Color.WHITE:Color.BLACK);
                graphics.fillOval(xOffset+(j+0.25)*fieldSize,yOffset+(i+0.25)*fieldSize,fieldSize/2,fieldSize/2);
            }
            System.out.println();
        }
    }

    public void draw(int[][] grid){
        drawGrid();
        drawPawns(grid);
    }

    public void drawWithMovablePawns(int[][] grid, Map<Integer,List<Integer>> moves){
        drawGrid();
        //sceneService.showAlert(Alert.AlertType.INFORMATION,"","","");
        drawMovablePawns(moves);
        //sceneService.showAlert(Alert.AlertType.INFORMATION,"","","");
        drawPawns(grid);
    }

    public void drawWithAvailableMovesForPawn(int index, int[][] grid, List<Integer> moves){
        drawGrid();
        drawAvailableMovesForPawn(index,moves);
        drawPawns(grid);
    }

    private void drawMovablePawns(Map<Integer,List<Integer>> moves){
        moves.keySet().forEach(ind->{
            int[] coord = gameLogicService.pawnCoords(ind);
            int j = coord[0];
            int i = coord[1];
            graphics.setFill(Color.YELLOW);
            graphics.fillRect(xOffset+j*fieldSize,yOffset+i*fieldSize,fieldSize,fieldSize);
        });
    }

    private void drawAvailableMovesForPawn(int index, List<Integer> moves){
        int[] coord = gameLogicService.pawnCoords(index);
        int j = coord[0];
        int i = coord[1];
        graphics.setFill(Color.ORANGE);
        graphics.fillRect(xOffset+j*fieldSize,yOffset+i*fieldSize,fieldSize,fieldSize);
        moves.forEach(ind->{
            int[] c = gameLogicService.pawnCoords(ind);
            int a = c[0];
            int b = c[1];
            graphics.setFill(Color.YELLOW);
            graphics.fillRect(xOffset+a*fieldSize,yOffset+b*fieldSize,fieldSize,fieldSize);
        });
    }

    public void draw(Map<Integer,List<Integer>> moves){
        graphics.setFill(Color.BLACK);
        graphics.fillRect(0,0,width,height);

        int[][] grid = game.getGrid().getGrid();

        for (int i=0;i<Grid.SIZE;i++){
            for (int j=0;j<Grid.SIZE;j++){
                graphics.setFill((i+j)%2==1 ? Color.rgb(139,69,19) : Color.WHITE);
                graphics.fillRect(xOffset+j*fieldSize,yOffset+i*fieldSize,fieldSize,fieldSize);
            }
        }

        for (int i=0;i<Grid.SIZE;i++){
            for (int j=0;j<Grid.SIZE;j++){
                if (grid[j][i]==0) continue;

                if (moves.containsKey(gameLogicService.pawnIndex(j,i))){
                    graphics.setFill(Color.YELLOW);
                    graphics.fillRect(xOffset+j*fieldSize,yOffset+i*fieldSize,fieldSize,fieldSize);
                }

                graphics.setFill(grid[j][i]==1 ? Color.WHITE:Color.BLACK);
                //if (game.getColor()==2)
                    graphics.fillOval(xOffset+(j+0.25)*fieldSize,yOffset+(i+0.25)*fieldSize,fieldSize/2,fieldSize/2);
                //else
                //    graphics.fillOval(xOffset+(Grid.SIZE-(j+0.75))*fieldSize,yOffset+(Grid.SIZE-(i+0.75))*fieldSize,
                //        fieldSize/2,
                //            fieldSize/2);
            }
        }
    }

    public void init(Game game) {
        canvas.setHeight(anchorPane.getHeight());
        canvas.setWidth(anchorPane.getWidth());
        this.width = canvas.getWidth();
        this.height = canvas.getHeight();
        this.game = game;

        this.fieldSize = 0.8*height/Grid.SIZE;
        this.xOffset = 0.5*(width-Grid.SIZE*fieldSize);
        this.yOffset = 0.5*(height-Grid.SIZE*fieldSize);

        System.out.println(game.toString());

        draw(game.getGrid().getGrid());
        messengerService.send(new TypedMessage(MsgType.gridUpdateAck,null));
        /*Map<Integer, List<Integer>> moves = gameLogicService.calculateMoves(game.getGrid().getGrid(),game.getColor());

        moves.forEach((i,l)->{
            int[] coord = gameLogicService.pawnCoords(i);
            System.out.println("Moves for ("+coord[0]+","+coord[1]+") :");
            l.forEach(j->{
                int[] c = gameLogicService.pawnCoords(j);
                System.out.println("("+c[0]+","+c[1]+")");
            });
            System.out.println();
        });
        game.setMoves(moves);*/

        //drawWithMovablePawns(game.getGrid().getGrid(),game.getMoves());
        //canvas.setOnMouseClicked(this::onMouseClicked);
    }

    public void onLeaveButtonClick(){
        messengerService.send(new TypedMessage(MsgType.leaveGame,null));
    }

    private void toggleMouseClick(boolean b){
        canvas.setOnMouseClicked(!b? null : this::onMouseClicked);
    }

    private synchronized void onMouseClicked(MouseEvent event){
        if (event.getEventType() != MouseEvent.MOUSE_CLICKED) return;
        double mx = event.getX();
        double my = event.getY();
        int x = (int)((mx-xOffset)/ fieldSize);
        int y = (int)((my-yOffset)/fieldSize);
        if (mx<xOffset || my< yOffset || x>=Grid.SIZE || y>= Grid.SIZE){
            game.deselect();
            drawWithMovablePawns(game.getGrid().getGrid(),game.getMoves());
            return;
        }
        System.out.println("x: "+x+" y: "+y);
        int index = gameLogicService.pawnIndex(x,y);
        if (game.isSelected()){
            if (!game.getMoves().get(game.getSelectedIndex()).contains(index)){
                game.deselect();
                drawWithMovablePawns(game.getGrid().getGrid(),game.getMoves());
            }
            else {
                game.deselect();
                toggleMouseClick(false);
                messengerService.send(new TypedMessage(MsgType.gamePawnMove,new GamePawnMoveMessage(game
                        .getSelectedIndex(),index)));
            }
            return;
        }

        if (game.getMoves().containsKey(index)){
            game.select(index);
            drawWithAvailableMovesForPawn(index,game.getGrid().getGrid(),game.getMoves().get(index));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.graphics = canvas.getGraphicsContext2D();
    }

    public synchronized void toggleTurn(boolean b){
        toggleMouseClick(b);
        if (b) {
            game.setMoves(gameLogicService.calculateMoves(game.getGrid().getGrid(), game.getColor()));
            if (b) drawWithMovablePawns(game.getGrid().getGrid(), game.getMoves());
            sceneService.showAlert(Alert.AlertType.INFORMATION,"Turn","Your turn","");
        }
        else {
            draw(game.getGrid().getGrid());
        }
    }

    public void updateGrid(int[][] grid){
        game.getGrid().setGrid(grid);
        draw(grid);
        messengerService.send(new TypedMessage(MsgType.gridUpdateAck,null));
    }
}
