package model;

import java.io.IOException;
import java.util.TimerTask;
import java.util.Timer;

import static java.lang.Math.round;
import static org.junit.jupiter.api.Assertions.fail;

// Pomodoro Timer with study (Pomodoro) timer, short break, and long break
public class PomodoroTimer {
    public enum State {
        Pomodoro,
        ShortBreak,
        LongBreak
    }

    private State state = State.Pomodoro;
    private Timer timer;
    private static int pomodoroLength = 5;  // standard Pomodoro Length is 25 minutes, SHORTENED to 5 seconds for tests
    private static int shortBreak = 3;      // standard break length is 5 minutes, SHORTENED to 3 seconds for testing
    private static int longBreak = 4;       // after 4 Pomodoros, break is 15 minutes, SHORTENED to 4 seconds for tests
    private static int period = 1000;
    private boolean paused = true;
    private int remainingTime;              // remaining time in timer
    private int pomodoroCounter;            // tracks number of fully complete Pomodoros

    // Getter methods
    public Integer getPomodoroLength() {
        return pomodoroLength;
    }

    public Integer getRemainingTime() {
        return remainingTime;
    }

    public Boolean getPaused() {
        return paused;
    }

    public State getState() {
        return state;
    }

    public Integer getPomodoroCounter() {
        return pomodoroCounter;
    }

    // REQUIRES: initialTime > 0
    // MODIFIES: this
    // EFFECTS: prints starting time, sets paused to false, sets remainingTime to initialTime, and begins timer tick
    public void startTimer(int initialTime) {
        state = State.Pomodoro;
        System.out.print("Starting Pomodoro! ");
        paused = false;
        System.out.print(initialTime);
        System.out.println(" seconds remaining!");
        remainingTime = initialTime;
        startTicking();
    }

    // MODIFIES: this
    // EFFECTS: Creates Timer and begins ticking once per second without delay
    private void startTicking() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println(round(remainingTime));
                    tickRemainingTime();
            }
        }, 0, period);
    }

    // EFFECTS: swaps between cases
    public void switchBetweenCases() throws NullPointerException {
        if (state == State.Pomodoro) {
            pomodoroFinish("pokemon.txt");
        } else if (state == State.ShortBreak) {
            shortBreakFinish();
        } else {
            longBreakFinish();
        }
    }

    // MODIFIES: this, TempCollection
    // EFFECTS: adds random pokemon to collection once complete, and checks if next break is long, else short
    public void pomodoroFinish(String filename) {
        System.out.println("Pomodoro timer complete.");
        Pokemon pokemon;
        pokemon = Pokemon.getRandomPokemon(filename);
        TempCollection.addPokemonToTemporaryCollection(pokemon);
        pomodoroCounter++;
        if (pomodoroCounter == 4) {
            System.out.println("Entering long break.");
            state = State.LongBreak;
            remainingTime = longBreak;
            pomodoroCounter = 0;
        } else {
            System.out.println("Entering short break.");
            remainingTime = shortBreak;
            state = State.ShortBreak;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets next break to Pomodoro timer following short break
    private void shortBreakFinish() {
        System.out.println("Short break complete. Entering Pomodoro timer.");
        remainingTime = pomodoroLength;
        state = State.Pomodoro;
    }

    // MODIFIES: this
    // EFFECTS: sets next break to Pomodoro timer following long break
    private void longBreakFinish() {
        System.out.println("Long break complete. Entering Pomodoro timer.");
        state = State.Pomodoro;
        remainingTime = pomodoroLength;
    }

    // MODIFIES: this
    // EFFECTS: Changes and resets timer accordingly once remainingTime finishes (reaches 1). Else, tick timer once
    private void tickRemainingTime() throws NullPointerException {
        if (remainingTime == 0) {
            switchBetweenCases();
            timer.cancel();
            timer.purge();
            startTicking();
        } else {
            remainingTime--;
        }
    }

    // MODIFIES: this
    // EFFECTS: pauses timer by setting paused = true
    public void pauseTimer() {
        System.out.println("Paused timer!");
        timer.cancel();
        paused = true;
    }

    // MODIFIES: this
    // EFFECTS: un-pauses timer by setting paused = false
    public void unpauseTimer() {
        paused = false;
        System.out.println("Un-paused timer! " + (round(remainingTime)) + " seconds remaining!");
        startTicking();
    }

    // MODIFIES: this
    // EFFECTS: resets timer by starting timer at initial values
    public void resetTimer() {
        if (!paused) {
            pauseTimer();
        }
        System.out.println("Reset timer!");
        startTimer(pomodoroLength);
    }

    // MODIFIES: this
    // EFFECTS: terminates timer
    public void exitTimer() {
        timer.cancel();
        System.out.println("Exiting timer. See you later!");
    }
}



