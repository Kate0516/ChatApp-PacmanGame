package edu.rice.comp504.model.command;

import edu.rice.comp504.model.board.IPacmanBoard;
import edu.rice.comp504.model.strategy.UpdateStrategyFactory;

import java.util.Objects;

public class UpdateCommandFactory implements IUpdateCommandFactory {
    private UpdateCommandFactory() {
    }

    private static final UpdateCommandFactory instance = new UpdateCommandFactory();

    public static UpdateCommandFactory getInstance() {
        return instance;
    }

    @Override
    public IUpdateCommand make(String type, IPacmanBoard board) {
        if (Objects.equals(type, "location")) {
            return new UpdateLocationCommand(board);
        } else {
            return new UpdateNullCommand(board);
        }
    }
}
