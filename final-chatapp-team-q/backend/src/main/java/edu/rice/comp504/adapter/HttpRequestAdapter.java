package edu.rice.comp504.adapter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.rice.comp504.model.authenticate.Authenticator;
import edu.rice.comp504.model.chatroom.ChatroomStore;
import edu.rice.comp504.model.chatroom.IChatRoom;
import edu.rice.comp504.model.store.ChatAppStore;
import edu.rice.comp504.model.store.IChatAppStore;
import edu.rice.comp504.model.user.IUser;
import edu.rice.comp504.model.user.User;
import edu.rice.comp504.model.userstorage.UserDB;
import edu.rice.comp504.util.JsonResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Adapter for REST request handling.
 */
public class HttpRequestAdapter {
    private final IChatAppStore store = ChatAppStore.getInstance();

    private static final HttpRequestAdapter instance = new HttpRequestAdapter();

    /**
     * Get HttpRequestAdapter instance.
     *
     * @return HttpRequestAdapter instance
     */
    public static HttpRequestAdapter getInstance() {
        return instance;
    }

    /**
     * create a new user.
     *
     * @return JsonResponse
     */
    public JsonResponse registerUser(JsonObject request) {
        try {
            String username = request.get("username").getAsString();
            String password = request.get("password").getAsString();
            int age = request.get("age").getAsInt();
            String school = request.get("school").getAsString();
            ArrayList<String> interests = new ArrayList<>();
            for (JsonElement interest : request.get("interest").getAsJsonArray()) {
                interests.add(interest.getAsString());
            }
            return store.registerNewUser(username, password, school, age, interests.toArray(new String[0]));

        } catch (Exception e) {
            return new JsonResponse(false, null, "", 400);
        }
    }

    /**
     * check if initiator is authenticated and then fetch the info for him.
     *
     * @return JsonResponse
     */
    public JsonResponse getUserInfo(String userToken, String username) {
        IUser user = Authenticator.getInstance().authenticateJwt(userToken);
        if (user == null) {
            return new JsonResponse(false, null, "", 401);
        }
        return store.getUser(user, username);
    }

    /**
     * Use case 3: create Chat room.
     *
     * @param userCookie cookie for the user
     * @param request    client side request
     * @return JsonResponse
     */
    public JsonResponse createChatRoom(String userCookie, JsonObject request) {
        IUser user = Authenticator.getInstance().authenticateJwt(userCookie);
        if (user == null) {
            return new JsonResponse(false, null, "", 401);
        }
        try {
            int size = request.get("size").getAsInt();
            String chatRoomName = request.get("chatroom_name").getAsString();
            boolean isPrivateChatroom = request.get("is_private").getAsBoolean();
            return store.createChatroom(user, chatRoomName, isPrivateChatroom, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResponse(false, null, "", 400);
    }

    /**
     * Add user to a chatroom.
     *
     * @param userCookie cookie for the user
     * @param request    client side request
     * @return JsonResponse
     */
    public JsonResponse addUserToChatRoom(String userCookie, JsonObject request) {
        IUser user = Authenticator.getInstance().authenticateJwt(userCookie);
        if (user == null) {
            return new JsonResponse(false, null, "", 401);
        }
        try {
            String invitee = request.get("Invitee").getAsString();
            int chatroomId = request.get("chatroom_id").getAsInt();
            return store.joinUserIntoChatroom(user, invitee, chatroomId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResponse(false, null, "", 400);
    }

    /**
     * Remove this user from this chatroom.
     *
     * @param userCookie cookie for the user
     * @param chatroomId chatroom id to remove from
     * @param username   the username to be removed
     * @return JsonResponse
     */
    public JsonResponse removeUserFromChatroom(String userCookie, int chatroomId, String username) {
        IUser user = Authenticator.getInstance().authenticateJwt(userCookie);
        if (user == null) {
            return new JsonResponse(false, null, "", 401);
        }
        try {
            return store.removeUserFromChatroom(user, username, chatroomId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResponse(false, null, "", 400);
    }

    /**
     * Get all chatroom list of this user.
     *
     * @param userCookie cookie for the user
     * @return JsonResponse
     */
    public JsonResponse getAllChatRooms(String userCookie) {
        IUser user = Authenticator.getInstance().authenticateJwt(userCookie);
        if (user == null) {
            return new JsonResponse(false, null, "", 401);
        }
        return new JsonResponse(true, ChatroomStore.getInstance().getAllChatRooms(), "");
    }

    /**
     * Get all users in this chatroom.
     *
     * @param userCookie    cookie for the user
     * @param chatroomIdStr string representation of the chatroom id
     * @return JsonResponse
     */
    public JsonResponse getUserInChatroom(String userCookie, String chatroomIdStr) {
        IUser user = Authenticator.getInstance().authenticateJwt(userCookie);
        if (user == null) {
            return new JsonResponse(false, null, "", 401);
        }
        try {
            int chatroomId = Integer.parseInt(chatroomIdStr);
            return store.getUserInChatroom(user, chatroomId);
        } catch (Exception e) {
            return new JsonResponse(false, null, "", 400);
        }
    }

    /**
     * "" if login fails; jwt for the user if login success.
     *
     * @return JsonResponse
     */
    public JsonResponse logInRegisteredUser(JsonObject request) {
        String username = request.get("username").getAsString();
        String password = request.get("password").getAsString();
        String userToken = store.loginUser(username, password);
        if (userToken == null || userToken.equals("")) {
            return new JsonResponse(false, null, "Invalid username or password", 400);
        } else {
            JsonObject resp = new JsonObject();
            resp.addProperty("cookie", userToken);
            return new JsonResponse(true, resp, "");
        }
    }
}