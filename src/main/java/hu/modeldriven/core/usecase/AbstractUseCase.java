package hu.modeldriven.core.usecase;

import hu.modeldriven.core.eventbus.EventBus;

public class AbstractUseCase implements UseCase {

    protected final EventBus eventBus;

    public AbstractUseCase(EventBus eventBus) {
        this.eventBus = eventBus;
    }

}
