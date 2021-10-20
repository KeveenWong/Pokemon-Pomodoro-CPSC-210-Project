package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

// A list of over 800 Pokemon
public class AllPokemonList {
    public final List<String> pokemonList;

    // REQUIRES: file to read
    // MODIFIES: this
    // EFFECTS: creates a list of all Pokemon names from provided filename
    public AllPokemonList(String filename) {
        List<String> pokemonList1;
        try {
            String pokemonString = Files.readAllLines(new File(
                    "." + File.separator + "data" + File.separator + filename).toPath()).get(0);
            pokemonList1 = Arrays.asList(pokemonString.split(","));
        } catch (IOException e) {
            System.out.println("Pokemon list could not be read.");
            throw new IllegalStateException();
        }

        pokemonList = pokemonList1;
    }
}
