package ui;

import model.PomodoroTimer;

import java.util.Scanner;

public class PomodoroPokemon {
    private Scanner input;
    private PomodoroTimer timer;

    public PomodoroPokemon() {
        runPomodoroApp();
    }

    private void initialize() {
        timer = new PomodoroTimer();
        input = new Scanner(System.in);
    }

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tt -> Pomodoro Timer");
        System.out.println("\tq -> quit");
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
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void processTimerCommand() {
        String selection = ""; // force entry into loop

        while (!(selection.equals("u") || selection.equals("p") || selection.equals("r") || selection.equals("e"))) {
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("u")) {
            if (!timer.getPaused()) {
                processTimerCommand();
            }
            timer.unpauseTimer();

        } else if (selection.equals("p")) {
            if (timer.getPaused()) {
                processTimerCommand();
            }
            timer.pauseTimer();

        } else if (selection.equals("r")) {
            timer.resetTimer();

        } else {
            timer.exitTimer();
            return;
        }

        processTimerCommand();
    }

    private void beginTimer() {
        timer.startTimer(timer.getPomodoroLength());
        System.out.println("p for pause");
        System.out.println("u for un-pause");
        System.out.println("r for reset");
        System.out.println("e for exit");
        processTimerCommand();
    }
}

