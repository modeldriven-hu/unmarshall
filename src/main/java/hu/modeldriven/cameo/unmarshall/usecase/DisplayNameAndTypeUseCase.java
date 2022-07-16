package hu.modeldriven.cameo.unmarshall.usecase;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.magicdraw.uml.symbols.shapes.PinView;
import hu.modeldriven.cameo.unmarshall.common.PresentationAction;
import hu.modeldriven.cameo.unmarshall.common.PresentationElementHelper;
import hu.modeldriven.cameo.unmarshall.event.ActionDataAvailableEvent;
import hu.modeldriven.cameo.unmarshall.event.CloseDialogRequestedEvent;
import hu.modeldriven.cameo.unmarshall.event.PinsUnmarshalledEvent;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.usecase.AbstractUseCase;

public class DisplayNameAndTypeUseCase extends AbstractUseCase {

    private PresentationAction action;

    public DisplayNameAndTypeUseCase(EventBus eventBus) {
        super(eventBus);
        eventBus.subscribe(ActionDataAvailableEvent.class, this::onDataAvailable);
        eventBus.subscribe(PinsUnmarshalledEvent.class, this::onPinsUnmarshalled);
    }

    private void onDataAvailable(ActionDataAvailableEvent event) {
        this.action = event.getAction();
    }

    private void onPinsUnmarshalled(PinsUnmarshalledEvent event) {

        var project = Application.getInstance().getProject();

        try {
            SessionManager.getInstance().createSession(project, "Displaying pin name and type");

            for (var presentationElement : action.getPresentationElement().getPresentationElements()) {
                if (presentationElement instanceof PinView) {
                    var pinView = (PinView) presentationElement;

                    if (event.getProperties().contains(pinView.getElement().getName())) {
                        PresentationElementHelper.showNameAndType(presentationElement);
                    }
                }
            }

            SessionManager.getInstance().closeSession(project);
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
        } finally {
            eventBus.publish(new CloseDialogRequestedEvent());
        }
    }

}
