package hu.modeldriven.cameo.unmarshall.event;

import com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.Action;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import hu.modeldriven.core.eventbus.Event;

public class ActionDataAvailableEvent implements Event {

    private final Action action;
    private final Class clazz;

    public ActionDataAvailableEvent(Action action, Class clazz) {
        this.action = action;
        this.clazz = clazz;
    }

    public Action getAction() {
        return action;
    }

    public Class getClazz() { return clazz; }

}
