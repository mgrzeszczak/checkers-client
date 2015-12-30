package pl.mg.checkers.message.msgs;

import pl.mg.checkers.message.Message;
import pl.mg.checkers.representation.LobbyRepresentation;
import java.util.Map;

/**
 * Created by maciej on 25.12.15.
 */
public class InitMessage extends Message {

    private Map<Long,LobbyRepresentation> lobbies;

    public InitMessage() {

    }

    public Map<Long, LobbyRepresentation> getLobbies() {
        return lobbies;
    }

    public void setLobbies(Map<Long, LobbyRepresentation> lobbies) {
        this.lobbies = lobbies;
    }
}
