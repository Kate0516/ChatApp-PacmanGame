package edu.rice.comp504.model.object;

import edu.rice.comp504.model.board.PacmanBoard;
import edu.rice.comp504.model.command.IUpdateCommand;
import edu.rice.comp504.model.command.UpdateCommandFactory;
import edu.rice.comp504.model.strategy.UpdateStrategyFactory;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CommandTest {
    @Test
    void testAll(){
        UpdateCommandFactory fa = UpdateCommandFactory.getInstance();
        PacmanBoard pac = new PacmanBoard(4,3);
        IUpdateCommand com = fa.make("location",pac);
        IUpdateCommand com1 = fa.make("null",pac);
        Ghost g = new Ghost(new Point(18, 19), UpdateStrategyFactory.getInstance().make("random"));
        Point loc = g.getLocation();
        com.execute(g);
        assertNotEquals(g.getLocation(),loc);

    }
}
