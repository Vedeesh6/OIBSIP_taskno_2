package com.oibprj;

import java.util.Random;

public class RandomNum {

    // Generate a Random Number
    public int generateNumber(int max, int min){
        return new Random().nextInt(max - min + 1) + min;
    }
}
