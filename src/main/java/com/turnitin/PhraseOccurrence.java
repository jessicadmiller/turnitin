package com.turnitin;

/**
 * Model representing a given phrase and the number of occurrences found in a sentence
 *
 * @author Jessica Miller <jessica.dressler@gmail.com>
 */
public class PhraseOccurrence {

    private String phrase;
    private int occurrenceCount;

    public PhraseOccurrence(String name, int count) {
        setOccurrenceCount(count);
        setPhrase(name);
    }

    public PhraseOccurrence(String name) {
        this(name, 0);
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setOccurrenceCount(int occurrenceCount) {
        this.occurrenceCount = occurrenceCount;
    }

    public int getOccurrenceCount() {
        return occurrenceCount;
    }
}
