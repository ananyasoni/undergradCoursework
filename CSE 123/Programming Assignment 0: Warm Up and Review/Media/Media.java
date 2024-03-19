import java.util.*;

/**
 * An interface to represent various types of media (movies, books, tv shows, songs, etc.).
 */
public interface Media {
    /**
     * Gets the title of this media.
     *
     * @return      The title of this media.
     */
    public String getTitle();

    /**
     * Gets all artists associated with this media.
     *
     * @return      A list of artists for this media.
     */
    public List<String> getArtists();

    /**
     * Adds a rating to this media.
     *
     * @param score     The score for the new rating. Should be non-negative.
     */
    public void addRating(int score);

    /**
     * Gets the number of times this media has been rated.
     *
     * @return      The number of ratings for this media.
     */
    public int getNumRatings();

    /**
     * Gets the average (mean) of all ratings for this media.
     *
     * @return      The average (mean) of all ratings for this media. If no ratings exist, returns 0.
     */
    public double getAverageRating();
}
