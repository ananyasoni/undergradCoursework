// Ananya Soni 
// 11/16/2023
// CSE 122 
// P2: Absurdle
// This class allows a user to play a game called Absurdle, a variant of Wordle,
// where a user keeps guessing the target word until they are successful. 
// This program gives the impression of picking a single target word, 
// but instead it considers the entire list of all possible target words which 
// conform to the users guesses so far. Each time the user guesses, this class prunes its 
// internal list of target words as little as possible, to intentionally prolong the game.
// This class allows a user to choose the size of words they would like 
// to guess, records the user's guesses, and generates and chooses a pattern for the user's 
// guesses. Once the user successfully guesses the target word this class prints
// the patterns of their guesses as well as the amount of guesses it took the user to 
// guess the target word.
import java.util.*;
import java.io.*;

public class Absurdle  {
    public static final String GREEN = "🟩";
    public static final String YELLOW = "🟨";
    public static final String GRAY = "⬜";

    // [[ ALL OF MAIN PROVIDED ]]
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the game of Absurdle.");

        System.out.print("What dictionary would you like to use? ");
        String dictName = console.next();

        System.out.print("What length word would you like to guess? ");
        int wordLength = console.nextInt();

        List<String> contents = loadFile(new Scanner(new File(dictName)));
        Set<String> words = pruneDictionary(contents, wordLength);

