// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

package persistence;

import model.Pokemon;
import model.PokemonCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WritingTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Writing writer = new Writing("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPokemonCollection() {
        try {
            PokemonCollection pc = new PokemonCollection();
            Writing writer = new Writing("./data/testWriterEmptyPokemonCollection.json");
            writer.open();
            writer.write(pc);
            writer.close();

            Reading reader = new Reading("./data/testWriterEmptyPokemonCollection.json");
            pc = reader.read();
            assertEquals(0, pc.numPokemons());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPokemonCollection() {
        try {
            PokemonCollection pc = new PokemonCollection();
            pc.addPokemonToCollection(new Pokemon("Pikachu", ""));
            pc.addPokemonToCollection(new Pokemon("Charmander", ""));
            Writing writer = new Writing("./data/testWriterGeneralPokemonCollection.json");
            writer.open();
            writer.write(pc);
            writer.close();

            Reading reader = new Reading("./data/testWriterGeneralPokemonCollection.json");
            pc = reader.read();
            List<Pokemon> pokemons = pc.getCollection();
            assertEquals(2, pokemons.size());
            checkPokemon("Pikachu", pokemons.get(0));
            checkPokemon("Charmander", pokemons.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}