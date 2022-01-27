package edu.rice.comp504.model.board;

import edu.rice.comp504.model.object.Ghost;

import java.awt.*;
import java.util.ArrayList;

public interface IPacmanBoard {
    /**
     * Get the 2D array of the board.
     * Each element in the array represent an element on the board.
     *
     * @return 2D array of the board
     */
    int[][] getBoard();

    /**
     * Get current score for the pacman.
     *
     * @return current score for the pacman.
     */
    int getScore();

    /**
     * Get current life for the pacman.
     *
     * @return current life for the pacman.
     */
    int getLife();


    /**
     * During each tick, update all object location.
     * Then do collision detection.
     */
    void tick();

    /**
     * Get current level for the game.
     *
     * @return the current game level
     */
    int getLevel();

    /**
     * Pause the game.
     */
    void stop();

    /**
     * Start/resume the game.
     */
    void start();

    /**
     * Init the board, reset all objects to their initial location.
     *
     * @param numberOfGhost number of ghost in the game
     * @param pacmanLife    how many life does pacman initially has
     */
    void init(int numberOfGhost, int pacmanLife);

    /**
     * Get the location for the pacman.
     *
     * @return the location for the pacman
     */
    Point getPacmanLocation();

    void changeDirection(String direction);

    /**
     * Get the direction for the pacman.
     *
     * @return direction of the pacman
     */
    String getPacmanDirection();

    /**
     * Get all ghosts in the board.
     *
     * @return all the ghosts
     */
    ArrayList<Ghost> getGhosts();
}
