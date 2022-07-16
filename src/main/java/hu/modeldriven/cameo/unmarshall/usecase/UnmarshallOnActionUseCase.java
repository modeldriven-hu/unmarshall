package hu.modeldriven.cameo.unmarshall.usecase;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.Action;
import com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.Pin;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import hu.modeldriven.cameo.unmarshall.common.Orientation;
import hu.modeldriven.cameo.unmarshall.common.PinType;
import hu.modeldriven.cameo.unmarshall.common.PresentationAction;
import hu.modeldriven.cameo.unmarshall.event.ActionDataAvailableEvent;
import hu.modeldriven.cameo.unmarshall.event.PinsUnmarshalledEvent;
import hu.modeldriven.cameo.unmarshall.event.UnmarshallRequestedEvent;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.usecase.AbstractUseCase;

import java.util.List;

public class UnmarshallOnActionUseCase extends AbstractUseCase {

    private List<Property> properties;
    private PresentationAction action;

    public UnmarshallOnActionUseCase(EventBus eventBus) {
        super(eventBus);
        eventBus.subscribe(ActionDataAvailableEvent.class, this::onDataAvailable);
        eventBus.subscribe(UnmarshallRequestedEvent.class, this::onUnmarshallRequested);
    }

    private void onDataAvailable(ActionDataAvailableEvent event) {
        this.action = event.getAction();
        this.properties = event.getClazz().getOwnedAttribute();
    }

    private void onUnmarshallRequested(UnmarshallRequestedEvent event) {

        var project = Application.getInstance().getProject();

        try {
            SessionManager.getInstance().createSession(project, "Adding pins to action");

            this.properties.stream()
                    .filter(p -> event.getProperties().contains(p.getName())).
                    forEach(p -> createProperty(project,
                            action.getAction(),
                            event.getOrientation(),
                            event.getPinType(),
                            p));

            SessionManager.getInstance().closeSession(project);

            eventBus.publish(new PinsUnmarshalledEvent(event.getProperties()));

        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
        }
    }

    private void createProperty(Project project, Action action, Orientation orientation, PinType pinType, Property property) {
        if (pinType == PinType.Input) {
            var pin = project.getElementsFactory().createInputPinInstance();
            setPinValues(pin, property);
            action.getInput().add(pin);
        } else {
            var pin = project.getElementsFactory().createOutputPinInstance();
            setPinValues(pin, property);
            action.getOutput().add(pin);
        }
    }

    private void setPinValues(Pin pin, Property property) {
        pin.setName(property.getName());
        pin.setType(property.getType());
        pin.setUpperValue(property.getUpperValue());
        pin.setLowerValue(property.getLowerValue());
    }

}
