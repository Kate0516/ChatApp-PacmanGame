package edu.rice.comp504.model.object;

import edu.rice.comp504.model.command.IUpdateCommand;

import java.awt.*;
import java.beans.PropertyChangeEvent;

public class Wall implements IObject {
    private Point location;

    public Wall(Point loc) {
        this.location = new Point(loc);
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
     * Get the type of the object.
     *
     * @return type of the object
     */
    @Override
    public ObjectType getObjectType() {
        return ObjectType.WALL;
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
}
