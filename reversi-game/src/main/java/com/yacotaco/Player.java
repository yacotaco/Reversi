package com.yacotaco;

/**
 * Player class.
 *
 * @author Kamil Kurach
 * @author https://github.com/yacotaco
 * @version 1.0
 */
public class Player {
    /** Player name. */
    private String name;
    /** Number of player points. */
    private Integer points;
    /** Player disc state which translates to disc color. */
    private Integer discState;

    /** Player constructor. */
    public Player() {

    }

    /**
     * Gets player name.
     *
     * @return player name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets player name.
     *
     * @param playerName player name.
     */
    public void setName(final String playerName) {
        this.name = playerName;
    }

    /**
     * Gets player points.
     *
     * @return player points.
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * Sets player points.
     *
     * @param playerPoints player points.
     */
    public void setPoints(final Integer playerPoints) {
        this.points = playerPoints;
    }

    /**
     * Gets disc state. (0 - white, 1 -black)
     *
     * @return disc state.
     */
    public Integer getDiscState() {
        return discState;
    }

    /**
     * Sets disc state translated to color of disc on the board.
     *
     * @param state disc state.
     */
    public void setDiscState(final Integer state) {
        this.discState = state;
    }
}
