package hu.modeldriven.cameo.unmarshall.usecase;

import com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.Action;
import com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.InputPin;
import com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.Pin;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import hu.modeldriven.cameo.unmarshall.event.ActionSelectedEvent;
import hu.modeldriven.cameo.unmarshall.event.PresentationElementSelectedEvent;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.usecase.AbstractUseCase;

public class CreateSelectionFromInputPinUseCase extends AbstractUseCase {

    public CreateSelectionFromInputPinUseCase(EventBus eventBus) {
        super(eventBus);
        eventBus.subscribe(PresentationElementSelectedEvent.class, this::onDiagramItemSelected);
    }

    public void onDiagramItemSelected(PresentationElementSelectedEvent event) {
        var element = event.getElement();

        if (element instanceof InputPin && element.getOwner() instanceof Action) {
            var pin = (Pin) element;
            var action = (Action) pin.getOwner();
            var type = pin.getType();

            if (type instanceof Class) {
                eventBus.publish(new ActionSelectedEvent(action, (Class) type));
            }
        }
    }

}
