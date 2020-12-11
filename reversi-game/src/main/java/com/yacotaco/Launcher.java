package com.yacotaco;

/**
 * Launcher class.
 *
 * Additional Luncher class was needed to run jar file (main class
 * cannot extend any class).
 *
 * @author Kamil Kurach
 * @author https://github.com/yacotaco
 * @version 1.0
 */
public final class Launcher {

    /**
     * Launcher constructor.
     */
    private Launcher() { }

    /**
     * Launcher main function runs App class main.
     * @param args args
     */
    public static void main(final String[] args) {
        App.main(args);
    }

}
