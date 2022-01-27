package edu.rice.comp504.model.chatroom;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatroomStoreTest {

    @Test
    void createChatroom() {
        IChatRoom chatRoom = ChatroomStore.getInstance().createChatroom("name", 10, false);
        assertSame(chatRoom, ChatroomStore.getInstance().getChatroom(chatRoom.getChatroomId()));
    }

    @Test
    void getAllChatRooms() {
        IChatRoom chatRoom1 = ChatroomStore.getInstance().createChatroom("name", 10, false);
        IChatRoom chatRoom2 = ChatroomStore.getInstance().createChatroom("name", 10, false);

        assertSame(chatRoom1, ChatroomStore.getInstance().getChatroom(chatRoom1.getChatroomId()));
        assertSame(chatRoom2, ChatroomStore.getInstance().getChatroom(chatRoom2.getChatroomId()));
        assertEquals(3, ChatroomStore.getInstance().getAllChatRooms().length);
    }
}