package hu.modeldriven.cameo.unmarshall.event;

import hu.modeldriven.cameo.unmarshall.common.Orientation;
import hu.modeldriven.cameo.unmarshall.common.PinType;
import hu.modeldriven.core.eventbus.Event;

import java.util.List;

public class UnmarshallRequestedEvent implements Event {

    private final PinType pinType;
    private final Orientation orientation;
    private final List<String> properties;

    public UnmarshallRequestedEvent(PinType pinType, Orientation orientation, List<String> properties) {
        this.pinType = pinType;
        this.orientation = orientation;
        this.properties = properties;
    }

    public PinType getPinType() {
        return pinType;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public List<String> getProperties() {
        return properties;
    }
}
