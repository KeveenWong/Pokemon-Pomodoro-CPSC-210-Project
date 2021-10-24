// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

package persistence;

import model.Pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPokemon(String name, Pokemon pokemon) {
        assertEquals(name, pokemon.getPokemonName());
    }
}
