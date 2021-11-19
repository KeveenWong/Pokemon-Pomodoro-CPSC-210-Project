package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AllPokemonListTest {

    @Test
    public void testAllPokemonListFail() {
        assertThrows(IllegalStateException.class, () -> new AllPokemonList("test"));
    }
}
