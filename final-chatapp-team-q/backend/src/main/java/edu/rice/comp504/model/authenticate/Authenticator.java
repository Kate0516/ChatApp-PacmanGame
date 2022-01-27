package edu.rice.comp504.model.authenticate;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.rice.comp504.model.user.IUser;
import edu.rice.comp504.model.userstorage.UserDB;

public class Authenticator implements IAuthenticator {
    private Authenticator() {
    }

    private static final String issuer = "quickchat";
    private static final Authenticator instance = new Authenticator();
    private static final String secret = "6K\"qor9.$z:eJR|y0NRW!3afH}r*lDWi2r?QshB'/!^A%J=&[";
    private final Algorithm algorithm = Algorithm.HMAC256(secret);
    private final JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .build(); //Reusable verifier instance

    /**
     * Get the singleton instance of Authenticator.
     *
     * @return the singleton instance of Authenticator
     */
    public static Authenticator getInstance() {
        return instance;
    }


    /**
     * Generate JSON Web Token for a user.
     *
     * @param user user object
     * @return the jwt for the user
     */
    @Override
    public String getJwtForUser(IUser user) {
        if (user == null) {
            return "";
        }
        try {
            return JWT.create()
                    .withClaim("username", user.getUsername())
                    .withIssuer(issuer)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            return "";
        }
    }

    /**
     * Authenticate a user with given jwt.
     *
     * @param token JSON Web Token provided by the user
     * @return the user object. if failed to authenticate, return null
     */
    @Override
    public IUser authenticateJwt(String token) {
        if (token == null) {
            return null;
        }
        try {
            DecodedJWT jwt = verifier.verify(token);
            String username = jwt.getClaim("username").asString();
            return UserDB.getInstance().getUserInfo(username);
        } catch (JWTVerificationException exception) {
            return null;
        }
    }
}
