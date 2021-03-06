package com.knowyourself.Plot;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.knowyourself.Constants;
import com.knowyourself.UI.DialogueTextPlane;
import com.knowyourself.UI.GameScreen;
import com.knowyourself.UI.ImageWindow;
import com.kotcrab.vis.ui.util.dialog.ConfirmDialogListener;
import com.kotcrab.vis.ui.util.dialog.Dialogs;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class PlotManager implements DialogueTextPlane.DialogueOnClickCallback{
    private DialogueTextPlane dialogueTextPlane;
    private ArrayList<Choice> plotChoices;
    private ArrayList<Dialogue> listofDialogues;
    private ImageWindow[] imageWindows;
    private AssetManager assets;
    private Game game;

    public PlotManager(Game game, DialogueTextPlane dialogueTextPlane, ArrayList<Choice> plotChoices, ArrayList<Dialogue> listofDialogues) {
        this.dialogueTextPlane = dialogueTextPlane;
        this.plotChoices = plotChoices;
        this.listofDialogues = listofDialogues;
        this.game = game;

        // Null Pointer Exception, causes crahses
//        showNextDialogue();
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

            if(targettedNextLine>63) {
                byte x = 1;
                byte baseByte = (byte)~(x & 0);
                if((targettedNextLine&(baseByte<<8))==(('s'&0xff)<<8)) {
                    Gdx.app.log("good base", "already In");
                    int id = (byte) (baseByte&targettedNextLine);
                    for(Choice c:plotChoices) {
                        if(c.getID() == id) {
                            displayChoice(c);
                            return;
                        }
                    }
                } else
                    /////////////for testing purposes
                try {

                    ByteBuffer dbuf = ByteBuffer.allocate(2);
                    dbuf.putShort((short)targettedNextLine);
                    byte[] bytes = dbuf.array();
                    String str = new String(bytes, "US-ASCII");
                    Gdx.app.log("Transition to", str);
                    game.setScreen(new GameScreen(assets, game, str));
                    return;
                } catch (UnsupportedEncodingException e) {
                    Gdx.app.log("Error", "Unsupported encoding US-ASCII");}
            } else if(targettedNextLine!= -1) {
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

    private void displayChoice(Choice c) {
        String[] choices = c.getChoiceDisplay().toArray(new String[0]);

        Integer[] whatever = Arrays.stream( c.getDialoguesToTransferTo() ).boxed().toArray( Integer[]::new );

        //confirmdialog may return result of any type, here we are just using ints
        Dialogs.showConfirmDialog(dialogueTextPlane.getStage(), "", "what do you do?",
                choices, whatever,
                new ConfirmDialogListener<Integer>() {
                    @Override
                    public void result (Integer result) {
                        Gdx.app.log("Result", String.valueOf(result));
                        targettedNextLine = result;
                        showNextDialogue();
                    }
                });

    }

    @Override
    public void onClick() {
        boolean doNotDisplay = false;

        Iterator<Choice> pc = plotChoices.iterator();
        while(pc.hasNext()) {
            Choice c = pc.next();
            if(c.getNextLine()==currentLineID) {
                if(c.getID() == -1)
                    pc.remove();
                doNotDisplay = true;

//////////////////////////////////////////////////////////////////////////////////
                displayChoice(c);

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

    private boolean forneaExists = false;
    private void highlightPeople(String speaking, String spokenTo) {
        final int []playerSide = {0, 1, 2};
        final int []npcSide = {3, 4, 5};

        forneaExists = false;
        if(speaking.equals("Fornea") || spokenTo.equals("Fornea")) {
            forneaExists = true;
        }

        String speakingCharacter ="", characterBeingSpokenTo="";
        speakingCharacter = whichCharacterIsThis(speaking);
        characterBeingSpokenTo = whichCharacterIsThis(spokenTo);

        try {
            boolean continuing=false;
            if(speaking.equals("Fornea")) {
            }else if(speaking.equals("Player")) {
                for(int ps:playerSide) {
                    if(continuing) {
                        imageWindows[ps].setFaded();
                        continue;
                    }
                    if(setImageWindowImage(imageWindows[ps], Constants.charDirectory+speakingCharacter)) {
                        continuing = true;
                    }
                }
            } else {
                for(int npcs: npcSide) {
                    if(continuing) {
                        imageWindows[npcs].setFaded();
                        continue;
                    }
                    if(setImageWindowImage(imageWindows[npcs], Constants.charDirectory+speakingCharacter)) {
                        continuing = true;
                    }
                }
            }
            continuing = false;
            if(spokenTo.equals("Fornea")) {

            } else if(spokenTo.equals("Player")) {
                for(int ps:playerSide) {
                    if(continuing) {
                        imageWindows[ps].setFaded();
                        continue;
                    }
                    if(setImageWindowImage(imageWindows[ps], Constants.charDirectory+characterBeingSpokenTo))
                        continuing = true;
                }
            } else {
                for(int npcs: npcSide) {
                    if(continuing) {
                        imageWindows[npcs].setFaded();
                        continue;
                    }
                    if(setImageWindowImage(imageWindows[npcs], Constants.charDirectory+characterBeingSpokenTo))
                        continuing = true;
                }
            }
        } catch (GdxRuntimeException e) {
            Gdx.app.log("Error", " Speaker or Listener | Image Doesn't Exist");
        }
    }

    public boolean setImageWindowImage(ImageWindow iw, String dir) {
        if(iw.isCurrentlyBlank()) {
            Gdx.app.log("Image", "Replacing a Blank Screen");
            iw.changeImage(dir, new TextureRegionDrawable(new TextureRegion(assets.get(dir, Texture.class))));
            if(!forneaExists)
                iw.setNonFaded();
            return true;
        }
        if(!iw.isCurrentlyFaded() && iw.getImagePath().equals(dir)) {
            Gdx.app.log("Image", "Already On Screen");
            if(forneaExists)
                iw.setFaded();
            return true;
        } else if (iw.isCurrentlyFaded() && iw.getImagePath().equals(dir)) {
            Gdx.app.log("Image", "fade in");
            iw.setNonFaded();
            return true;
        } else {
            if(!iw.isCurrentlyFaded()) {
                Gdx.app.log("Image", "fade out");
                iw.setFaded();
                return false;
            }
            Gdx.app.log("Image", "do nothing");
            return false;
        }
    }

}
