package model;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Temporary Pokemon collection to store Pokemon obtained from Pomodoro timer
public class TempCollection {

    private static List<Pokemon> tempCollection;

    // EFFECTS: initializes tempCollection
    public static void init() {
        tempCollection = new LinkedList<>();
    }

    public static List<Pokemon> getTempCollection() {
        return tempCollection;
    }

    // getter for size
    public static int getTempSize() {
        return tempCollection.size();
    }

    // MODIFIES: tempCollection
    // EFFECTS: adds given Pokemon to tempCollection
    public static void addPokemonToTemporaryCollection(Pokemon pokemon) {
        tempCollection.add(pokemon);
    }

    // EFFECTS: if tempCollection is empty, do nothing. else, print out list of Pokemon names in tempCollection
    public static Boolean printTempCollection() {
        if (!(tempCollection.isEmpty())) {
            System.out.println("You have Pokémon waiting to be added to your collection!");
            for (Pokemon pokemon : tempCollection) {
                System.out.println(pokemon.getPokemonName());
            }
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: clears tempCollection
    public static void resetTemp() {
        tempCollection.clear();
    }
}
