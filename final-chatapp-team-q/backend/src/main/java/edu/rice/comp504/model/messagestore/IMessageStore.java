package edu.rice.comp504.model.messagestore;

import edu.rice.comp504.model.message.IMessage;
import edu.rice.comp504.model.user.IUser;

/**
 * update notes:
 * 10.30: added function checkHateSpeech()
 */


/**
 * MessageStore for holding and maintaining the messages.
 */
public interface IMessageStore {
    /**
     * Get message given message id.
     *
     * @param messageId message's messageId
     * @return IMessage object
     */
    IMessage getMessage(int messageId);

    /**
     * Make a message with given content.
     *
     * @param content    message content
     * @param chatroomId chatroom id
     * @param initiator  sender of the message
     * @param target     the target to receive the message
     * @return the new message
     */
    IMessage addMessage(String content, int chatroomId, IUser initiator, String target);

    /**
     * Remove a message given messageId.
     *
     * @param messageId message's messageId that to be deleted
     * @return the removed message object
     */
    IMessage removeMessage(int messageId);

    /**
     * Update an existing message.
     *
     * @param messageId  id of message to be updated
     * @param newContent new content of the message
     * @return New message object
     */
    IMessage updateMessageContent(IUser initiator, int messageId, String newContent);

}
