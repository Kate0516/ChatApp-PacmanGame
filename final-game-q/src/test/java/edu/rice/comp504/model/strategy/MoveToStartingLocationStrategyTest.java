package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.board.PacmanBoard;
import edu.rice.comp504.model.command.IUpdateCommand;
import edu.rice.comp504.model.command.UpdateCommandFactory;
import edu.rice.comp504.model.object.Ghost;
import edu.rice.comp504.model.object.ObjectType;
import edu.rice.comp504.model.object.Pacman;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MoveToStartingLocationStrategyTest {

    @Test
    void getName() {
        assertEquals("to starting", MoveToStartingLocationStrategy.getInstance().getName());
    }

    @Test
    void update() {
        PacmanBoard board = new PacmanBoard(1, 3);
        for (int i = 0; i < 3; i++) {
            board.tick();
        }
        board.getGhosts().get(0).setUpdateStrategy(UpdateStrategyFactory.getInstance().make("to starting"));
        board.getGhosts().get(0).setType(ObjectType.FRIGHTENED_GHOST);
        for (int i = 0; i < 10; i++) {
            MoveToStartingLocationStrategy.getInstance().update(board.getGhosts().get(0), board);
        }
        assertEquals(board.getGhosts().get(0).getObjectType(), ObjectType.GHOST);
        assertEquals(board.getGhosts().get(0).getLocation(), board.getGhosts().get(0).getStartLocation());
    }
}