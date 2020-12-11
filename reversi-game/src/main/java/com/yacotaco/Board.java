package com.yacotaco;

import java.util.ArrayList;

/**
 * Board
 */
public class Board {
    private Disc[][] boardGrid = new Disc[8][8];

    Board() {
        initBoard();
    }

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

    public Disc getDiscFromBoard(Integer row, Integer col) {
        Disc disc = boardGrid[row][col];
        return disc;
    }

    public void modifyDiscState(Integer row, Integer col, Integer state) {
        Disc disc = getDiscFromBoard(row, col);
        disc.setState(state);
    }

    public ArrayList<Disc> getAllPlayerDiscs(Integer currentPlayerDiscState) {
        ArrayList<Disc> list = new ArrayList<Disc>();
        for (int row = 0; row < boardGrid.length; row++) {
            for (int col = 0; col < boardGrid[row].length; col++) {
                Disc disc = getDiscFromBoard(row, col);
                int discState = disc.getState();
                if (discState == currentPlayerDiscState) {
                    list.add(disc);
                }
            }
        }
        return list;
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

    public Disc[][] getBoardGrid() {
        return boardGrid;
    }
}
