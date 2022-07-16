package hu.modeldriven.cameo.unmarshall.event;

import hu.modeldriven.core.eventbus.Event;

import java.util.List;

public class PinsUnmarshalledEvent implements Event {

    private final List<String> properties;

    public PinsUnmarshalledEvent(List<String> properties) {
        this.properties = properties;
    }

    public List<String> getProperties() {
        return properties;
    }
}
