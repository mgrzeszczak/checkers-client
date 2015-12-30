package pl.mg.checkers.message.msgs;

/**
 * Created by maciej on 29.12.15.
 */
public class JoinLobbyMessage {

    private long lobbyId;
    private boolean success;

    public JoinLobbyMessage(long lobbyId, boolean success) {
        this.lobbyId = lobbyId;
        this.success = success;
    }

    public JoinLobbyMessage(long lobbyId) {
        this.lobbyId = lobbyId;
    }

    public JoinLobbyMessage() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(long lobbyId) {
        this.lobbyId = lobbyId;
    }
}
