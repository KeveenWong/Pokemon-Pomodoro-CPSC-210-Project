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
    public void testStartTimer() throws InterruptedException {
        timer.startTimer(5);
        assertEquals(5, timer.getRemainingTime());
        Thread.sleep(1100);
        assertEquals(4, timer.getRemainingTime());
    }

    @Test
    public void testTickRemainingTime() throws InterruptedException {

    }

    @Test
    public void testPauseTimer() throws InterruptedException {
        timer.startTimer(5);
        Thread.sleep(1100);
        assertEquals(4, timer.getRemainingTime());
        timer.pauseTimer();
        Thread.sleep(5000);
        assertEquals(4, timer.getRemainingTime());
    }

    @Test
    public void testUnpauseTimer() throws InterruptedException {
        timer.startTimer(5);
        Thread.sleep(1100);
        assertEquals(4, timer.getRemainingTime());
        timer.pauseTimer();
        Thread.sleep(2000);
        timer.unpauseTimer();
        Thread.sleep(3100);
        assertEquals(1, timer.getRemainingTime());

    }

    @Test
    public void testResetTimer() {
        timer.startTimer(10);
        timer.resetTimer();
        assertEquals(timer.getPomodoroLength(), timer.getRemainingTime());
        timer.pauseTimer();
        assertTrue(timer.getPaused());
    }

    @Test
    public void testExitTimer() {
        timer.startTimer(10);
        timer.exitTimer();
        assertEquals(10, timer.getRemainingTime());
    }

    @Test
    public void testGetState() {
        assertEquals(PomodoroTimer.State.Pomodoro, timer.getState());
    }

    @Test
    public void testSwitchBetweenCases() throws InterruptedException {
        timer.startTimer(2);
        Thread.sleep(1000);
    }

}