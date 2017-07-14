package com.knowyourself;

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

    public static Dialogue dalogueParser(String line) {
        String[] bracketSections = line.split("}");
        String[] contentSections = bracketSections[bracketSections.length - 1].split("|");
        int nextLine = -1;
        if(bracketSections.length > 2) {
            int count = 0;
            for(String s: bracketSections) {
                if(count == 0 || count == bracketSections.length -1)
                    continue;

                if(s.contains("{t:")) {
                    String[] ss = s.split("{t:");
                    nextLine = Integer.valueOf(ss[ss.length-1]);
                }

                count ++;
            }
        }
        return new Dialogue(Integer.valueOf(bracketSections[0]), contentSections[0], contentSections[1], contentSections[3], nextLine);
    }
}
