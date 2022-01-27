package edu.rice.comp504.model.chatroom;

import java.util.ArrayList;

public class ChatroomStore implements IChatroomStore {
    private final ArrayList<IChatRoom> chatRooms;

    private ChatroomStore() {
        chatRooms = new ArrayList<>();
    }

    private static final ChatroomStore instance = new ChatroomStore();

    public static ChatroomStore getInstance() {
        return instance;
    }

    @Override
    public IChatRoom createChatroom(String chatroomName, int chatroomSize, boolean isPrivateChatroom) {
        int id = chatRooms.size();
        IChatRoom chatRoom = new Chatroom(chatroomName, id, chatroomSize, isPrivateChatroom);
        this.chatRooms.add(chatRoom);
        return chatRoom;
    }

    @Override
    public IChatRoom getChatroom(int chatroomId) {
        for (IChatRoom chatRoom : chatRooms) {
            if (chatRoom.getChatroomId() == chatroomId) {
                return chatRoom;
            }
        }
        return null;
    }

    /**
     * Get all chat rooms.
     *
     * @return all chat room
     */
    @Override
    public IChatRoom[] getAllChatRooms() {
        return this.chatRooms.toArray(new IChatRoom[0]);
    }

    /**
     * Remove a chatroom from chatroom store.
     *
     * @param chatroomId chatroom id
     */
    @Override
    public void removeChatroom(int chatroomId) {
        this.chatRooms.removeIf((iChatRoom -> iChatRoom.getChatroomId() == chatroomId));
    }

    /**
     * Check if user is admin in the chatroom.
     *
     * @param user       initiator of the action
     * @param chatroomId id of message that is being updated
     * @return true if user is admin
     */
    public static boolean checkPrivilegeChatroom(String user, int chatroomId) {
        if (instance.getChatroom(chatroomId) == null) {
            return false;
        }
        if (instance.getChatroom(chatroomId).isUserAdmin(user)) {
            return true;
        }
        return false;
    }
}
