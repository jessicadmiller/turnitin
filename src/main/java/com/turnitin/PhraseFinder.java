package com.turnitin;

import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * TODO: PROVIDE DOCUMENTATION
 *
 * @author Jessica Miller <jessica.dressler@gmail.com>
 */
public class PhraseFinder {

    /**
     * subject sentence
     */
    private String sentence;

    /**
     * Holds the list of phrases to search
     */
    private Map<String, List<String>> allPhrases;

    /**
     * Holds results of phrase search for given sentence
     */
    private PhraseFinderResult phraseFinderResult;

    /**
     * Sets the subject sentence
     * NB: this will also reset any existing phrase counts
     *
     * @param sentence
     */
    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    /**
     * Gets the sentence that needs to be searched
     *
     * @return sentence
     */
    public String getSentence() {
        return sentence;
    }

    /**
     * Returns the results of the phrase search
     *
     * @return Phrase finder result
     */
    public PhraseFinderResult getPhraseFinderResult() {
        return this.phraseFinderResult;
    }

    /**
     * @param sentence     text that needs to searched for phrases
     * @param phrases      actual phrases that will be searched on
     */
    public PhraseFinder(String sentence, Map<String, List<String>> phrases) {
        this.sentence = sentence;
        this.allPhrases = phrases;
        this.initializePhraseOccurrences();
        this.countInstances();
    }

    /**
     * Creates a zero count for every phase in our watchlist
     */
    private void initializePhraseOccurrences() {
        List<PhraseOccurrence> occurrences = new ArrayList<>();

        //Iterating over the hashmap of phraseOccurrences
        for (Object o : this.allPhrases.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            List<String> value = (List<String>) pair.getValue();
            occurrences.addAll(splitWords(value));
            //it.remove();
        }
        this.phraseFinderResult = new PhraseFinderResult(occurrences);
    }

    /**
     * Parsing the sentence and replacing any characters that are not part of the word.
     */
    private List<PhraseOccurrence> splitWords(List<String> wordList) {
        List<PhraseOccurrence> phrasesToFind = new ArrayList<>();

        for (String w : wordList) {
            w = w.trim().toLowerCase();
            phrasesToFind.add(new PhraseOccurrence(w, 0));
        }
        return phrasesToFind;
    }

    /**
     * This will do the parsing and counting of the sentence
     */
    private void countInstances() {
        String normalizedSentence = this.sentence.toLowerCase();
        // Stepping through the phraseOccurrences to find in the sentence
        for (PhraseOccurrence occurrence : phraseFinderResult.getPhraseOccurrences()) {
            //Finding the first index of the phrase
            int position = normalizedSentence.indexOf(occurrence.getPhrase());

            // Stepping through the rest of the string to find any other instances
            // of the word.
            while (position >= 0) {
                position = normalizedSentence.indexOf(occurrence.getPhrase(), position);

                if (position >= 0) {
                    occurrence.setOccurrenceCount(occurrence.getOccurrenceCount() + 1);
                    position++;
                }
            }
        }
        this.phraseFinderResult.distinctPhrases();
    }
}
