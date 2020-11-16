package com.yacotaco;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * View
 */
public class View {
    private BorderPane borderPane;
    private TopBorderPane topBorderPane;
    private final double WIDTH = 75.0;
    private final double HEIGHT = 75.0;
    private final double RADIUS = 27.0;
    private final double MAIN_WIDTH = 750;
    private final double MAIN_HEIGHT = 700;
    private final int STROKE_WIDTH = 4;
    private final double MARKER_WIDTH = WIDTH - 2;
    private final double MARKER_HEIGHT = HEIGHT - 2;

    /**
     * @param borderPane organize elements in border layout
     */

    public View(Stage stage) {
        this.borderPane = new BorderPane();
        this.topBorderPane = new TopBorderPane();
        new BoardGrid();
        stage.setScene(new Scene(borderPane, MAIN_WIDTH, MAIN_HEIGHT));
        stage.show();
    }

    public class TopBorderPane {
        DiscView dv = new DiscView();
        private Button newGameButton = new Button("New Game");
        private Button loadButton = new Button("Load");
        private Button saveButton = new Button("Save");
        private Button exitButton = new Button("Exit");
        private StackPane whiteCounter;
        private StackPane blackCounter;
        private HBox hbox;

        public HBox addHBox() {
            hbox.setPadding(new Insets(15, 12, 15, 12));
            hbox.setSpacing(15);
            hbox.setStyle("-fx-background-color: #336459;");

            newGameButton.setPrefSize(100, 20);
        
            loadButton.setPrefSize(100, 20);
        
            saveButton.setPrefSize(100, 20);

            exitButton.setPrefSize(100, 20);

            whiteCounter = dv.makePointsCounterView(0);
            blackCounter = dv.makePointsCounterView(1);

            hbox.getChildren().addAll(newGameButton, loadButton, saveButton, exitButton, whiteCounter, blackCounter);
        
            return hbox;
        }

        public TopBorderPane() {
            this.hbox = new HBox();
            borderPane.setTop(addHBox());
        }

        public Button getNewGameButton() {
            return newGameButton;
        }

        public Button getLoadButton() {
            return loadButton;
        }

        public Button getSaveButton() {
            return saveButton;
        }

        public Button getExitButton() {
            return exitButton;
        }

        public StackPane getWhiteCounter() {
            return whiteCounter;
        }

        public StackPane getBlackCounter() {
            return blackCounter;
        }
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
                        square.getChildren().addAll(new Rectangle(WIDTH, HEIGHT, lightGreen));
                    } else {
                        Color darkGreen = Color.web("#008000", 1.0);
                        square.getChildren().addAll(new Rectangle(WIDTH, HEIGHT, darkGreen));
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
            Rectangle rectangle = new Rectangle(MARKER_WIDTH, MARKER_HEIGHT, Color.web("#9DC8E4", 0.30));
            rectangle.setStroke(Color.web("#9DC8E4", 1.0));
            rectangle.setStrokeWidth(STROKE_WIDTH);
            rectangle.setStrokeType(StrokeType.INSIDE);
            return rectangle;
        }
    }

    public class DiscView {

        public Circle makeDisc(Integer discState) {
            Circle circle = new Circle();
            if (discState == 0) {
                // white disc
                circle.setCenterX(WIDTH);
                circle.setCenterY(HEIGHT);
                circle.setRadius(RADIUS);
                circle.setFill(Color.WHITE);
            } else if (discState == 1) {
                // black disc
                circle.setCenterX(WIDTH);
                circle.setCenterY(HEIGHT);
                circle.setRadius(RADIUS);
                circle.setFill(Color.BLACK);
            }
            return circle;
        }

        public Circle makePlayerIndicator() {
            Circle indicator = new Circle();
            indicator.setCenterX(WIDTH);
            indicator.setCenterY(HEIGHT);
            indicator.setRadius(RADIUS+STROKE_WIDTH);
            indicator.setFill(Color.web("#9DC8E4", 0.05));
            indicator.setStroke(Color.web("#9DC8E4", 1.0));
            indicator.setStrokeWidth(STROKE_WIDTH);
            indicator.setStrokeType(StrokeType.INSIDE);
            return indicator; 
        }

        public StackPane makePointsCounterView(Integer discState) {
            DiscView dv = new DiscView();
            Circle disc = dv.makeDisc(discState);
            StackPane stack = new StackPane();
            Text discText = new Text();
            discText.setStrokeWidth(4);
    
            if (discState == 1) {
                discText.setFill(Color.WHITE);
            }
        
            stack.getChildren().addAll(disc, discText);
    
            return stack;
        }
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    public TopBorderPane getTopBorderPane() {
        return topBorderPane;
    }
}
