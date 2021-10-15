package model;

import java.util.TimerTask;
import java.util.Timer;

import static java.lang.Math.round;


public class PomodoroTimer {
    public enum State {
        Pomodoro,
        ShortBreak,
        LongBreak
    }

    private State state = State.Pomodoro;
    private Timer timer;
    private static int pomodoroLength = 5;  // standard Pomodoro Length is 25 minutes
    private static int shortBreak = 3;      // standard break length is 5 minutes
    private static int longBreak = 4;       // after 4 Pomodoros, break is 15 minutes
    private static int delay = 1000;
    private static int period = 1000;
    private boolean paused = true;
    private int remainingTime;                // remaining time in timer
    private int pomodoroCounter;                      // tracks number of fully complete Pomodoros

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
    // MODIFIES: this, paused, remainingTime
    // EFFECTS: prints starting time, sets paused to false, sets remainingTime to initialTime, and begins timer tick
    public void startTimer(int initialTime) {
        state = State.Pomodoro;
        System.out.print("Starting Pomodoro! ");
        paused = false;
        System.out.print(initialTime);
        System.out.println(" minutes remaining!");
        remainingTime = initialTime;
        startTicking();
    }

    // MODIFIES: this
    // EFFECTS: Creates Timer and begins ticking once per second after a 1-second initial delay
    private void startTicking() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                tickRemainingTime();
                System.out.println(round(remainingTime));
            }
        }, delay, period);
    }

    // MODIFIES: TempCollection, remainingTime, state
    // EFFECTS: Switches timer states after current timer runs out, adds Pokemon to TempCollection after Pomodoro,
    // and resets timer appropriately.
    public void switchBetweenCases() throws NullPointerException {
        switch (state) {
            case Pomodoro:
                pomodoroFinish();
                break;
            case ShortBreak:
                shortBreakFinish();
                break;
            case LongBreak:
                longBreakFinish();
                break;
        }
    }

    private void pomodoroFinish() {
        System.out.println("Pomodoro timer complete.");
        Pokemon pokemon = Pokemon.getRandomPokemon();
        TempCollection.addPokemonToTemporaryCollection(pokemon);
        pomodoroCounter++;
        if (pomodoroCounter == 4) {
            System.out.println("Entering long break.");
            state = State.LongBreak;
            remainingTime = longBreak;
            pomodoroCounter = 0;
            return;
        } else {
            System.out.println("Entering short break.");
            remainingTime = shortBreak;
            state = State.ShortBreak;
        }
    }

    private void shortBreakFinish() {
        System.out.println("Short break complete. Entering Pomodoro timer.");
        remainingTime = pomodoroLength;
        state = State.Pomodoro;
    }

    private void longBreakFinish() {
        System.out.println("Long break complete. Entering Pomodoro timer.");
        state = State.Pomodoro;
        remainingTime = pomodoroLength;
    }

    // MODIFIES: this, TempCollection
    // EFFECTS: Changes and resets timer accordingly once remainingTime finishes (reaches 1). Otherwise, tick timer once
    private void tickRemainingTime() throws NullPointerException {
        if (remainingTime == 1) {
            switchBetweenCases();
            timer.cancel();
            startTicking();
        }
        remainingTime--;
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
        System.out.println("Un-paused timer! " + (round(remainingTime)) + " minutes remaining!");
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



