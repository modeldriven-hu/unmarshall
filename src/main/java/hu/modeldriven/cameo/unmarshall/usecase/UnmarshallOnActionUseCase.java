package hu.modeldriven.cameo.unmarshall.usecase;

import com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.Action;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import hu.modeldriven.cameo.unmarshall.event.ActionDataAvailableEvent;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.usecase.AbstractUseCase;

import java.util.List;

public class UnmarshallOnActionUseCase extends AbstractUseCase {

    private List<Property> properties;
    private Action action;

    public UnmarshallOnActionUseCase(EventBus eventBus) {
        super(eventBus);
        eventBus.subscribe(ActionDataAvailableEvent.class, this::onDataAvailable);
        // FIXME add second action
    }

    private void onDataAvailable(ActionDataAvailableEvent event) {
        this.action = event.getAction();
        this.properties = event.getClazz().getOwnedAttribute();
    }

}
