package pl.mg.checkers.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.mg.checkers.message.MsgType;
import pl.mg.checkers.message.TypedMessage;
import pl.mg.checkers.message.msgs.JoinLobbyMessage;
import pl.mg.checkers.representation.ClientRepresentation;
import pl.mg.checkers.representation.LobbyRepresentation;
import pl.mg.checkers.service.MessengerService;
import pl.mg.checkers.service.SceneService;

/**
 * Created by maciej on 29.12.15.
 */
@Component
@Scope("prototype")
public class LobbyCellController {

    @FXML
    private Label lobbyId;
    @FXML
    private Label playerName;
    @FXML
    private Button joinButton;

    @Autowired
    private SceneService sceneService;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private MessengerService  messengerService;

    private LobbyRepresentation lobbyRepresentation;

    public void setLobbyRepresentation(LobbyRepresentation lobbyRepresentation) {
        this.lobbyRepresentation = lobbyRepresentation;
    }

    public void init() {
        lobbyId.setText(""+lobbyRepresentation.getId());
        String client = "";
        for (ClientRepresentation c : lobbyRepresentation.getClients()){
            client+=c.getNickname();
        }
        playerName.setText(client);
    }

    public void onJoinButtonClick(){
        //sceneService.switchScene(sceneService.loadScene("fxml/inLobby.fxml"));
        //InLobbyController controller = context.getBean(InLobbyController.class);
        messengerService.send(new TypedMessage(MsgType.joinLobby,new JoinLobbyMessage(lobbyRepresentation.getId())));
        //controller.setLobbyRepresentation(lobbyRepresentation);
        //sceneService.switchScene(sceneService.loadScene("fxml/game.fxml"));
        //GameController controller = context.getBean(GameController.class);
        //controller.draw();
        //controller.init();
    }

}
