package com.yacotaco;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controller
 */
public class Controller {
    private Board board;
    private View view;
    private Stage stage;
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

    public Controller(Board board, View view, Stage stage) {
        this.board = board;
        this.view = view;
        this.stage = stage;
        this.bg = view.new BoardGrid();
        this.dv = view.new DiscView();
        this.playerOne = new Player();
        this.playerTwo = new Player();
        initController();
    }

    private void initController() {
        initPlayer();
        onGridClick();
        onExitButtonClick();
        onNewGameButtonClick();
        onSaveButtonClick();
        onLoadButtonClick();
        setPlayerTurn(0);
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
        updatePointsCounters();

        updatePlayerTurnIndicators();
    }

    private void updatePlayerTurnIndicators() {
        int elementsInWhiteCounter = view.getTopBorderPane().getWhiteCounter().getChildren().size();
        int elementsInBlackCounter = view.getTopBorderPane().getBlackCounter().getChildren().size();

        if (playerTurn == 0) {
            view.getTopBorderPane().getWhiteCounter().getChildren().add(2, dv.makePlayerIndicator());
        } else if (playerTurn == 1) {
            view.getTopBorderPane().getBlackCounter().getChildren().add(2, dv.makePlayerIndicator());
        }

        if (elementsInWhiteCounter == 3) {
            view.getTopBorderPane().getWhiteCounter().getChildren().remove(2);
        } else if (elementsInBlackCounter == 3) {
            view.getTopBorderPane().getBlackCounter().getChildren().remove(2);
        }
    }

    private void updatePointsCounters() {
        countPlayerPoints(playerOne);
        countPlayerPoints(playerTwo);
        // update white disc
        Node nodeWhite = view.getTopBorderPane().getWhiteCounter().getChildren().get(1);
        Text textWhite = (Text) nodeWhite;
        textWhite.setText(Integer.toString(playerOne.getPoints()));

        // update black disc
        Node nodeBlack = view.getTopBorderPane().getBlackCounter().getChildren().get(1);
        Text textBlack = (Text) nodeBlack;
        textBlack.setText(Integer.toString(playerTwo.getPoints()));
    }

    private void countPlayerPoints(Player player) {
        Integer discState = player.getDiscState();
        player.setPoints(board.getAllPlayerDiscs(discState).size());
    }

    private ArrayList<Integer[]> getHorizontalMoves(Disc disc) {
        Integer discRow = disc.getRow();
        Integer discCol = disc.getCol();
        Integer colRight = discCol + 1;
        Integer opponentDiscState = 0;
        Integer nextDiscState = -1;
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
            nextDiscState = board.getDiscFromBoard(discRow, colRight).getState();
        }

