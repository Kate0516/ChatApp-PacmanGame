package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.board.IPacmanBoard;
import edu.rice.comp504.model.object.IObject;

public interface IUpdateStrategy {
    String getName();

    /**
     * Update the state of a IObject.
     * The state including location,
     *
     * @param object object to be updated
     * @param board  of the pacman game
     */
    void update(IObject object, IPacmanBoard board);
}
