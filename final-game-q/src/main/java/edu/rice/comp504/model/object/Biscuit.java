package edu.rice.comp504.model.object;

import edu.rice.comp504.model.command.IUpdateCommand;

import java.awt.*;
import java.beans.PropertyChangeEvent;

public class Biscuit extends AConsumableObject {
    private Point location;

    private ObjectType type;

    /**
     * Type: large or normal.
     * Large for Large dots
     *
     * @param type string of large or normal
     */
    public Biscuit(Point loc, String type) {
        this.location = new Point(loc);
        if (type.equals("large")) {
            this.type = ObjectType.LARGE_DOT;
        } else {
            this.type = ObjectType.BISCUIT;
        }
    }

    /**
     * Get the score of this object when pacman eats it.
     *
     * @return the score of the object
     */
    @Override
    public int getScore() {
        if (this.type == ObjectType.LARGE_DOT) {
            return 50;
        } else {
            return 10;
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
}
