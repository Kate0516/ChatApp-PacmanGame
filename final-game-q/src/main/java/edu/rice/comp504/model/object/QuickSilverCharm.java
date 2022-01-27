package edu.rice.comp504.model.object;

import edu.rice.comp504.model.command.IUpdateCommand;

import java.awt.*;
import java.beans.PropertyChangeEvent;

public class QuickSilverCharm extends AConsumableObject {

    private Point location;

    public QuickSilverCharm(Point loc) {
        this.location = new Point(loc);
    }


    /**
     * Get the score of this object when pacman eats it.
     *
     * @return the score of the object
     */
    @Override
    public int getScore() {
        return 100;
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
        return ObjectType.QUICKSILVER_CHARM;
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
