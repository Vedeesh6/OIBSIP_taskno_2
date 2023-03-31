package com.oibprj;

import java.io.*;
import java.util.*;

public class ScoreDoc {
    public String showScore(String filename){
        String score = "0";
        Scanner scan = new Scanner(Maincrame.class.getResourceAsStream(filename));
        score = scan.nextLine();scan.close();
        return score;
    }

    // Show the 2st Line Attempt in a filename and returns a String
    public String showGames(String filename){
        String attempt = "0";
        Scanner scan = new Scanner(Maincrame.class.getResourceAsStream(filename));
        scan.nextLine();
        attempt = scan.nextLine();scan.close();
        return attempt;
    }

    // Convert the showScore method to int
    public int intScore(String filename){
        return Integer.parseInt(showScore(filename));
    }

    // Convert the showsGames method to int
    public int intGames(String filename){
        return Integer.parseInt(showGames(filename));
    }

    // Overwrites the text file
    // Used in current_score.txt and num_game.txt
    public void write(String filename, int score){
        try{
            PrintWriter score_writer = new PrintWriter(new File(Maincrame.class.getResource(filename).getPath()));
            score_writer.write(String.valueOf(score));
            score_writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Overwrites the text file
    // Used in high_score.txt
    public void writeScoreAttempts(String filename, int score, int attemtps){
        try{
            PrintWriter score_writer = new PrintWriter(new File(Maincrame.class.getResource(filename).getPath()));
            score_writer.write(String.valueOf(score));
            score_writer.write("\n");
            score_writer.write(String.valueOf(attemtps));
            score_writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Compare the score if it is a high score
    public void compareScore(String high_score, String current_score, String current_played_games){
        // If the current score is higher than inside the high_score.txt file
        if (intScore(high_score) < intScore(current_score)){
            // Overwrite with new Score
            writeScoreAttempts(high_score, intScore(current_score), intScore(current_played_games));
        }
        // Else if the current score and the score inside the high_score.txt is equals
        else if(intScore(high_score) == intScore(current_score)){
            // If the number of games played by the user is lower than inside the high_score.txt
            if (intGames(high_score) > intScore(current_played_games)){
                // Overwrite with new Score
                writeScoreAttempts(high_score, intScore(current_score), intScore(current_played_games));
            }
        }
    }
}
