package edu.rice.comp504.model.userstorage;

import edu.rice.comp504.model.user.User;
import org.eclipse.jetty.websocket.api.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.*;

public class UserDBTest {
    UserDB db = UserDB.getInstance();
    String username = "zhen";
    String password = "123456";
    String school = "rice";
    String[] interests = {"C++", "JAVA", "Python"};
    User u = new User(username, password, school, 19, interests);

    @Test
    void testUserDB() {
        assertEquals(UserDB.getInstance(), db);
    }

    @Test
    void testUserExisits() {
        assertFalse(db.userExists("tom"));
        db.addNewUser(u);
        assertTrue(db.userExists("zhen"));
        assertTrue(db.userExists("all"));
    }

    @Test
    void testauthentic() {
        assertNull(db.authenticateUser("zhen", "123456"));
        db.addNewUser(u);
        assertEquals(db.authenticateUser("zhen", "123456"), u);
    }

    @Test
    void getuserInfo() {
        db.addNewUser(u);
        assertNull(db.getUserInfo("tom"));
        assertEquals(u.getUsername(), db.getUserInfo("zhen").getUsername());
    }

    @Test
    void testSession() {
        Session s = new Session() {
            @Override
            public void close() {

            }

            @Override
            public void close(CloseStatus closeStatus) {

            }

            @Override
            public void close(int i, String s) {

            }

            @Override
            public void disconnect() throws IOException {

            }

            @Override
            public long getIdleTimeout() {
                return 0;
            }

            @Override
            public InetSocketAddress getLocalAddress() {
                return null;
            }

            @Override
            public WebSocketPolicy getPolicy() {
                return null;
            }

            @Override
            public String getProtocolVersion() {
                return null;
            }

            @Override
            public RemoteEndpoint getRemote() {
                return null;
            }

            @Override
            public InetSocketAddress getRemoteAddress() {
                return null;
            }

            @Override
            public UpgradeRequest getUpgradeRequest() {
                return null;
            }

            @Override
            public UpgradeResponse getUpgradeResponse() {
                return null;
            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public void setIdleTimeout(long l) {

            }

            @Override
            public SuspendToken suspend() {
                return null;
            }
        };
        db.addNewUser(u);
        db.registerUserSession(u, s);
        assertEquals(db.getSessionByUser(u), s);
        db.removeUserSession(s);
        assertNull(db.getSessionByUser(u));
    }
}
