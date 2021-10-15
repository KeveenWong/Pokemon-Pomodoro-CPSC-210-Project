package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PokemonCollection {

    private static List<Pokemon> collection;

    public static void init() {
        collection = new LinkedList<>();
    }

    public static List<Pokemon> getCollection() {
        return collection;
    }

    public static void printCollection() {
        for (Pokemon pokemon : collection) {
            System.out.println(pokemon.getPokemonName());
        }

    }

    public static void addPokemonToCollection(Pokemon pokemon) {
        collection.add(pokemon);
    }


}
