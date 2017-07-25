package de.sgoral.darkestalmanac.events;

import de.sgoral.darkestalmanac.data.dataobjects.Experiment;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class ExperimentSelectedEvent extends Event {

    public static EventType<ExperimentSelectedEvent> EVENT_TYPE = new EventType<>("EXPERIMENT_SELECTED");

    private final Experiment experiment;

    public ExperimentSelectedEvent(Experiment experiment) {
        this(EVENT_TYPE, experiment);
    }

    public ExperimentSelectedEvent(EventType<? extends Event> eventType, Experiment experiment) {
        super(eventType);
        this.experiment = experiment;
    }

    public ExperimentSelectedEvent(Object source, EventTarget target, EventType<? extends Event> eventType, Experiment experiment) {
        super(source, target, eventType);
        this.experiment = experiment;
    }

    public Experiment getExperiment() {
        return experiment;
    }
}
