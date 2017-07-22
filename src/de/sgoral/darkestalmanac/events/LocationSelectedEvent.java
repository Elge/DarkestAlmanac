package de.sgoral.darkestalmanac.events;

import de.sgoral.darkestalmanac.data.dataobjects.Location;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class LocationSelectedEvent extends Event {

    public static EventType<LocationSelectedEvent> EVENT_TYPE = new EventType<>("LOCATION_SELECTED");

    private final Location location;

    public LocationSelectedEvent(Location location) {
        this(EVENT_TYPE, location);
    }

    public LocationSelectedEvent(EventType<? extends Event> eventType, Location location) {
        super(eventType);
        this.location = location;
    }

    public LocationSelectedEvent(Object source, EventTarget target, EventType<? extends Event> eventType, Location location) {
        super(source, target, eventType);
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
