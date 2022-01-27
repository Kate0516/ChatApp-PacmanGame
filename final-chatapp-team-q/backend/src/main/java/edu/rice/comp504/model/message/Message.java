package edu.rice.comp504.model.message;

/**
 * update notes:
 * 10.30: added attributes id and chatroomId.
 */

public class Message implements IMessage {
    private String content;
    private String sender;
    private String target;
    private int id;
    private int chatroomId;

    /**
     * Message Constructor.
     *
     * @param content content of message
     * @param sender  sender of message
     * @param target  target of message
     */
    public Message(int id, int chatroomId, String content, String sender, String target) {
        this.id = id;
        this.chatroomId = chatroomId;
        this.content = content;
        this.sender = sender;
        this.target = target;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getTarget() {
        return this.target;
    }

    @Override
    public int getChatroomId() {
        return this.chatroomId;
    }

    /**
     * Get the sender of the message.
     *
     * @return the sender username of this message
     */
    @Override
    public String getSender() {
        return this.sender;
    }
}
