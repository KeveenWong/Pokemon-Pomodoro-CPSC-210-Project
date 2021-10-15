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
    void testStartTimer() throws InterruptedException {
        timer.startTimer(600);
        assertEquals(600, timer.getRemainingTime());
        Thread.sleep(1100);
        assertEquals(599, timer.getRemainingTime());
        assertEquals(1, timer.getSecondCounter());
    }

    @Test
    void testTickRemainingTime() throws InterruptedException {
        timer.startTimer(65);
        timer.tickRemainingTime();
        Thread.sleep(3100);
        assertEquals(3, timer.getSecondCounter());
        Thread.sleep(5100);
        assertEquals(8, timer.getSecondCounter());
        Thread.sleep(51100);
        assertEquals(59, timer.getSecondCounter());
        Thread.sleep(1100);
        assertEquals(1, timer.getSecondCounter());
        Thread.sleep(4100);
        assertEquals(timer.getState(), PomodoroTimer.State.ShortBreak);
        assertEquals(300, timer.getRemainingTime());
    }

    @Test
    void testPauseTimer() throws InterruptedException {
        timer.startTimer(5);
        Thread.sleep(1100);
        assertEquals(4, timer.getRemainingTime());
        timer.pauseTimer();
        Thread.sleep(5000);
        assertEquals(4, timer.getRemainingTime());
    }

    @Test
    void testUnpauseTimer() throws InterruptedException {
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
    void testResetTimer() {
        timer.startTimer(10);
        timer.resetTimer();
        assertEquals(timer.getPomodoroLength(), timer.getRemainingTime());
        timer.pauseTimer();
        assertTrue(timer.getPaused());
    }

    @Test
    void testExitTimer() {
        timer.startTimer(10);
        timer.exitTimer();
        assertEquals(10, timer.getRemainingTime());
    }

    @Test
    void testGetState() {
        assertEquals(PomodoroTimer.State.Pomodoro, timer.getState());
    }

    @Test
    void testSwitchBetweenCases() throws InterruptedException {
        timer.startTimer(2);
        Thread.sleep(1000);
    }

}