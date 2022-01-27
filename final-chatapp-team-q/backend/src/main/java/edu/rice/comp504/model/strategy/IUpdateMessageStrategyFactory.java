package edu.rice.comp504.model.strategy;

/**
 * UpdateMessageStrategyFactory to generate UpdateMessageStrategy.
 */
public interface IUpdateMessageStrategyFactory {
    /**
     * Make a new MessageStrategy.
     *
     * @param type type of the strategy, "edit", "send", "delete"
     * @return the IUpdateMessageStrategy instance
     */
    IUpdateMessageStrategy make(String type);
}
