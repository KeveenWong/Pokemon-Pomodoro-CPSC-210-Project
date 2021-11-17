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
import javax.swing.text.DefaultCaret;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public class PomodoroPokemonGUI extends JPanel implements ActionListener {
    private static final String JSON_STORE = "./data/pokemoncollection.json";
    private JButton timerButton;
    private JButton collectionButton;
    private JButton exitButton;
    private JButton pauseButton;
    private PomodoroTimer timer;
    private Pokemon pokemon;
    private PokemonCollection pokemonCollection;
    private Writing jsonWriter;
    private Reading jsonReader;

    public PomodoroPokemonGUI() {
        timerButton = new JButton("Pomodoro Timer");
        timerButton.setVerticalTextPosition(AbstractButton.CENTER);
        timerButton.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        timerButton.setActionCommand("timer");

        collectionButton = new JButton("Collection");
        collectionButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        collectionButton.setHorizontalTextPosition(AbstractButton.CENTER);
        collectionButton.setActionCommand("collection");

        exitButton = new JButton("Quit");
        // Use the default text position of CENTER, TRAILING (RIGHT).
        exitButton.setActionCommand("quit");

        // Listen for actions on buttons
        timerButton.addActionListener(this);
        collectionButton.addActionListener(this);
        exitButton.addActionListener(this);

        timerButton.setToolTipText("Enter the Pomodoro Timer");
        collectionButton.setToolTipText("View your Pokemon collection");
        exitButton.setToolTipText("Exit the program");

        // Add Components to this container, using the default FlowLayout.
        add(timerButton);
        add(collectionButton);
        add(exitButton);
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
            createTimerFrame();
        } else if ("quit".equals(e.getActionCommand())) {
            System.out.println("See you next time!");
            System.exit(0);
        }
    }

    public void timerActionPerformed(ActionEvent e) {
        if ("pause".equals(e.getActionCommand())) {
            timer.pauseTimer();
        } else if ("quit".equals(e.getActionCommand())) {
            System.out.println("See you next time!");
            System.exit(0);
        }
    }

    public void createTimerFrame() {
        // Initialize and begin timer
        timer = new PomodoroTimer();
        timer.startTimer(timer.getPomodoroLength());

        // Create and set up the window with buttons.
        JFrame frame = new JFrame("PomodoroPokemonGUI");

        // Timer
        JLabel timerLabel = new JLabel("Time remaining: " + timer.getRemainingTime());
        // Pause
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(this);
        pauseButton.setActionCommand("pause");


        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(300, 300, 100, 100));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(pauseButton);
        panel.add(timerLabel);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


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

}
