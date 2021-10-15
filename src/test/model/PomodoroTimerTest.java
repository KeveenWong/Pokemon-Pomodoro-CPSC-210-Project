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
            Thread.sleep(1000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        assertEquals(599, timer.getRemainingTime());
        assertEquals(1, timer.getSecondCounter());
    }

    @Test
    void testSwitchBetweenCases() {
        timer.switchBetweenCases();
    }
}