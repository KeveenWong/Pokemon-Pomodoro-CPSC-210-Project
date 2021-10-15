package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllPokemonList {
    public static final List<String> pokemonList;

    static {
        List<String> temp;
        try {
            temp = Files.readAllLines(new File("pokemon.txt").toPath());
        } catch (IOException e) {
            temp = new ArrayList<>();
            e.printStackTrace();
        }
        String pokemonString = temp.get(0);
        pokemonList = Arrays.asList(pokemonString.split(","));
    }

}
