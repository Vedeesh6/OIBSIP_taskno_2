package com.oibprj;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameOut extends JFrame {

    // create a JPanel inside the JFrame
    private JPanel viewPanel;

    // Constructor of the Class
    public GameOut() {

        // Initialize the viewPanel the moment Game Class is called
        viewPanel = new JPanel(new BorderLayout());
        // Setting up the Game
        this.setTitle("Guess the Number"); // Title
        this.setPreferredSize(new Dimension(1246, 808)); // Dimension
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the Application when clicking x
        this.add(viewPanel, BorderLayout.CENTER); // Add the JPanel in this frame
        showView(new GoMenu(this));
        this.setVisible(true); // To view the window
        this.pack(); // To size the components(button, img) optimally
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(Maincrame.class.getResource("/com/oibprj/resources/icon.png"))); // Change the icon
        this.setResizable(false); // To avoid resizing the window
        this.setLocationRelativeTo(null); // To set the window in the center
        
        // Call the method for Background Music
        background_music();
    }

    // Method To View the Panel being assigned
    public void showView(JPanel jPanel){
        viewPanel.removeAll();
        viewPanel.add(jPanel, BorderLayout.CENTER);
        viewPanel.revalidate();
        viewPanel.repaint();
    }

    // Method For Background Music
    public void background_music() {
        try {
            // Setting up the Audio for Background Music
            String soundName = "/com/oibprj/resources/backgroundmusic.wav";
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Maincrame.class.getResource(soundName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            // Infinite Loop
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            // Adjust Sound
            FloatControl gainControl =  (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            // Start the Audio
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
