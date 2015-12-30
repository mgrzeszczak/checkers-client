package pl.mg.checkers.message.msgs;

/**
 * Created by maciej on 29.12.15.
 */
public class LeaveLobbyMessage {

    private long lobbyId;

    public LeaveLobbyMessage(long lobbyId) {
        this.lobbyId = lobbyId;
    }

    public LeaveLobbyMessage() {
    }

    public long getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(long lobbyId) {
        this.lobbyId = lobbyId;
    }
}
