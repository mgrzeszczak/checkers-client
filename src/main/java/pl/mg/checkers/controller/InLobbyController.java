package pl.mg.checkers.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mg.checkers.message.MsgType;
import pl.mg.checkers.message.TypedMessage;
import pl.mg.checkers.message.msgs.LeaveLobbyMessage;
import pl.mg.checkers.representation.LobbyRepresentation;
import pl.mg.checkers.service.MessengerService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by maciej on 29.12.15.
 */
@Component
public class InLobbyController implements Initializable {

    @FXML
    private Button leaveLobbyButton;
    @FXML
    private Label lobbyName;

    @Autowired
    private MessengerService messengerService;

    private LobbyRepresentation lobbyRepresentation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setLobbyRepresentation(LobbyRepresentation lobbyRepresentation) {
        this.lobbyRepresentation = lobbyRepresentation;
        this.lobbyName.setText("Lobby "+lobbyRepresentation.getId());
    }

    public void onLeaveLobbyButtonClick(){
        messengerService.send(new TypedMessage(MsgType.leaveLobby,new LeaveLobbyMessage(lobbyRepresentation.getId())));
    }

}

