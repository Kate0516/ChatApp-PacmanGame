package edu.rice.comp504.model.object;

import java.awt.*;
import java.beans.PropertyChangeListener;

public interface IObject extends PropertyChangeListener {
    /**
     * Get the location for the object.
     *
     * @return the location of the object
     */
    Point getLocation();

    /**
     * Get the type of the object.
     *
     * @return type of the object
     */
    ObjectType getObjectType();
}
