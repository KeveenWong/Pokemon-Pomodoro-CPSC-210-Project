package model;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TempCollection {

    private static List<Pokemon> tempCollection;

    public static void init() {
        tempCollection = new LinkedList<>();
    }

    public static List<Pokemon> getTempCollection() {
        return tempCollection;
    }

    public static void addPokemonToTemporaryCollection(Pokemon pokemon) {
        tempCollection.add(pokemon);
    }

    public static void printTempCollection() {
        if (!(tempCollection.isEmpty())) {
            System.out.println("You have Pokémon waiting to be added to your collection!");
            for (Pokemon pokemon : tempCollection) {
                System.out.println(pokemon.getPokemonName());
            }

        }
    }

    public static void resetTemp() {
        tempCollection.clear();
    }
}
