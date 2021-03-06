package com.knowyourself.Plot;

import com.badlogic.gdx.Gdx;
import com.bearfishapps.libs.Tools.Prefs;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class Dialogue{
    private int id, next;
    private String speaker, spokenTo, content;
    public Dialogue(int id, String speaker, String spokenTo, String content, int nextLine) {
        this.id = id;
        this.speaker = speaker;
        this.spokenTo = spokenTo;
        this.content = content;
        next = nextLine;
    }

    @Override
    public boolean equals(Object object) {
        if(object != null && object instanceof Dialogue)
        {
            if(this.id == ((Dialogue)object).getId())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    public int getId() {
        return id;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getSpokenTo() {
        return spokenTo;
    }

    public String getContent() {
        return content;
    }

    public int getNextLine() {
        return next;
    }

    public static Dialogue dialogueParser(String line) {
        String[] bracketSections = line.split("]");
        String[] contentSections = bracketSections[bracketSections.length - 1].split("`");
        int nextLine = -1;

        if(bracketSections.length > 2) {
            int count = 0;
            for(String s: bracketSections) {
                if(count == 0 || count == bracketSections.length-1) {
                    count++;
                    continue;
                }

                if(s.contains("[t:")) {
                    String[] ss = s.split(":");
                    if(ss.length == 2) {
                        nextLine = Integer.valueOf(ss[ss.length - 1]);
                    } else if(ss.length==3 && ss[1].equals("sel")) {
                        byte sInByte = 's';
                        byte value = Byte.valueOf(ss[2]);
                        nextLine = (sInByte&0xff)<<8|value&0xff;
                        Gdx.app.log("sInBytes", String.valueOf((sInByte&0xff)<<8));
                    }
                } else if(s.contains("[GOTO:")) {
                    String[] ss = s.split("\\[GOTO:");
                    // Basically what we do here is t convert the Letters of where to go next into an integer
                    // via the chars ascii values and then i could convert them back ot string later.
                    try {
                        byte[] bytes = ss[ss.length-1].getBytes("US-ASCII");
                        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
                        nextLine = wrapped.getShort();
                    } catch (UnsupportedEncodingException e) {
                        Gdx.app.log("Error", "Unsupported encoding US-ASCII"); }

                }

                if(s.contains("[set")) {
                    String[] instructions = s.split(":");
                    String[] nameNmod = instructions[1].split("=");
                    Prefs.setString(nameNmod[0], nameNmod[1]);
                }

                count ++;
            }
        }
        return new Dialogue(Integer.valueOf(bracketSections[0]), contentSections[0], contentSections[1], contentSections[2], nextLine);
    }
}
