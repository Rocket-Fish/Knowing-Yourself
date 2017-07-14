package com.knowyourself;

import java.util.ArrayList;

public class Choices  extends Dialogue{
    ArrayList<Dialogue> listOfChoices;
    ArrayList<String> choiceDisplay;

    public Choices(ArrayList<Dialogue> listOfChoices, ArrayList<String> choiceDisplay) {
        super(-1, "", "", "", -100);
        this.listOfChoices = listOfChoices;
        this.choiceDisplay = choiceDisplay;
        if(listOfChoices.size() != choiceDisplay.size()) {
            System.out.println("Error: The choices has not been made properly");
        }
    }
}