        List<String> guessedPatterns = new ArrayList<>();
        while (!isFinished(guessedPatterns)) {
            System.out.print("> ");
            String guess = console.next();
            String pattern = record(guess, words, wordLength);
            guessedPatterns.add(pattern);
            System.out.println(": " + pattern);
            System.out.println();
        }
        System.out.println("Absurdle " + guessedPatterns.size() + "/∞");
        System.out.println();
        printPatterns(guessedPatterns);
    }

    // [[ PROVIDED ]]
    // Prints out the given list of patterns.
    // - List<String> patterns: list of patterns from the game
    public static void printPatterns(List<String> patterns) {
        for (String pattern : patterns) {
            System.out.println(pattern);
        }
    }

    // [[ PROVIDED ]]
    // Returns true if the game is finished, meaning the user guessed the word. Returns
    // false otherwise.
    // - List<String> patterns: list of patterns from the game
    public static boolean isFinished(List<String> patterns) {
        if (patterns.isEmpty()) {
            return false;
        }
        String lastPattern = patterns.get(patterns.size() - 1);
        return !lastPattern.contains("⬜") && !lastPattern.contains("🟨");
    }

    // [[ PROVIDED ]]
    // Loads the contents of a given file Scanner into a List<String> and returns it.
    // - Scanner dictScan: contains file contents
    public static List<String> loadFile(Scanner dictScan) {
        List<String> contents = new ArrayList<>();
        while (dictScan.hasNext()) {
            contents.add(dictScan.next());
        }
        return contents;
    }

    // Behavior: 
    //   - This method prunes the dictionary containing words based 
    //     on the user's chosen word length. This method returns a set of words that 
    //     contain only the words from the dictionary that are the length specified by the
    //     user and eliminates any duplicates. 
    // Parameters:
    //   - List<String> contents: Contains all the words in the specified
    //     dictionary
    //   - int wordLength: The word size (amount of letters) of words 
    //     the user wants to guess 
    // Returns:
    //   - Set<String> words: a set of words that contain only the words from the dictionary 
    //     that are the length specified by the user and contains no duplicate words. 
    // Exceptions:
    //   - IllegalArgumentException -> this method throws an IllegalArgumentException
    //     when the given word length (parameter -> int wordLength) is less than one 
    public static Set<String> pruneDictionary(List<String> contents, int wordLength) {
        if(wordLength < 1) {
            throw new IllegalArgumentException();
        }
        Set<String> words = new TreeSet<>();
        for(int i = 0; i < contents.size(); i++) {
            String nextWord = contents.get(i);
            if(nextWord.length() == wordLength) {
                words.add(nextWord);
            }
        }
        return words;     
    }

    // Behavior: 
    //   - This method records the user's guesses, updates the current set of words that will 
    //     be under consideration, and returns the chosen pattern corresponding to the user's guess. 
    //     This method updates the set of words given based on the pattern that corresponds to the largest set of words.
    //     Note: This means that the set of words given will be changed to only contain the words that correspond
    //     to the pattern that is associated with the largest set of words).
    // Parameters:
    //   - String guess: The user's guess 
    //   - Set<String> words: A set of the current words under consideration
    //   - int wordLength: The word size (amount of letters) of words 
    //     the user wants to guess
    // Returns:
    //   - String chosenPattern: The pattern of grey, yellow, and green emojis
    //     that corresponds to the user's guess based on the largest set of 
    //     target words
    // Exceptions:
    //   - IllegalArgumentException -> this method throws an IllegalArgumentException
    //     if the amount of letters in the user's guess doesn't equal the user's 
    //     chosen wordLength or if the set of words is empty.
    public static String record(String guess, Set<String> words, int wordLength) {
        if(guess.length() != wordLength || words.isEmpty()) {
            throw new IllegalArgumentException();
        }
        //build patternToWords Map
        Map<String, Set<String>> patternToWords = new TreeMap<>();
        for(String word : words) {
            String nextPattern = patternFor(word, guess);
            if(!patternToWords.containsKey(nextPattern)) {
                patternToWords.put(nextPattern, new TreeSet<String>());
                patternToWords.get(nextPattern).add(word);
            } else {
                patternToWords.get(nextPattern).add(word);
            }
        }      
        String chosenPattern = choosePattern(words, patternToWords);
        return chosenPattern;
    }

    // Behavior: 
    //   - This helper method chooses the pattern that corresponds to the largest set 
    //     of words still remaining. In other words, this method chooses the pattern 
    //     that results in as little pruning of the dictionary as possible. This method
    //     also determines the next set of words that will be under consideration while
    //     choosing the pattern. The next set of words under consideration are the largest
    //     set of words. Note: This means that this method updates the current set of words based on the
    //     guess that corresponds to be the largest set. The set will be changed to only contain
    //     the words corresponds to the most common pattern.
    // Parameters:
    //   - Set<String> words: A set of the current words under consideration
    //   - Map<String, Set<String>> patternToWords: contains the patterns made 
    //     from the user's guess and the set of words that correspond to each 
    //     pattern
    // Returns:
    //   - String patternToReturn: The pattern of grey, yellow, and green emojis
    //     that corresponds to the user's guess and the largest set 
    //     of words still remaining.
    // Exceptions:
    //   - N/A
    public static String choosePattern(Set<String> words, Map<String, Set<String>> patternToWords) {
        String patternToReturn = "";
        int maxNumWords = 0;
        for(String pattern : patternToWords.keySet()) {
            if(patternToWords.get(pattern).size() > maxNumWords) {
                maxNumWords = patternToWords.get(pattern).size();
                patternToReturn = pattern;
            }      
        }
        words.clear();
        words.addAll(patternToWords.get(patternToReturn));  
        return patternToReturn;
    }

    // Behavior: 
    //   - This method generates the Wordle pattern for the user's guess
    //     compared against a word. It first finds exact matches in the user's
    //     guess and marks them green. Then it looks for approximate matches and marks
    //     them yellow only if they are an unused matching character from the word. And if
    //     there are multiple places to mark an approximate match this method chooses the 
    //     leftmost places. This method lastly, replaces all unused characters 
    //     with grey and returns the pattern.
    // Parameters:
    //   - String word: The target word  
    //   - String guess: The user's guess 
    // Returns:
    //   - String pattern: The pattern corresponding to the user's guess
    //     compared to the target word.
    // Exceptions:
    //   - N/A
    public static String patternFor(String word, String guess) {
        //create a List<String> pattern that contains letters in guess
        List<String> pattern = new ArrayList<>();
        for(int i = 0; i < guess.length(); i++) {
            pattern.add(guess.substring(i, i + 1));
        }
        //generate Map<Character, Integer> letterToCount to store the word's letter counts
        Map<Character, Integer> letterToCount = new TreeMap<>();
        for(int i = 0; i < word.length(); i++) {
            char nextLetter = word.charAt(i);
            if(!letterToCount.containsKey(nextLetter)) {
                letterToCount.put(nextLetter, 1);
            } else {
                letterToCount.put(nextLetter, letterToCount.get(nextLetter) + 1);
            }    
        }
        //mark exact matches
        for(int i = 0; i < pattern.size(); i++) {
            char nextLetter = word.charAt(i);
            if(pattern.get(i).charAt(0) == nextLetter) {
                pattern.set(i,"🟩"); 
                letterToCount.put(nextLetter, letterToCount.get(nextLetter) - 1);
            }
        }
        //mark approximate matches
        for(int i = 0; i < pattern.size(); i++) {
            String nextLetter = pattern.get(i);
            if(word.contains(nextLetter) && letterToCount.get(nextLetter.charAt(0)) > 0) {
                pattern.set(i,"🟨"); 
                letterToCount.put(nextLetter.charAt(0), letterToCount.get(nextLetter.charAt(0)) - 1);
            }
        }   
        //mark rest of letters gray and concatenate Strings to return the pattern
        String patternToReturn = "";
        for(int i = 0; i < pattern.size(); i++) {
            String nextLetter = pattern.get(i);
            if(!nextLetter.equals("🟩") && !nextLetter.equals("🟨")) {
                pattern.set(i,"⬜"); 
            }
            patternToReturn += pattern.get(i);
        }   
        return patternToReturn;
    }
}
