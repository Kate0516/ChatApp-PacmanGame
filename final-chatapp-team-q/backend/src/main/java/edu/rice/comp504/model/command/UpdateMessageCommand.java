package edu.rice.comp504.model.command;

import edu.rice.comp504.model.message.IMessage;
import edu.rice.comp504.model.strategy.IUpdateMessageStrategy;
import edu.rice.comp504.model.user.IUser;

public class UpdateMessageCommand implements IMessageCommand {
    private IMessage message;
    private IUpdateMessageStrategy strategy;

    public UpdateMessageCommand(IMessage message, IUpdateMessageStrategy strategy) {
        this.message = message;
        this.strategy = strategy;
    }

    @Override
    public void execute(IUser user) {
        strategy.update(user, message);
    }
}
