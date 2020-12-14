package com.yacotaco;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
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
 * View class.
 *
 * @author Kamil Kurach
 * @author https://github.com/yacotaco
 * @version 1.0
 */
public class View {
    /** */
    private BorderPane borderPane;
    /** */
    private TopBorderPane topBorderPane;
    /** */
    private final double width = 65.0;
    /** */
    private final double height = 65.0;
    /** */
    private final double radius = 28.0;
    /** */
    private final double mainWidth = 700;
    /** */
    private final double mainHeight = 800;
    /** */
    private final int strokeWidth = 2;
    /** */
    private final int indicatorRadius = 32;
    /** */
    private final int frameStrokeWidth = 2;
    /** */
    private final double frameInWidth = 600;
    /** */
    private final double frameInHeight = 600;
    /** */
    private final double frameOutWidth = 620;
    /** */
    private final double frameOutHeight = 620;
    /** */
    private final double markerWidth = width - 2;
    /** */
    private final double markerHeight = height - 2;
    /** */
    private final double shadowOpacity = 0.35;
    /** */
    private final double markerRadius = 5.0;

    /**
     * View class constructor.
     *
     * @param stage JavaFX container.
     */
    public View(final Stage stage) {
        this.borderPane = new BorderPane();
        this.topBorderPane = new TopBorderPane();
        new BoardGrid();
        new BottomBorderPane();
        Scene scene = new Scene(borderPane, mainWidth, mainHeight);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene);
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

    public class BottomBorderPane {
        private HBox bottomBox;

        public BottomBorderPane() {
            this.bottomBox = new HBox();
            bottomBox.getStyleClass().add("hbox");
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
            scoreHbox.getStyleClass().add("hbox");
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
            newTimedGameButton.setPrefSize(100, 10);
            aiPlayerButton.setPrefSize(100, 10);
            loadButton.setPrefSize(100, 10);
            saveButton.setPrefSize(100, 10);
            exitButton.setPrefSize(100, 10);
            menu.setSpacing(25);
            menu.getStyleClass().add("hbox2");
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

        public Button getAiPlayerButton() {
            return aiPlayerButton;
        }

        public void setAiPlayerButton(Button aiPlayerButton) {
            this.aiPlayerButton = aiPlayerButton;
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
            int min = Integer.valueOf(timerValue) / 60;
            int sec = Integer.valueOf(timerValue) % 60;
            if (min >= 10 && sec >= 10) {
                String time = String.format("%d:%d", min, sec);
                this.timerValue.setText(time);
            } else if (min < 10 && sec < 10) {
                String time = String.format("0%d:0%d", min, sec);
                this.timerValue.setText(time);
            } else if (min < 10 && sec >= 10) {
                String time = String.format("0%d:%d", min, sec);
                this.timerValue.setText(time);
            } else if (min >= 10 && sec < 10) {
                String time = String.format("%d:0%d", min, sec);
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
                    square.getStyleClass().add("pane");
                    if ((row + col) % 2 == 0) {
                        Color lightGreen = Color.web("#9fa881", 1.0);
                        square.getChildren().addAll(new Rectangle(width, height, lightGreen));
                    } else {
                        Color darkGreen = Color.web("#6f7d42", 1.0);
                        square.getChildren().addAll(new Rectangle(width, height, darkGreen));
                    }
                    boardGridPane.add(square, col, row);
                }
            }
            borderPane.setCenter(addBoardToFrame(boardGridPane));
        }

        private StackPane addBoardToFrame(GridPane boardGridPane) {
            StackPane stack = new StackPane();
            DropShadow dropShadow = new DropShadow();
            Rectangle rectangleOut = new Rectangle(frameOutWidth, frameOutHeight, Color.web("#332211", 0.80));
            Rectangle rectangleIn = new Rectangle(frameInWidth, frameInHeight, Color.web("#332211", 0.80));
            rectangleOut.setStroke(Color.BLACK);
            rectangleOut.setStrokeWidth(frameStrokeWidth);
            rectangleIn.setStroke(Color.BLACK);
            rectangleIn.setStrokeWidth(frameStrokeWidth);
            rectangleOut.setStrokeType(StrokeType.OUTSIDE);
            rectangleIn.setStrokeType(StrokeType.OUTSIDE);

            // add shadow to board
            dropShadow.setOffsetX(10);
            dropShadow.setOffsetY(10);
            dropShadow.setColor(Color.web("#000000", shadowOpacity));
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
            Rectangle rectangle = new Rectangle(markerWidth, markerHeight, Color.web("#9DC8E4", 0.30));
            rectangle.setStroke(Color.web("#9DC8E4", 1.0));
            rectangle.setStrokeWidth(strokeWidth);
            rectangle.setStrokeType(StrokeType.INSIDE);
            return rectangle;
        }
    }

    public class DiscView {

        public Circle makeDisc(Integer discState) {
            Circle circle = new Circle();
            if (discState == 0) {
                // white disc
                circle.setCenterX(width);
                circle.setCenterY(height);
                circle.setRadius(radius);
                circle.setFill(Color.WHITE);
            } else if (discState == 1) {
                // black disc
                circle.setCenterX(width);
                circle.setCenterY(height);
                circle.setRadius(radius);
                circle.setFill(Color.BLACK);
            }

            // add spot effect
            circle = addSpotEffect(circle, discState);
            return circle;
        }

        /** Adds drop shadow effect.
         * @deprecated
         * @param disc disc object.
         * @return disc with drop shadow effect.
         */
        public Circle addDropShadow(final Circle disc) {
            DropShadow dropShadow = new DropShadow();
            final int r = 1;
            final int x = 3;
            final int y = 3;
            dropShadow.setRadius(r);
            dropShadow.setOffsetX(x);
            dropShadow.setOffsetY(y);
            dropShadow.setColor(Color.web("#333333", shadowOpacity));
            disc.setEffect(dropShadow);
            return disc;
        }

        /** Adds spotlight effect to discs.
         *
         * @param disc disc object.
         * @param discState disc state.
         * @return disc with spotlight effect.
         */
        public Circle addSpotEffect(final Circle disc,
                final Integer discState) {
            Light.Spot light = new Light.Spot();
            final int x = 4;
            final int y = 1;
            final int z = 55;
            light.setColor(Color.WHITE);
            light.setX(x);
            light.setY(y);
            light.setZ(z);
            Lighting lighting = new Lighting();
            lighting.setLight(light);
            disc.setEffect(lighting);
            return disc;
        }

        /** Makes highlight indicator to show player turn.
         *  It is stacked with disc view and displayed on score panel.
         *
         * @return indicator circle.
         */
        public Circle makePlayerIndicator() {
            Circle indicator = new Circle();
            final double fillOpacity = 0.05;
            final double strokeOpacity = 1.0;
            indicator.setCenterX(width);
            indicator.setCenterY(height);
            indicator.setRadius(indicatorRadius);
            indicator.setFill(Color.web("#9DC8E4", fillOpacity));
            indicator.setStroke(Color.web("#9DC8E4", strokeOpacity));
            indicator.setStrokeWidth(strokeWidth);
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
            summary.getStyleClass().add("pane2");

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
            dropShadow.setColor(Color.web("#333333", shadowOpacity));
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

    public class DebugMarkers {

        public DebugMarkers() {

        }

        public Circle flipDebugMarker() {
            Circle circle = new Circle();
            circle.setCenterX(width);
            circle.setCenterY(height);
            circle.setRadius(markerRadius);
            circle.setFill(Color.RED);
            return circle;
        }
    }
}
