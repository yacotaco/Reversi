package com.yacotaco;

/**
 * Launcher class.
 *
 * @author Kamil Kurach
 * @author https://github.com/yacotaco
 * @version v1.0
 *
 * Additional Luncher class was needed to run jar file (main class
 * cannot extend any class).
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