        while (nextDiscState == opponentDiscState) {
            colRight++;

            if (colRight > board.getBoardGrid().length - 1) {
                break;
            }

            nextDiscState = board.getDiscFromBoard(discRow, colRight).getState();

            if (nextDiscState == -1) {
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

    private ArrayList<Integer[]> getDiagonalMoves(Disc disc) {
        ArrayList<Integer[]> result = new ArrayList<Integer[]>();
        Integer row = disc.getRow();
        Integer col = disc.getCol();
        Integer discState = disc.getState();
        Integer nextDiscState = -1;
        Integer opponentDiscState = 0;
        Integer prevDiscState = discState;

        if (disc.getState() == 0) {
            opponentDiscState = 1;
        } else if (disc.getState() == 1) {
            opponentDiscState = 0;
        }

        // diagonal up right
        for (int i = row - 1; i > 0; i--) {
            col++;

            if (col > board.getBoardGrid().length - 1) {
                break;
            }

            nextDiscState = board.getDiscFromBoard(i, col).getState();

            if (nextDiscState == discState) {
                break;
            }

            if (nextDiscState == opponentDiscState) {
                prevDiscState = opponentDiscState;
                continue;
            }

            if (nextDiscState == -1 && prevDiscState != discState) {
                Integer[] move = new Integer[2];
                move[0] = i;
                move[1] = col;
                result.add(move);
                break;
            } else if (nextDiscState == -1 && prevDiscState == discState) {
                break;
            }
        }

        row = disc.getRow();
        col = disc.getCol();
        discState = disc.getState();
        nextDiscState = -1;
        opponentDiscState = 0;
        prevDiscState = discState;

        if (disc.getState() == 0) {
            opponentDiscState = 1;
        } else if (disc.getState() == 1) {
            opponentDiscState = 0;
        }

        // diagonal down left
        for (int i = row + 1; i < board.getBoardGrid().length; i++) {
            col--;

            if (col < 0) {
                break;
            }

            nextDiscState = board.getDiscFromBoard(i, col).getState();

            if (nextDiscState == discState) {
                break;
            }

            if (nextDiscState == opponentDiscState) {
                prevDiscState = opponentDiscState;
                continue;
            }

            if (nextDiscState == -1 && prevDiscState != discState) {
                Integer[] move = new Integer[2];
                move[0] = i;
                move[1] = col;
                result.add(move);
                break;
            } else if (nextDiscState == -1 && prevDiscState == discState) {
                break;
            }
        }

        row = disc.getRow();
        col = disc.getCol();
        discState = disc.getState();
        nextDiscState = -1;
        opponentDiscState = 0;
        prevDiscState = discState;

        if (disc.getState() == 0) {
            opponentDiscState = 1;
        } else if (disc.getState() == 1) {
            opponentDiscState = 0;
        }

        // diagonal up left
        for (int i = col - 1; i > 0; i--) {
            row--;

            if (row < 0) {
                break;
            }

            nextDiscState = board.getDiscFromBoard(row, i).getState();

            if (nextDiscState == discState) {
                break;
            }

            if (nextDiscState == opponentDiscState) {
                prevDiscState = opponentDiscState;
                continue;
            }

            if (nextDiscState == -1 && prevDiscState != discState) {
                Integer[] move = new Integer[2];
                move[0] = row;
                move[1] = i;
                result.add(move);
                break;
            } else if (nextDiscState == -1 && prevDiscState == discState) {
                break;
            }
        }

        row = disc.getRow();
        col = disc.getCol();
        discState = disc.getState();
        nextDiscState = -1;
        opponentDiscState = 0;
        prevDiscState = discState;

        if (disc.getState() == 0) {
            opponentDiscState = 1;
        } else if (disc.getState() == 1) {
            opponentDiscState = 0;
        }

        // diagonal down right
        for (int i = col + 1; i < board.getBoardGrid().length; i++) {
            row++;

            if (row > board.getBoardGrid().length - 1) {
                break;
            }

            nextDiscState = board.getDiscFromBoard(row, i).getState();

            if (nextDiscState == discState) {
                break;
            }

            if (nextDiscState == opponentDiscState) {
                prevDiscState = opponentDiscState;
                continue;
            }

            if (nextDiscState == -1 && prevDiscState != discState) {
                Integer[] move = new Integer[2];
                move[0] = row;
                move[1] = i;
                result.add(move);
                break;
            } else if (nextDiscState == -1 && prevDiscState == discState) {
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
            ArrayList<Integer[]> hMoves = getHorizontalMoves(disc);
            for (Integer[] move : hMoves) {
                allValidMoves.add(move);
            }

            ArrayList<Integer[]> vMoves = getVerticalMoves(disc);
            for (Integer[] move : vMoves) {
                allValidMoves.add(move);
            }

            ArrayList<Integer[]> dMoves = getDiagonalMoves(disc);
            for (Integer[] move : dMoves) {
                allValidMoves.add(move);
            }
        }
    }

    private void flipHorizontalDiscs(Integer row, Integer col, Integer playerTurn) {
        Integer nextDiscState = -1;
        Integer primaryDiscState = playerTurn;
        ArrayList<Disc> discsToFlip = new ArrayList<Disc>();

        // add loop to check if placed move "close" opponent discs on right
        for (int i = col + 1; i < board.getBoardGrid().length - 1; i++) {
            nextDiscState = board.getDiscFromBoard(row, i).getState();
            if (nextDiscState != primaryDiscState) {
                Disc opponentDisc = board.getDiscFromBoard(row, i);
                discsToFlip.add(opponentDisc);
            }

            if (nextDiscState == -1) {
                discsToFlip.clear();
                break;
            } else if (nextDiscState == primaryDiscState) {
                for (Disc disc : discsToFlip) {
                    board.getDiscFromBoard(disc.getRow(), disc.getCol()).setState(primaryDiscState);
                }
                break;
            }
        }

        // add loop to check if placed move "close" opponent discs on left
        for (int i = col - 1; i >= 0; i--) {
            nextDiscState = board.getDiscFromBoard(row, i).getState();
            if (nextDiscState != primaryDiscState) {
                Disc opponentDisc = board.getDiscFromBoard(row, i);
                discsToFlip.add(opponentDisc);
            }

            if (nextDiscState == -1) {
                discsToFlip.clear();
                break;
            } else if (nextDiscState == primaryDiscState) {
                for (Disc disc : discsToFlip) {
                    board.getDiscFromBoard(disc.getRow(), disc.getCol()).setState(primaryDiscState);
                }
                break;
            }
        }
    }

    private void flipVerticalDiscs(Integer row, Integer col, Integer playerTurn) {
        Integer nextDiscState = -1;
        Integer primaryDiscState = playerTurn;
        ArrayList<Disc> discsToFlip = new ArrayList<Disc>();

        // add loop to check if placed move "close" opponent discs up
        for (int i = row - 1; i >= 0; i--) {
            nextDiscState = board.getDiscFromBoard(i, col).getState();
            if (nextDiscState != primaryDiscState) {
                Disc opponentDisc = board.getDiscFromBoard(i, col);
                discsToFlip.add(opponentDisc);
            }

            if (nextDiscState == -1) {
                discsToFlip.clear();
                break;
            } else if (nextDiscState == primaryDiscState) {
                for (Disc disc : discsToFlip) {
                    board.getDiscFromBoard(disc.getRow(), disc.getCol()).setState(primaryDiscState);
                }
                break;
            }
        }

        // add loop to check if placed move "close" opponent discs down
        for (int i = row + 1; i < board.getBoardGrid().length - 1; i++) {
            nextDiscState = board.getDiscFromBoard(i, col).getState();
            if (nextDiscState != primaryDiscState) {
                Disc opponentDisc = board.getDiscFromBoard(i, col);
                discsToFlip.add(opponentDisc);
            }

            if (nextDiscState == -1) {
                discsToFlip.clear();
                break;
            } else if (nextDiscState == primaryDiscState) {
                for (Disc disc : discsToFlip) {
                    board.getDiscFromBoard(disc.getRow(), disc.getCol()).setState(primaryDiscState);
                }
                break;
            }
        }
    }

    private void flipDiagonalDiscs(Integer row, Integer col, Integer playerTurn) {
        Integer nextDiscState = -1;
        Integer primaryDiscState = playerTurn;
        Integer tmpRow = row;
        Integer tmpCol = col;
        ArrayList<Disc> discsToFlip = new ArrayList<Disc>();

        // add loop to check if placed move "close" opponent discs (diagonal up right)
        for (int i = row - 1; i >= 0; i--) {
            col++;

            if (col > board.getBoardGrid().length - 1) {
                break;
            }

            nextDiscState = board.getDiscFromBoard(i, col).getState();
            if (nextDiscState != primaryDiscState) {
                Disc opponentDisc = board.getDiscFromBoard(i, col);
                discsToFlip.add(opponentDisc);
            }

            if (nextDiscState == -1) {
                discsToFlip.clear();
                break;
            } else if (nextDiscState == primaryDiscState) {
                for (Disc disc : discsToFlip) {
                    board.getDiscFromBoard(disc.getRow(), disc.getCol()).setState(primaryDiscState);
                }
                break;
            }
        }

        row = tmpRow;
        col = tmpCol;

        // add loop to check if placed move "close" opponent discs (diagonal down left)
        for (int i = row + 1; i < board.getBoardGrid().length; i++) {
            col--;

            if (col < 0) {
                break;
            }

            nextDiscState = board.getDiscFromBoard(i, col).getState();
            if (nextDiscState != primaryDiscState) {
                Disc opponentDisc = board.getDiscFromBoard(i, col);
                discsToFlip.add(opponentDisc);
            }

            if (nextDiscState == -1) {
                discsToFlip.clear();
                break;
            } else if (nextDiscState == primaryDiscState) {
                for (Disc disc : discsToFlip) {
                    board.getDiscFromBoard(disc.getRow(), disc.getCol()).setState(primaryDiscState);
                }
                break;
            }
        }

        row = tmpRow;
        col = tmpCol;

        // add loop to check if placed move "close" opponent discs (diagonal up left)
        for (int i = row - 1; i >= 0; i--) {
            col--;

            if (col < 0) {
                break;
            }

            nextDiscState = board.getDiscFromBoard(i, col).getState();
            if (nextDiscState != primaryDiscState) {
                Disc opponentDisc = board.getDiscFromBoard(i, col);
                discsToFlip.add(opponentDisc);
            }

            if (nextDiscState == -1) {
                discsToFlip.clear();
                break;
            } else if (nextDiscState == primaryDiscState) {
                for (Disc disc : discsToFlip) {
                    board.getDiscFromBoard(disc.getRow(), disc.getCol()).setState(primaryDiscState);
                }
                break;
            }
        }

        row = tmpRow;
        col = tmpCol;

        // add loop to check if placed move "close" opponent discs (diagonal down right)
        for (int i = row + 1; i < board.getBoardGrid().length; i++) {
            col++;

            if (col > board.getBoardGrid().length - 1) {
                break;
            }

            nextDiscState = board.getDiscFromBoard(i, col).getState();
            if (nextDiscState != primaryDiscState) {
                Disc opponentDisc = board.getDiscFromBoard(i, col);
                discsToFlip.add(opponentDisc);
            }

            if (nextDiscState == -1) {
                discsToFlip.clear();
                break;
            } else if (nextDiscState == primaryDiscState) {
                for (Disc disc : discsToFlip) {
                    board.getDiscFromBoard(disc.getRow(), disc.getCol()).setState(primaryDiscState);
                }
                break;
            }
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

                    // player can place disc only on empty square
                    if (board.getDiscFromBoard(row, col).getState() == -1 && validMove == true) {
                        board.modifyDiscState(row, col, playerTurn);
                        flipHorizontalDiscs(row, col, playerTurn);
                        flipVerticalDiscs(row, col, playerTurn);
                        flipDiagonalDiscs(row, col, playerTurn);
                        // change player after update
                        changePlayerTurn(playerTurn);

                        getValidMoves(playerTurn);

                        updateBoardView();
                    }
                }
            });
        });
    }

    private void onExitButtonClick() {
        view.getTopBorderPane().getExitButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setContentText("Do you want to exit game?");
                Optional<ButtonType> option = alert.showAndWait();

                if (ButtonType.OK.equals(option.get()) == true) {
                    System.exit(0);
                }

            }
        });
    }

    private void onNewGameButtonClick() {
        view.getTopBorderPane().getNewGameButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setPlayerTurn(1);
                board.initBoard();
                getValidMoves(playerTurn);
                updateBoardView();
            }
        });
    }

    private void onSaveButtonClick() {
        view.getTopBorderPane().getSaveButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                File file;
                LocalDateTime localDateTime = LocalDateTime.now();
                Disc[][] boardGrid = board.getBoardGrid();

                try {
                    String suffix = "_DATETIME_" + localDateTime;
                    file = File.createTempFile("REVERSI_GAME_SAVE_", suffix);
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);

                    for (int row = 0; row < boardGrid.length; row++) {
                        for (int col = 0; col < boardGrid[row].length; col++) {
                            Disc disc = board.getDiscFromBoard(row, col);
                            String s = row + "," + col + "," + disc.getState() + "\n";
                            try {
                                bw.write(s);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    String playerTurnString = Integer.toString(playerTurn);
                    bw.write(playerTurnString);
                    bw.close();

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setContentText("File saved!");
                    alert.show();

                } catch (IOException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Can't save file!");
                    alert.show();
                }
            }
        });
    }

    private void onLoadButtonClick() {
        view.getTopBorderPane().getLoadButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Game File");
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                File file = fileChooser.showOpenDialog(stage);
                try {
                    FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.length() > 1) {
                            String[] splitLine = line.split(",");
                            int row = Integer.valueOf(splitLine[0]);
                            int col = Integer.valueOf(splitLine[1]);
                            int discState = Integer.valueOf(splitLine[2]);
                            board.getDiscFromBoard(row, col).setState(discState);
                        } else {
                            int playerState = Integer.valueOf(line);
                            setPlayerTurn(playerState);
                            getValidMoves(playerTurn);
                            updateBoardView();
                        }
                    }
                } catch (NumberFormatException | IOException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setAlertType(AlertType.ERROR);
                    alert.setContentText("Can't read file!");
                    alert.show();
                }
            }
        });
    }
}