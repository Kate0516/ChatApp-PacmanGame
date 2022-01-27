package edu.rice.comp504.model.command;

import edu.rice.comp504.model.board.IPacmanBoard;

public interface IUpdateCommandFactory {
    /**
     * Make the IUpdateCommand instance given specified type.
     *
     * @param type  type of the command
     * @param board of the game
     * @return IUpdateCommand instance
     */
    IUpdateCommand make(String type, IPacmanBoard board);
}
