// Ananya Soni 
// 01/10/2024
// CSE 123
// P0: Warmup/Review
// This class represents a Book and allows a user
// to add a rating to a Book, see the authors of a 
// Book, get the title of a Book, get the number 
// of ratings from a Book, and get the average rating 
// of a Book.
import java.util.*;

public class Book implements Media {

    private String title;
    private List<String> authors;
    private int numRating;
    private double totalRating;

    // Behavior: 
    //   - This constructor creates a Book with the provided title and
    //     single author
    // Parameters:
    //   - String title: title of Book
    //   - String author: author of Book
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A 
    public Book(String title, String author) {
        this.title = title;
        this.authors = new ArrayList<String>();
        this.authors.add(author);  
        this.numRating = 0;
        this.totalRating = 0;
    }

    // Behavior: 
    //   - This constructor creates a Book with the provided title and
    //     multiple authors
    // Parameters:
    //   - String title: title of Book
    //   - List<String> author: list of multiple authors of Book
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A 
    public Book(String title, List<String> authors) {
        this.title = title;
        this.authors = new ArrayList<String>();
        this.authors.addAll(authors);
        this.numRating = 0;
        this.totalRating = 0;
    }

    // Behavior: 
    //   - This method gets the title of Book
    // Parameters:
    //   - N/A
    // Returns:
    //   - The title of the Book
    // Exceptions:
    //   - N/A 
    public String getTitle() {
        return this.title;
    }

    // Behavior: 
    //   - This method gets the authors associated 
    //     with this Book
    // Parameters:
    //   - N/A
    // Returns:
    //   - List<String> artists: the list of authors of this Book
    // Exceptions:
    //   - N/A 
    public List<String> getArtists() {
        List<String> artists = new ArrayList<>();
        artists.addAll(this.authors);
        return artists;
    }

    // Behavior: 
    //   - This method adds a rating to this Book based on provided
    //     rating. 
    // Parameters:
    //   - int score: non-negative score for new rating to Book
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A 
    public void addRating(int score) {
        this.totalRating += score;
        this.numRating++;
    }

    // Behavior: 
    //   - This method gets the number of times this Book has 
    //     been rated. 
    // Parameters:
    //   - N/A
    // Returns:
    //   - the number of ratings made for this Book
    // Exceptions:
    //   - N/A 
    public int getNumRatings() {
        return this.numRating;
    }

    // Behavior: 
    //   - This method gets the average (mean) of all ratings for 
    //     this Book.
    // Parameters:
    //   - N/A
    // Returns:
    //   - the average (mean) of all ratings for this Book. If no 
    //     ratings exist, this method returns 0.
    // Exceptions:
    //   - N/A 
    public double getAverageRating() {
        if(this.numRating == 0) {
            return 0;
        }
        //can't do 0/0 which is why we need the if statement
        return (totalRating / (double) numRating); 
    }

    // Behavior: 
    //   - This method produces a readable string representation of this Book.
    //     If the book has zero ratings, the string representation is in the form:
    //     <name> by [<authors>]: No ratings yet! If the book has at least one review,
    //     the string representation is in the form: <name> by [<authors>]: <average rating> 
    //     (<num ratings> ratings). note: The average ratng is rounded to at most two decimal 
    //     places.
    // Parameters:
    //   - N/A
    // Returns:
    //   - string representation of Book
    // Exceptions:
    //   - N/A 
    public String toString() {
        if(this.numRating == 0) {
            return(this.title + " by " + this.authors + ": No ratings yet!");
        } else {            
            return(this.title + " by " + this.authors + ": " + String.format("%.2f", this.getAverageRating())  + " (" + this.numRating + " ratings)");
        }
    }
}
