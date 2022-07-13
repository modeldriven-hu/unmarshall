package hu.modeldriven.cameo.unmarshall.usecase;

import com.nomagic.magicdraw.core.Application;
import hu.modeldriven.cameo.unmarshall.event.ActionSelectedEvent;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.usecase.UseCase;

public class ActionSelectedUseCase implements UseCase {

    public ActionSelectedUseCase() {
    }

    @Override
    public void register(EventBus eventBus) {
        eventBus.subscribe(ActionSelectedEvent.class, this::onActionSelected);
    }

    private void onActionSelected(ActionSelectedEvent event) {
        Application.getInstance().getGUILog().showError("event = " + event);
    }

}
