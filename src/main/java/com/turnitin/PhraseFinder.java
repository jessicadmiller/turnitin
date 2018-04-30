package com.turnitin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * TODO: PROVIDE DOCUMENTATION
 * @author Jessica Miller <jessica.dressler@gmail.com>
 */
public class PhraseFinder {

    /**
     * Static values of phrases
     * todo: should probably have phrases get passed into here from application - makes this more reusable.
     */
    private static Map<String, List<String>> PHRASES = new HashMap<>();

    static {
        PHRASES.put("1", Arrays.asList("afterward", "whenever", "however", "until", "as soon as", "as long as", "while", "again", "also", "besides"));
        PHRASES.put("2", Arrays.asList("therefore", "thus", "consequently", "as a result", "for this reason", "so", "so that", "accordingly", "because"));
        PHRASES.put("3", Arrays.asList("in addition to", "so", "moreover"));
        PHRASES.put("4", Arrays.asList("in general", "for the most part", "as a general rule", "on the whole", "usually", "typically", "in summary"));
    }

    /** subject sentence */
    private String sentence;
    /** List of phrases occurrences in the sentence */
    private List<PhraseOccurrence> phrases;

    private List<String> vocabulary;
    private List<Integer> vector;

    /**
     * Sets the subject sentence
     * NB: this will also reset any existing phrase counts
     * @param sentence
     */
    public void setSentence(String sentence) {
        this.sentence = sentence;
        this.clearPhraseModel();
    }

    public String getSentence() {
        return sentence;
    }
    public void setPhrases(List<PhraseOccurrence> phrases) {
        this.phrases = phrases;
    }

    public List<PhraseOccurrence> getPhrases() {
        return phrases;
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


    public PhraseFinder(String sentence) {
        this.sentence = sentence;
        this.setupPhraseModel();
        this.countInstances();
    }

    /**
     * Get the model setup to handle the word counts.
     */
    public void setupPhraseModel() {
        List<PhraseOccurrence> phrasesToFind = new ArrayList<>();

        //Iterating over the hashmap of phrases
        Iterator it = PHRASES.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            // Splitting the words up into a model and adding to the phrase list.
            // todo: remove toString to avoid complexity in splitWords
            phrasesToFind.addAll(splitWords(pair.getValue().toString()));
            it.remove();
        }

        this.phrases = phrasesToFind;
    }

    /**
     * If there is a new sentence clear counts
     */
    public void clearPhraseModel() {
        List<PhraseOccurrence> phrases = new ArrayList<>();
        if (this.phrases.size() == 0) {
            return;
        }
        for (PhraseOccurrence phrase : this.phrases) {
            phrase.setOccurrenceCount(0);
            phrases.add(phrase);
        }
        this.phrases = phrases;
    }

    /**
     * This will do the parsing and counting of the sentence
     */
    public void countInstances() {
        String normalizedSentence = this.sentence.toLowerCase();
        // Stepping through the phrases to find in the sentence
        for (PhraseOccurrence phrase : this.phrases) {
            //Finding the first index of the phrase
            int position = normalizedSentence.indexOf(phrase.getPhrase());

            // Stepping through the rest of the string to find any other instances
            // of the word.
            while (position >= 0) {
                position = normalizedSentence.indexOf(phrase.getPhrase(), position);

                if (position >= 0) {
                    phrase.setOccurrenceCount(phrase.getOccurrenceCount() + 1);
                    position++;
                }
            }
        }
    }

    /**
     * Parsing the sentence and replacing any characters that are not part of the word.
     */
    public List<PhraseOccurrence> splitWords(String wordList) {
        List<PhraseOccurrence> phrasesToFind = new ArrayList<>();
        // todo: fix associated with above todo
        wordList = wordList.replace('[', ' ');
        wordList = wordList.replace(']', ' ');
        String[] words = wordList.split(",");
        for (String w : words) {
            w = w.trim().toLowerCase();
            phrasesToFind.add(new PhraseOccurrence(w, 0));
        }
        return phrasesToFind;
    }

    /**
     * Handling removing any duplicates of phrases
     */
    public void getDistinct() {
        List<PhraseOccurrence> distinctPhrases = phrases.stream().filter(distinctByKey(p -> p.getPhrase()))
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
