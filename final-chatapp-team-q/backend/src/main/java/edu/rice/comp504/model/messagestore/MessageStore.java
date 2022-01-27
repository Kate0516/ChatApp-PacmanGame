package edu.rice.comp504.model.messagestore;

import edu.rice.comp504.model.message.IMessage;
import edu.rice.comp504.model.message.Message;
import edu.rice.comp504.model.user.IUser;

import java.util.ArrayList;

/**
 * update notes:
 * 10.30:
 * added an array of prohibitedWords
 * 10.31:
 * added LinkedList to store all message
 * Implemented add message function
 */

public class MessageStore implements IMessageStore {
    private static String[] prohibitedWords = new String[]{"hate speech"};
    private static ArrayList<IMessage> allMessage = new ArrayList<>();
    private static int nextMessageId = 0;

    private MessageStore() {
    }

    private static final MessageStore instance = new MessageStore();

    public static MessageStore getInstance() {
        return instance;
    }

    public static int getNextMessageId() {
        return nextMessageId++;
    }


    @Override
    public IMessage getMessage(int messageId) {
        for (IMessage msg : allMessage) {
            if (msg.getId() == messageId) {
                return msg;
            }
        }

        return null;
    }

    @Override
    public IMessage addMessage(String content, int chatroomId, IUser initiator, String target) {
        IMessage msg;
        if (checkHateSpeech(content)) {
            //MAKE A IMsg Obj whose content is replaced by an warning
            synchronized (allMessage) {
                msg = new Message(getNextMessageId(), chatroomId, "This message contains sensitive content. Please do not violate our rules of speech", initiator.getUsername(), target);
                allMessage.add(msg);
            }

            //accumulate hate speech count
            initiator.countHateSpeech();

        } else {
            synchronized (allMessage) {
                msg = new Message(getNextMessageId(), chatroomId, content, initiator.getUsername(), target);
                allMessage.add(msg);
            }
        }
        return msg;
    }

    private static boolean checkHateSpeech(String content) {
        for (String word : prohibitedWords) {
            if (content.toLowerCase().indexOf(word) > -1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IMessage removeMessage(int messageId) {
        IMessage msg;
        synchronized (allMessage) {
            msg = getMessage(messageId);
            if (msg != null) {
                allMessage.remove(msg);
            }
        }
        return msg;
    }

    /**
     * Update an existing message.
     *
     * @param messageId  id of message to be updated
     * @param newContent new content of the message
     * @return New message object
     */
    @Override
    public IMessage updateMessageContent(IUser initiator, int messageId, String newContent) {
        IMessage msg;
        if (checkHateSpeech(newContent)) {
            newContent = "This message contains sensitive content. Please do not violate our rules of speech";
            initiator.getHateSpeechCount();
        }

        synchronized (allMessage) {
            msg = getMessage(messageId);
            if (msg != null) {
                msg.setContent(newContent);
            }
        }
        return msg;
    }

    /**
     * Check if user is the owner/sender of the message.
     *
     * @param user      initiator of the action
     * @param messageId id of message that is being updated
     * @return true if user is owner
     */
    public static boolean checkPrivilegeMessage(String user, int messageId) {
        if (instance.getMessage(messageId) == null) {
            return false;
        }

        if (user.equals(instance.getMessage(messageId).getSender())) {
            return true;
        }
        return false;
    }
}
