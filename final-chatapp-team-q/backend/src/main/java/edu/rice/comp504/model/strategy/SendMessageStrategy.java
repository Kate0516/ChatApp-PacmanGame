package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.message.IMessage;
import edu.rice.comp504.model.user.IUser;
import com.google.gson.JsonObject;
import edu.rice.comp504.model.userstorage.UserDB;
import org.eclipse.jetty.websocket.api.Session;

/**
 * update notes:
 * 10.30: added singleton structure
 * 11.1 implemented update
 */
public class SendMessageStrategy implements IUpdateMessageStrategy {
    private SendMessageStrategy() {
    }

    private static final IUpdateMessageStrategy instance = new SendMessageStrategy();

    public static IUpdateMessageStrategy getInstance() {
        return instance;
    }

    /**
     * Strategy for sending message .
     *
     * @param user    the user to be sent
     * @param message the message tobe sent
     */
    @Override
    public void update(IUser user, IMessage message) {
        if (toReceiveMessage(user.getUsername(), message.getTarget(), message.getChatroomId())) {
            try {
                JsonObject jo = new JsonObject();
                JsonObject msgObj = new JsonObject();
                msgObj.addProperty("chatroom_id", message.getChatroomId());
                msgObj.addProperty("target", message.getTarget());
                msgObj.addProperty("content", message.getContent());
                msgObj.addProperty("sender", message.getSender());
                msgObj.addProperty("message_id", message.getId());
                jo.addProperty("type", "new_message");
                jo.add("payload", msgObj);
                Session session = UserDB.getInstance().getSessionByUser(user);
                if (session != null) {
                    session.getRemote().sendString(String.valueOf(jo));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
