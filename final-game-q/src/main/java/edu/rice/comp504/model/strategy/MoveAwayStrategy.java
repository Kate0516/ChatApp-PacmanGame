package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.board.IPacmanBoard;
import edu.rice.comp504.model.object.Ghost;
import edu.rice.comp504.model.object.IObject;

import java.awt.*;

public class MoveAwayStrategy implements IUpdateStrategy {
    private static final MoveAwayStrategy INSTANCE = new MoveAwayStrategy();

    /**
     * Get the singleton instance.
     *
     * @return singleton instance.
     */
    public static MoveAwayStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "away";
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
            Point pacmanPos = board.getPacmanLocation();
            Point nextPos = GreedySearch.away(current, pacmanPos, board);
            for (Ghost ghost : board.getGhosts()) {
                if (ghost != object && ghost.getLocation().equals(nextPos)) {
                    return;
                }
            }

            ((Ghost) object).setLocation(nextPos);
        }
    }
}
