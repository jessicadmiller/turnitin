package com.turnitin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {

    /**
     * TODO: PROVIDE DOCUMENTATION
     * @author Jessica Miller <jessica.dressler@gmail.com>
     */
    public static void main(String[] args) {

        //List of phrases to search on.
        Map<String, List<String>> PHRASES = new HashMap<>();

        PHRASES.put("1", Arrays.asList("afterward", "whenever", "however", "until", "as soon as", "as long as", "while", "again", "also", "besides"));
        PHRASES.put("2", Arrays.asList("therefore", "thus", "consequently", "as a result", "for this reason", "so", "so that", "accordingly", "because"));
        PHRASES.put("3", Arrays.asList("in addition to", "so", "moreover"));
        PHRASES.put("4", Arrays.asList("in general", "for the most part", "as a general rule", "on the whole", "usually", "typically", "in summary"));


        System.out.println("\nProblem 1\n");

        String text = "Afterward, a yellow bird landed on the tall tree in addition to a lazy tortoise.  " +
                "However, he had a red beak. In addition to white the patches on the wings, he was completely yellow. " +
                "In summary, it was yellow bird. In summary, it did not sing.";

        System.out.println(text);

        PhraseFinder stringFinder = new PhraseFinder(text,PHRASES);
        PhraseFinderResult resultsProblem1 = stringFinder.getPhraseFinderResult();


        for (PhraseOccurrence phrase : resultsProblem1.getPhraseOccurrences()) {
            System.out.println(phrase.getPhrase() + " : " + phrase.getOccurrenceCount());
        }

        System.out.println("\nProblem 2\n");

        String textProblem2 = "So, I have not seen this. So, usually, I don't follow all trends.";
        System.out.println(textProblem2);

        PhraseFinder stringFinder2 = new PhraseFinder(textProblem2,PHRASES);
        PhraseFinderResult resultsProblem2 = stringFinder2.getPhraseFinderResult();

        for (String phrase : resultsProblem2.getVocabulary()) {
            System.out.print(phrase + ", ");
        }

        System.out.println("");

        for (int count : resultsProblem2.getVector()) {
            System.out.print(count + ", ");
        }

    }
}
