package edu.rice.comp504.model.object;

public enum ObjectType {
    PACMAN(20),
    PACMAN_QUICKSILVER(21),
    GHOST(3),
    WALL(0),
    ROAD(2),
    BISCUIT(1),
    FRUIT_APPLE(5),
    FRUIT_BANANA(6),
    LARGE_DOT(4),
    QUICKSILVER_CHARM(7),
    FRIGHTENED_GHOST(8),
    GHOST_EYES_ONLY(9);

    private final int code;

    ObjectType(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
