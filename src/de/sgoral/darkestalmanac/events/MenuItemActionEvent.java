package de.sgoral.darkestalmanac.events;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class MenuItemActionEvent extends Event {

    public static EventType<MenuItemActionEvent> EVENT_TYPE_ROOT = new EventType<>("MENU_ITEM_ACTION");
    public static EventType<MenuItemActionEvent> EVENT_TYPE_NEW = new EventType<>(EVENT_TYPE_ROOT, "MENU_ITEM_ACTION_NEW");
    public static EventType<MenuItemActionEvent> EVENT_TYPE_OPEN = new EventType<>(EVENT_TYPE_ROOT, "MENU_ITEM_ACTION_OPEN");
    public static EventType<MenuItemActionEvent> EVENT_TYPE_SAVE = new EventType<>(EVENT_TYPE_ROOT, "MENU_ITEM_ACTION_SAVE");
    public static EventType<MenuItemActionEvent> EVENT_TYPE_SAVEAS = new EventType<>(EVENT_TYPE_ROOT, "MENU_ITEM_ACTION_SAVEAS");
    public static EventType<MenuItemActionEvent> EVENT_TYPE_EXIT = new EventType<>(EVENT_TYPE_ROOT, "MENU_ITEM_ACTION_EXIT");

    public MenuItemActionEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public MenuItemActionEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }
}
