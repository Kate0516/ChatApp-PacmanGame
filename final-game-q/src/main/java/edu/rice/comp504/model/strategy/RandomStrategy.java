package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.board.IPacmanBoard;
import edu.rice.comp504.model.object.Ghost;
import edu.rice.comp504.model.object.IObject;
import edu.rice.comp504.model.object.ObjectType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class RandomStrategy implements IUpdateStrategy {
    private static final RandomStrategy INSTANCE = new RandomStrategy();

    /**
     * Get the singleton instance.
     *
     * @return singleton instance.
     */
    public static RandomStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "random";
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
        Point current = object.getLocation();
        Random rand = new Random();
        ArrayList<Point> successors = new ArrayList<Point>();

        for (int i = 0; i < 4; i++) {
            int newX = current.x;
            int newY = current.y;
            if (i == 0) {
                newX -= 1;
            } else if (i == 1) {
                newX += 1;
            } else if (i == 2) {
                newY -= 1;
            } else {
                newY += 1;
            }
            int[][] gameBoard = board.getBoard();
            if (newX > 0 && newY > 0 && newX < gameBoard[0].length && newY < gameBoard.length && gameBoard[newX][newY] != 0) {
                if (gameBoard[newX][newY] != ObjectType.GHOST.getCode() && gameBoard[newX][newY] != ObjectType.GHOST_EYES_ONLY.getCode()
                        && gameBoard[newX][newY] != ObjectType.FRIGHTENED_GHOST.getCode()) {
                    successors.add(new Point(newX, newY));
                }

            }
        }
        if (successors.size() == 0) {
            return;
        }
        Point nextPos = successors.get(rand.nextInt(successors.size()));
        for (Ghost ghost : board.getGhosts()) {
            if (ghost != object && ghost.getLocation().equals(nextPos)) {
                return;
            }
        }
        ((Ghost) object).setLocation(nextPos);
    }
}
