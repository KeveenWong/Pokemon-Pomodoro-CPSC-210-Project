package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PokemonTest {
    private PokemonCollection pokemonCollection;
    private TempCollection tempCollection;

    @BeforeEach
    public void runBefore() {
        pokemonCollection = new PokemonCollection();
        tempCollection = new TempCollection();
    }

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

    @Test
    public void testGetCollection() {
        assertEquals(0, pokemonCollection.getCollection().size());
    }

    @Test
    public void testPrintCollectionEmpty() {
        assertFalse(pokemonCollection.printCollection());
    }

    @Test
    public void testPrintCollectionNotEmpty() {
        Pokemon pokemon1 = new Pokemon("test1", "");
        Pokemon pokemon2 = new Pokemon("test2", "");

        pokemonCollection.addPokemonToCollection(pokemon1);
        pokemonCollection.addPokemonToCollection(pokemon2);
        assertTrue(pokemonCollection.printCollection());
    }

    @Test
    public void testAddPokemonToCollection() {
        Pokemon pokemon1 = new Pokemon("test1", "");
        Pokemon pokemon2 = new Pokemon("test2", "");

        pokemonCollection.addPokemonToCollection(pokemon1);
        pokemonCollection.addPokemonToCollection(pokemon2);

        assertEquals(2, pokemonCollection.getCollection().size());

    }

    @Test
    public void testAllPokemonListFail() {
        assertThrows(IllegalStateException.class, () -> new AllPokemonList("test"));
    }
}
