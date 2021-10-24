package model;

import sun.awt.image.ImageWatched;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Temporary Pokemon collection to store Pokemon obtained from Pomodoro timer
public class TempCollection {
    private List<Pokemon> tempCollection;

    public TempCollection() {
        tempCollection = new LinkedList<>();
    }

    // getter for tempCollection
    public List<Pokemon> getTempCollectionList() {
        return tempCollection;
    }

    // getter for size
    public int getTempSize() {
        return tempCollection.size();
    }

    // MODIFIES: tempCollection
    // EFFECTS: adds given Pokemon to tempCollection
    public void addPokemonToTemporaryCollection(Pokemon pokemon) {
        tempCollection.add(pokemon);
    }

    // EFFECTS: if tempCollection is empty, do nothing. else, print out list of Pokemon names in tempCollection
    public Boolean printTempCollection() {
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
    public void resetTemp() {
        tempCollection.clear();
    }

}
