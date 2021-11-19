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

package ui;

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
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;

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
    private int currentTimerValue;
    private int timerSpeed = 1000;
    private int timerDelay = 1000;

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
    // EFFECTS: initializes timer, input, collections
    public void initialize() {
        timer = new PomodoroTimer();
        pokemonCollection = new PokemonCollection();
        jsonWriter = new Writing(JSON_STORE);
        jsonReader = new Reading(JSON_STORE);
    }

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
            System.exit(0);
        }
    }

    private void loadCollection() {
        try {
            pokemonCollection = jsonReader.read();
            saveAndLoadLabel.setText("Loaded collection from " + JSON_STORE);
        } catch (IOException exp) {
            saveAndLoadLabel.setText("Unable to read from file: " + JSON_STORE);
        }
    }

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


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {

        // Create and set up the window.
        JFrame frame = new JFrame("PomodoroPokemonGUI");
        frame.setSize(600,300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        PomodoroPokemonGUI newContentPane = new PomodoroPokemonGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        newContentPane.setBackground(Color.darkGray);

        // Centre the window
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * Create the GUI and show it.  For thread safety,
     *
     * @author www.codejava.net
     * @@ -172,4 +142,150 @@ public class PomodoroPokemonGUI extends JPanel implements ActionListener {
     * frame.setVisible(true);
     * }
     * <p>
     * public static void main(String[] args) {
     * //Schedule a job for the event-dispatching thread:
     * //creating and showing this application's GUI.
     * javax.swing.SwingUtilities.invokeLater(new Runnable() {
     * public void run() {
     * createAndShowGUI();
     * }
     * });
     * }
     * <p>
     * /**
     * This class extends from OutputStream to redirect output to a JTextArrea
     */
    public static class CustomOutputStream extends OutputStream {
        private JTextArea textArea;

        public CustomOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) throws IOException {
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
    public class TimerMenu extends JFrame {
        /**
         * The text area which is used for displaying logging information.
         */
        private JTextArea textArea;

        private PrintStream standardOut;


        @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
        public TimerMenu() {
            super("Pomodoro Timer");

            timer = new PomodoroTimer();

            textArea = new JTextArea(50, 10);
            textArea.setEditable(false);
            PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

            // keeps reference of standard output stream
            standardOut = System.out;

            // re-assigns standard output stream and error output stream
            System.setOut(printStream);
            System.setErr(printStream);

            // creates the GUI
            setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
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


            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 4;
            constraints.fill = GridBagConstraints.BOTH;
            constraints.weightx = 1.0;
            constraints.weighty = 1.0;

            add(new JScrollPane(textArea), constraints);

            // starts Pomodoro Timer
            timer.startTimer(timer.getPomodoroLength());

            // adds event handler for button Pause
            pauseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timer.pauseTimer();
                }
            });

            // adds event handler for button Un-pause
            unpauseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timer.unpauseTimer();
                }
            });

            // adds event handler for button Reset
            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timer.resetTimer();
                }
            });

            // adds event handler for button Exit
            exitButton.addActionListener(new ActionListener() {
                @Override
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

            setSize(480, 320);
            setLocationRelativeTo(null);    // centers on screen
        }

    }

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

        public SelectionMenu() {
            selectionGUI();
        }

        public void selectionGUI() {
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

        private void handleSelectionActions() {
            // adds event handler for button Yes
            yesButton.addActionListener(new ActionListener() {
                @Override
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

        private void inquireNextPokemon() {
            pokemon = timer.getTempCollection().getTempCollectionList().get(0);
            keepOrDiscardLabel.setText("Would you like to keep" + pokemon.getPokemonName() + "?");
        }

        private void releasePokemon() {
            collectionOrReleasedLabel.setText(pokemon.getPokemonName() + " was released.");
            timer.getTempCollection().getTempCollectionList().remove(pokemon);
        }

        private void addPokemonToCollection() {
            pokemonCollection.addPokemonToCollection(pokemon);
            collectionOrReleasedLabel.setText(pokemon.getPokemonName() + " added to collection.");
            timer.getTempCollection().getTempCollectionList().remove(pokemon);
        }

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



        public CollectionPanel() throws IOException {
            collectionGUI();
        }

        public void collectionGUI() {

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
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

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