package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PokemonCollectionTest {
    private PokemonCollection pokemonCollection;

    @BeforeEach
    public void runBefore() {
        pokemonCollection = new PokemonCollection();
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

}
