package model;

// Represents a Pokemon with its actual name and a given name (latter not yet implemented)
public class Pokemon {
    private String actualName;
    private String givenName;

    // Constructor
    public Pokemon(String actualName, String givenName) {
        this.actualName = actualName;
        this.givenName = givenName;
    }

    // Getter
    public String getPokemonName() {
        return this.actualName;
    }

    // REQUIRES: list must not be empty
    // MODIFIES: this
    // EFFECTS: Generates random index and prints/returns element at that index in list
    public static Pokemon getRandomPokemon() {
        int index = (int) (Math.random() * AllPokemonList.pokemonList.size());
        String name = AllPokemonList.pokemonList.get(index);
        Pokemon pokemon = new Pokemon(name, "");
        System.out.println("Your Pokémon is..." + name);
        return pokemon;
    }






}
