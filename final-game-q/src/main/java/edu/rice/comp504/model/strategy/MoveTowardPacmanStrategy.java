package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.board.IPacmanBoard;
import edu.rice.comp504.model.object.Ghost;
import edu.rice.comp504.model.object.IObject;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class MoveTowardPacmanStrategy implements IUpdateStrategy {
    private static final MoveTowardPacmanStrategy INSTANCE = new MoveTowardPacmanStrategy();

    /**
     * Get the singleton instance.
     *
     * @return singleton instance.
     */
    public static MoveTowardPacmanStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "towards pacman";
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
            Point pacmanLoc = board.getPacmanLocation();
            Point nextPos = AStarSearch.towards(current, pacmanLoc, board);
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
