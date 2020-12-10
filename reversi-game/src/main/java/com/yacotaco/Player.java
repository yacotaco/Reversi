package com.yacotaco;

/**
 * Player
 */
public class Player {
    private String name;
    private Integer points;
    private Integer discState;

    public Player() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getDiscState() {
        return discState;
    }

    public void setDiscState(Integer discState) {
        this.discState = discState;
    }
}