package edu.rice.comp504.model.userstorage;

import edu.rice.comp504.model.user.IUser;
import org.eclipse.jetty.websocket.api.Session;

/**
 * User DB responsible for holding and maintaining all user info.
 */
public interface IUserDB {
    /**
     * Check for if username exists in user db.
     *
     * @param username username string
     * @return true if user exists, false otherwise
     */
    boolean userExists(String username);

    /**
     * Authenticate a user based on given username and password.
     *
     * @param username username for the user
     * @param password password for the user
     * @return if user exists, return the IUser instance, otherwise, return null.
     */
    IUser authenticateUser(String username, String password);

    /**
     * Add a user to the user db. Username should be unique.
     *
     * @param user the IUser instance
     * @return the IUser instance if success, null if failure (username already exists)
     */
    IUser addNewUser(IUser user);

    /**
     * Get a user info based on its username.
     *
     * @param username user's username
     * @return user obj
     */
    IUser getUserInfo(String username);

    /**
     * Get the session of user. If user is logged in, return null
     *
     * @param user user instance
     * @return websocket session corresponding to user. return null if user is not loggedin
     */
    Session getSessionByUser(IUser user);

    /**
     * Register logged in user session.
     *
     * @param user    user instance
     * @param session session of the user
     */
    void registerUserSession(IUser user, Session session);

    /**
     * Remove a session.
     *
     * @param session session instance
     */
    void removeUserSession(Session session);

    /**
     * Check if a user is logged in by checking if we have its session.
     *
     * @param username username of the user
     * @return true if we have the user's session
     */
    boolean isUserLoggedIn(String username);
}
