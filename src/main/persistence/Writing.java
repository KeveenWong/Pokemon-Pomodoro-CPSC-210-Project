// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

package persistence;

import model.PokemonCollection;
import org.json.JSONObject;


import java.io.*;

// Represents a writer that writes JSON representation of PokemonCollection to file
public class Writing {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public Writing(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of PokemonCollection to file
    public void write(PokemonCollection pc) {
        JSONObject json = pc.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
