package com.knowyourself.Plot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.knowyourself.Constants;
import com.knowyourself.DialogueTextPlane;

import java.util.ArrayList;
import java.util.HashSet;

public class PlotManager implements DialogueTextPlane.DialogueOnClickCallback{
    private DialogueTextPlane dialogueTextPlane;
    private ArrayList<Choice> plotChoices;
    private ArrayList<Dialogue> listofDialogues;
    private HashSet<Dialogue> setofDialogues;

    public PlotManager(DialogueTextPlane dialogueTextPlane, ArrayList<Choice> plotChoices, ArrayList<Dialogue> listofDialogues, HashSet<Dialogue> setofDialogues) {
        this.dialogueTextPlane = dialogueTextPlane;
        this.plotChoices = plotChoices;
        this.listofDialogues = listofDialogues;
        this.setofDialogues = setofDialogues;

        showNextDialogue();
    }

    private String whichCharacterIsThis(String inputChar) {
        switch (inputChar) {
            case "Daelin":
                return Constants.Daelin;
            case "Lawrence":
                return Constants.Lawrence;
            case "Mikhail":
                return Constants.Mikhail;
            case "Naomi":
                return Constants.Naomi;
            case "Player":
                return Constants.Player;
            case "Sariel":
                return Constants.Sariel;
            case "Sol":
                return Constants.Sol;
            case "Torrent":
                return Constants.Torrent;
            case "Tristan":
                return Constants.Tristan;
            default:
                return "";
        }
    }
    private int nextTextNum = 0;
    public void showNextDialogue() {
        if (listofDialogues != null) {
            Dialogue d = listofDialogues.get(nextTextNum++);

            String speakingCharacter ="", characterBeingSpokenTo="";
            speakingCharacter = whichCharacterIsThis(d.getSpeaker());
            characterBeingSpokenTo = whichCharacterIsThis(d.getSpokenTo());

            try {
                dialogueTextPlane.setSpeakerImage(Constants.charDirectory + speakingCharacter);
            } catch (GdxRuntimeException e) {
                Gdx.app.log("Error", d.getSpeaker() + " | Image Doesn't Exist");
            }
            try {
                dialogueTextPlane.setSpokenToImage(Constants.charDirectory+characterBeingSpokenTo);
            } catch (GdxRuntimeException e) {
                Gdx.app.log("Error", d.getSpokenTo() + " | Image Doesn't Exist");
            }
            dialogueTextPlane.setTitle(d.getSpeaker());
            dialogueTextPlane.setText(d.getContent());
        }
    }

    @Override
    public void onClick() {
        showNextDialogue();
    }
}
