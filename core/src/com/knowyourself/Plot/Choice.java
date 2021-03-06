package com.knowyourself.Plot;

import com.bearfishapps.libs.Tools.Prefs;

import java.util.ArrayList;

public class Choice{
    private int afterLine;
    private int[] DialoguesToTransferTo;
    private ArrayList<String> choiceDisplay;
    private int ID = -1;

    public Choice(int afterLine, int[] DialoguesToTransferTo, ArrayList<String> choiceDisplay) {
        this.afterLine = afterLine;
        this.DialoguesToTransferTo = DialoguesToTransferTo;
        this.choiceDisplay = choiceDisplay;
        if(DialoguesToTransferTo.length != choiceDisplay.size()) {
            System.out.println("Error: The choices has not been made properly");
        }
    }

    public int getNextLine() {
        return afterLine;
    }

    public int[] getDialoguesToTransferTo() {
        return DialoguesToTransferTo;
    }

    public ArrayList<String> getChoiceDisplay() {
        return choiceDisplay;
    }

    public static Choice choiceParser(int afterLine, String line) {
        int location = line.indexOf("[sel:");
        int selNumber = Integer.valueOf(line.charAt(location + 5)+"");
        String tag = null;

        int[] transfer = new int[selNumber];
        ArrayList<String> choiceDisplay = new ArrayList<>();

        String[] bracketSections = line.split("\\[");
        int count = 0;
        for(String str: bracketSections) {
            if(str.equals("")) {
                continue;
            }
            if(str.contains("sel:")){
                String[] splitted = str.split(":");
                if(splitted.length==3) {
                    tag = splitted[2].split("\\]")[0];
                }
                continue;
            }
            String[] secondarySelection = str.split("]");
            if(!secondarySelection[0].contains(":")) {
                transfer[count++] = Integer.valueOf(secondarySelection[0]);
                choiceDisplay.add(secondarySelection[1]);
            } else if(secondarySelection[0].contains(":")) {
                String[] ss = secondarySelection[0].split("\\:");
                String[] sss = ss[1].split("==");

                if(Prefs.getStringValue(sss[0]).equals(sss[1])) {
                    transfer[count++] = Integer.valueOf(ss[0]);
                    choiceDisplay.add(secondarySelection[1]);
                } else {
                    int[] t = new int[transfer.length-1];
                    for(int i = 0; i < count-1; i ++) {
                        t[i]=transfer[i];
                    }
                    transfer = t;
                }
            }
        }

        Choice c = new Choice(afterLine, transfer, choiceDisplay);
        if(tag != null)
            c.setID(Integer.valueOf(tag));
         else
            c.setID(-1);

        return c;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
