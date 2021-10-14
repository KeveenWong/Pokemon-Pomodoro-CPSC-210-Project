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
    private static int pomodoroLength = 1500;  // standard Pomodoro Length is 25 minutes
    private static int shortBreak = 300;      // standard break length is 5 minutes
    private static int longBreak = 900;       // after 4 Pomodoros, break is 15 minutes
    private static int delay = 1000;
    private static int period = 1000;
    private boolean paused = true;
    private int remainingTime;                // remaining time in timer
    private int counter;                      // tracks number of fully complete Pomodoros
    private int secondCounter;

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

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public void startTimer(int initialTime) {
        state = State.Pomodoro;
        System.out.print("Starting Pomodoro! ");
        paused = false;
        System.out.print(initialTime / 60);
        System.out.println(" minutes remaining!");
        remainingTime = initialTime;
        startTicking();
    }

    private void startTicking() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                tickRemainingTime();
                if (secondCounter == 60) {
                    secondCounter = 0;
                    System.out.println(round(remainingTime / 60));
                }
            }
        }, delay, period);
    }


    private void switchBetweenCases() {
        switch (state) {
            case Pomodoro:
                if (counter == 4) {
                    System.out.println("Pomodoro timer complete. Entering long break.");
                    state = State.LongBreak;
                    remainingTime = longBreak;
                    counter = 0;
                    break;
                }
                System.out.println("Pomodoro timer complete. Entering short break.");
                remainingTime = shortBreak;
                state = State.ShortBreak;
                break;
            case ShortBreak:
                System.out.println("Short break complete. Entering Pomodoro timer.");
                remainingTime = pomodoroLength;
                state = State.Pomodoro;
                counter++;
                break;
            case LongBreak:
                System.out.println("Long break complete. Entering Pomodoro timer.");
                state = State.Pomodoro;
                remainingTime = pomodoroLength;
                counter++;
                break;
        }
    }

    private final void tickRemainingTime() {
        if (remainingTime == 1) {
            switchBetweenCases();
            timer.cancel();
            startTicking();
        }
        remainingTime--;
        secondCounter++;
    }

    public void pauseTimer() {
        System.out.println("Paused timer!");
        timer.cancel();
        paused = true;
    }

    public void unpauseTimer() {
        paused = false;
        System.out.println("Un-paused timer!");
        System.out.print(round(remainingTime / 60));
        System.out.println(" minutes remaining!");
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





