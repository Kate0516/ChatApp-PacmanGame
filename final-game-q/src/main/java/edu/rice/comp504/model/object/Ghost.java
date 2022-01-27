package edu.rice.comp504.model.object;

import edu.rice.comp504.model.command.IUpdateCommand;
import edu.rice.comp504.model.strategy.IUpdateStrategy;
import edu.rice.comp504.model.strategy.UpdateStrategyFactory;

import java.awt.*;
import java.beans.PropertyChangeEvent;

public class Ghost implements IObject {
    private IUpdateStrategy updateStrategy;
    private IUpdateStrategy originStrategy;
    private Point location;
    private Point startLoc;
    private ObjectType type;

    /**
     * Crate ghost.
     *
     * @param loc    location
     * @param strtgy strategy
     */
    public Ghost(Point loc, IUpdateStrategy strtgy) {
        this.location = new Point(loc);
        this.startLoc = new Point(loc);
        this.updateStrategy = strtgy;
        this.originStrategy = strtgy;
        this.type = ObjectType.GHOST;
    }

    public void setUpdateStrategy(IUpdateStrategy updateStrategy) {
        this.updateStrategy = updateStrategy;
    }

    public IUpdateStrategy getUpdateStrategy() {
        return updateStrategy;
    }

    /**
     * Get the start location for the object.
     *
     * @return the start location of the object
     */
    public Point getStartLocation() {
        return this.startLoc;
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

    /**
     * Set the location for the object.
     *
     * @param loc location of ghost
     */
    public void setLocation(Point loc) {
        this.location = loc;
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

    public void setType(ObjectType type) {
        this.type = type;
    }

    public void resetStrategy() {
        this.updateStrategy = UpdateStrategyFactory.getInstance().make(this.originStrategy.getName());
    }
}
