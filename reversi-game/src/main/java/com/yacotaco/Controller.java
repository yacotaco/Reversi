package com.yacotaco;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
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
        initController();
    }

    private void initController() {
        updateBoardView();
        onGridClick();
    }

    private void updateBoardView() {
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

    private void onGridClick() {
        view.getBoardGridPane().getChildren().forEach(square -> {
            square.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Node node = (Node) event.getSource();
                    Integer col = view.getBoardGridPane().getColumnIndex(node);
                    Integer row = view.getBoardGridPane().getRowIndex(node);
                    board.modifyDiscState(row, col, 1);
                    updateBoardView();
                }
            });
        });
    }
}