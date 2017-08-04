package de.sgoral.darkestalmanac.data.dataobjects;

import java.io.Serializable;

/**
 * The result of an interaction with a curio.
 */
public class Result implements Serializable {

    private int id;
    private int effectId;
    private transient Effect effect;
    private String comment;
    private int times;

    public Result(int id, Effect effect) {
        this(id, effect, null, 1);
    }

    public Result(int id, Effect effect, String comment) {
        this(id, effect, comment, 1);
    }

    public Result(int id, Effect effect, int times) {
        this(id, effect, null, times);
    }

    public Result(int id, Effect effect, String comment, int times) {
        this.id = id;
        this.effect = effect;
        this.comment = comment;
        this.times = times;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEffectId() {
        return effectId;
    }

    public void setEffectId(int effectId) {
        this.effectId = effectId;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public void incrementTimes() {
        this.times++;
    }

    public void decrementTimes() {
        this.times--;
    }

}
