package de.sgoral.darkestalmanac.data.dataobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A location to adventure in.
 */
public class Location implements Serializable {

    private Integer id;
    private String name;
    private transient List<Curio> curios = new ArrayList<>();

    public Location() {
    }

    public Location(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Curio> getCurios() {
        return curios;
    }

    public void setCurios(List<Curio> curios) {
        this.curios = curios;
    }

    public void addCurio(Curio curio) {
        this.curios.add(curio);
    }
}
