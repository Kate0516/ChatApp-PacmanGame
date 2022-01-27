package edu.rice.comp504.model.object;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PacmanTest {
    @Test
    void testAll(){
        Pacman p = new Pacman(new Point(0,0));
        p.setFacingDirection("up");
        assertEquals(p.getFacingDirection(), "up");
        p.setFacingDirection("down");
        assertEquals(p.getFacingDirection(), "down");
        p.setFacingDirection("left");
        assertEquals(p.getFacingDirection(), "left");
        p.setFacingDirection("right");
        assertEquals(p.getFacingDirection(), "right");
        assertEquals(p.isImmuneStatus(), false);
        assertEquals(p.getObjectType().getCode(), 20);
    }
}
