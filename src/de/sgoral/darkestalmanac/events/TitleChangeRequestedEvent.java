package de.sgoral.darkestalmanac.events;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class TitleChangeRequestedEvent extends Event {

    public static EventType<TitleChangeRequestedEvent> EVENT_TYPE = new EventType<>("TITLE_CHANGE_REQUESTED");

    private final String title;

    public TitleChangeRequestedEvent(String title) {
        this(EVENT_TYPE, title);
    }

    public TitleChangeRequestedEvent(EventType<? extends Event> eventType, String title) {
        super(eventType);
        this.title = title;
    }

    public TitleChangeRequestedEvent(Object source, EventTarget target, EventType<? extends Event> eventType, String title) {
        super(source, target, eventType);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
