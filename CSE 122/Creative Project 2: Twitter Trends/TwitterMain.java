// Ananya Soni 
// 11/11/2023
// CSE 122 
// C2: Twitter Trends
// Client program that uses the TwitterTrends and TweetBot objects.
// This class displays the analysis of the tweet trends including
// the most frequently occuring word and least frequently occuring word 
// in the list of tweets contained by the TweetBot. 
import java.util.*;
import java.io.*;

public class TwitterMain {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("tweets.txt")); // Make Scanner over tweet file
        List<String> tweets = new ArrayList<>();
        while (input.hasNextLine()) { // Add each tweet in file to List
            tweets.add(input.nextLine());
        }

        TweetBot bot = new TweetBot(tweets); // Create TweetBot object with list of tweets
        TwitterTrends trends = new TwitterTrends(bot); // Create TwitterTrends object

        // TODO: Call and display results from getMostFrequentWord and your
        // creative method here
        System.out.println("Most Trending Word: " + trends.getMostFrequentWord());
        System.out.println("Least Trending Word: " + trends.getLeastFrequentWord());
    }
}