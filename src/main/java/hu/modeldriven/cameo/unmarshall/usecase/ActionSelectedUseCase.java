package hu.modeldriven.cameo.unmarshall.usecase;

import hu.modeldriven.cameo.unmarshall.event.ActionDataAvailableEvent;
import hu.modeldriven.cameo.unmarshall.event.ActionSelectedEvent;
import hu.modeldriven.cameo.unmarshall.ui.UnmarshallDialog;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.usecase.AbstractUseCase;

public class ActionSelectedUseCase extends AbstractUseCase {

    private final UnmarshallDialog dialog;

    public ActionSelectedUseCase(EventBus eventBus) {
        super(eventBus);
        eventBus.subscribe(ActionSelectedEvent.class, this::onActionSelected);
        this.dialog = new UnmarshallDialog(null, eventBus);
    }

    private void onActionSelected(ActionSelectedEvent event) {
        this.dialog.setVisible(true);
        eventBus.publish(new ActionDataAvailableEvent(event.getAction(), event.getClazz()));
    }

}
