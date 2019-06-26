package com.yacotaco;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * View
 */
public class View {
    private GridPane boardGridPane;
    private BorderPane borderPane;
    private StackPane square;

    public View(Stage stage) {
        this.borderPane = new BorderPane();
        new BoardGrid();
        stage.setScene(new Scene(borderPane, 600, 600));
        stage.show();
    }

    public class BoardGrid {

        public BoardGrid() {
            initBordView();
        }

        private void initBordView() {
            boardGridPane = new GridPane();
            boardGridPane.setAlignment(Pos.CENTER);
            int rowNum = 8;
            int colNum = 8;
            for (int row = 0; row < rowNum; row++) {
                for (int col = 0; col < colNum; col++) {
                    square = new StackPane();
                    if ((row + col) % 2 == 0) {
                        Color lightGreen = Color.web("#00cc00", 1.0);
                        square.getChildren().addAll(new Rectangle(100, 100, lightGreen));
                    } else {
                        Color darkGreen = Color.web("#008000", 1.0);
                        square.getChildren().addAll(new Rectangle(100, 100, darkGreen));
                    }
                    boardGridPane.add(square, col, row);
                }
            }
            borderPane.setCenter(boardGridPane);
        }
    }

    public GridPane getBoardGridPane() {
        return boardGridPane;
    }

    public void setBoardGridPane(GridPane boardGridPane) {
        this.boardGridPane = boardGridPane;
    }

    public class DiscView {

        public Circle makeDisc(Integer discState) {
            Circle circle = new Circle();
            if (discState == 0) {
                // white disc
                circle.setCenterX(100.0f);
                circle.setCenterY(100.0f);
                circle.setRadius(30.0f);
                circle.setFill(Color.WHITE);
            } else if (discState == 1) {
                // black disc
                circle.setCenterX(100.0f);
                circle.setCenterY(100.0f);
                circle.setRadius(30.0f);
                circle.setFill(Color.BLACK);
            }
            return circle;
        }

    }
}