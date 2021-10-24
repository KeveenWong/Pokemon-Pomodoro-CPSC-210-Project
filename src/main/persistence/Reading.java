// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

package persistence;

import model.Pokemon;
import model.PokemonCollection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads PokemonCollection from JSON data stored in file
public class Reading {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public Reading(String source) {
        this.source = source;
    }

    // EFFECTS: reads PokemonCollection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PokemonCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePokemonCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses PokemonCollection from JSON object and returns it
    private PokemonCollection parsePokemonCollection(JSONObject jsonObject) {
        PokemonCollection pc = new PokemonCollection();
        addPokemons(pc, jsonObject);
        return pc;
    }

    // MODIFIES: pc
    // EFFECTS: parses Pokemons from JSON object and adds them to PokemonCollection
    private void addPokemons(PokemonCollection pc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Pokemons");
        for (Object json : jsonArray) {
            JSONObject nextPokemon = (JSONObject) json;
            addPokemon(pc, nextPokemon);
        }
    }

    // MODIFIES: pc
    // EFFECTS: parses Pokemon from JSON object and adds it to PokemonCollection
    private void addPokemon(PokemonCollection pc, JSONObject jsonObject) {
        String actualName = jsonObject.getString("name");
        String givenName = jsonObject.getString("name");

        Pokemon pokemon = new Pokemon(actualName, givenName);
        pc.addPokemonToCollection(pokemon);
    }
}
