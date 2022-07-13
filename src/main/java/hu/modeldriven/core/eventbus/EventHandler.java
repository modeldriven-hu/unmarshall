package hu.modeldriven.core.eventbus;

@FunctionalInterface
public interface EventHandler<T extends Event> {
    void handleEvent(T e);
}
