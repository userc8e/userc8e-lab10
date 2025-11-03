import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounter {

    //  METHODS

    // counts number of words in text through stopword
    // if stopword null --> count all words in file
    // return int count; unless count < 5 --> TooSmallText exception
    public int processText(StringBuffer text, String stopword) throws InvalidStopWordException, TooSmallText {
        boolean stopwordFound = false;
        int wordCount = 0; //words in text through stopword
        int fullCount = 0; /*count of all words in text
                            will be returned if stopword is null*/

        if (text == null) {
            return 0;
        }

        int count = 0;
        Pattern regex = Pattern.compile("[a-zA-Z0-9']+");
        Matcher regexMatcher = regex.matcher(text);
        while (regexMatcher.find()) {
            //System.out.println("I just found the word: " + regexMatcher.group());
            
            String word = regexMatcher.group();

            if (word.equals(stopword)) {
                stopwordFound = true;
            }

            if (stopword.equals("")) {
                fullCount++;
            } else {
                wordCount++;
            }
        } 

        // if the stopword is null, count all words in the file
        if (stopword.equals("")) {
            // method returns integer word count
            if (fullCount >= 5) {
                return fullCount;
            
            // unless word count <5 - throw TooSmallText exception
            } else {
                throw new TooSmallText("Text has " + fullCount + " words; need at least 5 words.");
            }
        }

        // if stopword is not found
        if (stopwordFound == false) {
            throw new InvalidStopWordException("Stopword was not found in text.");
        }
        
        return wordCount;
    }

    // converts path to a StringBuffer
    // returns StringBuffer
    // if file cant open --> prompt user to re-enter filenanme until it can open
    // if empty file --> raise EmptyFileException w/ file path in msg
    public StringBuffer processFile(String path) throws EmptyFileException {
        return null; //placeholder
    }

    // main method
    // prompts user input: 1 or 2
        // reprompt if not 1 or 2
    // check for stopword as an argument
    // calls methods above
    // displays msgs of exceptions raised
    // user gets 1 chance to re-specify a stopword if not found in text
    public static void main(String[] args) throws TooSmallText {

    }
}
