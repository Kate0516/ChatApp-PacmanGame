package edu.rice.comp504.model.user;

import edu.rice.comp504.util.HiddenInGson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * AUser abstract class defining constructor for the User.
 */
public abstract class AUser implements IUser {
    private String username;
    @HiddenInGson
    private String password;

    private String school;

    private int age;

    private final ArrayList<String> interests;

    /**
     * User constructor.
     *
     * @param username  username
     * @param password  password
     * @param school    school
     * @param interests interests
     */
    public AUser(String username, String password, String school, int age, String... interests) {
        this.username = username;
        this.password = password;
        this.school = school;
        this.age = age;
        this.interests = new ArrayList<>();
        this.interests.addAll(Arrays.asList(interests));
    }

    /**
     * Get the username.
     *
     * @return username
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Get user school.
     *
     * @return school
     */
    @Override
    public String getSchool() {
        return this.school;
    }

    /**
     * Get user interests.
     *
     * @return interests
     */
    @Override
    public ArrayList<String> getInterests() {
        return (ArrayList<String>) this.interests.clone();
    }

    /**
     * if the password match with the user's password.
     *
     * @return bool
     */
    public boolean authentication(String password) {
        return Objects.equals(this.password, password);
    }
}
