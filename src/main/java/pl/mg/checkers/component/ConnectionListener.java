package pl.mg.checkers.component;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.mg.checkers.controller.GameController;
import pl.mg.checkers.controller.InLobbyController;
import pl.mg.checkers.controller.LobbyCell;
import pl.mg.checkers.controller.LobbyController;
import pl.mg.checkers.game.Game;
import pl.mg.checkers.message.MsgType;
import pl.mg.checkers.message.TypedMessage;
import pl.mg.checkers.message.msgs.*;
import pl.mg.checkers.service.ConnectionService;
import pl.mg.checkers.service.JsonService;
import pl.mg.checkers.service.MessengerService;
import pl.mg.checkers.service.SceneService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by maciej on 25.12.15.
 */
@Component
@Scope("singleton")
public class ConnectionListener implements Runnable{

    private final int BUFFER_SIZE = 1024;
    private Logger logger = LogManager.getLogger(ConnectionListener.class);

    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private JsonService jsonService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private LobbyController lobbyController;
    @Autowired
    private MessengerService messengerService;
    @Autowired
    private GameController gameController;

    private InputStream is;

    public void setUp(InputStream is){
        this.is = is;
    }

    @Override
    public void run() {
        try {
            listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionService.disconnect();
    }

    private void listen() throws IOException, NumberFormatException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[BUFFER_SIZE];
        int b;
        while ((b = is.read())!=-1){
            sb.append(Character.toChars(b));
            if (!sb.toString().contains("\r\n\r\n")) continue;
            int length = Integer.valueOf(sb.toString().substring(0,sb.toString().indexOf("\r\n\r\n")));
            while (length>0){
                int read = is.read(buffer,0,BUFFER_SIZE);
                length-=read;
                baos.write(buffer,0,read);
            }
            String message = baos.toString("utf-8");
            System.out.println("RAW RECEIVED MSG: "+ message);

            jsonService.parseNode(message).ifPresent(this::handleMessage);
            baos.reset();
            sb.setLength(0);
        }
    }

    private void handleMessage(JsonNode node){
        String type = node.get("type").asText();
        String content = node.get("content").toString();

        if (!MsgType.isValidType(type)) return;

        logger.debug("RECEIVED: "+node.toString());

        switch (MsgType.valueOf(type)){
            case init:
                jsonService.parseObject(content,InitMessage.class).ifPresent(m->{
                    Platform.runLater(()->{
                        sceneService.changeScene(sceneService.loadScene("fxml/lobby.fxml"));
                        LobbyController controller = context.getBean(LobbyController.class);
                        controller.updateItems(m.getLobbies().values());
                    });
                });
                break;
            case lobbyUpdate:
                jsonService.parseObject(content, LobbyUpdateMessage.class).ifPresent(m->{
                    Platform.runLater(()->lobbyController.updateItems(m.getLobbies().values()));
                });
                break;
            case leaveGame:
                Platform.runLater(()->{
                    sceneService.changeScene(sceneService.loadScene("fxml/lobby.fxml"));
                    messengerService.send(new TypedMessage(MsgType.lobbyUpdate,null));
                });
                break;
            case createLobby:
                jsonService.parseObject(content, CreateLobbyMessage.class).ifPresent(m->{
                    Platform.runLater(()->{
                        sceneService.changeScene(sceneService.loadScene("fxml/inLobby.fxml"));
                        InLobbyController controller = context.getBean(InLobbyController.class);
                        controller.setLobbyRepresentation(m.getLobbyRepresentation());
                    });
                });
                break;
            case leaveLobby:
                Platform.runLater(()->{
                        sceneService.changeScene(sceneService.loadScene("fxml/lobby.fxml"));
                        messengerService.send(new TypedMessage(MsgType.lobbyUpdate,null));
                });
                break;
            case joinLobby:
                jsonService.parseObject(content,JoinLobbyMessage.class).ifPresent(m->{
                    if (!m.isSuccess()) Platform.runLater(()->{
                        sceneService.showAlert(Alert.AlertType.ERROR,"Error","Cannot join lobby.","Lobby is no longer" +
                                " available.");
                    });
                });
                break;
            case gameStarted:
                jsonService.parseObject(content, StartGameMessage.class).ifPresent(m->{
                    Platform.runLater(()->{
                        sceneService.changeScene(sceneService.loadScene("fxml/game.fxml"));
                        GameController gameController = context.getBean(GameController.class);
                        gameController.init(new Game(m));
                    });
                });
                break;
            case gameEnded:
                jsonService.parseObject(content,EndGameMessage.class).ifPresent(m->{
                    Platform.runLater(()->{
                        sceneService.showAlert(Alert.AlertType.INFORMATION,"Game ended",
                                m.isWon()?"You won!":"You lost!",m.isLeft()?"Your opponent left." : m.isWon()?
                                        "Congratulations!" : "Next time is the charm!");
                        sceneService.changeScene(sceneService.loadScene("fxml/lobby.fxml"));
                        messengerService.send(new TypedMessage(MsgType.lobbyUpdate,null));
                    });
                });
                break;
            case gamePawnMove:
                jsonService.parseObject(content,GamePawnMoveMessage.class).ifPresent(m->{
                    Platform.runLater(()->gameController.updateGrid(m.getGrid()));
                });
                break;
            case gameTurn:
                jsonService.parseObject(content,GameTurnMessage.class).ifPresent(m->{
                    Platform.runLater(()->gameController.toggleTurn(m.isPlayerTurn()));
                });
                break;
            default:
                break;
        }
    }
}
