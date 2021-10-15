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
    private static final int DELTA = 1;


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
        Thread.sleep(1000);
        assertEquals(4, timer.getRemainingTime(), DELTA);
        timer.pauseTimer();
        Thread.sleep(5000);
        assertEquals(4, timer.getRemainingTime(), DELTA);
    }

    @Test
    public void testUnpauseTimer() throws InterruptedException {
        timer.startTimer(5);
        Thread.sleep(1000);
        assertEquals(4, timer.getRemainingTime(), DELTA);
        timer.pauseTimer();
        Thread.sleep(2000);
        timer.unpauseTimer();
        Thread.sleep(3000);
        assertEquals(1, timer.getRemainingTime(), DELTA);

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
    public void testSwitchBetweenCases() {
        TempCollection.init();
        assertEquals(timer.getState(), PomodoroTimer.State.Pomodoro);
        timer.switchBetweenCases();
        assertEquals(1, timer.getPomodoroCounter());

        assertEquals(timer.getState(), PomodoroTimer.State.ShortBreak);
        timer.switchBetweenCases();

        assertEquals(timer.getState(), PomodoroTimer.State.Pomodoro);
        timer.switchBetweenCases();
        assertEquals(2, timer.getPomodoroCounter());

        timer.switchBetweenCases();
        timer.switchBetweenCases();
        timer.switchBetweenCases();
        timer.switchBetweenCases();
        assertEquals(0, timer.getPomodoroCounter());
        assertEquals(timer.getState(), PomodoroTimer.State.LongBreak);

    }

    @Test
    public void testPomodoroFinish() throws InterruptedException {
        TempCollection.init();
        timer.startTimer(5);
        Thread.sleep(5100);
        assertEquals(timer.getState(), PomodoroTimer.State.ShortBreak);

    }

    @Test
    public void testShortBreakFinish() throws InterruptedException {
        TempCollection.init();
        timer.startTimer(5);
        Thread.sleep(5100);
        // start short break
        Thread.sleep(2100);
        // start Pomodoro
        assertEquals(timer.getState(), PomodoroTimer.State.Pomodoro);
    }

    @Test
    public void testLongBreakFinish() throws InterruptedException {
        TempCollection.init();
        timer.startTimer(5);
        Thread.sleep(5100);
        // 1 pomodoro
        // start short break
        Thread.sleep(2100);
        // start Pomodoro
        assertEquals(timer.getState(), PomodoroTimer.State.Pomodoro);
        Thread.sleep(4100);
        // 2 pomodoro
        // start short break
        Thread.sleep(2100);
        // start Pomodoro
        Thread.sleep(4100);
        // 3 pomodoro
        // start short break
        Thread.sleep(2100);
        // start Pomodoro
        Thread.sleep(4100);
        // 4 pomodoro
        // start long break
        assertEquals(timer.getState(), PomodoroTimer.State.LongBreak);
    }

}