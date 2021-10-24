package ui;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import persistence.Reading;
import persistence.Writing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Pokemon Pomodoro application
public class PomodoroPokemon {
    private static final String JSON_STORE = "./data/pokemoncollection.json";
    private Scanner input;
    private PomodoroTimer timer;
    private Pokemon pokemon;
    private PokemonCollection pokemonCollection;
    private Writing jsonWriter;
    private Reading jsonReader;

    // EFFECTS: runs the pomodoro application
    public PomodoroPokemon() {
        runPomodoroApp();
    }

    // MODIFIES: this
    // EFFECTS: initializes timer, input, collections
    private void initialize() {
        timer = new PomodoroTimer();
        input = new Scanner(System.in);
        pokemonCollection = new PokemonCollection();
        jsonWriter = new Writing(JSON_STORE);
        jsonReader = new Reading(JSON_STORE);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tt -> Pomodoro Timer");
        System.out.println("\tc -> Collection");
        System.out.println("\ts -> Save collection to file");
        System.out.println("\tl -> Load collection from file");
        System.out.println("\tq -> Quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user input
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

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("t")) {
            beginTimer();
        } else if (command.equals("c")) {
            openCollection();
        } else if (command.equals("s")) {
            saveWorkRoom();
        } else if (command.equals("l")) {
            loadWorkRoom();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this, PomodoroTimer
    // EFFECTS: processes user command in Timer option
    private void processTimerCommand() {

        while (true) {
            String s = ""; // force entry into loop

            while (!(s.equals("u") || s.equals("p") || s.equals("r") || s.equals("e"))) {
                s = input.next();
                s = s.toLowerCase();
            }

            if (s.equals("u")) {
                if (!timer.getPaused()) {
                    continue;
                }
                timer.unpauseTimer();

            } else if (s.equals("p")) {
                if (timer.getPaused()) {
                    continue;
                }
                timer.pauseTimer();

            } else if (s.equals("r")) {
                timer.resetTimer();

            } else {
                exitAndDoSelection();
                return;
            }
        }
    }

    // MODIFIES: timer, TempCollection
    // EFFECTS: exits timer option and allows user to select Pokemon if available
    private void exitAndDoSelection() {
        timer.exitTimer();
        timer.getTempCollection().printTempCollection();
        pokemonSelection();
        timer.getTempCollection().resetTemp();
    }


    // MODIFIES: this
    // EFFECTS: processes user command in collection option
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

    // EFFECTS: starts timer at initial values and displays menu options
    private void beginTimer() {
        timer.startTimer(timer.getPomodoroLength());
        System.out.println("p for pause");
        System.out.println("u for un-pause");
        System.out.println("r for reset");
        System.out.println("e for exit");
        processTimerCommand();
    }

    // EFFECTS: prints all Pokemon in PokemonCollection and displays menu option
    private void openCollection() {
        System.out.println("Your Pokemon Collection:");
        pokemonCollection.printCollection();
        System.out.println("e for exit");
        processCollectionCommand();
    }

    // MODIFIES: this, PokemonCollection
    // EFFECTS: prompts user to select which Pokemon they would like to add to PokemonCollection
    private void pokemonSelection() {

        for (Pokemon pokemon : timer.getTempCollection().getTempCollectionList()) {
            String selection = "";
            System.out.println("Would you like to keep" + pokemon.getPokemonName() + "? (y/n)");

            while (!(selection.equals("y") || selection.equals("n"))) {
                selection = input.next();
                selection = selection.toLowerCase();
            }

            if (selection.equals("y")) {
                pokemonCollection.addPokemonToCollection(pokemon);
                System.out.println(pokemon.getPokemonName() + " added to collection.");
            }

            if (selection.equals("n")) {
                System.out.println(pokemon.getPokemonName() + " was released.");
            }

        }
    }

    // EFFECTS: saves the workroom to file
    private void saveWorkRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(pokemonCollection);
            jsonWriter.close();
            System.out.println("Saved collection to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWorkRoom() {
        try {
            pokemonCollection = jsonReader.read();
            System.out.println("Loaded collection from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}

