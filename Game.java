
package com.mycompany.finals;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    private Creature creature;

    // UI Components
    private JLabel statusLabel;
    private JLabel petImageLabel;
    private JTextArea logTextArea;
    private JButton playButton, eatButton, sleepButton, quitButton;

    public Game() {
        setTitle("Javagotchi - Tamagotchi Game");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Choose Species
        String[] speciesOptions = {"Cat", "Dog", "Fox"};

        int speciesChoice = JOptionPane.showOptionDialog(
            this,
            "What kind of animal would you like?",
            "Choose Species",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            speciesOptions,
            speciesOptions[0]
        );

        String species = (speciesChoice >= 0)
                ? speciesOptions[speciesChoice] : "Cat";

        String name = JOptionPane.showInputDialog(
            this,
            "What's the name of your " + species.toLowerCase() + "?",
            "Name Your Creature",
            JOptionPane.QUESTION_MESSAGE
        );

        if (name == null || name.trim().isEmpty()) {
            name = "Javagotchi";
        }

        // Initialize Creature
        creature = new Creature(name, species);

        // TOP PANEL: Status
        statusLabel = new JLabel(creature.Status(), JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        add(statusLabel, BorderLayout.NORTH);

        // CENTER PANEL: Pet Image and Game Log
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));

        // Pet Image Display
        petImageLabel = new JLabel();
        petImageLabel.setHorizontalAlignment(JLabel.CENTER);
        petImageLabel.setVerticalAlignment(JLabel.CENTER);
        petImageLabel.setPreferredSize(new Dimension(300, 300));

        centerPanel.add(petImageLabel, BorderLayout.CENTER);

        // Game Log
        logTextArea = new JTextArea(
            "Welcome to Javagotchi! Meet " + creature.getName()
            + " the " + species + "!\n"
        );

        logTextArea.setEditable(false);
        logTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(logTextArea);
        scrollPane.setBorder(
            BorderFactory.createTitledBorder("Game Log")
        );
        scrollPane.setPreferredSize(new Dimension(450, 110));

        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        // BOTTOM PANEL: Action Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 8, 8));

        buttonPanel.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        playButton = new JButton("Play");
        eatButton = new JButton("Eat");
        sleepButton = new JButton("Sleep");
        quitButton = new JButton("Quit");

        buttonPanel.add(playButton);
        buttonPanel.add(eatButton);
        buttonPanel.add(sleepButton);
        buttonPanel.add(quitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // BUTTON LISTENERS
        playButton.addActionListener(e -> handleAction(1));
        eatButton.addActionListener(e -> handleAction(2));
        sleepButton.addActionListener(e -> handleAction(3));
        quitButton.addActionListener(e -> handleAction(4));

        // Show Initial Pet Image
        updatePetImage(0);
    }

    // Update Pet Image Based on Expression
    private void updatePetImage(int lastAction) {

        String expression;

        // Check the pet's current condition
        if (creature.getEnergy() <= 4) {
            expression = "sleepy";

        } else if (creature.getHunger() <= 4) {
            expression = "hungry";

        } else if (creature.getHappiness() <= 4) {
            expression = "sad";

        } else if (lastAction == 3) {
            expression = "sleepy";

        } else {
            expression = "happy";
        }

        // Get Selected Animal
        String animal = creature.getSpecies();

        // Find Correct Image
        String imagePath = "/images/" + animal + "_" + expression + ".jpg";

        java.net.URL imageURL = getClass().getResource(imagePath);

        if (imageURL != null) {

            ImageIcon originalIcon = new ImageIcon(imageURL);

            Image scaledImage = originalIcon.getImage().getScaledInstance(
                250, 250, Image.SCALE_SMOOTH
            );

            petImageLabel.setIcon(new ImageIcon(scaledImage));
            petImageLabel.setText("");

        } else {

            petImageLabel.setIcon(null);
            petImageLabel.setText(
                "Image not found: " + imagePath
            );
        }
    }

    // Handle Button Actions
    private void handleAction(int choice) {

        if (creature.isDead()) {
            return;
        }

        String message = "";

        switch (choice) {

            case 1:
                creature.play();
                message = creature.getName() + " had fun playing!";
                break;

            case 2:
                creature.eat();
                message = creature.getName()
                        + " enjoyed a delicious meal!";
                break;

            case 3:
                creature.sleep();
                message = creature.getName()
                        + " took a nice cozy nap.";
                break;

            case 4:
                JOptionPane.showMessageDialog(
                    this,
                    "Awh. Time to put " + creature.getName()
                    + " to sleep :("
                );
                System.exit(0);
                break;
        }

        // Update Game Log and Status
        logTextArea.append(message + "\n");
        statusLabel.setText(creature.Status());

        // Update Pet Expression
        updatePetImage(choice);

        // Check If Creature Died
        if (creature.isDead()) {

            String deathMsg = creature.getDeathReason();

            logTextArea.append("\n[R.I.P.] " + deathMsg + "\n");

            JOptionPane.showMessageDialog(
                this,
                deathMsg,
                "Game Over",
                JOptionPane.ERROR_MESSAGE
            );

            playButton.setEnabled(false);
            eatButton.setEnabled(false);
            sleepButton.setEnabled(false);

            quitButton.setText("Your pet is dead");
        }

        logTextArea.setCaretPosition(
            logTextArea.getDocument().getLength()
        );
    }
}
