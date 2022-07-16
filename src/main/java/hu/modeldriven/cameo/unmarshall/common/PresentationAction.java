package hu.modeldriven.cameo.unmarshall.common;

import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.Action;

public class PresentationAction {

    private final PresentationElement presentationElement;
    private final Action action;

    public PresentationAction(PresentationElement presentationElement){
        this.presentationElement = presentationElement;
        this.action = (Action)presentationElement.getElement();
    }

    public PresentationElement getPresentationElement() {
        return presentationElement;
    }

    public Action getAction() {
        return action;
    }
}
