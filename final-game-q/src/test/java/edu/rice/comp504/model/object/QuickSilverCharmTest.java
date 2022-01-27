package edu.rice.comp504.model.object;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class QuickSilverCharmTest {
    @Test
    void testAll(){
        QuickSilverCharm p = new QuickSilverCharm(new Point(0,0));
        assertEquals(p.getObjectType().getCode(), 7);
        assertEquals(p.getScore(), 100);
        assertEquals(p.getLocation().x, 0);
        assertEquals(p.getLocation().y, 0);
    }
}