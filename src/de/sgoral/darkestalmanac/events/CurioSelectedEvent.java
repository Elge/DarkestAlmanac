package de.sgoral.darkestalmanac.events;

import de.sgoral.darkestalmanac.data.dataobjects.Curio;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class CurioSelectedEvent extends Event {

    public static EventType<CurioSelectedEvent> EVENT_TYPE = new EventType<>("CURIO_SELECTED");

    private final Curio curio;

    public CurioSelectedEvent(Curio curio) {
        this(EVENT_TYPE, curio);
    }

    public CurioSelectedEvent(EventType<? extends Event> eventType, Curio curio) {
        super(eventType);
        this.curio = curio;
    }

    public CurioSelectedEvent(Object source, EventTarget target, EventType<? extends Event> eventType, Curio curio) {
        super(source, target, eventType);
        this.curio = curio;
    }

    public Curio getCurio() {
        return curio;
    }
}
