package com.yacotaco;

/**
 * Disc class.
 *
 * @author Kamil Kurach
 * @author https://github.com/yacotaco
 * @version 1.0
 */
public class Disc {
    /** Position in column. */
    private Integer col;
    /** Position in row. */
    private Integer row;
    /** Disc state. */
    private Integer state;

    /** Disc constructor. */
    public Disc() {
    }

    /**
     * Gets disc column coordinate.
     *
     * @return column value.
     */
    public Integer getCol() {
        return col;
    }

    /**
     * Sets disc column coordinate.
     *
     * @param col position in column.
     */
    public void setCol(final Integer col) {
        this.col = col;
    }

    /**
     * Gets disc row coordinate.
     *
     * @return position in column.
     */
    public Integer getRow() {
        return row;
    }

    /**
     * Sets disc row coordiante.
     *
     * @param row position in row.
     */
    public void setRow(Integer row) {
        this.row = row;
    }

    /**
     * Gets disc state.
     *
     * @return disc state.
     */
    public Integer getState() {
        return state;
    }

    /**
     * Sets disc state.
     *
     * @param state disc state.
     */
    public void setState(Integer state) {
        this.state = state;
    };
}
