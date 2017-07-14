package com.knowyourself;

public class Dialogue{
    private int id;
    private String speaker, spokenTo, content;
    public Dialogue(int id, String speaker, String spokenTo, String content) {
        this.id = id;
        this.speaker = speaker;
        this.spokenTo = spokenTo;
        this.content = content;
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
}
