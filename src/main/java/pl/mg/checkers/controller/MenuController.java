package pl.mg.checkers.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.mg.checkers.service.ConnectionService;
import pl.mg.checkers.service.SceneService;

/**
 * Created by maciej on 29.12.15.
 */
@Component
@Scope("singleton")
public class MenuController {

    @FXML
    private Button playButton;
    @FXML
    private TextField nickField;

    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private SceneService sceneService;

    public void onPlayButtonClick(){
        if (nickField.getText().isEmpty()){
            sceneService.showAlert(Alert.AlertType.ERROR,"Error","Invalid nickname","Please enter a valid nickname");
            return;
        }
        connectionService.connect("http://www.greyshack.bbarwik.com",8080,nickField.getText());
    }

}
