package edu.rice.comp504.model.board;

import edu.rice.comp504.model.command.UpdateCommandFactory;
import edu.rice.comp504.model.object.*;
import edu.rice.comp504.model.strategy.UpdateStrategyFactory;

import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class PacmanBoard implements IPacmanBoard {

    public PacmanBoard(int numberOfGhost, int pacmanLife) {
        this.numberOfGhost = numberOfGhost;
        this.init(numberOfGhost, pacmanLife);
    }

    private static final int foodRefreshTime = 50;
    private static final int charmRefreshTime = 50;
    private int tickCount = 0;
    private static final int[][] originBoard = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 0},
            {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0},
            {0, 4, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 4, 0},
            {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 2, 0, 0, 2, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 2, 3, 3, 3, 3, 2, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 0, 2, 3, 3, 3, 3, 2, 0, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 2, 2, 2, 2, 2, 2, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 2, 2, 2, 2, 20, 2, 2, 2, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0},
            {0, 4, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 4, 0},
            {0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 7, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
    private final int numberOfGhost;
    private boolean caneat;
    // The board only stores consumable objects and walls.
    // Ghost and Pacman are stored separately.
    private IObject[][] board;

    private ArrayList<Ghost> ghosts;

    private Pacman pacman;

    private int level;

    private int score;

    private int life;

    private Timer timer = new Timer();

    PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private int leftDots;

    private int fruitFlashTime;
    private int quicksilverFlashTime;
    private int moveAwayTimeMax = 50;
    private int immuneTime;
    private int moveAwayTime;

    private final int ghostScore = 200;
    private int eatenGhostNum = 0;

    /**
     * Get the 2D array of the board.
     * Each element in the array represent an element on the board.
     *
     * @return 2D array of the board
     */
    @Override
    public int[][] getBoard() {
        int[][] intboard = new int[31][28];
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < 28; j++) {
                intboard[i][j] = this.board[i][j].getObjectType().getCode();

                //If ghost and PacMan occupies the same coordinate, pacman should be on top
                for (Ghost g : ghosts) {
                    Point gpos = g.getLocation();
                    intboard[(int) gpos.getX()][(int) gpos.getY()] = g.getObjectType().getCode();
                }

                Point pacpos = this.pacman.getLocation();
                intboard[(int) pacpos.getX()][(int) pacpos.getY()] = this.pacman.getObjectType().getCode();
            }
        }
        return intboard;
    }

    @Override
    public void changeDirection(String direction) {
        this.pacman.setFacingDirection(direction);
    }

    /**
     * Get current score for the pacman.
     *
     * @return current score for the pacman.
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * During each tick, update all object location.
     * Then do collision detection.
     * pacman and ghost move one unit
     * if pacman meet ghost, pacman life minus one
     * wall should not move
     * if consumable, add points and transform to road（to avoid null exception）
     */
    @Override
    public void tick() {
        tickCount++;

        //refresh fruit and quicksilver
        this.fruitFlashTime--;
        this.quicksilverFlashTime--;
        if (this.immuneTime > 0) {
            this.immuneTime--;
        } else {
            this.pacman.setImmuneStatus(false);
            this.pacman.setType(ObjectType.PACMAN);
        }

        if (this.moveAwayTime > 0) {
            this.moveAwayTime--;
        } else if (this.moveAwayTime == 0) {
            for (int i = 0; i < ghosts.size(); i++) {
                if (i % 3 == 0) {
                    ghosts.get(i).setUpdateStrategy(UpdateStrategyFactory.getInstance().make("towards pacman"));
                } else if (i % 3 == 1) {
                    ghosts.get(i).setUpdateStrategy(UpdateStrategyFactory.getInstance().make("patrol"));
                } else {
                    ghosts.get(i).setUpdateStrategy(UpdateStrategyFactory.getInstance().make("random"));
                }
                ghosts.get(i).setType(ObjectType.GHOST);
            }
            this.caneat = false;
        }

        if (fruitFlashTime <= 0) {
            int x = (int) Math.floor(Math.random() * 30);
            int y = (int) Math.floor(Math.random() * 28);
            int count = 40;
            while (this.board[x][y].getObjectType() != ObjectType.ROAD && count > 0) {
                x = (int) Math.floor(Math.random() * 30);
                y = (int) Math.floor(Math.random() * 28);
                count -= 1;
            }
            if (count > 0) {
                int z = (int) Math.floor(Math.random() * 3);
                if (z <= 1) {
                    this.board[x][y] = new Fruit(new Point(x, y), "banana");
                } else {
                    this.board[x][y] = new Fruit(new Point(x, y), "apple");
                }
                this.fruitFlashTime = foodRefreshTime;
            }
        }
        if (quicksilverFlashTime <= 0) {
            int x = (int) Math.floor(Math.random() * 30);
            int y = (int) Math.floor(Math.random() * 28);
            int count = 40;
            while (this.board[x][y].getObjectType() != ObjectType.ROAD && count > 0) {
                x = (int) Math.floor(Math.random() * 30);
                y = (int) Math.floor(Math.random() * 28);
                count -= 1;
            }
            if (count > 0) {
                this.board[x][y] = new QuickSilverCharm(new Point(x, y));
                this.quicksilverFlashTime = 100;
            }

        }

        //assume ghost do not run into walls
        if (tickCount % 3 != 0) {
            pcs.firePropertyChange("", null, UpdateCommandFactory.getInstance().make("location", this));
        }

        Point pacpos = this.pacman.getLocation();
        String dir = this.pacman.getFacingDirection();
        int x = 0;
        int y = 0;
        int vel = this.pacman.getVelocity();
        if (Objects.equals(dir, "up")) {
            x = pacpos.x - vel;
            y = pacpos.y;
        } else if (Objects.equals(dir, "down")) {
            x = pacpos.x + vel;
            y = pacpos.y;
        } else if (Objects.equals(dir, "left")) {
            x = pacpos.x;
            y = pacpos.y - vel;
            //pacman teleport through tunnel
            if (y <= 0) {
                y = 27;
            }
        } else if (Objects.equals(dir, "right")) {
            x = pacpos.x;
            y = pacpos.y + vel;
            if (y >= 28) {
                y = 0;
            }
        }
        if (x >= 0 && x < 31 && y >= 0 && y < 28 && this.board[x][y].getObjectType() != ObjectType.WALL) {
            //pacman move to the next location if not blocked by wall
            this.pacman.setLocation(new Point(x, y));
        }

        //check whether consumable is activated
        if (this.board[x][y].getObjectType() == ObjectType.BISCUIT || this.board[x][y].getObjectType() == ObjectType.LARGE_DOT) {
            if (this.board[x][y].getObjectType() == ObjectType.LARGE_DOT) {
                for (Ghost g : this.ghosts) {
                    g.setType(ObjectType.FRIGHTENED_GHOST);
                    g.setUpdateStrategy(UpdateStrategyFactory.getInstance().make("away"));
                }
                this.moveAwayTime = moveAwayTimeMax;
                this.eatenGhostNum = 0;
                this.caneat = true;
            }
            Biscuit bis = (Biscuit) this.board[x][y];
            this.score += bis.getScore();
            this.board[x][y] = new Road(new Point(x, y));
            this.leftDots--;
        } else if (this.board[x][y].getObjectType() == ObjectType.FRUIT_APPLE || this.board[x][y].getObjectType() == ObjectType.FRUIT_BANANA) {
            Fruit bis = (Fruit) this.board[x][y];
            this.score += bis.getScore();
            this.board[x][y] = new Road(new Point(x, y));
        } else if (this.board[x][y].getObjectType() == ObjectType.QUICKSILVER_CHARM) {
            QuickSilverCharm bis = (QuickSilverCharm) this.board[x][y];
            this.score += bis.getScore();
            this.board[x][y] = new Road(new Point(x, y));
            this.pacman.setImmuneStatus(true);
            //change pacman type to quicksilver
            this.pacman.setType(ObjectType.PACMAN_QUICKSILVER);
            this.immuneTime = 20;
        }

        //check if meet ghost
        for (Ghost g : ghosts) {
            Point gpos = g.getLocation();
            if (gpos.equals(pacpos)) {
                if (this.caneat) {
                    if (!g.getUpdateStrategy().getName().equals("to starting")) {
                        this.score += Math.pow(2, this.eatenGhostNum) * this.ghostScore;
//                        System.out.println(Math.pow(2, this.eatenGhostNum) * this.ghostScore);
                        this.eatenGhostNum += 1;
                    }
                    g.setUpdateStrategy(UpdateStrategyFactory.getInstance().make("to starting"));
                    g.setType(ObjectType.GHOST_EYES_ONLY);
                } else {
                    if (!this.pacman.isImmuneStatus()) {
                        this.life--;
                        //lose only one life if meet multiple ghost together
                        this.immuneTime = 6;
                        this.pacman.setImmuneStatus(true);
                        break;
                    }
                }

            }
        }

        //game over
        if (this.life <= 0) {
            stop();
        }

        if (this.leftDots <= 0) {
            this.level++;
            int prelevel = this.level;
            int prescore = this.score;
            init(numberOfGhost, this.life);
            this.level = prelevel;
            this.score = prescore;
            this.stop();
            this.start();
        }
    }

    /**
     * Get current level for the game.
     *
     * @return the current game level
     */
    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getLife() {
        return life;
    }

    /**
     * Pause the game.
     */
    @Override
    public void stop() {
        timer.cancel();
    }

    /**
     * Start/resume the game.
     */
    @Override
    public void start() {
        //  the refresh rate of tick is related to level
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        }, 0, Math.max(300 - (this.level - 1) * 100, 100));
    }

    @Override
    public void init(int numberOfGhost, int pacmanLife) {
        //start from level one
        this.level = 1;
        this.life = pacmanLife;
        this.ghosts = new ArrayList<Ghost>();
        this.score = 0;
        this.leftDots = 0;
        //refresh after 50 ticms
        this.fruitFlashTime = foodRefreshTime;
        this.quicksilverFlashTime = charmRefreshTime;
        this.moveAwayTime = 0;
        this.caneat = false;

        this.board = new IObject[31][28];
        int ghostCount = 0;
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < 28; j++) {
                if (originBoard[i][j] == 0) {
                    this.board[i][j] = new Wall(new Point(i, j));
                } else if (originBoard[i][j] == 1) {
                    this.board[i][j] = new Biscuit(new Point(i, j), "small");
                    this.leftDots++;
                } else if (originBoard[i][j] == 3) {
                    //assign different strategy to each ghost
                    if (ghostCount < numberOfGhost) {
                        Ghost g;
                        if (ghostCount % 3 == 0) {
                            g = new Ghost(new Point(i, j), UpdateStrategyFactory.getInstance().make("random"));
                        } else if (ghostCount % 3 == 1) {
                            g = new Ghost(new Point(i, j), UpdateStrategyFactory.getInstance().make("random"));
                        } else {
                            g = new Ghost(new Point(i, j), UpdateStrategyFactory.getInstance().make("random"));
                        }
                        ghosts.add(g);
                        pcs.addPropertyChangeListener(g);
                        ghostCount++;
                    }
                    this.board[i][j] = new Road(new Point(i, j));
                } else if (originBoard[i][j] == 4) {
                    this.board[i][j] = new Biscuit(new Point(i, j), "large");
                    this.leftDots++;
                } else if (originBoard[i][j] == 5) {
                    this.board[i][j] = new Fruit(new Point(i, j), "apple");
                } else if (originBoard[i][j] == 6) {
                    this.board[i][j] = new Fruit(new Point(i, j), "banana");
                } else if (originBoard[i][j] == 7) {
                    this.board[i][j] = new QuickSilverCharm(new Point(i, j));
                } else if (originBoard[i][j] == 20) {
                    Pacman p = new Pacman(new Point(i, j));
                    this.board[i][j] = new Road(new Point(i, j));
                    this.pacman = p;
                } else {
                    this.board[i][j] = new Road(new Point(i, j));
                }
            }
        }

    }

    /**
     * Get the location for the pacman.
     *
     * @return the location for the pacman
     */
    @Override
    public Point getPacmanLocation() {
        return this.pacman.getLocation();
    }


    /**
     * Get the direction for the pacman.
     *
     * @return direction of the pacman
     */
    @Override
    public String getPacmanDirection() {
        return this.pacman.getFacingDirection();
    }

    //this method is only relevant to the test cases
    public void setPacmanloc(Point p) {
        this.pacman.setLocation(p);
    }

    //this method is only relevant to the test cases
    public void setLeftDots(int n) {
        this.leftDots = 0;
    }


    /**
     * Get all ghosts in the board.
     *
     * @return all the ghosts
     */
    @Override
    public ArrayList<Ghost> getGhosts() {
        return this.ghosts;
    }
}
