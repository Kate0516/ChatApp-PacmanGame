package edu.rice.comp504.model.chatroom;

import com.google.gson.annotations.SerializedName;
import edu.rice.comp504.util.HiddenInGson;

import java.util.HashSet;

public class Chatroom implements IChatRoom {

    @SerializedName("chatroom_name")
    private final String chatroomName;

    @SerializedName("chatroom_id")
    private final int chatroomId;

    @SerializedName("chatroom_size")
    private final int chatroomSize;

    @SerializedName("is_private")
    private final boolean isPrivateChatroom;

    @HiddenInGson
    private final HashSet<String> bannedUser;


    private final HashSet<String> users;


    private final HashSet<String> admins;

    /**
     * Chatroom constructor.
     *
     * @param chatroomName      name of the chatroom
     * @param chatroomId        chatroom id
     * @param chatroomSize      chatroom size
     * @param isPrivateChatroom isPrivateChatroom
     */
    public Chatroom(String chatroomName, int chatroomId, int chatroomSize, boolean isPrivateChatroom) {
        this.chatroomName = chatroomName;
        this.chatroomId = chatroomId;
        this.chatroomSize = chatroomSize;
        this.isPrivateChatroom = isPrivateChatroom;
        this.bannedUser = new HashSet<>();
        this.users = new HashSet<>();
        this.admins = new HashSet<>();
    }

    /**
     * Check if a user is in the ban list.
     *
     * @param username username of the user to be checked
     * @return user is in the ban list
     */
    @Override
    public boolean isUserInBanList(String username) {
        return bannedUser.contains(username);
    }

    /**
     * Get chatroom id.
     *
     * @return chatroom id
     */
    @Override
    public int getChatroomId() {
        return this.chatroomId;
    }

    /**
     * Get all users in the chatroom.
     *
     * @return all users in chatroom
     */
    @Override
    public String[] getUsersInChatroom() {
        return users.toArray(new String[0]);
    }

    /**
     * Get all admins in the chatroom.
     *
     * @return all admin in the chatroom
     */
    @Override
    public String[] getAdmin() {
        return admins.toArray(new String[0]);
    }


    /**
     * Get if the chatroom is private.
     *
     * @return is the chatroom private
     */
    @Override
    public boolean isPrivateChatroom() {
        return this.isPrivateChatroom;
    }

    /**
     * Add a user to the ban list of the chatroom.
     *
     * @param username the username of the user to be banned
     */
    @Override
    public void addUserToBanList(String username) {
        this.bannedUser.add(username);
    }

    /**
     * Add a user to the chatroom.
     *
     * @param username the username of the user to be added into the chatroom
     */
    @Override
    public void addUserToChatroom(String username) {
        this.users.add(username);
    }

    /**
     * Remove a user from the chatroom.
     *
     * @param username the username of the user to be removed
     */
    @Override
    public void removeUserFromChatroom(String username) {
        this.users.remove(username);
    }

    /**
     * Remove(free) a user from ban list.
     *
     * @param username the username of the user to be freed
     */
    @Override
    public void removeUserFromBanList(String username) {
        this.bannedUser.remove(username);
    }

    /**
     * Check if a user exists in the chat room.
     *
     * @param username username of the user to be checked
     * @return true if  the user exists
     */
    @Override
    public boolean isUserInChatroom(String username) {
        return this.users.contains(username);
    }

    /**
     * Check if a user is the admin in the chat room.
     *
     * @param username username of the user to be checked
     * @return true if the user is the admin
     */
    @Override
    public boolean isUserAdmin(String username) {
        return this.admins.contains(username);
    }

    /**
     * Add a user as the admin.
     *
     * @param username username of the user to be added as an admin
     */
    @Override
    public void addAdmin(String username) {
        this.admins.add(username);
    }

    @Override
    public void removeAdmin(String username) {
        this.admins.remove(username);
    }

    /**
     * Get the size of the chatroom.
     *
     * @return chatroom size
     */
    @Override
    public int getSize() {
        return this.chatroomSize;
    }
}
