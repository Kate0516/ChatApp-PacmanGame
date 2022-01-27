package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.board.IPacmanBoard;
import edu.rice.comp504.model.object.Ghost;
import edu.rice.comp504.model.object.IObject;
import edu.rice.comp504.model.object.ObjectType;

import java.awt.*;

public class MoveToStartingLocationStrategy implements IUpdateStrategy {
    private static final MoveToStartingLocationStrategy INSTANCE = new MoveToStartingLocationStrategy();

    /**
     * Get the singleton instance.
     *
     * @return singleton instance.
     */
    public static MoveToStartingLocationStrategy getInstance() {
        return INSTANCE;
    }


    @Override
    public String getName() {
        return "to starting";
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
        if (object instanceof Ghost) {
            Point current = object.getLocation();
            Point startingLoc = ((Ghost) object).getStartLocation();
            if (current.equals(startingLoc)) {
                ((Ghost) object).setType(ObjectType.GHOST);
                ((Ghost) object).resetStrategy();
                return;
            }
            Point nextPos = AStarSearch.towards(current, startingLoc, board);
            if (nextPos != null) {
                for (Ghost ghost : board.getGhosts()) {
                    if (ghost != object && ghost.getLocation().equals(nextPos)) {
                        return;
                    }
                }
                ((Ghost) object).setLocation(nextPos);
            }
        }
    }
}
