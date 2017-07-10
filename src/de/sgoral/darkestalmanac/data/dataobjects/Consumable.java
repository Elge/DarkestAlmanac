package de.sgoral.darkestalmanac.data.dataobjects;

import java.io.Serializable;

/**
 * A consumable for the heroes to use.
 */
public class Consumable implements Serializable {

    private int id;
    private String name;

    public Consumable(int id, String name) {
        this.id = id;
        this.name = name;
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
}
