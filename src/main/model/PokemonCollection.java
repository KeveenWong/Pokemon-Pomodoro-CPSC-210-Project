package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PokemonCollection {

    private static List<Pokemon> collection;

    // EFFECTS: initializes collection
    public static void init() {
        collection = new LinkedList<>();
    }

    public static List<Pokemon> getCollection() {
        return collection;
    }

    // EFFECTS: prints each name of Pokemon in collection list
    public static void printCollection() {
        for (Pokemon pokemon : collection) {
            System.out.println(pokemon.getPokemonName());
        }

    }

    // MODIFIES: collection
    // EFFECTS: adds given Pokemon to collection list
    public static void addPokemonToCollection(Pokemon pokemon) {
        collection.add(pokemon);
    }


}
