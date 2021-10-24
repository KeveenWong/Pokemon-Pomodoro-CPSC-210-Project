// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

package persistence;

import model.Pokemon;
import model.PokemonCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReadingTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        Reading reader = new Reading("./data/noSuchFile.json");
        try {
            PokemonCollection pc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPokemonCollection() {
        Reading reader = new Reading("./data/testReaderEmptyPokemonCollection.json");
        try {
            PokemonCollection pc = reader.read();
            assertEquals(0, pc.numPokemons());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPokemonCollection() {
        Reading reader = new Reading("./data/testReaderGeneralPokemonCollection.json");
        try {
            PokemonCollection pc = reader.read();
            List<Pokemon> pokemons = pc.getCollection();
            assertEquals(2, pokemons.size());
            checkPokemon(" Pikachu", pokemons.get(0));
            checkPokemon(" Squirtle", pokemons.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}