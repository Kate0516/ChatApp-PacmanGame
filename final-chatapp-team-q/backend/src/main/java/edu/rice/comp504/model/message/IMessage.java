package edu.rice.comp504.model.message;

/**
 * Message interface defining the message structure.
 */
public interface IMessage {
    /**
     * Get the content of a message.
     *
     * @return the message content
     */
    String getContent();

    /**
     * Edit content of message.
     *
     * @param content new content of message
     */
    void setContent(String content);

    /**
     * Get the message id.
     *
     * @return message id
     */
    int getId();

    /**
     * Get the target receiver for the message.
     *
     * @return return "all" for sending the message to all the users in the chatroom, otherwise, return a username.
     */
    String getTarget();

    /**
     * Get the chat room id which the message is in.
     *
     * @return the chatroom id
     */
    int getChatroomId();

    /**
     * Get the sender of the message.
     *
     * @return the sender username of this message
     */
    String getSender();
}
