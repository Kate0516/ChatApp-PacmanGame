package edu.rice.comp504.model.board;

import edu.rice.comp504.util.JsonStatusResponse;

public interface IBoardStore {

    /**
     * Get this board with the given board ID.
     *
     * @param boardId the board ID
     * @return this pacman board.
     */
    IPacmanBoard getBoard(int boardId);

    /**
     * Create a new board.
     *
     * @return the id of the new board
     */
    int createNewBoard(int ghostNum, int lifeNum);


    /**
     * Change Pacman's direction on the board of the specific id.
     *
     * @param boardId   the id of the board
     * @param direction the new direction
     * @return the JsonStatusResponse
     */
    JsonStatusResponse changeDirection(int boardId, String direction);


    /**
     * Start this board with the given board ID.
     *
     * @param boardId the board ID
     * @return the JsonStatusResponse
     */
    JsonStatusResponse start(int boardId);

    /**
     * Reset this board with the given board ID.
     *
     * @param boardId the board ID
     * @return the board
     */
    IPacmanBoard reset(int boardId);

}
