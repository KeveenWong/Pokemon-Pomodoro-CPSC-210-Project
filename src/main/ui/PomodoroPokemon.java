package ui;

import model.*;

import java.util.Scanner;

public class PomodoroPokemon {
    private Scanner input;
    private PomodoroTimer timer;
    private Pokemon pokemon;

    public PomodoroPokemon() {
        runPomodoroApp();
    }

    private void initialize() {
        timer = new PomodoroTimer();
        input = new Scanner(System.in);
        TempCollection.init();
        PokemonCollection.init();
    }

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tt -> Pomodoro Timer");
        System.out.println("\tc -> Collection");
        System.out.println("\tq -> Quit");
    }

    private void runPomodoroApp() {
        boolean keepGoing = true;
        String command;

        initialize();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nSee you next time!");
    }

    private void processCommand(String command) {
        if (command.equals("t")) {
            beginTimer();
        } else if (command.equals("c")) {
            openCollection();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void processTimerCommand() {

        while (true) {
            String selection = ""; // force entry into loop

            while (!(selection.equals("u") || selection.equals("p") || selection.equals("r") || selection.equals("e"))) {
                selection = input.next();
                selection = selection.toLowerCase();
            }

            if (selection.equals("u")) {
                if (!timer.getPaused()) {
                    continue;
                }
                timer.unpauseTimer();

            } else if (selection.equals("p")) {
                if (timer.getPaused()) {
                    continue;
                }
                timer.pauseTimer();

            } else if (selection.equals("r")) {
                timer.resetTimer();

            } else {
                timer.exitTimer();
                TempCollection.printTempCollection();
                pokemonSelection();
                TempCollection.resetTemp();
                return;
            }
        }
    }

    private void processCollectionCommand() {
        String selection = ""; // force entry into loop

        while (!(selection.equals("e"))) {
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("e")) {
            System.out.println("Goodbye!");
        }
    }

    private void beginTimer() {
        timer.startTimer(timer.getPomodoroLength());
        System.out.println("p for pause");
        System.out.println("u for un-pause");
        System.out.println("r for reset");
        System.out.println("e for exit");
        processTimerCommand();
    }

    private void openCollection() {
        System.out.println("Your Pokemon Collection:");
        PokemonCollection.printCollection();
        System.out.println("e for exit");
        processCollectionCommand();
    }

    private void pokemonSelection() {

        for (Pokemon pokemon : TempCollection.getTempCollection()) {
            String selection = "";
            System.out.println("Would you like to keep" + pokemon.getPokemonName() + "? (y/n)");

            while (!(selection.equals("y") || selection.equals("n"))) {
                selection = input.next();
                selection = selection.toLowerCase();
            }

            if (selection.equals("y")) {
                PokemonCollection.addPokemonToCollection(pokemon);
                System.out.println(pokemon.getPokemonName() + " added to collection.");
            }

            if (selection.equals("n")) {
                System.out.println(pokemon.getPokemonName() + " was released.");
            }

        }
    }
}

