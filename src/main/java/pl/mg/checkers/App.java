package pl.mg.checkers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.mg.checkers.config.AppConfig;
import pl.mg.checkers.service.SceneService;
/**
 * Created by maciej on 25.12.15.
 */
public class App extends Application {

    public static int WIDTH = 600;
    public static int HEIGHT = 480;

    public static void main(String args[]){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e->{
            System.out.println("Closing...");
            Platform.exit();
            System.exit(0);
        });
        SceneService sceneService = context.getBean(SceneService.class);
        sceneService.setUp(primaryStage);
    }

}
