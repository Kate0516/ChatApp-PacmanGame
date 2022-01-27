package edu.rice.comp504.model.user;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * User interface defines the basic behavior of a user.
 */
public interface IUser extends PropertyChangeListener {
    /**
     * Get username.
     *
     * @return username
     */
    String getUsername();

    /**
     * Get user school.
     *
     * @return user school
     */
    String getSchool();

    /**
     * Get user interests.
     *
     * @return user's interests
     */
    ArrayList<String> getInterests();

    /**
     * Get how many times a user said 'hate speech'.
     *
     * @return times of hate speech
     */
    int getHateSpeechCount();

    /**
     * Accumulate user count of hate speech by 1.
     *
     * @return the new hate speech count
     */
    int countHateSpeech();

    /**
     * check if a user is banned because using 'hate speech'.
     *
     * @return true if the user is banned.
     */
    boolean isUserBanedBySystem();

}
