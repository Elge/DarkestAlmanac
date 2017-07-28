package de.sgoral.darkestalmanac.events;

import de.sgoral.darkestalmanac.data.dataobjects.Location;
import javafx.event.Event;
import javafx.event.EventType;

public class EditLocationEvent extends Event {

    public static EventType<EditLocationEvent> EVENT_TYPE_ROOT = new EventType<>("LOCATION_ROOT");
    public static EventType<EditLocationEvent> EVENT_TYPE_NEW = new EventType<>(EVENT_TYPE_ROOT, "LOCATION_NEW");
    public static EventType<EditLocationEvent> EVENT_TYPE_EDIT = new EventType<>(EVENT_TYPE_ROOT, "LOCATION_EDIT");
    public static EventType<EditLocationEvent> EVENT_TYPE_SAVE = new EventType<>(EVENT_TYPE_ROOT, "LOCATION_SAVE");
    public static EventType<EditLocationEvent> EVENT_TYPE_CANCEL = new EventType<>(EVENT_TYPE_ROOT, "LOCATION_CANCEL");
    public static EventType<EditLocationEvent> EVENT_TYPE_DELETE = new EventType<>(EVENT_TYPE_ROOT, "LOCATION_DELETE");

    private final Location location;

    public EditLocationEvent(EventType<? extends Event> eventType, Location location) {
        super(eventType);
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
