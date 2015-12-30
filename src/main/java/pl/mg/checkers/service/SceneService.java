package pl.mg.checkers.service;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pl.mg.checkers.App;
import java.io.IOException;
import java.util.Optional;
import java.util.Stack;
/**
 * Created by maciej on 25.12.15.
 */
@Service
@Scope("singleton")
public class SceneService {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private ConnectionService connectionService;

    private Stack<Scene> scenes = new Stack<>();
    private Logger logger = LogManager.getLogger(SceneService.class);

    private Stage stage;

    /***
     * Initiates this service and shows login screen.
     * @param stage Primary stage of the application.
     */
    public void setUp(Stage stage){
        this.stage = stage;
        Optional<Scene> scene = loadScene("fxml/menu.fxml",App.WIDTH,App.HEIGHT);
        scene.ifPresent(s->{
            stage.setTitle("Checkers");
            stage.setScene(s);
            stage.show();
        });
        if (!scene.isPresent()) Platform.exit();
    }

    /***
     * Changes current scene and pushes previous one onto the stack.
     * @param scene New scene to be shown.
     */
    public void switchScene(Scene scene){
        scenes.push(stage.getScene());
        stage.setScene(scene);
    }

    /***
     * Changes current scene. Previous scene is lost.
     * @param scene New scene to be shown.
     */
    public void changeScene(Scene scene){
        stage.setScene(scene);
    }

    /***
     * Changes scene to the first one on the stack.
     * Does nothing if stack is empty.
     */
    public void closeScene(){
        if (scenes.isEmpty()) return;
        stage.setScene(scenes.pop());
    }

    public Scene loadScene(String uri){
        Optional<Scene> scene = loadScene(uri,App.WIDTH,App.HEIGHT);
        if (!scene.isPresent()) Platform.exit();
        return scene.get();
    }

    public Optional<Scene> loadScene(String uri, int width, int height){
        Optional<Parent> parent = loadParent(uri);
        return parent.isPresent()? Optional.of(new Scene(parent.get(),width,height)) : Optional.empty();
    }

    public Optional<Parent> loadParent(String uri){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(uri));
            loader.setControllerFactory(context::getBean);
            return Optional.of(loader.load());
        } catch (IOException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void showAlert(Alert.AlertType type, String title, String headerText, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
