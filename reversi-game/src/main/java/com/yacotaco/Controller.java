package com.yacotaco;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


/**
 * Controller
 */
public class Controller {
    private Board board;
    private View view;

    public Controller(Board board, View view) {
        this.board = board;
        this.view = view;
        printBoard();
    } 

    private void printBoard() {
        GridPane boardGrid = view.getBoardGridPane();
        View.DiscView dv = view.new DiscView();

        for (Node square : boardGrid.getChildren()) {
            Integer col = boardGrid.getColumnIndex(square);
            Integer row = boardGrid.getRowIndex(square);
            Integer discState = board.getDiscFromBoard(row, col).getState();
            StackPane sp = (StackPane) square;
            sp.getChildren().add(dv.makeDisc(discState));
        }
    }
}