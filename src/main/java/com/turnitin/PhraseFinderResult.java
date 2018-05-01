package com.turnitin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PhraseFinderResult {

    /** List of phraseOccurrences occurrences in the sentence */
    private List<PhraseOccurrence> phraseOccurrences;
    private Map<String, List<String>> allPhrases;
    private List<String> vocabulary;
    private List<Integer> vector;
    private String sentence;
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

    public void setPhraseOccurrences(List<PhraseOccurrence> phraseOccurrences) {
        this.phraseOccurrences = phraseOccurrences;
    }

    public List<PhraseOccurrence> getPhraseOccurrences() {
        return phraseOccurrences;
    }
    public void setVocabulary(List<String> vocabulary) {
        this.vocabulary = vocabulary;
    }

    public List<String> getVocabulary() {
        return this.vocabulary;
    }
    public void setVector(List<Integer> vector) {
        this.vector = vector;
    }

    public List<Integer> getVector() {
        return this.vector;
    }

    public PhraseFinderResult(Map<String, List<String>> phrases,String sentence,boolean distinctList){
        this.sentence = sentence;
        this.allPhrases = phrases;
        this.initializePhraseOccurrences();
        this.countInstances();
        if(distinctList){
            DistinctPhrases();
        }
    }

    /**
     * Creates a zero count for every phase in our watchlist
     */
    public void initializePhraseOccurrences() {
        List<PhraseOccurrence> occurrences = new ArrayList<>();

        //Iterating over the hashmap of phraseOccurrences
        Iterator it = this.allPhrases.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            List<String> value = (List<String>)pair.getValue();
            occurrences.addAll(splitWords(value));
            //it.remove();
        }

        this.phraseOccurrences=occurrences;
    }

    /**
     * Parsing the sentence and replacing any characters that are not part of the word.
     */
    public List<PhraseOccurrence> splitWords(List<String> wordList) {
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
    public void countInstances() {
        String normalizedSentence = this.sentence.toLowerCase();
        // Stepping through the phraseOccurrences to find in the sentence
        for (PhraseOccurrence occurrence : this.phraseOccurrences) {
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
    }

    /**
     * Handling removing any duplicates of phraseOccurrences
     * todo: getter behavior really setter. rethink name
     */
    public void DistinctPhrases() {
        List<PhraseOccurrence> distinctPhrases = this.phraseOccurrences.stream()
                .filter(distinctByKey(p -> p.getPhrase()))
                .collect(Collectors.toList());

        List<String> vocabulary = new ArrayList<>();
        List<Integer> vector = new ArrayList<>();

        for (PhraseOccurrence phrase : distinctPhrases) {
            vocabulary.add(phrase.getPhrase());
            vector.add(phrase.getOccurrenceCount());
        }

        this.vocabulary = vocabulary;
        this.vector = vector;
    }

    /**
     * Ensures that a property exists in a given list only once
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


}
