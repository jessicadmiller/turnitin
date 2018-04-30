package com.turnitin;

//Handles the phrases and counts
public class Phrase {

    private String phraseName;
    private int phraseCount;

    public void setPhraseName(String name) {
        phraseName = name;
    }

    public String getPhraseName() {
        return phraseName;
    }

    public void setPhraseCount(int count) {
        phraseCount = count;
    }

    public int getPhraseCount() {
        return phraseCount;
    }

    public Phrase(String name, int count) {
        setPhraseCount(count);
        setPhraseName(name);
    }
}
