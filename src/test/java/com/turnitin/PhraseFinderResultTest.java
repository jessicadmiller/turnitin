package com.turnitin;

import junit.framework.TestCase;

import java.util.*;

public class PhraseFinderResultTest extends TestCase {

    public void testGetSetPhraseFinderResult() {

        List<PhraseOccurrence> occurrences = new ArrayList<PhraseOccurrence>() {
            {
                add(new PhraseOccurrence("afterward"));
                add(new PhraseOccurrence("whenever"));
                add(new PhraseOccurrence("however"));
                add(new PhraseOccurrence("until"));
            }};
        PhraseFinderResult subject = new PhraseFinderResult(occurrences);
        assertEquals(occurrences, subject.getPhraseOccurrences());
    }

    public void testGetSetVocabulary() {
        List<PhraseOccurrence> occurrences = new ArrayList<PhraseOccurrence>() {
            {
                add(new PhraseOccurrence("afterward"));
                add(new PhraseOccurrence("whenever"));
                add(new PhraseOccurrence("however"));
            }};
        PhraseFinderResult subject = new PhraseFinderResult(occurrences);
        List<String> vocab  = new ArrayList<String>() {
            {
                add("afterward");
                add("whenever");
                add("however");
            }};
        assertEquals(vocab, subject.getVocabulary());
    }
}