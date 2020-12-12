package com.yacotaco;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * App class. Inits and binds all modules.
 *
 * @author Kamil Kurach
 * @author https://github.com/yacotaco
 * @version 1.0
 */
public final class App extends Application {

    @Override
    public void start(final Stage stage) {
        Board board = new Board();
        View view = new View(stage);
        Controller controller = new Controller(board, view, stage);
    }

    /**
     * App main function.
     *
     * @param args args.
     */
    public static void main(final String[] args) {
        launch();
    }

}
