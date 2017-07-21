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

    public DataStorage() {
        locations = new ArrayList<>();
        curios = new ArrayList<>();
        effects = new ArrayList<>();
        consumables = new ArrayList<>();
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
}
