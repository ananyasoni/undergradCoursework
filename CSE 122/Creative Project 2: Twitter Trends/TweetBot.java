// Ananya Soni 
// 11/11/2023
// CSE 122 
// C2: Twitter Trends
// TA: Kevin
// This class reads in and manages the state of tweets. Users can access 
// tweets one by one, get a count of the total number of tweets that currently exist
// add a tweet, remove a specified tweet, and reset to read tweets from the
// beginning of the list of tweets.
import java.util.*;
import java.io.*;

public class TweetBot {
    // list of tweets
    private List<String> tweets;
    // a pointer that keeps track of which tweet the program is
    // currently at while traversing the list of tweets
    private int iterator;

    // Behavior: 
    //   - This constructor contructs a tweet bot. It initializes a list of tweets containing  
    //     all the tweets provided. This constructor also enables accessing tweets from the 
    //     beginning of the tweet list.
    // Parameters:
    //   - List<String> tweets: a list of tweets the user provides 
    // Returns:
    //   - N/A
    // Exceptions:
    //   - IllegalArgumentException: if the given list of tweets contains less than 1 tweet, 
    //     an IllegalArgumentException is thrown.
    public TweetBot(List<String> tweets) {
        if(tweets.size() < 1) {
            throw new IllegalArgumentException();
        }
        this.tweets = new ArrayList<String>();
        this.tweets.addAll(tweets);
        this.iterator = 0;
    }

    // Behavior: 
    //   - Resets the iteration state of the TweetBot such that subsequent calls to nextTweet 
    //     start back at the beginning of the tweet list. 
    // Parameters:
    //   - N/A
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public void reset() {
        this.iterator = 0;
    }

    // Behavior: 
    //   - This method returns the number of tweets that are currently in the 
    //     list of tweets.
    // Parameters:
    //   - N/A
    // Returns:
    //   - The size of the list of tweets which tells the user how many tweets 
    //     are currently in the list of tweets
    // Exceptions:
    //   - N/A
    public int numTweets() {
        return this.tweets.size();
    }

    // Behavior: 
    //   - This method allows a user to add a tweet to the list of tweets.
    // Parameters:
    //   - String tweet: The tweet to be added to the list of tweets
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public void addTweet(String tweet) {
        this.tweets.add(tweet);
    }

    // Behavior: 
    //   - This method returns the next tweet in the list of current tweets.  
    //     If all the tweets in the list are exhausted, this method cycles
    //     around to the start of the list.
    // Parameters:
    //   - N/A
    // Returns:
    //   - next tweet in the list of tweets
    // Exceptions:
    //   - N/A
    public String nextTweet() {
        if(this.iterator >= tweets.size()) {
            this.iterator = 0;
        }
        String next = this.tweets.get(iterator);
        this.iterator ++;
        return next;   
    }
    
    // Behavior: 
    //   - This method removes the given tweet from the list of tweets
    //     The remove method preserves the current iteration order of the 
    //     nextTweet method above such that the order in which the user 
    //     accesses tweets remains the same.
    // Parameters:
    //   - String tweet: tweet to be removed from the list of tweets
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public void removeTweet(String tweet) {
        if(this.tweets.contains(tweet)) {
            if(this.tweets.indexOf(tweet) < iterator && iterator > 0) {
                this.iterator --;
            }   
            this.tweets.remove(tweet);        
        }    
    }
}  