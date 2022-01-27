package edu.rice.comp504.model.object;

import edu.rice.comp504.model.board.BoardStore;
import edu.rice.comp504.model.board.IPacmanBoard;
import edu.rice.comp504.model.board.PacmanBoard;
import edu.rice.comp504.util.JsonStatusResponse;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BoardTest {
    //在pacmanboard里添加了两个函数用来测试
    /*
        public void setPacmanloc(Point p) {
        this.pacman.setLocation(p);
    }

    public void setLeftDots(int n) {
        this.leftDots = 0;
    }*/
    @Test
    void testStore() {
        BoardStore bs = BoardStore.getInstance();
        IPacmanBoard board = bs.getBoard(0);
        assertEquals(board,null);
        JsonStatusResponse js = bs.changeDirection(0,"lft");
        bs.createNewBoard(4,3);
        board = bs.getBoard(0);
        js = bs.changeDirection(0,"left");
        js = bs.start(0);
        bs.reset(0);
    }
    @Test
    void testBoard() {
        PacmanBoard board = new PacmanBoard(4,3);
        assertEquals(0,board.getScore());
        assertEquals(1,board.getLevel());
        assertEquals(3,board.getLife());

        board.init(4,3);
        assertEquals("right",board.getPacmanDirection());
        for(int i = 0; i < 101; i++){
            if(i % 4 == 1){
                board.changeDirection("left");
            }
            if(i % 4 == 2){
                board.changeDirection("right");
            }
            if(i % 4 == 3){
                board.changeDirection("up");
            }
            if(i % 4 == 0){
                board.changeDirection("down");
            }
            board.tick();
        }


        //board.tick();

        board.init(4,3);
        board.setPacmanloc(new Point(29,0));
        board.tick();
        board.setPacmanloc(new Point(1,25));
        board.tick();
        board.setPacmanloc(new Point(1,0));
        board.tick();
        board.tick();
        board.setPacmanloc(new Point(3,0));
        board.tick();
        board.init(4,3);
        board.setPacmanloc(new Point(12,13));
        board.tick();
        //board.tick();

        board.setLeftDots(0);
        //board.tick();
    }
    @Test
    void testStop(){
        PacmanBoard board = new PacmanBoard(4,3);
        board.stop();
    }
}
