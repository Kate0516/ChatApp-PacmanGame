package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.message.IMessage;
import edu.rice.comp504.model.user.IUser;
import com.google.gson.JsonObject;
import edu.rice.comp504.model.userstorage.UserDB;

/**
 * update notes:
 * 10.30: added singleton structure
 * 11.1 implemented update
 */
public class EditMessageStrategy implements IUpdateMessageStrategy {
    private EditMessageStrategy() {
    }

    private static final IUpdateMessageStrategy instance = new EditMessageStrategy();

    public static IUpdateMessageStrategy getInstance() {
        return instance;
    }

    /**
     * Strategy for updating message history with given user and message.
     *
     * @param user    the user data to be updated
     * @param message the message related to the user
     */
    @Override
    public void update(IUser user, IMessage message) {
        if (toReceiveMessage(user.getUsername(), message.getTarget(), message.getChatroomId())) {
            try {
                JsonObject jo = new JsonObject();
                JsonObject msgObj = new JsonObject();
                msgObj.addProperty("message_id", message.getId());
                msgObj.addProperty("content", message.getContent());
                jo.addProperty("type", "edit_message");
                jo.add("payload", msgObj);
                UserDB.getInstance().getSessionByUser(user).getRemote().sendString(String.valueOf(jo));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
