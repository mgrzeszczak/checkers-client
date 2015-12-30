package pl.mg.checkers.message.msgs;

import pl.mg.checkers.message.Message;

/**
 * Created by maciej on 25.12.15.
 */
public class ChangeNicknameMessage extends Message {

    private String newNickname;

    public ChangeNicknameMessage(String newNickname) {
        this.newNickname = newNickname;
    }

    public ChangeNicknameMessage() {
    }

    public String getNewNickname() {
        return newNickname;
    }

    public void setNewNickname(String newNickname) {
        this.newNickname = newNickname;
    }
}
