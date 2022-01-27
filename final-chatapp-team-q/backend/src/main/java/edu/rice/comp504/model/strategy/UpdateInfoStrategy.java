package edu.rice.comp504.model.strategy;

import com.google.gson.JsonObject;
import edu.rice.comp504.model.message.IMessage;
import edu.rice.comp504.model.user.IUser;
import edu.rice.comp504.model.userstorage.UserDB;
import org.eclipse.jetty.websocket.api.Session;

/**
 * update notes:
 * 10.30: added singleton structure
 * 11.1 implemented update
 */
public class UpdateInfoStrategy implements IUpdateMessageStrategy {
    private UpdateInfoStrategy() {
    }

    private static final IUpdateMessageStrategy instance = new UpdateInfoStrategy();

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
        if (user != null) {
            try {
                JsonObject jo = new JsonObject();
                JsonObject msgObj = new JsonObject();
                jo.addProperty("type", "update_info");
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
