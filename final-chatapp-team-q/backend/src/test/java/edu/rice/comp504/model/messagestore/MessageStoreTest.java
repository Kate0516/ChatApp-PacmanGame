package edu.rice.comp504.model.messagestore;

import edu.rice.comp504.model.message.IMessage;
import edu.rice.comp504.model.message.Message;
import edu.rice.comp504.model.user.IUser;
import edu.rice.comp504.model.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageStoreTest {
    static IUser testSender = new User("testSender", "12345", "Rice U", 19);
    static IUser testDummy = new User("testDummy", "12345", "Rice U", 19);
    static IMessageStore instance = MessageStore.getInstance();

    @Test
    void testMessageStore() {
        //Test Add Message
        //case: SUCCESSFUL add message with valid content
        assertEquals(1, instance.addMessage("testGetMessage id=1", 3, testSender, "all").getId());
        assertEquals(2, instance.addMessage("testGetMessage id=2", 3, testSender, "all").getId());

        //case: FAIL add message with hate speech, content will be modified
        assertEquals("This message contains sensitive content. Please do not violate our rules of speech", instance.addMessage("this contains hate speech", 0, testSender, "all").getContent());

        //Test Get Message
        //case: SUCCESSFUL message is in store
        assertEquals("testGetMessage id=1", instance.getMessage(1).getContent());
        assertEquals(1, instance.getMessage(1).getId());
        assertEquals(3, instance.getMessage(1).getChatroomId());
        assertEquals("all", instance.getMessage(1).getTarget());
        assertEquals("testGetMessage id=2", instance.getMessage(2).getContent());
        assertEquals(2, instance.getMessage(2).getId());
        assertEquals("This message contains sensitive content. Please do not violate our rules of speech", instance.getMessage(3).getContent());
        assertEquals(3, instance.getMessage(3).getId());

        //case: FAIL message does not exist
        assertEquals(null, instance.getMessage(4));

        //Test Update Message
        //case: SUCCESSFUL
        assertEquals("modified, no longer contains hs", instance.updateMessageContent(testSender, 3, "modified, no longer contains hs").getContent());
        assertEquals("modified, no longer contains hs", instance.getMessage(3).getContent());
        assertEquals("This message contains sensitive content. Please do not violate our rules of speech", instance.updateMessageContent(testSender, 3, "hate speech").getContent());
        assertEquals("This message contains sensitive content. Please do not violate our rules of speech", instance.getMessage(3).getContent());

        //Test Delete Message
        //case: SUCCESSFUL
        assertEquals("This message contains sensitive content. Please do not violate our rules of speech", instance.removeMessage(3).getContent());
        assertEquals(null, instance.getMessage(3));

        assertEquals("testGetMessage id=1", instance.removeMessage(1).getContent());
        assertEquals(null, instance.getMessage(1));

        assertEquals("testGetMessage id=2", instance.getMessage(2).getContent());

        //Test Check Privilege
        assertTrue(MessageStore.checkPrivilegeMessage(testSender.getUsername(), 2));
        assertFalse(MessageStore.checkPrivilegeMessage(testDummy.getUsername(), 2));

    }

}