package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.board.IPacmanBoard;
import edu.rice.comp504.model.object.ObjectType;

import java.awt.*;

/**
 * Greedy search algorithm util.
 */
public class GreedySearch {

    /**
     * Calculate the Manhattan Distance
     *
     * @param a one point
     * @param b the other point
     * @return manhattan distance between two points.
     */
    private static int manhattanDist(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    /**
     * Using Greedy algorithm to find the next location on the path moving away from target point.
     *
     * @param start  start location
     * @param target target location
     * @param board  the game board
     * @return next location to move away
     */
    public static Point away(Point start, Point target, IPacmanBoard board) {
        int farthest = -1;
        Point nextPos = start;
        for (int i = 0; i <= 3; i++) {
            int newX = start.x;
            int newY = start.y;
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
            if (newX > 0 && newY > 0 && newX < gameBoard.length && newY < gameBoard[0].length && gameBoard[newX][newY] != 0) {
                if (gameBoard[newX][newY] != ObjectType.GHOST.getCode() && gameBoard[newX][newY] != ObjectType.GHOST_EYES_ONLY.getCode()
                        && gameBoard[newX][newY] != ObjectType.FRIGHTENED_GHOST.getCode()) {
                    Point successor = new Point(newX, newY);
                    int dist = manhattanDist(successor, target);
                    if (dist > farthest) {
                        farthest = dist;
                        nextPos = successor;
                    }
                }

            }

        }
        return nextPos;
    }

}
