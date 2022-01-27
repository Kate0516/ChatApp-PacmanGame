package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.message.IMessage;
import edu.rice.comp504.model.user.IUser;

/**
 * update notes:
 * 10.30:
 * new class created in case of unrecognized request
 * added singleton structure
 */

public class NullMessageStrategy implements IUpdateMessageStrategy {
    private NullMessageStrategy() {
    }

    private static final IUpdateMessageStrategy instance = new NullMessageStrategy();

    public static IUpdateMessageStrategy getInstance() {
        return instance;
    }

    @Override
    public void update(IUser user, IMessage message) {

    }
}
