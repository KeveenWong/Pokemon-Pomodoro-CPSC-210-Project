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
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class PomodoroPokemonGUI extends JPanel implements ActionListener {
    private static final String JSON_STORE = "./data/pokemoncollection.json";
    private JButton timerButton;
    private JButton collectionButton;
    private JButton quitButton;
    private JButton pauseButton;
    private JButton unpauseButton;
    private JButton resetButton;
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

        timerButton = new JButton("Pomodoro Timer");
        timerButton.setVerticalTextPosition(AbstractButton.CENTER);
        timerButton.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        timerButton.setActionCommand("timer");

        collectionButton = new JButton("Collection");
        collectionButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        collectionButton.setHorizontalTextPosition(AbstractButton.CENTER);
        collectionButton.setActionCommand("collection");

        quitButton = new JButton("Quit");
        // Use the default text position of CENTER, TRAILING (RIGHT).
        quitButton.setActionCommand("quit");

        // Listen for actions on buttons
        timerButton.addActionListener(this);
        collectionButton.addActionListener(this);
        quitButton.addActionListener(this);

        timerButton.setToolTipText("Enter the Pomodoro Timer");
        collectionButton.setToolTipText("View your Pokemon collection");
        quitButton.setToolTipText("Exit the program");

        // Add Components to this container, using the default FlowLayout.
        add(timerButton);
        add(collectionButton);
        add(quitButton);
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
        } else if ("quit".equals(e.getActionCommand())) {
            System.out.println("See you next time!");
            System.exit(0);
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        PomodoroPokemonGUI newContentPane = new PomodoroPokemonGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
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
     * This class extends from OutputStream to redirect output to a JTextArrea
     *
     * @author www.codejava.net
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
}
