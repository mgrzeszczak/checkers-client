package pl.mg.checkers.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.mg.checkers.representation.LobbyRepresentation;

import java.io.IOException;

/**
 * Created by maciej on 29.12.15.
 */
public class LobbyCell extends ListCell<LobbyRepresentation> {

    private LobbyCellController controller;
    private Parent root;
    private ApplicationContext context;

    public LobbyCell(ApplicationContext context) {
        this.context = context;
        try {
            init();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void init() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/lobbyCell.fxml"));
        loader.setControllerFactory(context::getBean);
        root = loader.load();
        controller = loader.getController();
    }

    @Override
    protected void updateItem(LobbyRepresentation item, boolean empty) {
        super.updateItem(item, empty);
        if (item==null){
            setGraphic(null);
            return;
        }
        controller.setLobbyRepresentation(item);
        controller.init();
        setGraphic(root);
    }
}
