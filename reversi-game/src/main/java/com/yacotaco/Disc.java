package com.yacotaco;

/**
 * Disc
 */
public class Disc {
    private Integer col;
    private Integer row;
    private Integer state;

    /**
     * @param col   column position on board
     * @param row   row position on board
     * @param state state translates to disc color (0 - white player, 1 - black
     *              player)
     */

    public Disc() {
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    };
}