// Ananya Soni 
// 02/07/2024
// CSE 123
// P1: Mini-Git
// TA: Lainey
// This class represents a Repository and allows users
// to track changes they have made. A user can create a 
// Repository, create a commit within their Repository, 
// get their most recent Commit, see their history, drop a 
// Commit from their Repository and merge two Repository's while
// maintaining the reverse chronological order of the Commit history.
import java.util.*;
import java.text.SimpleDateFormat;

public class Repository {

    private String name;
    private Commit repoHead;
    private int numCommits;
    
    
    // Behavior: 
    //   - This constructor constructs a new Repository
    // Parameters:
    //   - String name: name of the Repository
    // Returns:
    //   - N/A
    // Exceptions:
    //   - IllegalArgumentException: throws new IllegalArgumentException 
    //     if the given name is null or empty   
    public Repository(String name) {
        if(name == null || name.length() == 0) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.numCommits = 0;
        this.repoHead = null;
    }

    // Behavior: 
    //   - This method returns the id of the most 
    //     recent Commit. If there is no Commits in 
    //     the Repository this method returns null.
    // Parameters:
    //   - N/A
    // Returns:
    //   - String id: returns null if there are no Commits
    //     in this repository and if there are Commits then 
    //     returns the id of the most recent Commit made to this
    //     Repository
    // Exceptions:
    //   - N/A   
    public String getRepoHead() {
        if(this.repoHead == null) {
            return null;
        }
        return repoHead.id;
    }

    // Behavior: 
    //   - This method returns the number of Commits 
    //     made to this Repository
    // Parameters:
    //   - N/A
    // Returns:
    //   - int: the number of Commits in this Repository
    // Exceptions:
    //   - N/A   
    public int getRepoSize() {
        return this.numCommits;
    }

    // Behavior: 
    //   - This method returns a String reperesentation of
    //     this Repository in the following format: 
    //     <name> - Current head: <head> where head is the 
    //     String representation of the most recent Commit made to 
    //     this Repository. If there are no commits in this 
    //     repository, instead return <name> - No commits
    // Parameters:
    //   - N/A
    // Returns:
    //   - String representation of this Repository 
    // Exceptions:
    //   - N/A   
    @Override 
    public String toString() {
        if(this.numCommits == 0 || this.repoHead == null) {
            //<name> - No commits
            return (this.name + " - No commits");
        }
        //<name> - Current head: <head>
        return (this.name + " - Current head: " + repoHead.toString());

    }

    // Behavior: 
    //   - This method return true if the Commit with ID targetId 
    //     is in the Repository, false if not. 
    // Parameters:
    //   - N/A
    // Returns:
    //   - boolean: return true if the Commit with ID targetId 
    //     is in the Repository, false if not. 
    // Exceptions:
    //   - N/A   
    public boolean contains(String targetId) {
        Commit curr = this.repoHead;
        while(curr != null) {
            if(curr.id.equals(targetId)) {
                return true;
            }
            curr = curr.past;
        }
        return false;
    }

    // Behavior: 
    //   - Return a String representation of the most recent n Commits in 
    //     this repository, with the most recent first.  
    //     * If there are fewer than n Commits in this Repository, 
    //       this method return them all.
    //     * If there are no Commits in this Repository, this method returns 
    //       an empty string.
    //     * If n is non-positive, throw an IllegalArgumentException
    // Parameters:
    //   - int n: number of Commits this method returns a String 
    //     representation of 
    // Returns:
    //   - String history: history of n Commits 
    // Exceptions:
    //   - IllegalArgumentException: this method throws a new IllegalArgumentException
    //     if n is less than or equal to 0
    public String getHistory(int n) {
        if(n <= 0) {
            throw new IllegalArgumentException();
        }
        String history = "";
        Commit curr = this.repoHead;
        while(curr != null && n > 0) {
            history += (curr.toString() + "\n");
            curr = curr.past;
            n--;
        }
        return history;
    }

    // Behavior: 
    //   - This method creates a new Commit with the given message
    //     in this Repository. 
    // Parameters:
    //   - String message: message for the Commit 
    // Returns:
    //   - String id: returns the id of the most recent
    //     Commit made to this Repository
    // Exceptions:
    //   - N/A   
    public String commit(String message) {
        this.repoHead = new Commit(message, this.repoHead);
        this.numCommits++;
        return this.repoHead.id;
    }

    // Behavior: 
    //   - This method removes the commit with the id targetId from this Repository, 
    //     maintaining the rest of the history. This method returns true if the commit 
    //     was successfully dropped, and false if there is no commit that matches the  
    //     given id in the Repository.
    // Parameters:
    //   - N/A
    // Returns:
    //   - boolean: return true if the Commit with id targetId 
    //     is successfully removed from this Repository and false
    //     otherwise.
    // Exceptions:
    //   - N/A   
    public boolean drop(String targetId) {
        //case where Repository contains no 
        //commits 
        if(repoHead == null) {
            return false;
        } 
        Commit curr = this.repoHead;
        //case where targetId is in the commit repoHead 
        //is currently referencing 
        if(repoHead.id.equals(targetId)) {
            this.repoHead = this.repoHead.past;
            this.numCommits--;
            return true;
        }
        //case where targetId is in the commit 
        //in the middle or end of the list of all
        //commits
        while(curr.past != null) {
            if(curr.past.id.equals(targetId)) {
                curr.past = curr.past.past;
                this.numCommits--;
                return true;
            }
            curr = curr.past;
        }
        //did not find targetId 
        return false;
    }

