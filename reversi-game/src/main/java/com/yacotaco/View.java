package com.yacotaco;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

/**
 * View
 */
public class View {
    private BorderPane borderPane;

    /**
     * @param borderPane organize elements in border layout
     */

    public View(Stage stage) {
        this.borderPane = new BorderPane();
        new BoardGrid();
        stage.setScene(new Scene(borderPane, 800, 800));
        stage.show();
    }

    public class BoardGrid {
        private GridPane boardGridPane;
        private StackPane square;

        /**
         * @param boardGridPane 8x8 grid with StackPane object in each cell
         * @param square        StackPane object holds rectangle and circle objects
         */

        public BoardGrid() {
            this.boardGridPane = new GridPane();
            initBordView();
        }

        private void initBordView() {
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

        public GridPane getBoardGridPane() {
            return boardGridPane;
        }

        public void setBoardGridPane(GridPane boardGridPane) {
            this.boardGridPane = boardGridPane;
        }

        public Rectangle validMoveMarker() {
            return new Rectangle(100, 100, Color.web("#EE4540", 0.5));
        }
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

        public Shape makeCircle() {
            Circle whole = new Circle(100.0f, 100.0f, 30);
            Circle inside = new Circle(100.0f, 100.0f, 28);
            Shape circle = Shape.subtract(whole, inside);
            circle.setFill(Color.BLACK);

            return circle;
        }
    }
}
