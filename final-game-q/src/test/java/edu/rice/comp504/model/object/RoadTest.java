package edu.rice.comp504.model.object;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class RoadTest {

    @Test
    void testAll(){
        Road p = new Road(new Point(0,0));
        assertEquals(p.getObjectType().getCode(), 2);
        assertEquals(p.getLocation().x, 0);
        assertEquals(p.getLocation().y, 0);
    }

}