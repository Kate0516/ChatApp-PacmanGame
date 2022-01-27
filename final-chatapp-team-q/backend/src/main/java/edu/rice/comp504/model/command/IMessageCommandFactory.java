package edu.rice.comp504.model.command;

import edu.rice.comp504.model.message.IMessage;

/**
 * MessageCommandFactory to generate IMessageCommands.
 */
public interface IMessageCommandFactory {
    /**
     * Make a IMessageCommand with given method.
     *
     * @param message message au
     * @param method  method of the command, delete, send, edit
     * @return IMessageCommand object
     */
    IMessageCommand make(IMessage message, String method);
}
