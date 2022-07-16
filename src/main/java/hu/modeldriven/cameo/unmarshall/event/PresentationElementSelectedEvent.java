package hu.modeldriven.cameo.unmarshall.event;

import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.Action;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import hu.modeldriven.cameo.unmarshall.common.PresentationAction;
import hu.modeldriven.core.eventbus.Event;

public class PresentationElementSelectedEvent implements Event {

    private final PresentationElement presentationElement;

    public PresentationElementSelectedEvent(PresentationElement presentationElement) {
        this.presentationElement = presentationElement;
    }

    public PresentationElement getPresentationElement() {
        return presentationElement;
    }

    public Element getElement(){
        return presentationElement.getElement();
    }
}
