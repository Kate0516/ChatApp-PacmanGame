package edu.rice.comp504.model.authenticate;

import edu.rice.comp504.model.user.IUser;

/**
 * Authenticator class for authenticate and generate jwt for user.
 */
public interface IAuthenticator {
    /**
     * Generate JSON Web Token for a user.
      * @param user user object
     * @return the jwt for the user
     */
    String getJwtForUser(IUser user);

    /**
     * Authenticate a user with given jwt.
     * @param jwt JSON Web Token provided by the user
     * @return the user object. if failed to authenticate, return null
     */
    IUser authenticateJwt(String jwt);
}
