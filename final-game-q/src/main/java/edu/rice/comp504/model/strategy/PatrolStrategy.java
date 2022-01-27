package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.board.IPacmanBoard;
import edu.rice.comp504.model.object.Ghost;
import edu.rice.comp504.model.object.IObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


// In patrol Strategy, ghost will walk around certain point.
public class PatrolStrategy implements IUpdateStrategy {
    private static final PatrolStrategy INSTANCE = new PatrolStrategy();
    private final Map<Point, Point> route = new HashMap<>();

    /**
     * Get the singleton instance.
     *
     * @return singleton instance.
     */
    public static PatrolStrategy getInstance() {
        return INSTANCE;
    }

    /**
     * Constructor. Initialize the route map.
     */
    private PatrolStrategy() {
        for (int i = 2; i <= 6; i++) {
            route.put(new Point(1, i - 1), new Point(1, i));
            route.put(new Point(5, 7 - i + 1), new Point(5, 7 - i));
        }

        for (int i = 2; i <= 5; i++) {
            route.put(new Point(i - 1, 6), new Point(i, 6));
            route.put(new Point(6 - i + 1, 1), new Point(6 - i, 1));
        }
    }

    @Override
    public String getName() {
        return "patrol";
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
            if (!route.containsKey(current)) {
                Point nextPos = AStarSearch.towards(current, new Point(1, 1), board);
                if (nextPos != null) {
                    for (Ghost ghost : board.getGhosts()) {
                        if (ghost != object && ghost.getLocation().equals(nextPos)) {
                            return;
                        }
                    }

                    ((Ghost) object).setLocation(nextPos);
                }
                return;
            }
            Point nextPos = route.get(current);
            for (Ghost ghost : board.getGhosts()) {
                if (ghost != object && ghost.getLocation().equals(nextPos)) {
                    return;
                }
            }
            ((Ghost) object).setLocation(nextPos);
        }
    }
}
