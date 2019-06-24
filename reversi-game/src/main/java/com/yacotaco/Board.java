package com.yacotaco;

/**
 * Board
 */
public class Board {
    private Disc[][] boardGrid = new Disc[8][8];

    Board() {
    
    }

    /**
     * @param boardGrid 2d array of disc objects
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

    private void addDisc(Integer row, Integer col, Integer state) {
        Disc disc = new Disc();
        disc.setRow(row);
        disc.setCol(col);
        disc.setState(state);
        boardGrid[row][col] = disc;
    }

    private Disc getDiscFromBoard(Integer row, Integer col) {
        Disc disc = boardGrid[row][col];
        return disc;
    }

    private void modifyDiscState(Integer row, Integer col, Integer state) {
        Disc disc = getDiscFromBoard(row, col);
        disc.setState(state);
    }

    public void printBoard() {
        for (int row = 0; row < boardGrid.length; row++) {
            for (int col = 0; col < boardGrid[row].length; col++) {
                Disc disc = getDiscFromBoard(row, col);
                System.out.print(disc.getState() + "\t");
            }
            System.out.println();
        }
    }
}
