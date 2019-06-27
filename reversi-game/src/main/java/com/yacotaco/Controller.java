package com.yacotaco;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * Controller
 */
public class Controller {
    private Board board;
    private View view;
    private View.BoardGrid bg;
    private View.DiscView dv;

    /**
     * @param board Board class passed to controller
     * @param view  View class passed to controller
     * @param bg    BoardGrid view nested class
     * @param dv    DiscView view nested class
     */

    public Controller(Board board, View view) {
        this.board = board;
        this.view = view;
        this.bg = view.new BoardGrid();
        this.dv = view.new DiscView();
        initController();
    }

    private void initController() {
        updateBoardView();
        onGridClick();
    }

    private void updateBoardView() {
        for (Node square : bg.getBoardGridPane().getChildren()) {
            Integer col = bg.getBoardGridPane().getColumnIndex(square);
            Integer row = bg.getBoardGridPane().getRowIndex(square);
            Integer discState = board.getDiscFromBoard(row, col).getState();
            StackPane sp = (StackPane) square;
            sp.getChildren().add(dv.makeDisc(discState));
        }
    }

    private void onGridClick() {
        bg.getBoardGridPane().getChildren().forEach(square -> {
            square.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Node node = (Node) event.getSource();
                    Integer col = bg.getBoardGridPane().getColumnIndex(node);
                    Integer row = bg.getBoardGridPane().getRowIndex(node);
                    board.modifyDiscState(row, col, 1);
                    updateBoardView();
                }
            });
        });
    }
}