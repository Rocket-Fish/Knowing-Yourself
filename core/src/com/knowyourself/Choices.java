package com.knowyourself;

import java.util.ArrayList;

public class Choices {
    ArrayList<Dialogue> listOfChoices;
    ArrayList<String> choiceDisplay;

    public Choices(ArrayList<Dialogue> listOfChoices, ArrayList<String> choiceDisplay) {
        this.listOfChoices = listOfChoices;
        this.choiceDisplay = choiceDisplay;
        if(listOfChoices.size() != choiceDisplay.size()) {
            System.out.println("Error: The choices has not been made properly");
        }
    }
}
