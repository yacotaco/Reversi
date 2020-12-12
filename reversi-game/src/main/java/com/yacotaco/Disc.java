package com.yacotaco;

/**
 * Disc class.
 *
 * @author Kamil Kurach
 * @author https://github.com/yacotaco
 * @version 1.0
 */
public class Disc {
    /** Position in row. */
    private Integer row;
    /** Position in column. */
    private Integer col;
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
     * @param colValue position in column.
     */
    public void setCol(final Integer colValue) {
        this.col = colValue;
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
     * @param rowValue position in row.
     */
    public void setRow(final Integer rowValue) {
        this.row = rowValue;
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
     * @param discState disc state.
     */
    public void setState(final Integer discState) {
        this.state = discState;
    };
}
