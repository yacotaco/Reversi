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
    private Player playerOne;
    private Player playerTwo;
    private Integer playerTurn;

    /**
     * @param board     Board class passed to controller
     * @param view      View class passed to controller
     * @param bg        BoardGrid view nested class
     * @param dv        DiscView view nested class
     * @param playerOne Player class
     * @param playerTwo Player class
     */

    public Controller(Board board, View view) {
        this.board = board;
        this.view = view;
        this.bg = view.new BoardGrid();
        this.dv = view.new DiscView();
        this.playerOne = new Player();
        this.playerTwo = new Player();
        initController();
    }

    private void initController() {
        initPlayer();
        updateBoardView();
        onGridClick();
        setPlayerTurn(1);
    }

    private void setPlayerTurn(Integer state) {
        this.playerTurn = state;
    }

    private void changePlayerTurn(Integer playerTurn) {
        if(playerTurn == 0) {
            setPlayerTurn(1);
        } else if (playerTurn == 1) {
            setPlayerTurn(0);
        }
    }

    private void initPlayer() {
        playerOne.setDiscState(0);
        playerOne.setName("A");
        playerTwo.setDiscState(1);
        playerTwo.setName("B");
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
                    board.modifyDiscState(row, col, playerTurn);
                    updateBoardView();

                    // change player after update 
                    changePlayerTurn(playerTurn);
                }
            });
        });
    }
}