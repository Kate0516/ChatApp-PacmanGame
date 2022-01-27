package edu.rice.comp504.model.user;

import edu.rice.comp504.model.command.IMessageCommand;

import java.beans.PropertyChangeEvent;

public class User extends AUser {
    public User(String username, String password, String school, int age, String... interests) {
        super(username, password, school, age, interests);
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ((IMessageCommand) evt.getNewValue()).execute(this);
    }

    @Override
    public int getHateSpeechCount() {
        return 0;
    }

    @Override
    public int countHateSpeech() {
        return 0;
    }

    @Override
    public boolean isUserBanedBySystem() {
        return false;
    }
}
