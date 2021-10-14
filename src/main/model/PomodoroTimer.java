package model;

import java.util.TimerTask;
import java.util.Timer;

public class PomodoroTimer {
    private Timer timer;
    private static int pomodoroLength = 50; // standard Pomodoro Length is 25 minutes
    private static int shortBreak = 5;      // standard break length is 5 minutes
    private static int longBreak = 15;       // after 4 Pomodoros, break is 15 minutes
    private static int delay = 100;
    private static int period = 100;
    private boolean paused = true;
    private int remainingTime;                // remaining time in timer
    private int counter;                      // tracks number of fully complete Pomodoros

    public Integer getPomodoroLength() {
        return pomodoroLength;
    }

    public Integer getRemainingTime() {
        return remainingTime;
    }

    public Boolean getPaused() {
        return paused;
    }

    public void startTimer(int initialTime) {
        System.out.print("Starting Pomodoro! ");
        paused = false;
        System.out.print(initialTime);
        System.out.println(" minutes remaining!");
        remainingTime = initialTime;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                tickRemainingTime();
                System.out.println(remainingTime);
            }
        }, delay, period);
    }

    private final void tickRemainingTime() {
        if (remainingTime == 1) {
            timer.cancel();
        }
        remainingTime--;
    }

    public void pauseTimer() {
        timer.cancel();
        System.out.println("Paused timer!");
        paused = true;
    }

    public void unpauseTimer() {
        paused = false;
        System.out.println("Un-paused timer!");
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                tickRemainingTime();
                System.out.println(remainingTime);
            }
        }, delay, period);
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

    public int startShortBreak(int shortBreak) {
        return 0;
    }

    public int startLongBreak(int longBreak) {
        return 0;
    }













}





