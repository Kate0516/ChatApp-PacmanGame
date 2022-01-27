package edu.rice.comp504.model.command;

import edu.rice.comp504.model.object.Ghost;
import edu.rice.comp504.model.object.IObject;

public interface IUpdateCommand {
    /**
     * Execute certain command on the object.
     *
     * @param object object to execute command
     */
    void execute(IObject object);
}
