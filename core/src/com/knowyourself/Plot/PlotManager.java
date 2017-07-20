package com.knowyourself.Plot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.knowyourself.Constants;
import com.knowyourself.DialogueTextPlane;
import com.knowyourself.utils.ImageWindow;
import com.kotcrab.vis.ui.util.dialog.ConfirmDialogListener;
import com.kotcrab.vis.ui.util.dialog.Dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class PlotManager implements DialogueTextPlane.DialogueOnClickCallback{
    private DialogueTextPlane dialogueTextPlane;
    private ArrayList<Choice> plotChoices;
    private ArrayList<Dialogue> listofDialogues;
    private ImageWindow[] imageWindows;
    private AssetManager assets;

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

            dialogueTextPlane.setTitle(d.getSpeaker());
            dialogueTextPlane.setText(d.getContent());

            highlightPeople(d.getSpeaker(), d.getSpokenTo());

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

    public void attachImageWindows(ImageWindow[] windows, AssetManager assets) {
        imageWindows = windows;
        this.assets = assets;
    }

    private void highlightPeople(String speaking, String spokenTo) {
        final int []playerSide = {0, 1, 2};
        final int []npcSide = {3, 4, 5};

        if(speaking.equals("Fornea") || spokenTo.equals("Fornea")) {
            return;
        }

        String speakingCharacter ="", characterBeingSpokenTo="";
        speakingCharacter = whichCharacterIsThis(speaking);
        characterBeingSpokenTo = whichCharacterIsThis(spokenTo);

        for(ImageWindow iw: imageWindows) {
            iw.setFaded();
        }

        try {
            if(speaking.equals("Player")) {
                for(int ps:playerSide) {
                    if(setImageWindowImage(imageWindows[ps], Constants.charDirectory+speakingCharacter))
                        break;
                }
            } else {
                for(int npcs: npcSide) {
                    if(setImageWindowImage(imageWindows[npcs], Constants.charDirectory+speakingCharacter))
                        break;
                }
            }
            if(spokenTo.equals("Player")) {
                for(int ps:playerSide) {
                    if(setImageWindowImage(imageWindows[ps], Constants.charDirectory+characterBeingSpokenTo))
                        break;
                }
            } else {
                for(int npcs: npcSide) {
                    if(setImageWindowImage(imageWindows[npcs], Constants.charDirectory+characterBeingSpokenTo))
                        break;
                }
            }
        } catch (GdxRuntimeException e) {
            Gdx.app.log("Error", " Speaker or Listener | Image Doesn't Exist");
        }
    }

    public boolean setImageWindowImage(ImageWindow iw, String dir) {
        if(iw.isCurrentlyBlank() || iw.getImagePath().equals(dir)) {
            iw.changeImage(dir, new TextureRegionDrawable(new TextureRegion(assets.get(dir, Texture.class))));
            iw.setNonFaded();
            return true;
        }
        return false;
    }

}
