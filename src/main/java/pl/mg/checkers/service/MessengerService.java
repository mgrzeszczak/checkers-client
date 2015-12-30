package pl.mg.checkers.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pl.mg.checkers.message.TypedMessage;

import java.io.IOException;

/**
 * Created by maciej on 25.12.15.
 */
@Service
@Scope("singleton")
public class MessengerService {

    @Autowired
    private JsonService jsonService;
    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private ThreadPoolService threadPoolService;

    private Logger logger = LogManager.getLogger(MessengerService.class);

    public void send(TypedMessage message){
        threadPoolService.execute(()->jsonService.stringify(message).ifPresent(this::pass));
    }

    private void pass(String stringified){
        try {
            logger.debug("SENDING: "+stringified);
            connectionService.getOs().write((stringified.length()+"\r\n\r\n"+stringified).getBytes());
        } catch(IOException e){
            connectionService.disconnect();
        }
    }

}
