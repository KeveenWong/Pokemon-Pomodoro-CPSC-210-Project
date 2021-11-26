package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

// Represents the user's actual Pokemon collection
public class PokemonCollection {
    private List<Pokemon> collection;

    // EFFECTS: initializes collection
    public PokemonCollection() {
        collection = new LinkedList<>();
    }

    public List<Pokemon> getCollection() {
        return collection;
    }

    // EFFECTS: prints each name of Pokemon in collection list
    public Boolean printCollection() {
        if (!(collection.isEmpty())) {
            for (Pokemon pokemon : collection) {
                System.out.println(pokemon.getPokemonName());
            }
            return true;
        }
        System.out.println("It's awfully empty in here... try collecting some Pokémon!");
        return false;

    }

    // EFFECTS: returns number of Pokemons in this workroom
    public int numPokemons() {
        return collection.size();
    }

    // MODIFIES: PokemonCollection
    // EFFECTS: adds given Pokemon to collection list
    public void addPokemonToCollection(Pokemon pokemon) {
        collection.add(pokemon);
        EventLog.getInstance().logEvent(new Event("Added Pokemon to Collection."));
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Pokemons", pokemonToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray pokemonToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Pokemon p : collection) {
            jsonArray.put(p.toJson());
            EventLog.getInstance().logEvent(new Event("Saved Pokemon to JSON."));

        }

        return jsonArray;
    }
}
