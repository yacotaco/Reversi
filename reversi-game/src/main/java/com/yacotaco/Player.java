package com.yacotaco;

/**
 * Player
 */
public class Player {
    private String name;
    private Integer points;

    /**
     * @param playerName player name
     * @param points     scored points
     */

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
}