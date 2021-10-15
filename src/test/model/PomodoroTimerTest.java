package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PomodoroTimerTest {
    private PomodoroTimer timer;

    @BeforeEach
    public void runBefore() {
        timer = new PomodoroTimer();
    }

    @Test
    void testStartTimer() {
        timer.startTimer(600);
        assertEquals(600, timer.getRemainingTime());
        try
        {
            Thread.sleep(1100);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        assertEquals(599, timer.getRemainingTime());
        assertEquals(1, timer.getSecondCounter());
    }

    @Test
    void testTickRemainingTime() {
        timer.startTimer(2);
        timer.tickRemainingTime();
        assertEquals(1, timer.getRemainingTime());
        timer.tickRemainingTime();
        assertEquals(timer.getState(), PomodoroTimer.State.ShortBreak);
        assertEquals(timer.getShortBreak(), timer.getRemainingTime());
    }

    @Test
    void testPauseTimer() {
        timer.startTimer(5);
        try
        {
            Thread.sleep(1100);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        assertEquals(4, timer.getRemainingTime());
        timer.pauseTimer();
        try
        {
            Thread.sleep(5000);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        assertEquals(4, timer.getRemainingTime());
    }

    @Test
    void testUnpauseTimer() {
        timer.startTimer(5);
        try {
            Thread.sleep(1100);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        assertEquals(4, timer.getRemainingTime());
        timer.pauseTimer();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        timer.unpauseTimer();
        try {
            Thread.sleep(3100);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        assertEquals(1, timer.getRemainingTime());

    }

    @Test
    void testResetTimer() {
        timer.startTimer(10);
        timer.resetTimer();
        assertEquals(1500, timer.getRemainingTime());
    }

}