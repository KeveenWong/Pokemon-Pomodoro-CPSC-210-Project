package model;

public class Pokemon {
    private String actualName;
    private String givenName;


    public Pokemon(String actualName, String givenName) {
        this.actualName = actualName;
        this.givenName = givenName;
    }

    public String getPokemonName() {
        return this.actualName;
    }

    public static Pokemon getRandomPokemon() {
        int index = (int) (Math.random() * AllPokemonList.pokemonList.size());
        String name = AllPokemonList.pokemonList.get(index);
        Pokemon pokemon = new Pokemon(name, "");
        System.out.println("Your Pokémon is..." + name);
        return pokemon;
    }






}
