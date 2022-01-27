package edu.rice.comp504.model.object;

import edu.rice.comp504.model.command.IUpdateCommand;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.Objects;


public class Pacman implements IObject {

    private Direction facingDirection;
    private boolean isImmune;
    private Point location;
    private int velocity;
    private ObjectType type;

    public int getVelocity() {
        return this.velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    /**
     * The constructor of the Pacman.
     *
     * @param loc the location of the Pacman.
     */
    public Pacman(Point loc) {
        this.location = loc;
        this.isImmune = false;
        this.type = ObjectType.PACMAN;
        this.facingDirection = Direction.RIGHT;
        this.velocity = 1;
    }

    /**
     * Set the Pacman's direction.
     *
     * @param facingDirection the direction to face.
     */
    public void setFacingDirection(String facingDirection) {
        if (Objects.equals(facingDirection, "up")) {
            this.facingDirection = Direction.UP;
        } else if (Objects.equals(facingDirection, "down")) {
            this.facingDirection = Direction.DOWN;
        } else if (Objects.equals(facingDirection, "left")) {
            this.facingDirection = Direction.LEFT;
        } else if (Objects.equals(facingDirection, "right")) {
            this.facingDirection = Direction.RIGHT;
        }
    }

    /**
     * Get the Pacman's facing direction.
     *
     * @return the Pacman's facing direction
     */
    public String getFacingDirection() {
        if (this.facingDirection == Direction.UP) {
            return "up";
        } else if (this.facingDirection == Direction.DOWN) {
            return "down";
        } else if (this.facingDirection == Direction.LEFT) {
            return "left";
        } else {
            return "right";
        }
    }

    /**
     * Get the location for the object.
     *
     * @return the location of the object
     */
    @Override
    public Point getLocation() {
        return this.location;
    }

    public void setLocation(Point pos) {
        this.location = new Point(pos);
    }

    /**
     * Get the type of the object.
     *
     * @return type of the object
     */
    @Override
    public ObjectType getObjectType() {
        return this.type;
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ((IUpdateCommand) evt.getNewValue()).execute(this);
    }


    /**
     * If pacman is in immune status, the pacman can kill the ghost.
     *
     * @return if the pacman is in immune status
     */
    public boolean isImmuneStatus() {
        return isImmune;
    }


    /**
     * Set the Pacman's immunization status.
     *
     * @param immune the immunization status
     */
    public void setImmuneStatus(boolean immune) {
        isImmune = immune;
    }

    /**
     * Set object type for pacman.
     *
     * @param type type
     */
    public void setType(ObjectType type) {
        this.type = type;
    }
}
