package edu.rice.comp504.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.rice.comp504.model.authenticate.Authenticator;
import edu.rice.comp504.model.store.ChatAppStore;
import edu.rice.comp504.model.store.IChatAppStore;
import edu.rice.comp504.model.user.IUser;
import edu.rice.comp504.model.userstorage.UserDB;
import edu.rice.comp504.util.GsonExcludeStrategy;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;


/**
 * WebSocket Adapter to handle communication through web socket.
 */
@WebSocket
public class WebSocketAdapter {
    private final Gson gson;
    private JsonParser jsonParser = new JsonParser();
    private final IChatAppStore store = ChatAppStore.getInstance();

    public WebSocketAdapter() {
        gson = new GsonBuilder().setExclusionStrategies(new GsonExcludeStrategy()).create();
    }

    /**
     * Open user's session.
     *
     * @param session The user whose session is opened.
     */
    @OnWebSocketConnect
    public void onConnect(Session session) {

    }

    /**
     * Close the user's session and remove that session from store.
     *
     * @param session The use whose session is closed.
     */
    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        UserDB.getInstance().removeUserSession(session);
    }

    /**
     * Send a message.
     *
     * @param session The session user sending the message.
     * @param message The message to be sent.
     */
    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        System.out.println(message);
        try {
            JsonObject request = jsonParser.parse(message).getAsJsonObject();
            String cookie = request.get("cookie").getAsString();
            IUser initiator = Authenticator.getInstance().authenticateJwt(cookie);
            if (initiator == null) {
                return;
            }
            String type = request.get("type").getAsString();

            if (type.equals("send_message")) {
                // Use case 9
                JsonObject payload = request.get("payload").getAsJsonObject();
                int chatroomId = payload.get("chatroom_id").getAsInt();
                String target = payload.get("target").getAsString();
                String content = payload.get("content").getAsString();
                store.sendMessageInChatRoom(initiator, content, chatroomId, target);
            } else if (type.equals("delete_message")) {
                //user case 11
                int messageId = request.get("payload").getAsJsonObject().get("message_id").getAsInt();
                store.deleteMessage(initiator, messageId);
            } else if (type.equals("edit_message")) {
                //use case 13
                JsonObject payload = request.get("payload").getAsJsonObject();
                int messageId = payload.get("message_id").getAsInt();
                String content = payload.get("content").getAsString();
                store.editMessage(initiator, messageId, content);
            } else if (type.equals("register_session")) {
                //use case 16
                UserDB.getInstance().registerUserSession(initiator, session);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
