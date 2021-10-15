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

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public void startTimer(int initialTime) {
        state = State.Pomodoro;
        System.out.print("Starting Pomodoro! ");
        paused = false;
        System.out.print(initialTime);
        System.out.println(" minutes remaining!");
        remainingTime = initialTime;
        startTicking();
    }

    private void startTicking() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                tickRemainingTime();
                System.out.println(round(remainingTime));
            }
        }, delay, period);
    }


    public void switchBetweenCases() {
        switch (state) {
            case Pomodoro:
                System.out.println("Pomodoro timer complete.");
                Pokemon pokemon = Pokemon.getRandomPokemon();
                TempCollection.addPokemonToTemporaryCollection(pokemon);
                System.out.println("Entering short break.");
                remainingTime = shortBreak;
                state = State.ShortBreak;
                break;
            case ShortBreak:
                System.out.println("Short break complete. Entering Pomodoro timer.");
                remainingTime = pomodoroLength;
                state = State.Pomodoro;
                break;
            case LongBreak:
                System.out.println("Long break complete. Entering Pomodoro timer.");
                state = State.Pomodoro;
                remainingTime = pomodoroLength;
                break;
        }
    }

    private void tickRemainingTime() {
        if (remainingTime == 1) {
            if (state == State.ShortBreak || state == State.LongBreak) {
                pomodoroCounter++;
            } else if (pomodoroCounter == 4) {
                System.out.println("Pomodoro timer complete.");
                Pokemon pokemon = Pokemon.getRandomPokemon();
                TempCollection.addPokemonToTemporaryCollection(pokemon);
                System.out.println("Entering long break.");
                state = State.LongBreak;
                remainingTime = longBreak;
                pomodoroCounter = 0;
                return;
            }
            switchBetweenCases();
            timer.cancel();
            startTicking();
        }
        remainingTime--;
    }

    public void pauseTimer() {
        System.out.println("Paused timer!");
        timer.cancel();
        paused = true;
    }

    public void unpauseTimer() {
        paused = false;
        System.out.println("Un-paused timer! " + (round(remainingTime)) + " minutes remaining!");
        startTicking();
    }

    public void resetTimer() {
        if (!paused) {
            pauseTimer();
        }
        System.out.println("Reset timer!");
        startTimer(pomodoroLength);
    }

    public void exitTimer() {
        timer.cancel();
        System.out.println("Exiting timer. See you later!");
    }
}



