package de.sgoral.darkestalmanac.events;

import de.sgoral.darkestalmanac.data.dataobjects.Curio;
import de.sgoral.darkestalmanac.data.dataobjects.Location;
import javafx.event.Event;
import javafx.event.EventType;

public class CurioEditEvent extends Event {

    public static EventType<CurioEditEvent> EVENT_TYPE_ROOT = new EventType<>("CURIO_ROOT");
    public static EventType<CurioEditEvent> EVENT_TYPE_NEW = new EventType<>(EVENT_TYPE_ROOT, "CURIO_NEW");
    public static EventType<CurioEditEvent> EVENT_TYPE_EDIT = new EventType<>(EVENT_TYPE_ROOT, "CURIO_EDIT");
    public static EventType<CurioEditEvent> EVENT_TYPE_SAVE = new EventType<>(EVENT_TYPE_ROOT, "CURIO_SAVE");
    public static EventType<CurioEditEvent> EVENT_TYPE_CANCEL = new EventType<>(EVENT_TYPE_ROOT, "CURIO_CANCEL");
    public static EventType<CurioEditEvent> EVENT_TYPE_DELETE = new EventType<>(EVENT_TYPE_ROOT, "CURIO_DELETE");

    private final Curio curio;
    private final Location location;

    public CurioEditEvent(EventType<? extends Event> eventType, Location currentLocation, Curio curio) {
        super(eventType);
        this.location = currentLocation;
        this.curio = curio;
    }

    public Curio getCurio() {
        return curio;
    }

    public Location getLocation() {
        return location;
    }
}
