package edu.rice.comp504.model.userstorage;

import org.eclipse.jetty.websocket.api.Session;
import edu.rice.comp504.model.user.IUser;
import edu.rice.comp504.model.user.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class UserDB implements IUserDB {

    private final ArrayList<IUser> users;
    private static final Map<Session, IUser> sessionUserMap = new ConcurrentHashMap<>();

    private UserDB() {
        this.users = new ArrayList<>();
    }

    private static final UserDB instance = new UserDB();

    public static UserDB getInstance() {
        return instance;
    }

    /**
     * Check for if username exists in user db.
     *
     * @param username username string
     * @return true if user exists, false otherwise
     */
    @Override
    public boolean userExists(String username) {
        if (username.equals("all")) {
            return true;
        }
        for (IUser u : this.users) {
            if (Objects.equals(u.getUsername(), username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Authenticate a user based on given username and password.
     *
     * @param username username for the user
     * @param password password for the user
     * @return if user exists, return the IUser instance, otherwise, return null.
     */
    @Override
    public IUser authenticateUser(String username, String password) {
        if (userExists(username)) {
            User iu = (User) getUserInfo(username);
            if (iu.authentication(password)) {
                return iu;
            }
        }
        return null;
    }

    /**
     * Add a user to the user db. Username should be unique.
     *
     * @param user the IUser instance
     * @return the IUser instance if success, null if failure (username already exists)
     */
    @Override
    public IUser addNewUser(IUser user) {
        if (userExists(user.getUsername())) {
            return null;
        } else {
            this.users.add(user);
            return user;
        }
    }

    /**
     * Get a user info based on its username.
     *
     * @param username user's username
     * @return user obj
     */
    @Override
    public IUser getUserInfo(String username) {
        for (IUser u : this.users) {
            if (Objects.equals(u.getUsername(), username)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Get the session of user. If user is logged in, return null
     *
     * @param user user instance
     * @return websocket session corresponding to user. return null if user is not loggedin
     */
    @Override
    public Session getSessionByUser(IUser user) {
        //if (isUserLoggedIn(user.getUsername())) {
        //改了一下，不然会循环调用
        for (Session s : sessionUserMap.keySet()) {
            if (sessionUserMap.get(s) == user) {
                return s;
            }
        }
        //}
        return null;
    }

    /**
     * Register logged in user session.
     *
     * @param user    user instance
     * @param session session of the user
     */
    @Override
    public void registerUserSession(IUser user, Session session) {
        sessionUserMap.put(session, user);
    }

    /**
     * Remove a session.
     *
     * @param session session instance
     */
    @Override
    public void removeUserSession(Session session) {
        sessionUserMap.remove(session);
    }

    /**
     * Check if a user is logged in by checking if we have its session.
     *
     * @param username username of the user
     * @return true if we have the user's session
     */
    @Override
    public boolean isUserLoggedIn(String username) {
        IUser u = getUserInfo(username);
        Session s = getSessionByUser(u);
        return s != null;
    }
}
