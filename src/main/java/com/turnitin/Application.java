package com.turnitin;

public class Application {

    /**
     * TODO: PROVIDE DOCUMENTATION
     * @author Jessica Miller <jessica.dressler@gmail.com>
     */
    public static void main(String[] args) {

        System.out.println("\nProblem 1\n");

        String text = "Afterward, a yellow bird landed on the tall tree in addition to a lazy tortoise.  " +
                "However, he had a red beak. In addition to white the patches on the wings, he was completely yellow. " +
                "In summary, it was yellow bird. In summary, it did not sing.";

        System.out.println(text);

        PhraseFinder stringFinder = new PhraseFinder(text);

        for (PhraseOccurrence phrase : stringFinder.getPhraseOccurrences()) {
            System.out.println(phrase.getPhrase() + " : " + phrase.getOccurrenceCount());
        }

        System.out.println("\nProblem 2\n");

        stringFinder.setSentence("So, I have not seen this. So, usually, I don't follow all trends.");
        System.out.println(stringFinder.getSentence());

        stringFinder.countInstances();
        stringFinder.getDistinct();

        for (String phrase : stringFinder.getVocabulary()) {
            System.out.print(phrase + ", ");
        }

        System.out.println("");

        for (int count : stringFinder.getVector()) {
            System.out.print(count + ", ");
        }

    }
}
