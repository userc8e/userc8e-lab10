import java.io.File;
import java.io.IOException;
import java.nio.file.*; // Import the Scanner class to read text files
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounter {

    //  METHODS

    // counts number of words in text through stopword
    // if stopword null --> count all words in file
    // return int count; unless count < 5 --> TooSmallText exception
    public static int processText(StringBuffer text, String stopword) throws InvalidStopwordException, TooSmallText {
        boolean stopwordFound = false;
        int wordCount = 0; //words in text through stopword
        int fullCount = 0; /*count of all words in text
                            will be returned if stopword is null*/

        if (text == null) {
            throw new TooSmallText("Text is null.");
        }

        int count = 0;
        Pattern regex = Pattern.compile("[a-zA-Z0-9']+");
        Matcher matcher = regex.matcher(text);
        while (matcher.find()) {
            //System.out.println("I just found the word: " + regexMatcher.group());
            
            String word = matcher.group();

            if (stopword != null && word.equals(stopword)) {
                stopwordFound = true;
            }

            if (stopword == null) {
                fullCount++;
            } else {
                wordCount++;
            }
        } 

        // if the stopword is null, count all words in the file
        if (stopword == null) {
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
            throw new InvalidStopwordException("Stopword was not found in text.");
        }

        return wordCount;
    }


    // converts path to a StringBuffer
    // returns StringBuffer
    // if file cant open --> prompt user to re-enter filenanme until it can open
    // if empty file --> raise EmptyFileException w/ file path in msg
    public static StringBuffer processFile(String path) throws EmptyFileException {
        Scanner scan = new Scanner(System.in);
        File userFile = new File(path);

        // if file cant open --> prompt user to re-enter filename
        while (userFile.exists() == false) {
            System.out.println("File can't be opened, re-enter filename: ");
            path = scan.nextLine();
            userFile = new File(path);
        }

        Path filePath = Paths.get(path);
        StringBuffer text = new StringBuffer();

        try {
            List<String> textLines = Files.readAllLines(filePath);
            if (textLines.isEmpty()) {
                scan.close();
                throw new EmptyFileException("File " + userFile + " is empty.");
            }
            for (String line : textLines) {
                text.append(line + "\n");
            }
        } catch (IOException e) {
            return new StringBuffer();
        }

        scan.close();
        return text;
    }


    // main method
    // prompts user input: 1 or 2
        // reprompt if not 1 or 2
    // check for stopword as an argument
    // calls methods above
    // displays msgs of exceptions raised
    // user gets 1 chance to re-specify a stopword if not found in text
    public static void main(String[] args) throws TooSmallText {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter option 1 or 2: ");
        int option = scan.nextInt(); //user will choose option 1 or 2

        // reprompt user if not 1 or 2
        if (option != 1 && option != 2) {
            System.out.println("Invalid input. Enter 1 or 2: ");
            option = scan.nextInt();
            scan.close();

            // ends if not 1 nor 2 again
            // (they only get 1 chance)
            if (option != 1 && option != 2) {
                System.out.println("Invalid input, exiting.");
                return;
            }
        }

        switch(option) {
            case 1:
                String filePath = args[0];
                try {
                    try {
                        StringBuffer fileBuffer = processFile(filePath);
                        String stopword = null;
                        int wordCount = 0;

                        if (args.length > 1) {
                            stopword = args[1];
                        }

                        wordCount = processText(fileBuffer, stopword);
                        System.out.println("Found " + wordCount + " words.");
                    } catch (EmptyFileException e) {
                        throw new TooSmallText("Only found 0 words");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case 2:
                String text = args[0];
                StringBuffer buffer = new StringBuffer(text);
                try {
                    processText(buffer, null);
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
        }
    }
}
