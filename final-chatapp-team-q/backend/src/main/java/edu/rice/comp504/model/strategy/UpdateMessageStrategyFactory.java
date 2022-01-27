package edu.rice.comp504.model.strategy;

/**
 * update notes:
 * 10.30: implemented make method
 */

public class UpdateMessageStrategyFactory implements IUpdateMessageStrategyFactory {
    private UpdateMessageStrategyFactory() {
    }

    private static final UpdateMessageStrategyFactory instance = new UpdateMessageStrategyFactory();

    public static UpdateMessageStrategyFactory getInstance() {
        return instance;
    }

    @Override
    public IUpdateMessageStrategy make(String type) {
        switch (type) {
            case "send":
                return SendMessageStrategy.getInstance();
            case "delete":
                return DeleteMessageStrategy.getInstance();
            case "edit":
                return EditMessageStrategy.getInstance();
            case "update_info":
                return UpdateInfoStrategy.getInstance();
            default:
                return NullMessageStrategy.getInstance();
        }
    }
}
