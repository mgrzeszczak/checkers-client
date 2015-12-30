package pl.mg.checkers.representation;

/**
 * Created by maciej on 25.12.15.
 */
public class ClientRepresentation {

    private String nickname;

    public ClientRepresentation() {
    }

    public ClientRepresentation(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
