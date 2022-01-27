package edu.rice.comp504.model.object;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BiscuitTest {
    @Test
    void testAll(){
        Biscuit p = new Biscuit(new Point(0,0),"large");
        assertEquals(p.getObjectType().getCode(), 4);
        assertEquals(p.getScore(), 50);
        assertEquals(p.getLocation().x, 0);
        assertEquals(p.getLocation().y, 0);

        Biscuit sp = new Biscuit(new Point(0,0),"small");
        assertEquals(sp.getObjectType().getCode(), 1);
        assertEquals(sp.getScore(), 10);
    }
}
