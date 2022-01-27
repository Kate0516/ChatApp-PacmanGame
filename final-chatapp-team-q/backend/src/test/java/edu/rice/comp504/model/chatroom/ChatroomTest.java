package edu.rice.comp504.model.chatroom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ChatroomTest {
    private IChatRoom chatRoom;

    @BeforeEach
    public void init() {
        chatRoom = new Chatroom("test", 0, 10, false);
    }

    @Test
    void isUserInBanList() {
        chatRoom.addUserToBanList("test");
        assertTrue(chatRoom.isUserInBanList("test"));
        assertFalse(chatRoom.isUserInBanList("test2"));
    }

    @Test
    void getChatroomId() {
        assertEquals(0, chatRoom.getChatroomId());
    }

    @Test
    void getUsersInChatroom() {
        chatRoom.addUserToChatroom("test1");
        chatRoom.addUserToChatroom("test2");
        String[] users = chatRoom.getUsersInChatroom();
        assertEquals(2, users.length);
        assertTrue(Arrays.asList(users).contains("test1"));
        assertTrue(Arrays.asList(users).contains("test2"));
    }

    @Test
    void getAdmin() {
        chatRoom.addAdmin("test1");
        chatRoom.addAdmin("test2");
        String[] users = chatRoom.getAdmin();
        assertEquals(2, users.length);
        assertTrue(Arrays.asList(users).contains("test1"));
        assertTrue(Arrays.asList(users).contains("test2"));
    }

    @Test
    void removeUser() {
        chatRoom.addUserToChatroom("test1");
        chatRoom.addUserToChatroom("test2");
        chatRoom.removeUserFromChatroom("test1");

        String[] users = chatRoom.getUsersInChatroom();
        assertEquals(1, users.length);
        assertFalse(Arrays.asList(users).contains("test1"));
    }

    @Test
    void isPrivateChatroom() {
        assertFalse(chatRoom.isPrivateChatroom());
    }


    @Test
    void removeUserFromBanList() {
        chatRoom.addUserToBanList("test1");
        assertTrue(chatRoom.isUserInBanList("test1"));
        chatRoom.removeUserFromBanList("test1");
        assertFalse(chatRoom.isUserInBanList("test1"));
    }

    @Test
    void isUserInChatroom() {
        chatRoom.addUserToChatroom("test");
        assertTrue(chatRoom.isUserInChatroom("test"));
    }

    @Test
    void isUserAdmin() {
        chatRoom.addAdmin("test");
        assertTrue(chatRoom.isUserAdmin("test"));
    }

    @Test
    void getSize() {
        assertEquals(10, chatRoom.getSize());
    }
}