package pl.mg.checkers.service;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pl.mg.checkers.component.ConnectionListener;
import pl.mg.checkers.message.MsgType;
import pl.mg.checkers.message.TypedMessage;
import pl.mg.checkers.message.msgs.ChangeNicknameMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by maciej on 25.12.15.
 */
@Service
@Scope("singleton")
public class ConnectionService {

    private String host;
    private int port;
    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private String nick;

    @Autowired
    private ConnectionListener connectionListener;
    @Autowired
    private ThreadPoolService threadPoolService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private MessengerService messengerService;

    public void connect(String host, int port, String nick){
        this.host = host;
        this.port = port;
        this.nick = nick.isEmpty()? null : nick;
        try {
            this.socket = new Socket(host, port);
        } catch (IOException e){
            sceneService.showAlert(Alert.AlertType.ERROR,"Error","Connection Error","Cannot connect to the server.");
            return;
        }
        try{
            this.is = socket.getInputStream();
            this.os = socket.getOutputStream();
            connectionListener.setUp(is);
            threadPoolService.execute(connectionListener);
            threadPoolService.execute(()->messengerService.send(new TypedMessage(MsgType.changeName,new
                    ChangeNicknameMessage(nick))));
        } catch (IOException e){
            disconnect();
        }
    }

    public void disconnect(){
        try {
            socket.close();
            Platform.runLater(()->{
                sceneService.showAlert(Alert.AlertType.ERROR,"Error","Disconnected","You've been disconnected.");
                sceneService.changeScene(sceneService.loadScene("fxml/menu.fxml"));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }

    public InputStream getIs() {
        return is;
    }

    public OutputStream getOs() {
        return os;
    }
}
