package com.turnitin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PhraseFinderResult {

    /** List of phraseOccurrences occurrences in the sentence */
    private List<PhraseOccurrence> phraseOccurrences;
    private List<String> vocabulary;
    private List<Integer> vector;

    public List<PhraseOccurrence> getPhraseOccurrences() {
        return phraseOccurrences;
    }

    public List<String> getVocabulary() {
        return this.vocabulary;
    }

    public List<Integer> getVector() {
        return this.vector;
    }

    public PhraseFinderResult(List<PhraseOccurrence> occurrence){
        this.phraseOccurrences = occurrence;
    }

    /**
     * Handling removing any duplicates of phraseOccurrences
     */
    public void distinctPhrases() {
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
    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
