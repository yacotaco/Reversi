package com.yacotaco;

import java.util.ArrayList;

/**
 * Board class.
 *
 * @author Kamil Kurach
 * @author https://github.com/yacotaco
 * @version 1.0
 */
public class Board {
    /** Number of rows. */
    private final int rows = 8;
    /** Number of columns. */
    private final int cols = 8;
    /** Representation of board in 2d array. */
    private Disc[][] boardGrid = new Disc[rows][cols];

    /**
     * Board constructor.
     */
    public Board() {
        initBoard();
    }

    /**
     * Inits board with two discs for each player.
     * All discs are initially set to -1 which translates
     * in view to empty square on board.
     */
    public void initBoard() {
        Integer state = -1;
        for (int row = 0; row < boardGrid.length; row++) {
            for (int col = 0; col < boardGrid[row].length; col++) {
                addDisc(row, col, state);
            }
        }

        modifyDiscState(3, 3, 0);
        modifyDiscState(3, 4, 1);
        modifyDiscState(4, 3, 1);
        modifyDiscState(4, 4, 0);
    }

    /**
     * Adds disc object to board array.
     *
     * @param row   position in row.
     * @param col   position in column.
     * @param discState disc state.
     */
    private void addDisc(final Integer row, final Integer col,
     final Integer discState) {
        Disc disc = new Disc();
        disc.setRow(row);
        disc.setCol(col);
        disc.setState(discState);
        boardGrid[row][col] = disc;
    }

    /**
     * Gets disc object from board for given coordinates.
     *
     * @param row position in row.
     * @param col position in column.
     * @return Disc object.
     */
    public Disc getDiscFromBoard(final Integer row, final Integer col) {
        Disc disc = boardGrid[row][col];
        return disc;
    }

    /**
     * Modifies state of disc.
     *
     * @param row   position in row.
     * @param col   position in column.
     * @param discState disc state.
     */
    public void modifyDiscState(final Integer row, final Integer col,
     final Integer discState) {
        Disc disc = getDiscFromBoard(row, col);
        disc.setState(discState);
    }

    /**
     * Gets all discs of particular player.
     *
     * @param currentPlayer player disc state (or playerTurn).
     * @see playerTurn.
     * @return list of Disc objects.
     */
    public ArrayList<Disc> getAllPlayerDiscs(final Integer currentPlayer) {
        ArrayList<Disc> list = new ArrayList<Disc>();
        for (int row = 0; row < boardGrid.length; row++) {
            for (int col = 0; col < boardGrid[row].length; col++) {
                Disc disc = getDiscFromBoard(row, col);
                int discState = disc.getState();
                if (discState == currentPlayer) {
                    list.add(disc);
                }
            }
        }
        return list;
    }

    /** Prints current state of the board to console. */
    public void printBoard() {
        for (int row = 0; row < boardGrid.length; row++) {
            for (int col = 0; col < boardGrid[row].length; col++) {
                Disc disc = getDiscFromBoard(row, col);
                System.out.print(disc.getState() + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Gets boardGrid.
     *
     * @return 2d array of Disc objects.
     */
    public Disc[][] getBoardGrid() {
        return boardGrid;
    }
}
