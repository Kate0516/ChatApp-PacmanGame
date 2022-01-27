package edu.rice.comp504.model.object;

public abstract class AConsumableObject implements IObject {
    /**
     * Get the score of this object when pacman eats it.
     *
     * @return the score of the object
     */
    abstract int getScore();

}