    // Behavior: 
    //   - This method takes all the Commits in the other Repository and moves 
    //     them into this Repository, combining the two Repository histories such 
    //     that chronological order is preserved. That is, after executing this method, 
    //     this Repository contains all Commits that were from this Repository and the 
    //     other Repository, and the Commits are order from most recent to least recent.
    //     If the other repository is empty, this Repository remains unchanged.
    //     If this repository is empty, all Commits in the other Repository are moved 
    //     by this method to this Repository.
    // Parameters:
    //   - Repository other: the other Respository that is merged to this Repository
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A   
    public void synchronize(Repository other) {
        //case where this Repository has no 
        //commits 
        this.numCommits += other.numCommits;
        other.numCommits = 0;
        if(this.repoHead == null || this.numCommits == 0) {
            this.repoHead = other.repoHead;
            other.repoHead = null;
        } else if(other.repoHead != null) {
            Commit curr1 = this.repoHead;
            Commit curr2 = other.repoHead;
            //head case:
            if(other.repoHead.timeStamp > this.repoHead.timeStamp) {
                Commit temp2 = other.repoHead;
                //keep moving reference to Commits in the other Repository such that we land on the 
                //last consecutive Commit with a timeStamp greater than the timeStamp the Commit 
                //head1 is referencing
                while(temp2 != null && temp2.past != null && temp2.timeStamp > curr1.timeStamp 
                      && temp2.past.timeStamp > curr1.timeStamp) {
                    temp2 = temp2.past;
                }
                this.repoHead = other.repoHead;
                other.repoHead = temp2.past;
                temp2.past = curr1;
            }
            //middle or end case
            curr1 = this.repoHead;
            curr2 = other.repoHead;
            while(curr1 != null && curr2 != null && curr1.past != null) {
                if(curr2.timeStamp > curr1.past.timeStamp) {
                    Commit temp1 = curr1.past;
                    Commit temp2 = curr2;
                    while(temp2 != null && temp2.past != null && temp2.timeStamp > 
                          curr1.past.timeStamp && temp2.past.timeStamp > curr1.past.timeStamp) {
                        temp2 = temp2.past;
                    }
                    curr1.past = other.repoHead;
                    other.repoHead = temp2.past;
                    temp2.past = temp1;
                    curr2 = other.repoHead;
                    curr1 = temp1;
                } else {
                    curr1 = curr1.past;
                }
            }
            //Commits still remaining in the other Repository
            if(other.repoHead != null) {
                curr1.past = other.repoHead;
            }
            other.repoHead = null;
        }
    }

    /*
     * A class that represents a single commit in the repository.
     * Commits are characterized by an identifier, a commit message,
     * and the time that the commit was made. A commit also stores
     * a reference to the immediately previous commit if it exists.
     *
     * Staff Note: You may notice that the comments in this 
     * class openly mention the fields of the class. This is fine 
     * because the fields of the Commit class are public. In general, 
     * be careful about revealing implementation details!
     */
    public class Commit {

        private static int currentCommitID;

        /**
         * The time, in milliseconds, at which this commit was created.
         */
        public final long timeStamp;

        /**
         * A unique identifier for this commit.
         */
        public final String id;

        /**
         * A message describing the changes made in this commit.
         */
        public final String message;

        /**
         * A reference to the previous commit, if it exists. Otherwise, null.
         */
        public Commit past;

        /**
         * Constructs a commit object. The unique identifier and timestamp
         * are automatically generated.
         * @param message A message describing the changes made in this commit.
         * @param past A reference to the commit made immediately before this
         *             commit.
         */
        public Commit(String message, Commit past) {
            this.id = "" + currentCommitID++;
            this.message = message;
            this.timeStamp = System.currentTimeMillis();
            this.past = past;
        }

        /**
         * Constructs a commit object with no previous commit. The unique
         * identifier and timestamp are automatically generated.
         * @param message A message describing the changes made in this commit.
         */
        public Commit(String message) {
            this(message, null);
        }

        /**
         * Returns a string representation of this commit. The string
         * representation consists of this commit's unique identifier,
         * timestamp, and message, in the following form:
         *      "[identifier] at [timestamp]: [message]"
         * @return The string representation of this collection.
         */
        @Override
        public String toString() {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(timeStamp);

            return id + " at " + formatter.format(date) + ": " + message;
        }

        /**
        * Resets the IDs of the commit nodes such that they reset to 0.
        * Primarily for testing purposes.
        */
        public static void resetIds() {
            Commit.currentCommitID = 0;
        }
    }
}
