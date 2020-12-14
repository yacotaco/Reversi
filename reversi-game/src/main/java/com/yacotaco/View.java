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
            initBoardView();
        }

        /**
         * Inits board view.
         * Grid pane with squares.
         */
        private void initBoardView() {
            boardGridPane.setAlignment(Pos.CENTER);
            final int rowNum = 8;
            final int colNum = 8;
            final double opacity = 1.0;
            for (int row = 0; row < rowNum; row++) {
                for (int col = 0; col < colNum; col++) {
                    square = new StackPane();
                    square.getStyleClass().add("pane");
                    if ((row + col) % 2 == 0) {
                        Color lightGreen = Color.web("#9fa881", opacity);
                        square.getChildren().addAll(new Rectangle(width,
                            height, lightGreen));
                    } else {
                        Color darkGreen = Color.web("#6f7d42", opacity);
                        square.getChildren().addAll(new Rectangle(width,
                            height, darkGreen));
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

    /** DiscView class.
     *  Contains all view generating functions and
     *  effects related to disc object.
     */
    public class DiscView {

        /** Makes disc filled with color defined by state.
         *
         * @param discState disc state (0 - white, 1 - black).
         * @return Circle object.
         */
        public Circle makeDisc(final Integer discState) {
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

            // add spotlight effect
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

        /** Makes view for counter to display player points.
         *
         * @param discState disc state.
         * @return stack object with circel object and text filed.
         */
        public StackPane makePointsCounterView(final Integer discState) {
            final int stroke = 4;
            DiscView dv = new DiscView();
            Circle disc = dv.makeDisc(discState);
            StackPane stack = new StackPane();
            Text discText = new Text();
            discText.setStrokeWidth(stroke);
            discText.setStyle("-fx-font-size: 15;");

            if (discState == 1) {
                discText.setFill(Color.WHITE);
            }

            stack.getChildren().addAll(disc, discText);

            return stack;
        }
    }

    /** Gets border pane.
     *
     * @return border pane object.
     */
    public BorderPane getBorderPane() {
        return borderPane;
    }

    /** Sets border pane.
     *
     * @param bPane border pane object.
     */
    public void setBorderPane(final BorderPane bPane) {
        this.borderPane = bPane;
    }

    /** Gets top border pane.
     *
     * @return class object with content of top border pane.
     */
    public TopBorderPane getTopBorderPane() {
        return topBorderPane;
    }

    /**
    * SummaryView class.
    */
    public class SummaryView {
        /** Main view container. */
        private StackPane summary;
        /** White player object.*/
        private Player playerOne;
        /** Black player object. */
        private Player playerTwo;
        /** Text filed object. */
        private Text text;

        /** SummaryView constructor.
         * Creates summary box with information about winner.
         *
         * @param whitePlayer Player class object.
         * @param blackPlayer Player class object.
         */
        public SummaryView(final Player whitePlayer, final Player blackPlayer) {
            this.summary = new StackPane();
            this.text = new Text();
            this.playerOne = whitePlayer;
            this.playerTwo = blackPlayer;
            createSummaryView();
        }

        /** Creates summary view.
         *
         * @return stack pane with summery elements.
         */
        private StackPane createSummaryView() {
            final int maxHeight = 150;
            final int maxWidth = 300;
            final int spacing = 40;
            summary = addDropShadow(summary);
            summary.setAlignment(Pos.CENTER);
            summary.setMaxHeight(maxHeight);
            summary.setMaxWidth(maxWidth);
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
            vbox.setSpacing(spacing);
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(text);
            summary.getChildren().addAll(vbox);

            return summary;
        }

        /** Adds drop shadow effect to summary box.
         *
         * @param gameSummary stack pane with summery elements.
         * @return summery object with drop shadow effect.
         */
        private StackPane addDropShadow(final StackPane gameSummary) {
            DropShadow dropShadow = new DropShadow();
            final int r = 1;
            final int x = 4;
            final int y = 4;
            dropShadow.setRadius(r);
            dropShadow.setOffsetX(x);
            dropShadow.setOffsetY(y);
            dropShadow.setColor(Color.web("#333333", shadowOpacity));
            gameSummary.setEffect(dropShadow);
            return gameSummary;
        }

        /** Gets summary object.
         *
         * @return stack pane with summery elements.
         */
        public StackPane getSummary() {
            return summary;
        }

        /** Sets summery object.
         *
         * @param gameSummary stack pane object.
         */
        public void setSummary(final StackPane gameSummary) {
            this.summary = gameSummary;
        }

    }

    /** DebugMarker class.
     *  Contains view for debug markers.
     */
    public class DebugMarkers {

        /**DebugMarker container. */
        public DebugMarkers() {

        }

        /** Creates debug marker for flipped discs.
         *
         * @return Circle object.
         */
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
