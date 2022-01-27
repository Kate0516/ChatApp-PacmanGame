package edu.rice.comp504.model.command;

import edu.rice.comp504.model.message.IMessage;
import edu.rice.comp504.model.strategy.UpdateMessageStrategyFactory;

/**
 * update notes:
 * 10.31: implemented make()
 */

public class MessageCommandFactory implements IMessageCommandFactory {
    private MessageCommandFactory() {
    }

    private static final MessageCommandFactory instance = new MessageCommandFactory();

    public static MessageCommandFactory getInstance() {
        return instance;
    }

    @Override
    public IMessageCommand make(IMessage message, String method) {

        return new UpdateMessageCommand(message, UpdateMessageStrategyFactory.getInstance().make(method));

    }
}
