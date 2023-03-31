package com.oibprj;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GoMenu extends JPanel {
    // Declaring the Game class for changing the scene
    final private GameOut game;

    // Create an instance of ScoreFiles class to handle the .txt Files
    ScoreDoc scoreFiles = new ScoreDoc();

    // Constructor of the Class
    public GoMenu(GameOut game){
        this.game = game;

        // Called the method for adding the components in JPanel
        createGUI();
    }

    // All the components are found here
    public void createGUI(){

        // Declare the variables needed
        JLabel scoreLabel, scoreText, playButton, playText;

        // Setting up and Display the High Score Label Image
        scoreLabel = new JLabel(new ImageIcon(Maincrame.class.getResource("/com/oibprj/resources/high_score_label.png")));
        scoreLabel.setBorder(new EmptyBorder(600, 0, 0, 0));
        
        add(scoreLabel);

        // Setting up and Display the High Score
        scoreText = new JLabel(scoreFiles.showScore("/com/oibprj/result/highscore.txt") + " points for " + scoreFiles.showGames("/com/oibprj/result/highscore.txt") + " rounds");
        scoreText.setFont(new Font("Algerian", Font.BOLD, 22));
        scoreText.setForeground(Color.WHITE);
        scoreText.setBorder(new EmptyBorder(700,0,0,400));
        add(scoreText);

        // Setting up and Display the Play Button
        playButton = new JLabel(new ImageIcon(Maincrame.class.getResource("/com/oibprj/resources/play_button.png")));
        playButton.setBorder(new EmptyBorder(600, 50, 0, 0));
        playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkPlay(playButton);
        add(playButton);

        // Setting up and Display the "Click to Play" Text Image
        playText = new JLabel(new ImageIcon(Maincrame.class.getResource("/com/oibprj/resources/click_play.png")));
        playText.setBorder(new EmptyBorder(600,0,0,0));
        playText.setAlignmentX(CENTER_ALIGNMENT);
        add(playText);
    }

    // Method for Linking to Play Section
    public void linkPlay(JLabel jLabel){
        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Set the data inside to zero
                scoreFiles.write("/com/oibprj/result/currentscore.txt", 0);
                scoreFiles.write("/com/oibprj/result/numgame.txt", 0);
                game.showView(new PlayGame(game));
            }
        });
    }

    // For changing the background of JPanel
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Maincrame.class.getResource("/com/oibprj/resources/background.png"))).getImage(), 0, 0, null);
    }
}
