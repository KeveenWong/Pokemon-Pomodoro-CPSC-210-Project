package ui;

import java.io.FileNotFoundException;

import static ui.PomodoroPokemonGUI.createAndShowGUI;

public class Main {
    public static void main(String[] args) {
        new PomodoroPokemon();

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

    }
}
