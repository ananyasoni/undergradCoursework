// Ananya Soni 
// 11/11/2023
// CSE 122 
// C2: Twitter Trends
// This class analyzes the tweets in a TweetBot. Users can 
// get information on the most frequent word that appears
// in the list of tweets contained by the TweetBot as well as the 
// least frequent word that appears in the list of tweets.
import java.util.*;
import java.io.*;

public class TwitterTrends {
    //instance of TweetBot class
    private TweetBot bot;

    // Behavior: 
    //   - This constructor initializes TwitterTrends with the provided
    //     TweetBot for analysis.  
    // Parameters:
    //   - TweetBot bot: the given TweetBot to analyze
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public TwitterTrends(TweetBot bot) {
        this.bot = bot;
    }

    // Behavior: 
    //   - This method returns the most frequent word among all tweets in the TweetBot we are
    //     analyzing, case-insensitively. In other words, on aggregate, this method returns  
    //     the word that is the most frequently used across all the tweets. If multiple words
    //     have the same frequency amongst the tweets the word that comes first in alphabetical
    //     order is returned.
    // Parameters:
    //   - N/A
    // Returns:
    //   - The most frequent word among all tweets in the TweetBot
    // Exceptions:
    //   - N/A   
    public String getMostFrequentWord() {
        Map<String, Integer> wordToCount = getTweetWordCounts();
        String wordToReturn = "";
        int max = Integer.MIN_VALUE;
        for(String word : wordToCount.keySet()) {
            if(wordToCount.get(word) > max) {
                wordToReturn = word;
                max = wordToCount.get(word);
           }
        }
        return wordToReturn;
    }

    // Behavior: 
    //   - This method returns the least frequent word among all tweets in the TweetBot we are
    //     analyzing, case-insensitively. In other words, on aggregate, this method returns 
    //     the word that is the least frequently used across all the tweets. If multiple words
    //     have the same frequency amongst the tweets the word that comes first in alphabetical
    //     order is returned.
    // Parameters:
    //   - N/A
    // Returns:
    //   - The least frequent word among all tweets in the TweetBot
    // Exceptions:
    //   - N/A
    public String getLeastFrequentWord() {
        Map<String, Integer> wordToCount = getTweetWordCounts();
        String wordToReturn = "";
        int min = Integer.MAX_VALUE;
        for(String word : wordToCount.keySet()) {
            if(wordToCount.get(word) < min) {
                wordToReturn = word;
                min = wordToCount.get(word);
            }
        }
        return wordToReturn;
    }

    // Behavior: 
    //   - This method records the amount of times each word appears in all the  
    //     tweets and returns a map that contains all the words in the list of tweets
    //     contained by the TweetBot and the frequency associated with each word 
    //     (how many times each word appears in the exhaustive list of all the tweets).
    // Parameters:
    //   - N/A
    // Returns:
    //   - Map<String, Integer> wordToCount: a map that contains all the words in the list of tweets
    //     and the frequency associated with each word 
    // Exceptions:
    //   - N/A
    private Map<String, Integer> getTweetWordCounts() {
        Map<String, Integer> wordToCount = new TreeMap<>();
        for(int i = 0; i < this.bot.numTweets(); i++) {
            String nextTweet = this.bot.nextTweet();
            Scanner input = new Scanner(nextTweet);
            while(input.hasNext()) {
                String word = input.next().toLowerCase();
                if(!wordToCount.containsKey(word)) {
                    wordToCount.put(word, 1);
                } else {
                    wordToCount.put(word, wordToCount.get(word) + 1);
                }
            }
        }
        return wordToCount;
    }
}
