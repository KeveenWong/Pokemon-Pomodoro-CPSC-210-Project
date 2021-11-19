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
    public void testAllPokemonListFail() {
        assertThrows(IllegalStateException.class, () -> new AllPokemonList("test"));
    }
}
