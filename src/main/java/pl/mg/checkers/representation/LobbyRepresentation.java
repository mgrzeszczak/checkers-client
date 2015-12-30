package pl.mg.checkers.representation;

import java.util.Set;
/**
 * Created by maciej on 25.12.15.
 */
public class LobbyRepresentation {

    private long id;
    private Set<ClientRepresentation> clients;

    public LobbyRepresentation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<ClientRepresentation> getClients() {
        return clients;
    }

    public void setClients(Set<ClientRepresentation> clients) {
        this.clients = clients;
    }
}
