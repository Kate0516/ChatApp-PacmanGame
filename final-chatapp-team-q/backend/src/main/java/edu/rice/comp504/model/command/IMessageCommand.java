package edu.rice.comp504.model.command;

import edu.rice.comp504.model.user.IUser;

/**
 * MessageCommand interface to update user based on a message.
 */
public interface IMessageCommand {
    /**
     * Execute a Command on user
     *
     * @param user the user to be executed the command on.
     */
    void execute(IUser user);
}
