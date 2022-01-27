package edu.rice.comp504.model.chatroom;

import edu.rice.comp504.model.user.IUser;

/**
 * Chatroom Interface design.
 */
public interface IChatRoom {
    /**
     * Get chatroom id.
     *
     * @return chat room id
     */
    int getChatroomId();

    /**
     * Get all the users' username in this chat room.
     *
     * @return all the users' username in this chat room
     */
    String[] getUsersInChatroom();


    /**
     * Is this chat room a private chatroom.
     *
     * @return true if this chatroom this a private chatroom
     */
    boolean isPrivateChatroom();

    /**
     * Get all the admins' username in this chatroom.
     *
     * @return all the admins' username
     */
    String[] getAdmin();


    /**
     * Check if a user exists in the chat room.
     *
     * @param username username of the user to be checked
     * @return true if  the user exists
     */
    boolean isUserInChatroom(String username);

    /**
     * Check if a user is the admin in the chat room.
     *
     * @param username username of the user to be checked
     * @return true if the user is the admin
     */
    boolean isUserAdmin(String username);

    /**
     * Check if user is in the ban list of this chatroom.
     *
     * @param username username of the user to be checked
     * @return if the user is in the ban list.
     */
    boolean isUserInBanList(String username);

    /**
     * Add a user to the ban list.
     *
     * @param username the username of the user to be banned
     */
    void addUserToBanList(String username);

    /**
     * Add a user to the chatroom.
     *
     * @param username the username of the user to be added into the chatroom
     */
    void addUserToChatroom(String username);

    /**
     * Remove a user from the chatroom.
     *
     * @param username the username of the user to be removed
     */
    void removeUserFromChatroom(String username);

    /**
     * Remove a user from the ban list.
     *
     * @param username the username of the user to be freed
     */
    void removeUserFromBanList(String username);

    /**
     * Add a user as the admin.
     *
     * @param username username of the user to be added as an admin
     */
    void addAdmin(String username);


    void removeAdmin(String username);

    /**
     * Get the size of the chatroom.
     *
     * @return chatroom size
     */
    int getSize();
}
