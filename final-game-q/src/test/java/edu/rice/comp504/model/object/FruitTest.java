package edu.rice.comp504.model.object;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FruitTest {

    @Test
    void testAll(){
        Fruit p = new Fruit(new Point(0,0),"apple");
        assertEquals(p.getObjectType().getCode(), 5);
        assertEquals(p.getScore(), 100);
        assertEquals(p.getLocation().x, 0);
        assertEquals(p.getLocation().y, 0);

        Fruit n = new Fruit(new Point(0,0),"mango");
        assertEquals(n.getObjectType(), null);
    }
}
