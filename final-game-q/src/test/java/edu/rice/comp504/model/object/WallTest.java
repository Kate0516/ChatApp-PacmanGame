package edu.rice.comp504.model.object;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {
    @Test
    void testAll(){
        Wall p = new Wall(new Point(0,0));
        assertEquals(p.getObjectType().getCode(), 0);
        assertEquals(p.getLocation().x, 0);
        assertEquals(p.getLocation().y, 0);
    }

}