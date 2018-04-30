package com.turnitin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StringFinder {

    //Static values of phrases
    private static Map<String, List<String>> PHRASES = new HashMap<>();

    static {
        PHRASES.put("1", Arrays.asList("afterward", "whenever", "however", "until", "as soon as", "as long as", "while", "again", "also", "besides"));
        PHRASES.put("2", Arrays.asList("therefore", "thus", "consequently", "as a result", "for this reason", "so", "so that", "accordingly", "because"));
        PHRASES.put("3", Arrays.asList("in addition to", "so", "moreover"));
        PHRASES.put("4", Arrays.asList("in general", "for the most part", "as a general rule", "on the whole", "usually", "typically", "in summary"));
    }

    //Text to search
    private String sentence;
    //List of phrases to count in the sentence
    private List<Phrase> phrases;
    private List<String> vocabulary;
    private List<Integer> vector;

    public void setSentence(String sentence) {
        this.sentence = sentence;

        // Setting a new sentence so let's clear the phrase model
        this.clearPhraseModel();
    }

    public String getSentence() {
        return sentence;
    }

    public void setPhrases(List<Phrase> phrases) {
        this.phrases = phrases;
    }

    public List<Phrase> getPhrases() {
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


    public StringFinder(String sentence) {
        this.sentence = sentence;
        this.setupPhraseModel();
        this.countInstances();
    }

    //Get the model setup to handle the word counts.
    public void setupPhraseModel() {
        List<Phrase> phrasesToFind = new ArrayList<>();

        //Iterating over the hashmap of phrases
        Iterator it = PHRASES.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            //Splitting the words up into a model and adding to the phrase list.
            phrasesToFind.addAll(splitWords(pair.getValue().toString()));
            it.remove();
        }

        this.phrases = phrasesToFind;
    }

    //If there is a new sentence clear counts
    public void clearPhraseModel() {
        List<Phrase> phrases = new ArrayList<>();
        if (this.phrases.size() == 0) {
            return;
        }
        for (Phrase phrase : this.phrases) {
            phrase.setPhraseCount(0);
            phrases.add(phrase);
        }
        this.phrases = phrases;
    }

    //This will do the parsing and counting of the sentence
    public void countInstances() {
        String normalizedSentence = this.sentence.toLowerCase();
        //Stepping through the phrases to find in the sentence
        for (Phrase phrase : this.phrases) {
            //Finding the first index of the phrase
            int position = normalizedSentence.indexOf(phrase.getPhraseName());

            //Stepping through the rest of the string to find any other instances
            // of the word.
            while (position >= 0) {
                position = normalizedSentence.indexOf(phrase.getPhraseName(), position);

                if (position >= 0) {
                    phrase.setPhraseCount(phrase.getPhraseCount() + 1);
                    position++;
                }
            }
        }
    }

    // Parsing the sentence and replacing any characters
    // that are not part of the word.
    public List<Phrase> splitWords(String wordList) {
        List<Phrase> phrasesToFind = new ArrayList<>();
        wordList = wordList.replace('[', ' ');
        wordList = wordList.replace(']', ' ');
        String[] words = wordList.split(",");
        for (String w : words) {
            w = w.trim().toLowerCase();
            phrasesToFind.add(new Phrase(w, 0));
        }
        return phrasesToFind;
    }

    // Handling removing any duplicates of phrases
    public void getDistinct() {
        List<Phrase> distinctPhrases = phrases.stream().filter(distinctByKey(p -> p.getPhraseName()))
                .collect(Collectors.toList());

        List<String> vocabulary = new ArrayList<>();
        List<Integer> vector = new ArrayList<>();

        for (Phrase phrase : distinctPhrases) {
            vocabulary.add(phrase.getPhraseName());
            vector.add(phrase.getPhraseCount());
        }

        this.vocabulary = vocabulary;
        this.vector = vector;
    }

    //Takes in a property and makes sure the list only has one instance of the property
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
