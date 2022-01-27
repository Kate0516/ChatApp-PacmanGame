package edu.rice.comp504.model.user;

import edu.rice.comp504.model.authenticate.Authenticator;
import edu.rice.comp504.model.command.MessageCommandFactory;
import edu.rice.comp504.model.message.IMessage;
import edu.rice.comp504.model.messagestore.IMessageStore;
import edu.rice.comp504.model.messagestore.MessageStore;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {


    @Test
    void testUser() {
        String username = "zhen";
        String password = "123456";
        String school = "rice";
        String[] interests = {"C++", "JAVA", "Python"};
        User u = new User(username, password, school, 18, interests);

        assertEquals(u.getUsername(), "zhen");
        assertEquals(u.getSchool(), "rice");
        assertTrue(u.authentication("123456"));
        ArrayList<String> a = new ArrayList<String>(List.of(interests));
        assertEquals(u.getInterests(), a);
    }

    @Test
    void testPropertyChange() {
        String username = "zhen";
        String password = "123456";
        String school = "rice";
        String[] interests = {"C++", "JAVA", "Python"};
        User u = new User(username, password, school, 19, interests);
        PropertyChangeSupport pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(u);
        IMessage message = MessageStore.getInstance().addMessage("a", 1, u, "b");
        System.out.println(message);
        pcs.firePropertyChange("", null, MessageCommandFactory.getInstance().make(message, ""));
    }
}
