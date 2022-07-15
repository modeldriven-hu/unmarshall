package hu.modeldriven.cameo.unmarshall.usecase;

import hu.modeldriven.cameo.unmarshall.event.ActionDataAvailableEvent;
import hu.modeldriven.cameo.unmarshall.ui.PropertyRecord;
import hu.modeldriven.cameo.unmarshall.ui.UnmarshallPanel;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.usecase.AbstractUseCase;

import java.util.stream.Collectors;

public class SetSelectionDataUseCase extends AbstractUseCase {

    private final UnmarshallPanel panel;

    public SetSelectionDataUseCase(EventBus eventBus, UnmarshallPanel panel) {
        super(eventBus);
        this.panel = panel;
        eventBus.subscribe(ActionDataAvailableEvent.class, this::onDataAvailable);
    }

    private void onDataAvailable(ActionDataAvailableEvent event) {
        var properties = event.getClazz().getOwnedAttribute();

        var propertyRecords = properties
                .stream()
                .map(p -> p.getName())
                .map(name -> new PropertyRecord(name))
                .collect(Collectors.toList());

        this.panel.setPropertyRecords(propertyRecords);
    }

}
