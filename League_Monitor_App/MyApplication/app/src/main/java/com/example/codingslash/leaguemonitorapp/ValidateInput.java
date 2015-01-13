package com.example.codingslash.leaguemonitorapp;

import android.util.Log;

/**
 * Created by Operator on 1/12/2015.
 * Validates summoner name entry to make sure it isn't left blank or contains any invalid characters.
 */
public class ValidateInput {

    String input;

//    // set of characters that are acceptable
//    String charsetNA[] = {};
//    String charsetEUW[] = {};
//    String charsetEUNE[] = {};

    public ValidateInput(String input) {
        this.input = input;
    }

    public static boolean validateInput(String input) {
        // check for gucciness

        if (input.trim().equals(""))
        {
            // name invalid
            return false;
        } else {
            // name valid
            return true;
        }
    }
}
