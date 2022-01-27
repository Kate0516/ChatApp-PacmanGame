package edu.rice.comp504.model.store;

import edu.rice.comp504.model.message.IMessage;
import edu.rice.comp504.model.user.IUser;
import edu.rice.comp504.util.JsonResponse;

/**
 * IChatAppStore is responsible for storing all the objects in the ChatApp. Objects include user, chat room and messages.
 */
public interface IChatAppStore {


    /**
     * Login user returns the jwt for the user.
     *
     * @param username User's username
     * @param password password
     * @return "" if login fails, jwt if login success
     */
    String loginUser(String username, String password);

    /**
     * Use case 1: create account.
     * Create an account.
     *
     * @param username  username for the user
     * @param password  password for the user
     * @param school    school info of the user
     * @param interests interests of the user
     * @param age       the age of the user
     * @return json response
     */
    JsonResponse registerNewUser(String username, String password, String school, int age, String... interests);

    /**
     * Use case 2: present user info.
     * Get the user info of the specific user.
     *
     * @param initiator the user who called this method
     * @param username  username for the user whose user info to be retrieved
     * @return json response
     */
    JsonResponse getUser(IUser initiator, String username);

    /**
     * Use case 3: create chat room
     *
     * @param initiator       the user who called this method
     * @param chatroomName    name for the chatroom
     * @param privateChatroom is this chatroom private.
     * @param chatroomSize    size of the chatroom
     * @return json response
     */
    JsonResponse createChatroom(IUser initiator, String chatroomName, boolean privateChatroom, int chatroomSize);

    /**
     * Use case 4: join chatroom, Use case 5: invite user to chatroom
     * Add a user to the chatroom.
     *
     * @param initiator  the user hwo called this method
     * @param username   username for the user to join the chat room.
     * @param chatroomId chat room id
     * @return json response
     */
    JsonResponse joinUserIntoChatroom(IUser initiator, String username, int chatroomId);

    /**
     * Use case 6: Leave chatroom
     * Remove a user itself from the chatroom.
     *
     * @param initiator  user who called this method
     * @param username   username of the user to be removed.
     * @param chatroomId chat room id
     * @return json response
     */
    JsonResponse removeUserFromChatroom(IUser initiator, String username, int chatroomId);

    /**
     * Use case 8: get user in chatroom
     * Get all user in a chat room.
     *
     * @param initiator  the user called this method
     * @param chatroomId chatroom id
     * @return array of user info
     */
    JsonResponse getUserInChatroom(IUser initiator, int chatroomId);

    /**
     * Use case 9: Send message.
     * Send a message in the chatroom.
     *
     * @param initiator  the user called this method
     * @param message    message content
     * @param chatroomId chat room id
     * @param target     target user's username, 'all' for all user in the chatroom
     */
    void sendMessageInChatRoom(IUser initiator, String message, int chatroomId, String target);

    /**
     * Use case 11: delete message.
     * Delete a message given the message id.
     *
     * @param initiator the user called this method
     * @param messageId message if of the message
     */
    void deleteMessage(IUser initiator, int messageId);

    /**
     * Use case 12: edit message.
     * Edit an existing message.
     *
     * @param initiator  the user called this method
     * @param messageId  id of the message
     * @param newContent new content for the message
     */
    void editMessage(IUser initiator, int messageId, String newContent);
}
