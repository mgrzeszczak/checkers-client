package pl.mg.checkers.message;

/**
 * Created by maciej on 25.12.15.
 */
public enum MsgType {

    /*
    Downstream
     */
    init, lobbyUpdate, gameStarted, gameEnded,
    /*
    Upstream
     */
    changeName, leaveLobby, joinLobby, leaveGame,
    /*
    Both
     */
    createLobby, gamePawnMove, gameTurn, gridUpdateAck, gameContinueTurn;


    public static boolean isValidType(String type){
        for (MsgType messageType : MsgType.values()){
            if (messageType.name().equals(type)) return true;
        }
        return false;
    }

}
