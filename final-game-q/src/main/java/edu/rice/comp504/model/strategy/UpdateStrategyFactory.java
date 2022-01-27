package edu.rice.comp504.model.strategy;

public class UpdateStrategyFactory implements IUpdateStrategyFactory {
    private UpdateStrategyFactory() {
    }

    private static final UpdateStrategyFactory instance = new UpdateStrategyFactory();

    /**
     * Get instance for UpdateStrategyFactory.
     *
     * @return the instance
     */
    public static UpdateStrategyFactory getInstance() {
        return instance;
    }

    /**
     * Make the IUpdateStrategy Instance given specified type.
     *
     * @param type type of the strategy
     * @return IUpdateStrategy instance
     */
    @Override
    public IUpdateStrategy make(String type) {
        switch (type) {
            case "away":
                return MoveAwayStrategy.getInstance();
            case "to starting":
                return MoveToStartingLocationStrategy.getInstance();
            case "towards pacman":
                return MoveTowardPacmanStrategy.getInstance();
            case "patrol":
                return PatrolStrategy.getInstance();
            case "random":
                return RandomStrategy.getInstance();
            default:
                return NullStrategy.getInstance();
        }
    }
}
