package pl.mg.checkers.message.msgs;

import pl.mg.checkers.representation.LobbyRepresentation;

/**
 * Created by maciej on 29.12.15.
 */
public class CreateLobbyMessage {

    private LobbyRepresentation lobbyRepresentation;

    public CreateLobbyMessage(LobbyRepresentation lobbyRepresentation) {
        this.lobbyRepresentation = lobbyRepresentation;
    }

    public CreateLobbyMessage() {
    }

    public LobbyRepresentation getLobbyRepresentation() {
        return lobbyRepresentation;
    }

    public void setLobbyRepresentation(LobbyRepresentation lobbyRepresentation) {
        this.lobbyRepresentation = lobbyRepresentation;
    }
}
