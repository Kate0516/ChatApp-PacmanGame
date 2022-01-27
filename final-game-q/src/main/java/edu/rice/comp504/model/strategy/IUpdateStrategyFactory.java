package edu.rice.comp504.model.strategy;

public interface IUpdateStrategyFactory {
    /**
     * Make the IUpdateStrategy Instance given specified type.
     *
     * @param type type of the strategy
     * @return IUpdateStrategy instance
     */
    IUpdateStrategy make(String type);
}
