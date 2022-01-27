package edu.rice.comp504.model.store;

import com.google.gson.JsonObject;
import edu.rice.comp504.model.authenticate.Authenticator;
import edu.rice.comp504.model.chatroom.Chatroom;
import edu.rice.comp504.model.chatroom.ChatroomStore;
import edu.rice.comp504.model.chatroom.IChatRoom;
import edu.rice.comp504.model.chatroom.IChatroomStore;
import edu.rice.comp504.model.messagestore.IMessageStore;
import edu.rice.comp504.model.messagestore.MessageStore;
import edu.rice.comp504.model.user.IUser;
import edu.rice.comp504.model.user.User;
import edu.rice.comp504.model.userstorage.UserDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.rice.comp504.util.GsonExcludeStrategy;
import edu.rice.comp504.util.JsonResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChatAppStoreTest {

    static IChatAppStore instance = ChatAppStore.getInstance();
    static IMessageStore messageStore = MessageStore.getInstance();
    static IChatroomStore chatroomStore = ChatroomStore.getInstance();
    private final Gson gson = new GsonBuilder().setExclusionStrategies(new GsonExcludeStrategy()).create();

    @Test
    void testChatAppStore() {
        //Test registerNewUser
        //Case: success
        JsonResponse res = instance.registerNewUser("testSender", "123", "Rice U", 21, "CS");
        assertTrue(res.success);
        res = instance.registerNewUser("testReceiver", "123", "Rice U", 21);
        assertTrue(res.success);
        res = instance.registerNewUser("testNotReceiver", "123", "Rice U", 21);
        assertTrue(res.success);

        //Case: fail
        res = instance.registerNewUser("testSender", "123", "Rice U", 21, "CS");
        assertEquals("user already exists", res.message);
        assertEquals(400, res.statusCode);

        //Test Login
        //Case fail
        assertEquals(null, instance.loginUser("testSender", "123456"));

        //Case success
        String userToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJxdWlja2NoYXQiLCJ1c2VybmFtZSI6InRlc3RTZW5kZXIifQ.AtWYBqN58WNfmuAQNAi03UHJmzVE5n1PrAO4Wocs3DY";
        assertEquals(userToken, instance.loginUser("testSender", "123"));

        //Test get user with authenticator
        //Case success
        IUser testSender = Authenticator.getInstance().authenticateJwt(userToken);
        assertEquals("testSender", testSender.getUsername());

        //Case fail
        IUser testFailedAuth = Authenticator.getInstance().authenticateJwt("123");
        assertEquals(null, testFailedAuth);

        //Test getUser by chatAppStore
        res = instance.getUser(testSender, "testSender");
        assertTrue(res.success);
        JsonObject resJson = gson.toJsonTree(res.payload).getAsJsonObject();
        assertEquals("testSender", resJson.get("username").getAsString());
        assertEquals("Rice U", resJson.get("school").getAsString());

        //Case fail
        res = instance.getUser(testFailedAuth, "testFailed");
        assertEquals(404, res.statusCode);

        //Test createChatRoom
        //case success
        res = instance.createChatroom(testSender, "testRoom", false, 99);
        assertTrue(res.success);

        IChatRoom testRoom = chatroomStore.getChatroom(3);
        assertEquals(99, testRoom.getSize());

        //case fail
        res = instance.createChatroom(testSender, "failed room", true, 0);
        assertEquals(400, res.statusCode);

        //Test get users in chatroom
        //case success
        res = instance.getUserInChatroom(testSender, testRoom.getChatroomId());
        instance.joinUserIntoChatroom(testSender, "testReceiver", 1);
        assertTrue(res.success);
        IUser[] userList = new IUser[1];
        userList[0] = testSender;
        assertEquals(gson.toJson(userList), gson.toJson(res.payload));

        //Test join chatroom
        //case success
        res = instance.joinUserIntoChatroom(testSender, "testReceiver", 3);
        assertTrue(res.success);

        assertEquals(2, testRoom.getUsersInChatroom().length);

        //case fail
        res = instance.joinUserIntoChatroom(testSender, "testReceiver", 3);
        assertEquals(400, res.statusCode);

        res = instance.joinUserIntoChatroom(testSender, "testFailed", 3);
        assertEquals(404, res.statusCode);

        res = instance.joinUserIntoChatroom(testSender, "testNotReceiver", 10);
        assertEquals(404, res.statusCode);

        //Test remove user from chatroom
        //JsonResponse removeUserFromChatroom(IUser initiator, String username, int chatroomId);
        //case fail
        res = instance.removeUserFromChatroom(testSender, "testFailed", 3);
        assertEquals(404, res.statusCode);

        res = instance.removeUserFromChatroom(testSender, "testReceiver", 10);
        assertEquals(404, res.statusCode);

        //case success
        res = instance.removeUserFromChatroom(testSender, "testReceiver", 3);
        assertTrue(res.success);
        assertEquals(1, testRoom.getUsersInChatroom().length);
        res = instance.joinUserIntoChatroom(testSender, "testReceiver", 3);

        //Test send message
        //Case success. Cant have tests as we have no frontend session and this method has no return value
        instance.sendMessageInChatRoom(testSender, "test send message", 3, "all");

        //Test edit message
        //Case success. Cant have tests as we have no frontend session and this method has no return value
        instance.editMessage(testSender, 2, "updated message");

        //Test delete message
        //Case success. Cant have tests as we have no frontend session and this method has no return value
        instance.deleteMessage(testSender, 2);

    }
}
