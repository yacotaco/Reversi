package com.yacotaco;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
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
    private final double WIDTH = 65.0;
    private final double HEIGHT = 65.0;
    private final double RADIUS = 26.0;
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
    private final double SHADOW_OPACITY = 0.35;

    /**
     * @param stage JavaFX container
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
        private TimerView timerViewWhite = new TimerView();
        private TimerView timerViewBlack = new TimerView();
        private Text timerValueWhite;
        private Text timerValueBlack;
        private Button newGameButton = new Button("New Game");
        private Button newTimedGameButton = new Button("Timer");
        private Button aiPlayerButton = new Button("AI Player");
        private Button loadButton = new Button("Load");
        private Button saveButton = new Button("Save");
        private Button exitButton = new Button("Exit");
        private StackPane whiteCounter;
        private StackPane blackCounter;
        private HBox scoreHbox;
        private HBox menu;
        private VBox vbox;

        public TopBorderPane() {
            this.scoreHbox = new HBox();
            this.menu = new HBox();
            this.vbox = new VBox();
            vbox.getChildren().addAll(addMenu(), addScoreHBox());
            borderPane.setTop(vbox);
        }

        public HBox addScoreHBox() {
            scoreHbox.setPadding(new Insets(5, 0, 5, 0));
            scoreHbox.setSpacing(25);
            scoreHbox.setPrefHeight(80);
            scoreHbox.setStyle("-fx-background-color: #336459; -fx-border-width: 1; -fx-border-color: #000000;");
            scoreHbox.setAlignment(Pos.CENTER);
            whiteCounter = dv.makePointsCounterView(0);
            blackCounter = dv.makePointsCounterView(1);
            timerValueWhite = timerViewWhite.getTimerValue();
            timerValueBlack = timerViewBlack.getTimerValue();

            scoreHbox.getChildren().addAll(timerViewWhite.makeTimerView(timerValueWhite), whiteCounter, blackCounter,
                    timerViewBlack.makeTimerView(timerValueBlack));

            return scoreHbox;
        }

        public HBox addMenu() {
            newGameButton.setPrefSize(100, 10);
            newGameButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #336459;");

            newTimedGameButton.setPrefSize(100, 10);
            newTimedGameButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #336459;");

            aiPlayerButton.setPrefSize(100, 10);
            aiPlayerButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #336459;");

            loadButton.setPrefSize(100, 10);
            loadButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #336459;");

            saveButton.setPrefSize(100, 10);
            saveButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #336459;");

            exitButton.setPrefSize(100, 10);
            exitButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #336459;");

            menu.setSpacing(25);
            menu.setStyle("-fx-background-color: #332211;");
            menu.getChildren().addAll(newGameButton, newTimedGameButton, aiPlayerButton, loadButton, saveButton,
                    exitButton);

            return menu;
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

        public Button getNewTimedGameButton() {
            return newTimedGameButton;
        }

        public void setNewTimedGameButton(Button newTimedGameButton) {
            this.newTimedGameButton = newTimedGameButton;
        }
    }

    public class TimerView {
        private Text timerValue;

        public TimerView() {
            this.timerValue = new Text();
        }

        public StackPane makeTimerView(Text timerValue) {
            StackPane timerStack = new StackPane();
            timerStack.setAlignment(Pos.CENTER);
            Rectangle r = new Rectangle(70, 30, Color.web("#332211"));
            r.setArcHeight(30);
            r.setArcWidth(30);
            r.setStroke(Color.web("#000000"));
            r.setStrokeWidth(1);
            timerValue.setStrokeWidth(4);
            timerValue.setStyle("-fx-font-size: 15;");
            timerStack.getChildren().addAll(r, timerValue);
            return timerStack;
        }

        public Text getTimerValue() {
            return timerValue;
        }

        public void setTimerValue(String timerValue) {
            if (timerValue.length() < 2) {
                String time = "00:0" + timerValue;
                this.timerValue.setText(time);
            } else {
                String time = "00:" + timerValue;
                this.timerValue.setText(time);
            }
        }

        public void addHighlight() {
            this.timerValue.setFill(Color.web("#9DC8E4", 1.0));
        }

        public void removeHighlight() {
            this.timerValue.setFill(Color.BLACK);
        }

        public void timeoutHighlight() {
            this.timerValue.setFill(Color.RED);
        }

        public void switchOffTimer() {
            this.timerValue.setFill(Color.web("#332211", 1.0));
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
                    square.setStyle("-fx-border-width: 0.25; -fx-border-color: #000000;");
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
            DropShadow dropShadow = new DropShadow();
            Rectangle rectangleOut = new Rectangle(FRAME_OUT_WIDTH, FRAME_OUT_HEIGHT, Color.web("#332211", 0.80));
            Rectangle rectangleIn = new Rectangle(FRAME_IN_WIDTH, FRAME_IN_HEIGHT, Color.web("#332211", 0.80));
            rectangleOut.setStroke(Color.BLACK);
            rectangleOut.setStrokeWidth(FRAME_STROKE_WIDTH);
            rectangleIn.setStroke(Color.BLACK);
            rectangleIn.setStrokeWidth(FRAME_STROKE_WIDTH);
            rectangleOut.setStrokeType(StrokeType.OUTSIDE);
            rectangleIn.setStrokeType(StrokeType.OUTSIDE);

            // add shadow to board
            dropShadow.setOffsetX(10);
            dropShadow.setOffsetY(10);
            dropShadow.setColor(Color.web("#000000", SHADOW_OPACITY));
            rectangleOut.setEffect(dropShadow);

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

        public Circle addDropShadow(Circle disc) {
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(1);
            dropShadow.setOffsetX(3);
            dropShadow.setOffsetY(3);
            dropShadow.setColor(Color.web("#333333", SHADOW_OPACITY));
            disc.setEffect(dropShadow);
            return disc;
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

    public class SummaryView {
        private StackPane summary;
        private Player playerOne;
        private Player playerTwo;
        private Text text;

        /**
         * @param playerOne Player class object
         * @param playerTwo Player class object
         */

        public SummaryView(Player playerOne, Player playerTwo) {
            this.summary = new StackPane();
            this.text = new Text();
            this.playerOne = playerOne;
            this.playerTwo = playerTwo;
            createSummaryView();
        }

        private StackPane createSummaryView() {

            summary = addDropShadow(summary);
            summary.setAlignment(Pos.CENTER);
            summary.setMaxHeight(150);
            summary.setMaxWidth(300);
            summary.setStyle("-fx-background-color: #2A363B; -fx-border-width: 10; -fx-border-color: #336459;");

            if (playerOne.getPoints() > playerTwo.getPoints()) {
                text = new Text("White wins!");
                text.setFill(Color.WHITE);
                text.setStyle("-fx-font-size: 25;");
            } else if (playerOne.getPoints() < playerTwo.getPoints()) {
                text = new Text("Black wins!");
                text.setFill(Color.BLACK);
                text.setStyle("-fx-font-size: 25;");
            } else {
                text = new Text("Draw!");
                text.setFill(Color.BROWN);
                text.setStyle("-fx-font-size: 25;");
            }

            VBox vbox = new VBox();
            vbox.setSpacing(40);
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(text);
            summary.getChildren().addAll(vbox);

            return summary;
        }

        private StackPane addDropShadow(StackPane summary) {
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(1);
            dropShadow.setOffsetX(4);
            dropShadow.setOffsetY(4);
            dropShadow.setColor(Color.web("#333333", SHADOW_OPACITY));
            summary.setEffect(dropShadow);
            return summary;
        }

        public StackPane getSummary() {
            return summary;
        }

        public void setSummary(StackPane summary) {
            this.summary = summary;
        }

    }

}
