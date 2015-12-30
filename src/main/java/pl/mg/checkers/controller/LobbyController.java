package pl.mg.checkers.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.mg.checkers.message.MsgType;
import pl.mg.checkers.message.TypedMessage;
import pl.mg.checkers.representation.LobbyRepresentation;
import pl.mg.checkers.service.MessengerService;
import pl.mg.checkers.service.SceneService;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
/**
 * Created by maciej on 29.12.15.
 */
@Component
@Scope("singleton")
public class LobbyController implements Initializable{

    @FXML
    private ListView<LobbyRepresentation> listView;
    @FXML
    private Button createLobbyButton;

    @Autowired
    private ApplicationContext context;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private MessengerService messengerService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setCellFactory(v-> new LobbyCell(context));
        listView.setItems(FXCollections.emptyObservableList());
    }

    public void updateItems(Collection<LobbyRepresentation> items){
        listView.setItems(FXCollections.observableArrayList(items));
    }

    public void onCreateLobbyButtonClick(){
        messengerService.send(new TypedMessage(MsgType.createLobby,null));
    }

    public void onLogoutButtonClick(){

    }

    public void onRefreshButtonClick(){
        messengerService.send(new TypedMessage(MsgType.lobbyUpdate,null));
    }
}
