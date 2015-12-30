package pl.mg.checkers.message;

/**
 * Created by maciej on 25.12.15.
 */
public class TypedMessage<T> {

    private MsgType type;
    private T content;

    public TypedMessage(MsgType type, T content) {
        this.type = type;
        this.content = content;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
