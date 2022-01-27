package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.board.IPacmanBoard;
import edu.rice.comp504.model.object.IObject;

public class NullStrategy implements IUpdateStrategy {
    private NullStrategy() {

    }

    private static final IUpdateStrategy instance = new NullStrategy();

    public static IUpdateStrategy getInstance() {
        return instance;
    }

    @Override
    public String getName() {
        return null;
    }

    /**
     * Update the state of a IObject.
     * The state including location,
     *
     * @param object object to be updated
     * @param board  of the pacman game
     */
    @Override
    public void update(IObject object, IPacmanBoard board) {
    }
}
