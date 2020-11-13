package com.yacotaco;

import java.util.ArrayList;
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
    private ArrayList<Integer[]> allValidMoves = new ArrayList<Integer[]>();

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
        onGridClick();
        setPlayerTurn(1);
        getValidMoves(playerTurn);
        updateBoardView();
    }

    private void setPlayerTurn(Integer state) {
        this.playerTurn = state;
    }

    private void changePlayerTurn(Integer playerTurn) {
        if (playerTurn == 0) {
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

            if (sp.getChildren().size() == 2) {
                sp.getChildren().remove(1);
            }

            sp.getChildren().add(dv.makeDisc(discState));

            for (Integer[] move : allValidMoves) {
                if (row == move[0] && col == move[1]) {
                    sp.getChildren().add(bg.validMoveMarker());
                }
            }

            if (sp.getChildren().size() > 3) {
                sp.getChildren().remove(2, sp.getChildren().size() - 1);
            }
        }
    }

    private ArrayList<Integer[]> getHorizontalMoves(Disc disc) {
        Integer discRow = disc.getRow();
        Integer discCol = disc.getCol();
        Integer colRight = discCol + 1;
        Integer opponentDiscState = 0;
        Integer nextDiscStateRight = -1;
        ArrayList<Integer[]> result = new ArrayList<Integer[]>();

        if (disc.getState() == 0) {
            opponentDiscState = 1;
        } else if (disc.getState() == 1) {
            opponentDiscState = 0;
        }

        // search right
        if (colRight > board.getBoardGrid().length - 1) {
            return result;
        } else {
            nextDiscStateRight = board.getDiscFromBoard(discRow, colRight).getState();
        }

        while (nextDiscStateRight == opponentDiscState) {
            colRight++;

            if (colRight > board.getBoardGrid().length - 1) {
                break;
            }

            nextDiscStateRight = board.getDiscFromBoard(discRow, colRight).getState();

            if (nextDiscStateRight == -1) {
                Integer[] move = new Integer[2];
                move[0] = discRow;
                move[1] = colRight;
                result.add(move);
                break;
            }
        }

        // search left
        Integer colLeft = discCol - 1;
        Integer nextDiscStateLeft = -1;

        if (colLeft < 0) {
            return result;
        } else {
            nextDiscStateLeft = board.getDiscFromBoard(discRow, colLeft).getState();
        }

        while (nextDiscStateLeft == opponentDiscState) {
            colLeft--;

            if (colLeft < 0) {
                break;
            }

            nextDiscStateLeft = board.getDiscFromBoard(discRow, colLeft).getState();

            if (nextDiscStateLeft == -1) {
                Integer[] move = new Integer[2];
                move[0] = discRow;
                move[1] = colLeft;
                result.add(move);
                break;
            }
        }
        return result;
    }

    private ArrayList<Integer[]> getVerticalMoves(Disc disc) {
        Integer discRow = disc.getRow();
        Integer discCol = disc.getCol();
        Integer rowUp = discRow - 1;
        Integer opponentDiscState = 0;
        Integer nextDiscStateUp = -1;
        ArrayList<Integer[]> result = new ArrayList<Integer[]>();

        if (disc.getState() == 0) {
            opponentDiscState = 1;
        } else if (disc.getState() == 1) {
            opponentDiscState = 0;
        }

        // search up
        if (rowUp < 0) {
            return result;
        } else {
            nextDiscStateUp = board.getDiscFromBoard(rowUp, discCol).getState();
        }

        while (nextDiscStateUp == opponentDiscState) {
            rowUp--;
            if (rowUp < 0) {
                break;
            }

            nextDiscStateUp = board.getDiscFromBoard(rowUp, discCol).getState();

            if (nextDiscStateUp == -1) {
                Integer[] move = new Integer[2];
                move[0] = rowUp;
                move[1] = discCol;
                result.add(move);
                break;
            }
        }

        Integer rowDown = discRow + 1;
        Integer nextDiscStateDown = -1;

        // search down
        if (rowDown > board.getBoardGrid().length - 1) {
            return result;
        } else {
            nextDiscStateDown = board.getDiscFromBoard(rowDown, discCol).getState();
        }

        while (nextDiscStateDown == opponentDiscState) {
            rowDown++;
            if (rowDown > board.getBoardGrid().length - 1) {
                break;
            }

            nextDiscStateDown = board.getDiscFromBoard(rowDown, discCol).getState();

            if (nextDiscStateDown == -1) {
                Integer[] move = new Integer[2];
                move[0] = rowDown;
                move[1] = discCol;
                result.add(move);
                break;
            }
        }
        return result;
    }

    private void getValidMoves(Integer playerTurn) {
        allValidMoves.clear();
        // generate posible moves for player
        ArrayList<Disc> list = board.getAllPlayerDiscs(playerTurn);
        for (Disc disc : list) {
            System.out.println(disc.getRow() + " " + disc.getCol() + " player " + disc.getState());
            ArrayList<Integer[]> hMoves = getHorizontalMoves(disc);
            for (Integer[] move : hMoves) {
                allValidMoves.add(move);
            }

            ArrayList<Integer[]> vMoves = getVerticalMoves(disc);
            for (Integer[] move : vMoves) {
                allValidMoves.add(move);
            }

        }
    }

    private void flipHorizontalDiscs(Integer row, Integer col, Integer playerTurn) {
        Integer discRow = row;
        Integer discCol = col;
        Integer colRight = discCol + 1;
        Integer nextDiscStateRight = -1;

        nextDiscStateRight = board.getDiscFromBoard(discRow, colRight).getState();

        while (nextDiscStateRight != playerTurn) {
            if (nextDiscStateRight == -1 || nextDiscStateRight == playerTurn) {
                break;
            }

            board.modifyDiscState(discRow, colRight, playerTurn);
            colRight++;

            nextDiscStateRight = board.getDiscFromBoard(discRow, colRight).getState();
        }

        Integer colLeft = discCol - 1;
        Integer nextDiscStateLeft = -1;

        nextDiscStateLeft = board.getDiscFromBoard(discRow, colLeft).getState();

        while (nextDiscStateLeft != playerTurn && nextDiscStateLeft != -1) {
            if (nextDiscStateLeft == -1 || nextDiscStateLeft == playerTurn) {
                break;
            }

            board.modifyDiscState(discRow, colLeft, playerTurn);
            colLeft--;

            nextDiscStateLeft = board.getDiscFromBoard(discRow, colLeft).getState();
        }
    }

    private boolean validatePlacedMove(Integer row, Integer col) {
        boolean result = false;
        for (Integer[] move : allValidMoves) {
            if (row == move[0] && col == move[1]) {
                result = true;
            }
        }
        return result;
    }

    private void onGridClick() {
        bg.getBoardGridPane().getChildren().forEach(square -> {
            square.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Node node = (Node) event.getSource();
                    Integer col = bg.getBoardGridPane().getColumnIndex(node);
                    Integer row = bg.getBoardGridPane().getRowIndex(node);

                    boolean validMove = validatePlacedMove(row, col);
                    System.out.println(validMove);

                    // player can place disc only on empty square
                    if (board.getDiscFromBoard(row, col).getState() == -1 && validMove == true) {
                        board.modifyDiscState(row, col, playerTurn);
                        flipHorizontalDiscs(row, col, playerTurn);
                        // change player after update
                        changePlayerTurn(playerTurn);
                        // debug
                        System.out.println("--------------");
                        getValidMoves(playerTurn);

                        updateBoardView();
                    }
                }
            });
        });
    }
}