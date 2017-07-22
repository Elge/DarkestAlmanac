package de.sgoral.darkestalmanac.events;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

import java.io.File;

public class FileSelectedEvent extends Event {

    public static EventType<FileSelectedEvent> EVENT_TYPE = new EventType<>("FILE_SELECTED");

    private final File selectedFile;

    public FileSelectedEvent(File selectedFile) {
        this(EVENT_TYPE, selectedFile);
    }

    public FileSelectedEvent(EventType<? extends Event> eventType, File selectedFile) {
        super(eventType);
        this.selectedFile = selectedFile;
    }

    public FileSelectedEvent(Object source, EventTarget target, EventType<? extends Event> eventType, File selectedFile) {
        super(source, target, eventType);
        this.selectedFile = selectedFile;
    }

    public File getSelectedFile() {
        return selectedFile;
    }
}
