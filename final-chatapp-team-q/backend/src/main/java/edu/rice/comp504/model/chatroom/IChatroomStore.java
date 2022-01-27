package edu.rice.comp504.model.chatroom;

/**
 * Chatroom store interface for holding chat rooms.
 */
public interface IChatroomStore {
    /**
     * Get the chatroom with given chatroomId.
     * @param chatroomId chatroomId
     * @return the chatroom object
     */
    IChatRoom getChatroom(int chatroomId);

    /**
     * Create a chat room with given chatroom name.
     * @param chatroomName chatroom name
     * @return the chatroom object.
     */
    IChatRoom createChatroom(String chatroomName, int chatroomSize, boolean isPrivateChatroom);

    /**
     * Get all chat rooms.
     * @return all chat room
     */
    IChatRoom[] getAllChatRooms();

    /**
     * Remove a chatroom from chatroom store.
     * @param chatroomId chatroom id
     */
    void removeChatroom(int chatroomId);
}
