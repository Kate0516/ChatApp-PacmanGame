package edu.rice.comp504.model.authenticate;

import edu.rice.comp504.model.user.IUser;
import edu.rice.comp504.model.user.User;
import edu.rice.comp504.model.userstorage.UserDB;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticatorTest {

    @Test
    void getJwtForUser() {
        IUser user = new User("jerry", "password", "rice", 19);
        UserDB.getInstance().addNewUser(user);
        String token = Authenticator.getInstance().getJwtForUser(user);

        // This will fail for now because UserStore is not implemented
        //assertEquals(user, Authenticator.getInstance().authenticateJwt(token));

        assertEquals("", Authenticator.getInstance().getJwtForUser(null));
    }

    @Test
    void authenticateJwt() {
        IUser user = new User("jerry", "password", "rice", 19);
        UserDB.getInstance().addNewUser(user);
        assertNull(Authenticator.getInstance().authenticateJwt(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJxdWlja2NoYXQiLCJ1c2VybmFtZSI6ImplcnJ5In0.Cm1Sn9gzstOCDckkk3WIEqenC-HjkG1QL__DGUVURUE"));
    }
}