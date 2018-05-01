![TurnItIn Logo](https://guides.turnitin.com/@api/deki/files/6878/turnitin-logo-2x.png?revision=1)

# Turn It In Homework Submission - Jessica Miller

## Explanation of Classes

### Application
Main page of the application that defines the phrases and displays the results

### PhraseFinder
Setup the data and returns the results of the phrase finder result

### PhraseOccurrence
Holds all the property information about a phrase. Includes
count and the Phrase.

### PhraseFinderResult
Handles counting and formatting the results. 

### Assumptions
The first problem does not require a distinct list of phrases and the
second problem does.

## Running The Application

From the command line, run the following commands

```sh
$> cd /path/to/project
$> mvn clean package
$> java -jar target/*.jar
```