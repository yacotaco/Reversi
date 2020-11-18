package com.yacotaco;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    private final double MAIN_WIDTH = 700;
    private final double MAIN_HEIGHT = 800;
    private final int STROKE_WIDTH = 2;
    private final int INDICATOR_RADIUS = 30;
    private final int FRAME_STROKE_WIDTH = 2;
    private final double FRAME_IN_WIDTH = 600;
    private final double FRAME_IN_HEIGHT = 600;
    private final double FRAME_OUT_WIDTH = 620;
    private final double FRAME_OUT_HEIGHT = 620;
    private final double MARKER_WIDTH = WIDTH - 2;
    private final double MARKER_HEIGHT = HEIGHT - 2;

    /**
     * @param borderPane organize elements in border layout
     */

    public View(Stage stage) {
        this.borderPane = new BorderPane();
        this.topBorderPane = new TopBorderPane();
        new BoardGrid();
        new BottomBorderPane();
        setBorderPaneStyle();
        stage.setScene(new Scene(borderPane, MAIN_WIDTH, MAIN_HEIGHT));
        stage.show();
    }

    public void highLightPoints(Text textWhite, Text textBlack, Integer playerTurn) {
        if (playerTurn == 0) {
            textWhite.setFill(Color.web("#9DC8E4", 1.0));
            textBlack.setFill(Color.WHITE);
        } else if (playerTurn == 1) {
            textBlack.setFill(Color.web("#9DC8E4", 1.0));
            textWhite.setFill(Color.BLACK);
        }
    }

    private void setBorderPaneStyle() {
        this.borderPane.setStyle("-fx-background-color: #2A363B; -fx-border-width: 1; -fx-border-color: #000000;");
    }

    public class BottomBorderPane {
        private HBox bottomBox;

        public BottomBorderPane() {
            this.bottomBox = new HBox();
            bottomBox.setStyle("-fx-background-color: #336459; -fx-border-width: 1; -fx-border-color: #000000;");
            bottomBox.setPadding(new Insets(10, 12, 10, 12));
            borderPane.setBottom(bottomBox);
        }
    }

    public class TopBorderPane {
        private DiscView dv = new DiscView();
        private TimerView timerViewWhite = new TimerView(0);
        private TimerView timerViewBlack = new TimerView(1);
        private Button newGameButton = new Button("New Game");
        private Button loadButton = new Button("Load");
        private Button saveButton = new Button("Save");
        private Button exitButton = new Button("Exit");
        private StackPane whiteCounter;
        private StackPane blackCounter;
        private HBox scoreHbox;
        private HBox menu;
        private VBox vbox;

        public HBox addScoreHBox() {
            scoreHbox.setPadding(new Insets(15, 12, 15, 12));
            scoreHbox.setSpacing(20);
            scoreHbox.setStyle("-fx-background-color: #336459; -fx-border-width: 1; -fx-border-color: #000000;");
            scoreHbox.setAlignment(Pos.CENTER);
            whiteCounter = dv.makePointsCounterView(0);
            blackCounter = dv.makePointsCounterView(1);
            Text timerWhite = timerViewWhite.getTimerView();
            Text timerBlack = timerViewBlack.getTimerView();

            scoreHbox.getChildren().addAll(timerWhite, whiteCounter, blackCounter, timerBlack);

            return scoreHbox;
        }

        public HBox addMenu() {
            newGameButton.setPrefSize(100, 10);
            newGameButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #336459;");
            loadButton.setPrefSize(100, 10);
            loadButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #336459;");
            saveButton.setPrefSize(100, 10);
            saveButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #336459;");
            exitButton.setPrefSize(100, 10);
            exitButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #336459;");
        
            menu.setStyle("-fx-background-color: #332211;");
            menu.getChildren().addAll(newGameButton, loadButton, saveButton, exitButton);

            return menu;
        }

        public TopBorderPane() {
            this.scoreHbox = new HBox();
            this.menu = new HBox();
            this.vbox = new VBox();
            vbox.getChildren().addAll(addMenu(), addScoreHBox());
            borderPane.setTop(vbox);
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

        public TimerView getTimerViewWhite() {
            return timerViewWhite;
        }

        public void setTimerViewWhite(TimerView timerViewWhite) {
            this.timerViewWhite = timerViewWhite;
        }

        public TimerView getTimerViewBlack() {
            return timerViewBlack;
        }

        public void setTimerViewBlack(TimerView timerViewBlack) {
            this.timerViewBlack = timerViewBlack;
        }
    }

    public class TimerView {
        private Text timerView;

        public TimerView(Integer discState) {
            this.timerView = makeTimerView(discState);
        }

        private Text makeTimerView(Integer discState) {
            Text timerText = new Text();
            timerText.setStrokeWidth(4);
            timerText.setStyle("-fx-font-size: 15;");
            return timerText;
        }

        public Text getTimerView() {
            return timerView;
        }

        public void setTimerView(String timerValue) {
            this.timerView.setText(timerValue);
        }

        public void addHighlight() {
            this.timerView.setFill(Color.web("#9DC8E4", 1.0));
        }

        public void removeHighlight () {
            this.timerView.setFill(Color.BLACK);
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
                        Color lightGreen = Color.web("#9fa881", 1.0);
                        square.getChildren().addAll(new Rectangle(WIDTH, HEIGHT, lightGreen));
                    } else {
                        Color darkGreen = Color.web("#6f7d42", 1.0);
                        square.getChildren().addAll(new Rectangle(WIDTH, HEIGHT, darkGreen));
                    }
                    boardGridPane.add(square, col, row);
                }
            }
            borderPane.setCenter(addBoardToFrame(boardGridPane));
        }

        private StackPane addBoardToFrame(GridPane boardGridPane) {
            StackPane stack = new StackPane();
            Rectangle rectangleOut = new Rectangle(FRAME_OUT_WIDTH, FRAME_OUT_HEIGHT, Color.web("#332211", 0.80));
            Rectangle rectangleIn = new Rectangle(FRAME_IN_WIDTH, FRAME_IN_HEIGHT, Color.web("#332211", 0.80));
            rectangleOut.setStroke(Color.BLACK);
            rectangleOut.setStrokeWidth(FRAME_STROKE_WIDTH);
            rectangleIn.setStroke(Color.BLACK);
            rectangleIn.setStrokeWidth(FRAME_STROKE_WIDTH);
            rectangleOut.setStrokeType(StrokeType.OUTSIDE);
            rectangleIn.setStrokeType(StrokeType.OUTSIDE);
            stack.getChildren().addAll(rectangleOut, rectangleIn, boardGridPane);
            return stack;
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
            indicator.setRadius(INDICATOR_RADIUS);
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
            discText.setStyle("-fx-font-size: 15;");

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
