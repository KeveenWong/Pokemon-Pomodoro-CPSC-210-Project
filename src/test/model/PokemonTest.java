package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PokemonTest {

    @Test
    public void testGetPokemonName() {
        Pokemon pokemon = new Pokemon("test", "");
        assertEquals("test", pokemon.getPokemonName());
    }

    @Test
    // NOTE: since this uses random it is a bit challenging/odd to do a test for so I just printed a bunch of random
    // pokemon to make sure it was working consistently.
    public void testGetRandomPokemon() {
        for (int i = 0; i < 1000; i++) {
            Pokemon.getRandomPokemon("pokemon.txt");
        }
    }

    @Test
    public void testPrintTempCollectionEmpty() {
        TempCollection.init();
        assertFalse(TempCollection.printTempCollection());
    }

    @Test
    public void testPrintTempCollectionNotEmpty() {
        Pokemon pokemon1 = new Pokemon("test1", "");
        Pokemon pokemon2 = new Pokemon("test2", "");

        TempCollection.init();
        TempCollection.addPokemonToTemporaryCollection(pokemon1);
        TempCollection.addPokemonToTemporaryCollection(pokemon2);
        assertTrue(TempCollection.printTempCollection());

    }

    @Test
    public void testResetTemp() {
        Pokemon pokemon1 = new Pokemon("test1", "");
        Pokemon pokemon2 = new Pokemon("test2", "");
        TempCollection.init();
        TempCollection.addPokemonToTemporaryCollection(pokemon1);
        TempCollection.addPokemonToTemporaryCollection(pokemon2);

        assertEquals(2, TempCollection.getTempSize());
        TempCollection.resetTemp();
        assertEquals(0, TempCollection.getTempSize());

    }

    @Test
    public void testGetTempCollection() {
        TempCollection.init();
        assertEquals(0, TempCollection.getTempCollection().size());
    }

    @Test
    public void testGetCollection() {
        PokemonCollection.init();
        assertEquals(0, PokemonCollection.getCollection().size());
    }

    @Test
    public void testPrintCollectionEmpty() {
        PokemonCollection.init();
        assertFalse(PokemonCollection.printCollection());
    }

    @Test
    public void testPrintCollectionNotEmpty() {
        Pokemon pokemon1 = new Pokemon("test1", "");
        Pokemon pokemon2 = new Pokemon("test2", "");

        PokemonCollection.init();
        PokemonCollection.addPokemonToCollection(pokemon1);
        PokemonCollection.addPokemonToCollection(pokemon2);
        assertTrue(PokemonCollection.printCollection());
    }

    @Test
    public void testAddPokemonToCollection() {
        Pokemon pokemon1 = new Pokemon("test1", "");
        Pokemon pokemon2 = new Pokemon("test2", "");

        PokemonCollection.init();
        PokemonCollection.addPokemonToCollection(pokemon1);
        PokemonCollection.addPokemonToCollection(pokemon2);

        assertEquals(2, PokemonCollection.getCollection().size());

    }

    @Test
    public void testAllPokemonListFail() {
        assertThrows(IllegalStateException.class, () -> new AllPokemonList("test"));
    }
}
