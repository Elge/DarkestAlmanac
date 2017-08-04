package de.sgoral.darkestalmanac.data.dataobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds all the locations, curios, consumables and effects.
 */
public class DataStorage implements Serializable {

    private List<Location> locations;
    private List<Effect> effects;
    private List<Consumable> consumables;
    private List<Curio> curios;
    private transient int lastId = 0;

    public DataStorage() {
        locations = new ArrayList<>();
        curios = new ArrayList<>();
        effects = new ArrayList<>();
        consumables = new ArrayList<>();
    }

    public static DataStorage createNewDataStorage() {
        DataStorage dataStorage = new DataStorage();
        dataStorage.addConsumable(new Consumable(dataStorage.generateId(), "None"));
        dataStorage.addEffect(new Effect(dataStorage.generateId(), "None", false, false));
        return dataStorage;
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

    public List<Curio> getCurios() {
        return curios;
    }

    public void setCurios(List<Curio> curios) {
        this.curios = curios;
    }

    public boolean addCurio(Curio curio) {
        return this.curios.add(curio);
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }

    public boolean addEffect(Effect effect) {
        return this.effects.add(effect);
    }

    public List<Consumable> getConsumables() {
        return consumables;
    }

    public void setConsumables(List<Consumable> consumables) {
        this.consumables = consumables;
    }

    public boolean addConsumable(Consumable consumable) {
        return this.consumables.add(consumable);
    }

    public void removeLocation(Location location) {
        locations.remove(location);

        List<Curio> toRemove = new ArrayList<>();

        for (Curio curio : curios) {
            if (curio.getLocations().contains(location)) {
                curio.getLocations().remove(location);
                if (curio.getLocations().size() == 0) {
                    toRemove.add(curio);
                }
            }
        }

        for (Curio curio : toRemove) {
            removeCurio(curio);
        }
    }

    public void removeCurio(Curio curio) {
        curios.remove(curio);

        for (Location location : locations) {
            location.getCurios().remove(curio);
        }
    }

    public int generateId() {
        return lastId++;
    }

    public void initialiseIdGenerator() {
        for (Location location : locations) {
            if (lastId < location.getId()) {
                lastId = location.getId();
            }
        }

        for (Consumable consumable : consumables) {
            if (lastId < consumable.getId()) {
                lastId = consumable.getId();
            }
        }

        for (Effect effect : effects) {
            if (lastId < effect.getId()) {
                lastId = effect.getId();
            }
        }

        for (Curio curio : curios) {
            if (lastId < curio.getId()) {
                lastId = curio.getId();
            }

            for (Experiment experiment : curio.getExperiments()) {
                if (lastId < experiment.getId()) {
                    lastId = experiment.getId();
                }

                for (Result result : experiment.getResults()) {
                    if (lastId < result.getId()) {
                        lastId = result.getId();
                    }
                }
            }
        }

        System.out.println("ID generator set to " + lastId);
    }
}
