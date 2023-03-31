package com.oibprj;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class PlayGame extends JPanel {

    // Declaring the Game class for changing the scene
    final private GameOut game;
    int max,min;

    // Create an instance of Random class to generate a random number
    RandomNum randomNumber = new RandomNum();

    // Create an instance of ScoringSystem class for making points and attempts
    ScoringSys scoringSystem = new ScoringSys();

    // Create an instance of ScoreFiles class to handle the .txt Files
    ScoreDoc scoreFiles = new ScoreDoc();

    public PlayGame(GameOut game){
        this.game = game;

         // Prompt the user to select a difficulty mode
         Object[] options = { "Easy", "Medium", "Hard" };
         int mode = JOptionPane.showOptionDialog(null, "Select a difficulty mode", "Guess the Number",
                 JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
 
         // Initialize game parameters based on the selected difficulty mode
         switch (mode) {
         case 0: // Easy mode
             min = 1;
             max = 10;
             break;
         case 1: // Medium mode
             min = 1;
             max = 50;
             break;
         case 2: // Hard mode
             min = 1;
             max = 100;
             break;
         default: // Default to easy mode if the user closes the dialog
             min = 1;
             max = 10;
             break;
        }

        // Called the method for adding the components in JPanel
        createGUI();
    }

    // All the components are found here
    private void createGUI() {

        // Declare the variables needed
        JLabel playScore, mysteryNumber, statusImage, enterButton, continueButton, backButton;
        JTextField inputText;
        JPanel gridPanel;
        int random = randomNumber.generateNumber(max,min); // generates a random number

        // Setting up and Display the Score in the Current Game
        playScore = new JLabel("Score: " + scoreFiles.intScore("/com/oibprj/result/currentscore.txt") + "   Games: " + scoreFiles.intScore("/com/oibprj/result/numgame.txt"));
        playScore.setFont(new Font("Algerian", Font.BOLD, 22));
        playScore.setForeground(Color.WHITE);
        playScore.setBorder(new EmptyBorder(20,0,0,0));
        playScore.setAlignmentX(CENTER_ALIGNMENT);
        add(playScore);

        // Setting up and Display the Mystery Number
        mysteryNumber = new JLabel();
        mysteryNumber.setIcon(new ImageIcon(Maincrame.class.getResource("/com/oibprj/resources/mystery_square_2.png")));
        add(mysteryNumber);

        // Setting up and Display the Status of the Inputted Text
        statusImage = new JLabel(new ImageIcon(Maincrame.class.getResource("/com/oibprj/resources/input_number.png")));
        statusImage.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        add(statusImage);

        // Setting up another JPanel to make the components place beside each other
        gridPanel = new JPanel();
        gridPanel.setMaximumSize(new Dimension(260, 50));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        gridPanel.setLayout(new GridLayout(0, 2));
        gridPanel.setOpaque(false);

        // Setting up and Display the Input JTextfield
        inputText = new JTextField((max==10)?"1-10":(max==50)?"1-50":"1-100");
        inputText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        inputText.setBackground(new Color(57,56,56));
        inputText.setForeground(new Color(245,245,246));
        inputText.setHorizontalAlignment(JTextField.CENTER);
        inputText.setFont(new Font("Arial", Font.BOLD, 28));
        gridPanel.add(inputText);

        // Setting up and Display the Enter Button
        enterButton = new JLabel(new ImageIcon(Maincrame.class.getResource("/com/oibprj/resources/enter_button.png")));
        enterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gridPanel.add(enterButton);

        // Add the gridPanel to this JPanel
        add(gridPanel);

        // Setting up the Continue Button without displaying
        continueButton = new JLabel(new ImageIcon(Maincrame.class.getResource("/com/oibprj/resources/continue_button.png")));
        continueButton.setAlignmentX(CENTER_ALIGNMENT);
        continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        continueButton.setVisible(false);
        continueGame(continueButton);
        add(continueButton);

        // Setting up and Display the Back to Menu Button
        backButton = new JLabel(new ImageIcon(Maincrame.class.getResource("/com/oibprj/resources/back_to_menu_button.png")));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        linkMenu(backButton);
        add(backButton);

        // When user click the enter button
        enterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeStatus(inputText, mysteryNumber, random, statusImage, gridPanel, continueButton, backButton);
            }
        });

        // When user hits the button key while inputting in text field
        inputText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeStatus(inputText, mysteryNumber, random, statusImage, gridPanel, continueButton, backButton);
            }
        });
    }

    // Method for Linking to Play Section
    public void linkMenu(JLabel jLabel){
        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(null, "Want to Stop the Game?", "Are you Sure", JOptionPane.YES_NO_OPTION);
                if(dialogResult == 0) {
                    // Insert the current score and number of games played to High Score when quiting the game
                    scoreFiles.compareScore("/com/oibprj/result/highscore.txt", "/com/oibprj/result/currentscore.txt", "/com/oibprj/result/numgame.txt");
                    game.showView(new GoMenu(game));
                }
            }
        });
    }

    // Method to continue a game
    public void continueGame(JLabel jLabel){
        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                game.showView(new PlayGame(game));
            }
        });
    }

    // For changing the background of JPanel
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Maincrame.class.getResource("/com/oibprj/resources/background_1.png"))).getImage(), 0, 0, null);
    }

    // Method for Changing the Status Image
    private void changeStatus(JTextField input, JLabel mysterynum, int randnum, JLabel status, JPanel gridPanel, JLabel contButton, JLabel backButton){
        // If the random number and the user input is correct
        if (String.valueOf(randnum).equals(input.getText())) {
            // Change the image of status
            status.setIcon(new ImageIcon(Maincrame.class.getResource("/com/oibprj/resources/correct.png")));
            // Change the value of ? to the number
            mysterynum.setText(input.getText());
            // Hide the Panel with the enter button and text field
            gridPanel.setVisible(false);
            // Set and Show the continue button (for playing again)
            contButton.setVisible(true);
            contButton.setBorder(new EmptyBorder(-15,0,0,0));
            // Move the back button to top
            backButton.setBorder(new EmptyBorder(-9,0,0,0));
            // Get the points(based on number of attempts) and update the score
            scoringSystem.scoreAttempt();
            // Overwrite the txt score plus the current_score
            scoreFiles.write("/com/oibprj/result/currentscore.txt", scoreFiles.intScore("/com/oibprj/result/currentscore.txt") + scoringSystem.getScore());
            // Set how many games played continuously
            scoreFiles.write("/com/oibprj/result/numgame.txt", scoreFiles.intScore("/com/oibprj/result/numgame.txt") + 1);
            // If the input is correct, play the sound effect for correct
            sound_effect("/com/oibprj/resources/correct.wav");
        } else {
            // If the input is wrong, play the sound effect for wrong
            sound_effect("/com/oibprj/resources/wrong.wav");
            // Catch any possible error if the user didn't input a number
            try {
                // Convert the user input number (string) to int
                int textToInt = Integer.parseInt(input.getText());
                // Comparing the user input to the random number
                if(textToInt > max || textToInt < min) {
                    // If the user input is out of range, executed this block of code
                    status.setIcon(new ImageIcon(Maincrame.class.getResource("/com/oibprj/resources/out_of_range.png")));
                } else if (textToInt > randnum ){
                    // If the user input higher than random number, executed this block of code
                    status.setIcon(new ImageIcon(Maincrame.class.getResource("/com/oibprj/resources/too_high.png")));
                } else if(textToInt < randnum){
                    // If the user input lower than random number, executed this block of code
                    status.setIcon(new ImageIcon(Maincrame.class.getResource("/com/oibprj/resources/too_low.png")));
                }
            } catch (NumberFormatException ex) {
                // Is user input anything aside from numbers, executed this block of code
                status.setIcon(new ImageIcon(Maincrame.class.getResource("/com/oibprj/resources/invalid_input.png")));
            }
        }
        // Remove the Text in text field once the user call this method
        input.setText("");
        // Increase the number of attempts
        scoringSystem.incrementAttempt();

    }

    //// Method For Sound Effects Music
    public void sound_effect(String soundName) {
        try {
            // Setting up the Audio for Background Music
            AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(Maincrame.class.getResource((soundName)));
            Clip clip2 = AudioSystem.getClip();
            clip2.open(audioInputStream2);
            // Adjust Sound
            FloatControl gainControl =  (FloatControl) clip2.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            // Start the Audio
            clip2.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
