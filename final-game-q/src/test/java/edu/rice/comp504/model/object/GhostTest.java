package edu.rice.comp504.model.object;

import edu.rice.comp504.model.strategy.UpdateStrategyFactory;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GhostTest {
    @Test
    void testAll(){
        Ghost g = new Ghost(new Point(0,0), UpdateStrategyFactory.getInstance().make("away"));
        assertEquals(g.getStartLocation().x, 0);
        assertEquals(g.getStartLocation().y, 0);
        assertEquals(g.getObjectType().getCode(), 3);

    }
}
