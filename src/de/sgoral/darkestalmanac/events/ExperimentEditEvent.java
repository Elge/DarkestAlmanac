package de.sgoral.darkestalmanac.events;

import de.sgoral.darkestalmanac.data.dataobjects.Curio;
import de.sgoral.darkestalmanac.data.dataobjects.Experiment;
import javafx.event.Event;
import javafx.event.EventType;

public class ExperimentEditEvent extends Event {

    public static EventType<ExperimentEditEvent> EVENT_TYPE_ROOT = new EventType<>("EXPERIMENT_ROOT");
    public static EventType<ExperimentEditEvent> EVENT_TYPE_NEW = new EventType<>(EVENT_TYPE_ROOT, "EXPERIMENT_NEW");
    public static EventType<ExperimentEditEvent> EVENT_TYPE_EDIT = new EventType<>(EVENT_TYPE_ROOT, "EXPERIMENT_EDIT");
    public static EventType<ExperimentEditEvent> EVENT_TYPE_SAVE = new EventType<>(EVENT_TYPE_ROOT, "EXPERIMENT_SAVE");
    public static EventType<ExperimentEditEvent> EVENT_TYPE_CANCEL = new EventType<>(EVENT_TYPE_ROOT, "EXPERIMENT_CANCEL");
    public static EventType<ExperimentEditEvent> EVENT_TYPE_DELETE = new EventType<>(EVENT_TYPE_ROOT, "EXPERIMENT_DELETE");

    private final Curio curio;
    private final Experiment experiment;

    public ExperimentEditEvent(EventType<? extends Event> eventType, Curio curio, Experiment experiment) {
        super(eventType);
        this.curio = curio;
        this.experiment = experiment;
    }

    public Curio getCurio() {
        return curio;
    }

    public Experiment getExperiment() {
        return experiment;
    }

}
