package de.sgoral.darkestalmanac.data.dataobjects;

import java.io.Serializable;

/**
 * The effect an interaction with a curio might have on the heroes.
 */
public class Effect implements Serializable {

    private int id;
    private String name;
    private boolean positive;

    public Effect(int id, String name, boolean positive) {
        this.id = id;
        this.name = name;
        this.positive = positive;
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

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }
}
