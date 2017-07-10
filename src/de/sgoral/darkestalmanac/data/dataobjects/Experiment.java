package de.sgoral.darkestalmanac.data.dataobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An experiment is the action of using a consumable (or none at all) on a curio to see what happens.
 */
public class Experiment implements Serializable {

    private int id;
    private int consumableId;
    private transient Consumable consumable;
    private List<Result> results = new ArrayList<>();

    public Experiment(int id, Consumable consumable) {
        this(id, consumable, null);
    }

    public Experiment(int id, Result result) {
        this(id, null, result);
    }

    public Experiment(int id, Consumable consumable, Result result) {
        this.id = id;
        this.consumable = consumable;
        this.results.add(result);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConsumableId() {
        return consumableId;
    }

    public void setConsumableId(int consumableId) {
        this.consumableId = consumableId;
    }

    public Consumable getConsumable() {
        return consumable;
    }

    public void setConsumable(Consumable consumable) {
        this.consumable = consumable;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public boolean addResult(Result result) {
        return this.results.add(result);
    }
}
