package model;

import java.io.IOException;
import java.util.List;

// Represents a Pokemon with its actual name and a given name (latter not yet implemented)
public class Pokemon {
    private String actualName;
    private String givenName;
    private AllPokemonList pokemonList;

    // Constructor
    public Pokemon(String actualName, String givenName) {
        this.actualName = actualName;
        this.givenName = givenName;
        this.pokemonList = new AllPokemonList("pokemon.txt");
    }

    // Getter
    public String getPokemonName() {
        return this.actualName;
    }

    // REQUIRES: list must not be empty
    // MODIFIES: this
    // EFFECTS: Generates random index and prints/returns element at that index in list
    // NOTE: since this uses random it is a bit challenging/odd to do a test for
    public static Pokemon getRandomPokemon(String filename) {
        AllPokemonList pokemonList = new AllPokemonList(filename);
        int index = (int) (Math.random() * pokemonList.pokemonList.size());
        String name = pokemonList.pokemonList.get(index);
        Pokemon pokemon = new Pokemon(name, "");
        System.out.println("Your Pokémon is..." + name);
        return pokemon;
    }






}
