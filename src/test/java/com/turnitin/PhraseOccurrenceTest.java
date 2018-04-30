package com.turnitin;

import junit.framework.TestCase;

public class PhraseOccurrenceTest extends TestCase {

    public void testSetGetPhrase() {
        PhraseOccurrence subject = new PhraseOccurrence("name");
        subject.setPhrase("noodles");
        assertEquals("noodles", subject.getPhrase());
    }

    public void testSetGetOccurrenceCount() {
        PhraseOccurrence subject = new PhraseOccurrence("name");
        subject.setOccurrenceCount(100);
        assertEquals(100, subject.getOccurrenceCount());
    }

    public void testConstructorWithDefaultOccurrence() {
        PhraseOccurrence subject = new PhraseOccurrence("name");
        assertEquals(0, subject.getOccurrenceCount());
    }

}