package com.turnitin;

import java.util.*;
import java.util.List;

/**
 * TODO: PROVIDE DOCUMENTATION
 * @author Jessica Miller <jessica.dressler@gmail.com>
 */
public class PhraseFinder {


    /** subject sentence */
    private String sentence;
    private Map<String, List<String>> allPhrases;
    private PhraseFinderResult phraseFinderResult;

    /**
     * Sets the subject sentence
     * NB: this will also reset any existing phrase counts
     * @param sentence
     */
    public void setSentence(String sentence) {
        this.sentence = sentence;

    }

    public String getSentence() {
        return sentence;
    }

    public PhraseFinderResult getPhraseFinderResult(){
        return this.phraseFinderResult;
    }


    public PhraseFinder(String sentence,Map<String, List<String>> phrases, boolean distinctList) {
        this.sentence = sentence;
        this.allPhrases = phrases;

        this.phraseFinderResult = new PhraseFinderResult(phrases,sentence,distinctList);
    }
}
