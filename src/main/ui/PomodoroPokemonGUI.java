/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
this class references code from the following sources:
https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
https://stackoverflow.com/questions/22162398/how-to-set-a-background-picture-in-jpanel
https://www.codejava.net/java-se/swing/redirect-standard-output-streams-to-jtextarea
*/

package ui;

import model.Event;
import model.EventLog;
import model.Pokemon;
import model.PokemonCollection;
import model.PomodoroTimer;
import persistence.Reading;
import persistence.Writing;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

// Pokemon Pomodoro GUI application
public class PomodoroPokemonGUI extends JPanel implements ActionListener {
    private static final String JSON_STORE = "./data/pokemoncollection.json";
    private JButton timerButton;
    private JButton collectionButton;
    private JButton saveButton;
    private JButton loadButton;
    private JLabel saveAndLoadLabel;
    private JButton quitButton;
    private JButton pauseButton;
    private JButton unpauseButton;
    private JButton resetButton;
    private JButton exitButton;
    private JButton clearButton;
    private PomodoroTimer timer;
    private Pokemon pokemon;
    private PokemonCollection pokemonCollection;
    private Writing jsonWriter;
    private Reading jsonReader;

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */

    // MODIFIES: this
    // EFFECTS: creates the GUI and shows it
    public static void createAndShowGUI() {

        // Create and set up the window.
        JFrame frame = new JFrame("PomodoroPokemonGUI");
        frame.setSize(600,300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Create and set up the content pane.
        PomodoroPokemonGUI newContentPane = new PomodoroPokemonGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        newContentPane.setBackground(Color.darkGray);

        // Centre the window
        frame.setLocationRelativeTo(null);
    }

    // EFFECTS: runs the program
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds all aspects of main GUI
    public PomodoroPokemonGUI() {
        initialize();

        initializeMainButtons();

        // Listen for actions on buttons
        timerButton.addActionListener(this);
        collectionButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        quitButton.addActionListener(this);

        // Add Components to this container, using the default FlowLayout.
        add(timerButton);
        add(collectionButton);
        add(saveButton);
        add(loadButton);
        add(quitButton);

        // Initialize and add saving and loading label to the GUI
        saveAndLoadLabel = new JLabel("");
        add(saveAndLoadLabel);
    }

    // MODIFIES: this
    // EFFECTS: initializes all buttons, sets action commands and tooltip text
    public void initializeMainButtons() {
        // Initialize all buttons to main GUI
        timerButton = new JButton("Pomodoro Timer");
        timerButton.setVerticalTextPosition(AbstractButton.CENTER);
        timerButton.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        timerButton.setActionCommand("timer");

        collectionButton = new JButton("Collection");
        collectionButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        collectionButton.setHorizontalTextPosition(AbstractButton.CENTER);
        collectionButton.setActionCommand("collection");

        saveButton = new JButton("Save");
        saveButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        saveButton.setHorizontalTextPosition(AbstractButton.LEFT);
        saveButton.setActionCommand("save");

        loadButton = new JButton("Load");
        loadButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        loadButton.setHorizontalTextPosition(AbstractButton.RIGHT);
        loadButton.setActionCommand("load");

        quitButton = new JButton("Quit");
        // Use the default text position of CENTER, TRAILING (RIGHT).
        quitButton.setActionCommand("quit");

        // Set tool tip button texts
        timerButton.setToolTipText("Enter the Pomodoro Timer");
        collectionButton.setToolTipText("View your Pokemon collection");
        saveButton.setToolTipText("Save your collection");
        loadButton.setToolTipText("Load your collection");
        quitButton.setToolTipText("Quit the program");
    }

    // MODIFIES: this
    // EFFECTS: initializes timer, collection, writer and reader
    public void initialize() {
        timer = new PomodoroTimer();
        pokemonCollection = new PokemonCollection();
        jsonWriter = new Writing(JSON_STORE);
        jsonReader = new Reading(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: listens to action performed and runs code based on what button the user presses
    public void actionPerformed(ActionEvent e) {
        if ("timer".equals(e.getActionCommand())) {
            new TimerMenu().setVisible(true);
            saveAndLoadLabel.setText("");
        } else if ("collection".equals(e.getActionCommand())) {
            try {
                new CollectionPanel();
                saveAndLoadLabel.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if ("save".equals(e.getActionCommand())) {
            saveCollection();
        } else if ("load".equals(e.getActionCommand())) {
            loadCollection();
        } else if ("quit".equals(e.getActionCommand())) {
            System.out.println("See you next time!");
            for (Event event : EventLog.getInstance()) {
                System.out.println(event);
            }
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Collection from file
    private void loadCollection() {
        try {
            pokemonCollection = jsonReader.read();
            saveAndLoadLabel.setText("Loaded collection from " + JSON_STORE);
        } catch (IOException exp) {
            saveAndLoadLabel.setText("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this, pokemonCollection
    // EFFECTS: saves Collection from file
    private void saveCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(pokemonCollection);
            jsonWriter.close();
            saveAndLoadLabel.setText("Saved collection to " + JSON_STORE);
        } catch (FileNotFoundException exp) {
            saveAndLoadLabel.setText("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: Extends from OutputStream to redirect output to a JTextArea
    public static class CustomOutputStream extends OutputStream {
        private JTextArea textArea;

        public CustomOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        // EFFECTS: redirects data and scrolls the text area to the end of data
        public void write(int b) {
            // redirects data to the text area
            textArea.append(String.valueOf((char) b));
            // scrolls the text area to the end of data
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }

    /**
     * This class redirect output to a JTextArea representing a Pomodoro Timer
     * taken from CodeJava and modified for personal use
     *
     * @author www.codejava.net
     */
    // Menu for Timer
    public class TimerMenu extends JFrame {
        /**
         * The text area which is used for displaying logging information.
         */
        private JTextArea textArea;

        private PrintStream standardOut;

        private GridBagConstraints constraints;

        // MODIFIES: this
        // EFFECTS: initializes and adds all aspects of Timer GUI
        public TimerMenu() {
            super("Pomodoro Timer");

            timer = new PomodoroTimer();

            printConsoleToJTextArea();

            initializeGuiAndAddButtons();

            addScrollPaneWithConstraints();

            // starts Pomodoro Timer
            timer.startTimer(timer.getPomodoroLength());

            addMainTimerActions();

            addOtherTimerActions();

            setSize(480, 320);
            setLocationRelativeTo(null);    // centers on screen
        }

        // MODIFIES: this
        // EFFECTS: adds timer actions for exitButton and clearButton
        private void addOtherTimerActions() {
            // adds event handler for button Exit
            exitButton.addActionListener(new ActionListener() {
                @Override
                // EFFECTS: performs action for exitButton
                public void actionPerformed(ActionEvent e) {
                    System.setOut(standardOut);
                    System.setErr(standardOut);

                    dispose();
                    exitAndGoToSelection();
                }
            });


            // adds event handler for button Clear
            clearButton.addActionListener(new ActionListener() {
                @Override
                // EFFECTS: performs action for clearButton
                public void actionPerformed(ActionEvent evt) {
                    // clears the text area
                    try {
                        textArea.getDocument().remove(0,
                                textArea.getDocument().getLength());
                        standardOut.println("Text area cleared");
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }

        // MODIFIES: this, timer
        // EFFECTS: adds timer actions for pauseButton, unpauseButton, resetButton
        private void addMainTimerActions() {
            // adds event handler for button Pause
            pauseButton.addActionListener(new ActionListener() {
                @Override
                // MODIFIES: timer
                // EFFECTS: performs action for pauseButton
                public void actionPerformed(ActionEvent e) {
                    timer.pauseTimer();
                }
            });

            // adds event handler for button Un-pause
            unpauseButton.addActionListener(new ActionListener() {
                @Override
                // MODIFIES: timer
                // EFFECTS: performs action for unpauseButton
                public void actionPerformed(ActionEvent e) {
                    timer.unpauseTimer();
                }
            });

            // adds event handler for button Reset
            resetButton.addActionListener(new ActionListener() {
                @Override
                // MODIFIES: timer
                // EFFECTS: performs action for resetButton
                public void actionPerformed(ActionEvent e) {
                    timer.resetTimer();
                }
            });
        }

        // MODIFIES: this
        // EFFECTS: adds scroll pane to textArea with constraints
        private void addScrollPaneWithConstraints() {
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 4;
            constraints.fill = GridBagConstraints.BOTH;
            constraints.weightx = 1.0;
            constraints.weighty = 1.0;

            add(new JScrollPane(textArea), constraints);
        }

        // MODIFIES: this
        // EFFECTS: prints console text to the JTextArea
        private void printConsoleToJTextArea() {
            textArea = new JTextArea(50, 10);
            textArea.setEditable(false);
            PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

            // keeps reference of standard output stream
            standardOut = System.out;

            // re-assigns standard output stream and error output stream
            System.setOut(printStream);
            System.setErr(printStream);
        }

        // MODIFIES: this
        // EFFECTS: creates GUI and adds buttons to GUI
        private void initializeGuiAndAddButtons() {
            // creates the GUI
            setLayout(new GridBagLayout());
            constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.insets = new Insets(10, 5, 10, 5);
            constraints.anchor = GridBagConstraints.WEST;

            // Pause
            pauseButton = new JButton("Pause");
            add(pauseButton, constraints);

            // Un-pause
            unpauseButton = new JButton("Un-pause");
            constraints.gridx = 1;
            add(unpauseButton, constraints);

            // Reset
            resetButton = new JButton("Reset");
            constraints.gridx = 2;
            add(resetButton, constraints);

            // Exit
            exitButton = new JButton("Exit");
            constraints.gridx = 3;
            add(exitButton, constraints);

            // Clear
            clearButton = new JButton("Clear");
            constraints.gridx = 4;
            add(clearButton, constraints);
        }

    }

    // MODIFIES: timer
    // EFFECTS: exits Pomodoro Timer and enters Selection Menu
    private void exitAndGoToSelection() {
        timer.exitTimer();
        if (!timer.getTempCollection().getTempCollectionList().isEmpty()) {
            new SelectionMenu();
        }
    }

    public class SelectionMenu extends JFrame {
        private JFrame frame;
        private JPanel panel;
        private JButton yesButton;
        private JButton noButton;
        private JLabel label;
        private JLabel keepOrDiscardLabel;
        private JLabel collectionOrReleasedLabel;

        // MODIFIES: this
        // EFFECTS: creates Selection Menu with all buttons and labels
        public SelectionMenu() {
            initSelectionGui();

            panel.add(yesButton);
            panel.add(noButton);
            panel.add(label);
            panel.add(keepOrDiscardLabel);
            panel.add(collectionOrReleasedLabel);
            frame.add(panel);

            inquireNextPokemon();

            handleSelectionActions();
        }

        // EFFECTS: handles Yes and No selection options
        private void handleSelectionActions() {
            // adds event handler for button Yes
            yesButton.addActionListener(new ActionListener() {
                @Override
                // MODIFIES: this, TempCollection
                // EFFECTS: performs action for yesButton (adds pokemon, checks next Pokemon if there is one)
                public void actionPerformed(ActionEvent e) {

                    addPokemonToCollection();
                    if (timer.getTempCollection().getTempCollectionList().isEmpty()) {
                        frame.dispose();
                        return;
                    }
                    inquireNextPokemon();
                }
            });

            // adds event handler for button No
            noButton.addActionListener(new ActionListener() {
                @Override
                // MODIFIES: this, TempCollection
                // EFFECTS: performs action for noButton (releases pokemon and checks for next pokemon,
                // closes menu if no more pokemon
                public void actionPerformed(ActionEvent e) {
                    releasePokemon();
                    if (timer.getTempCollection().getTempCollectionList().isEmpty()) {
                        frame.dispose();
                        return;
                    }
                    inquireNextPokemon();
                }
            });
        }

        // EFFECTS: asks user about next pokemon in TempCollection
        private void inquireNextPokemon() {
            pokemon = timer.getTempCollection().getTempCollectionList().get(0);
            keepOrDiscardLabel.setText("Would you like to keep" + pokemon.getPokemonName() + "?");
        }

        // MODIFIES: this, TempCollection
        // EFFECTS: removes pokemon from TempCollection
        private void releasePokemon() {
            collectionOrReleasedLabel.setText(pokemon.getPokemonName() + " was released.");
            timer.getTempCollection().getTempCollectionList().remove(pokemon);
        }

        // MODIFIES: this, pokemonCollection, TempCollection
        // EFFECTS: adds pokemon to pokemonCollection and removes from TempCollection
        private void addPokemonToCollection() {
            pokemonCollection.addPokemonToCollection(pokemon);
            collectionOrReleasedLabel.setText(pokemon.getPokemonName() + " added to collection.");
            timer.getTempCollection().getTempCollectionList().remove(pokemon);
        }

        // MODIFIES: this
        // EFFECTS: creates Selection Menu GUI
        private void initSelectionGui() {
            frame = new JFrame("Pokemon Selection");
            frame.setVisible(true);
            frame.setSize(600, 200);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            panel = new JPanel();
            panel.setBackground(Color.PINK);

            yesButton = new JButton("YES");
            yesButton.setVerticalTextPosition(AbstractButton.BOTTOM);
            yesButton.setHorizontalTextPosition(AbstractButton.CENTER);

            noButton = new JButton("NO");
            noButton.setVerticalTextPosition(AbstractButton.BOTTOM);
            noButton.setHorizontalTextPosition(AbstractButton.RIGHT);

            label = new JLabel("You have Pokémon waiting to be added to your collection!");

            keepOrDiscardLabel = new JLabel("");

            collectionOrReleasedLabel = new JLabel("");
            collectionOrReleasedLabel.setForeground(Color.BLUE);
        }
    }

    public class CollectionPanel extends JPanel {
        private JFrame frame;
        private JPanel panel;
        private JLabel title;
        private JLabel pokemonList;
        private JButton exitButton;

        GridBagConstraints gbc = new GridBagConstraints();


        // MODIFIES: this
        // EFFECTS: initializes and creates Collection Panel with all collected pokemon listed
        public CollectionPanel() throws IOException {
            initializeCollectionGui();

            // prints all Pokemon in collection
            for (Pokemon pokemon : pokemonCollection.getCollection()) {
                pokemonList = new JLabel(pokemon.getPokemonName());
                frame.add(pokemonList, gbc);
            }

            frame.add(exitButton, gbc);

            // adds event handler for button Exit
            exitButton.addActionListener(new ActionListener() {
                @Override
                // MODIFIES: this
                // EFFECTS: performs action for exitButton (dispose/close Collection Panel)
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

        // MODIFIES: this
        // EFFECTS: initializes Collection Panel GUI
        private void initializeCollectionGui() {
            try {
                BufferedImage img = ImageIO.read(new File(
                        "." + File.separator + "data" + File.separator + "pokemonimg.jpeg"));

                frame = new JFrame("Pokemon Collection");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                frame.setContentPane(new JLabel(new ImageIcon(img)));

                frame.setLayout(new GridBagLayout());
                gbc.gridwidth = GridBagConstraints.REMAINDER;

                panel = new JPanel();
                JScrollPane scroller = new JScrollPane(panel);
                frame.add(scroller);

                title = new JLabel("Your Pokemon Collection");
                title.setForeground(Color.MAGENTA);
                frame.add(title, gbc);

                pokemonList = new JLabel("");

                exitButton = new JButton("Exit");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}