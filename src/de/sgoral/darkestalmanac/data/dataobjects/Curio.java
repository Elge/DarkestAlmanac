package de.sgoral.darkestalmanac.data.dataobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A curio to interact with.
 */
public class Curio implements Serializable {

    private int id;
    private String name;
    private int[] locationIds;
    private transient List<Location> locations = new ArrayList<>();
    private List<Experiment> experiments = new ArrayList<>();

    public Curio(int id, String name, Location location) {
        this.id = id;
        this.name = name;

        this.locations.add(location);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(int[] locationIds) {
        this.locationIds = locationIds;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public boolean addLocation(Location location) {
        return this.locations.add(location);
    }

    public List<Experiment> getExperiments() {
        return experiments;
    }

    public void setExperiments(List<Experiment> experiments) {
        this.experiments = experiments;
    }

    public boolean addExperiment(Experiment experiment) {
        return this.experiments.add(experiment);
    }
}
