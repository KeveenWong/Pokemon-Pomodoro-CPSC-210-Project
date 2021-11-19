package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TempCollectionTest {
    private TempCollection tempCollection;

    @BeforeEach
    public void runBefore() {
        tempCollection = new TempCollection();
    }

    @Test
    public void testPrintTempCollectionEmpty() {
        assertFalse(tempCollection.printTempCollection());
    }

    @Test
    public void testPrintTempCollectionNotEmpty() {
        Pokemon pokemon1 = new Pokemon("test1", "");
        Pokemon pokemon2 = new Pokemon("test2", "");

        tempCollection.addPokemonToTemporaryCollection(pokemon1);
        tempCollection.addPokemonToTemporaryCollection(pokemon2);
        assertTrue(tempCollection.printTempCollection());

    }

    @Test
    public void testResetTemp() {
        Pokemon pokemon1 = new Pokemon("test1", "");
        Pokemon pokemon2 = new Pokemon("test2", "");
        tempCollection.addPokemonToTemporaryCollection(pokemon1);
        tempCollection.addPokemonToTemporaryCollection(pokemon2);

        assertEquals(2, tempCollection.getTempSize());
        tempCollection.resetTemp();
        assertEquals(0, tempCollection.getTempSize());

    }

    @Test
    public void testGetTempCollection() {
        assertEquals(0, tempCollection.getTempCollectionList().size());
    }

}
