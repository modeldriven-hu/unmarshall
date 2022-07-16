package hu.modeldriven.cameo.unmarshall.event;

import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.Action;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import hu.modeldriven.cameo.unmarshall.common.PresentationAction;
import hu.modeldriven.core.eventbus.Event;

public class ActionSelectedEvent implements Event {

    private final PresentationAction action;
    private final Class clazz;

    public ActionSelectedEvent(PresentationAction action, Class clazz) {
        this.action = action;
        this.clazz = clazz;
    }

    public PresentationAction getAction() {
        return action;
    }

    public Class getClazz() {
        return clazz;
    }

}
