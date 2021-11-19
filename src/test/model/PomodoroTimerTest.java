package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.awt.image.ImageWatched;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PomodoroTimerTest {
    private PomodoroTimer timer;
    private PokemonCollection pokemonCollection;
    private TempCollection tempCollection;

    @BeforeEach
    public void runBefore() {
        timer = new PomodoroTimer();
        pokemonCollection = new PokemonCollection();
        tempCollection = new TempCollection();
    }

    private static final int DELTA = 1;

    @Test
    public void testStartTimer() throws InterruptedException {
        timer.startTimer(5);
        assertEquals(5, timer.getRemainingTime());
        Thread.sleep(1100);
        assertEquals(4, timer.getRemainingTime(), DELTA);
    }

    @Test
    public void testTickRemainingTime() throws InterruptedException {
        timer.startTimer(3);
        Thread.sleep(3100);
        assertEquals(timer.getState(), PomodoroTimer.State.ShortBreak);
        timer.switchBetweenCases();
        assertEquals(timer.getState(), PomodoroTimer.State.Pomodoro);

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
        timer.unpauseTimer();
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
        timer.pauseTimer();
        assertEquals(timer.getPomodoroLength(), timer.getRemainingTime());
        assertTrue(timer.getPaused());
        timer.pauseTimer();
        timer.resetTimer();
        assertEquals(timer.getPomodoroLength(), timer.getRemainingTime());

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
        timer.startTimer(5);
        Thread.sleep(5100);
        assertEquals(timer.getState(), PomodoroTimer.State.ShortBreak);
        assertEquals(1, timer.getPomodoroCounter());
        assertEquals(3, timer.getRemainingTime(), DELTA);

    }

    @Test
    public void testShortBreakFinish() throws InterruptedException {
        timer.startTimer(5);
        Thread.sleep(5100);
        // start short break
        Thread.sleep(3100);
        // start Pomodoro
        assertEquals(timer.getState(), PomodoroTimer.State.Pomodoro);
        assertEquals(5, timer.getRemainingTime(), DELTA);
    }

    @Test
    public void testLongBreakFinish() throws InterruptedException {
        timer.startTimer(5);
        Thread.sleep(5100);
        // 1 pomodoro
        // start short break
        Thread.sleep(3100);
        // start Pomodoro
        assertEquals(timer.getState(), PomodoroTimer.State.Pomodoro);
        Thread.sleep(5100);
        // 2 pomodoro
        // start short break
        Thread.sleep(3100);
        // start Pomodoro
        Thread.sleep(5100);
        // 3 pomodoro
        // start short break
        Thread.sleep(3100);
        // start Pomodoro
        Thread.sleep(5100);
        // 4 pomodoro
        // start long break
        assertEquals(timer.getState(), PomodoroTimer.State.LongBreak);
        assertEquals(4, timer.getRemainingTime(), DELTA);
    }

    @Test
    public void testGetTempCollection() {
        Pokemon pokemon = new Pokemon("test", "");
        timer.getTempCollection().addPokemonToTemporaryCollection(pokemon);
        assertEquals(1, timer.getTempCollection().getTempCollectionList().size());

    }

}
