package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.chatroom.ChatroomStore;
import edu.rice.comp504.model.message.IMessage;
import edu.rice.comp504.model.user.IUser;

/**
 * UpdateMessageStrategy to send update info to users with given message.
 */
public interface IUpdateMessageStrategy {
    /**
     * Strategy for updating user data with given user and message.
     *
     * @param user    the user data to be updated
     * @param message the message related to the user
     */
    void update(IUser user, IMessage message);

    /**
     *
     *
     * @param username this user to check if s/he can receive the message
     * @param target this message target (i.e. all or the specific username)
     * @param chatroomId the message is sent in this chatroom id
     * @return boolean value if the user can receive the message
     */
    default boolean toReceiveMessage(String username, String target, int chatroomId) {
        if (ChatroomStore.getInstance().getChatroom(chatroomId).isUserInChatroom(username)) {
            if (target.equals("all") || target.equals(username)) {
                return true;
            }
        }
        return false;
    }
}