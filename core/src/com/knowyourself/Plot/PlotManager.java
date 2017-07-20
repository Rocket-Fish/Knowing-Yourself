package com.knowyourself.Plot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.knowyourself.Constants;
import com.knowyourself.DialogueTextPlane;
import com.kotcrab.vis.ui.util.dialog.ConfirmDialogListener;
import com.kotcrab.vis.ui.util.dialog.Dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class PlotManager implements DialogueTextPlane.DialogueOnClickCallback{
    private DialogueTextPlane dialogueTextPlane;
    private ArrayList<Choice> plotChoices;
    private ArrayList<Dialogue> listofDialogues;

    public PlotManager(DialogueTextPlane dialogueTextPlane, ArrayList<Choice> plotChoices, ArrayList<Dialogue> listofDialogues) {
        this.dialogueTextPlane = dialogueTextPlane;
        this.plotChoices = plotChoices;
        this.listofDialogues = listofDialogues;

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
    private int targettedNextLine = -1;
    private int currentLineID = 0;
    public void showNextDialogue() {
        if (listofDialogues != null) {

            if(targettedNextLine!= -1) {
                int pos = 0;
                for(Dialogue dd: listofDialogues) {
                    if(dd.getId() == targettedNextLine) {
                        nextTextNum = pos;
                    }
                    pos ++;
                }
                targettedNextLine = -1;
            }

            Dialogue d = listofDialogues.get(nextTextNum++);
            currentLineID = d.getId();

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

            if(d.getNextLine() != -1) {
                targettedNextLine = d.getNextLine();
            }

        }
    }

    @Override
    public void onClick() {
        boolean doNotDisplay = false;
        Iterator<Choice> pc = plotChoices.iterator();
        while(pc.hasNext()) {
            Choice c = pc.next();
            if(c.getNextLine()==currentLineID) {
                pc.remove();
                doNotDisplay = true;

//////////////////////////////////////////////////////////////////////////////////

                String[] choices = c.getChoiceDisplay().toArray(new String[0]);

                Integer[] whatever = Arrays.stream( c.getDialoguesToTransferTo() ).boxed().toArray( Integer[]::new );

                //confirmdialog may return result of any type, here we are just using ints
                Dialogs.showConfirmDialog(dialogueTextPlane.getStage(), "confirm dialog", "what do you want?",
                        choices, whatever,
                        new ConfirmDialogListener<Integer>() {
                            @Override
                            public void result (Integer result) {
                                Gdx.app.log("Result", String.valueOf(result));
                                targettedNextLine = result;
                                showNextDialogue();
                            }
                        });

//////////////////////////////////////////////////////////////////////////////////
            }
        }
        if(!doNotDisplay)
            showNextDialogue();
    }
}
