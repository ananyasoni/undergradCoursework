// Ananya Soni 
// 10/19/2023
// CSE 122 
// P1: Music Playlist
// TA: Kevin
// This class allows a user to create a functioning Music Playlist. A user can add songs to the
// Music Playlist, play a song, print history of songs played, clear history of songs played, 
// delete songs from recent history or beginning of history, and end the program by entering
// "Q" case insensitively. 
import java.util.*;
import java.io.*;

public class MusicPlaylist {
    public static void main(String[] args) {
        System.out.println("Welcome to the CSE 122 Music Playlist!");
        Queue<String> musicPlaylist = new LinkedList<String>();
        Stack<String> history = new Stack<String>();
        Scanner input = new Scanner(System.in);
        String userCommand = "";
        while(!(userCommand.equalsIgnoreCase("Q"))) {
            userCommand = getUserCommand(input);
            if(userCommand.equalsIgnoreCase("A")) {
                addSong(musicPlaylist, input);    
            } 
            else if(userCommand.equalsIgnoreCase("P")) {
                playSong(musicPlaylist, history);
            } 
            else if(userCommand.equalsIgnoreCase("PR")) {
                printHistory(history);
            } 
            else if(userCommand.equalsIgnoreCase("C")) {
                history.clear();
            }
            else if(userCommand.equalsIgnoreCase("D")) {
                deleteHistory(history, input);
            } else {
                System.out.println();
            }  
        }
    }

    // Behavior: 
    //   - This method prints the prompt that asks the user what they would like
    //     to do and then returns the users command (what the user enters in the console)
    // Parameters:
    //   - Scanner console: allows this method to read user input
    // Returns:
    //   - String userCommand: What the user enters in the console
    // Exceptions:
    //   - N/A
    public static String getUserCommand(Scanner console) {
            System.out.println("(A) Add song");
            System.out.println("(P) Play song");
            System.out.println("(Pr) Print history");
            System.out.println("(C) Clear history");
            System.out.println("(D) Delete from history");
            System.out.println("(Q) Quit");
            System.out.println();
            System.out.print("Enter your choice: ");
            String userCommand = console.nextLine();
            return userCommand;
    }

    // Behavior: 
    //   - This method prompts users to add a song to their playlist via the console. Once added,
    //     this method prints out the song added to the playlist.
    // Parameters:
    //   - Queue<String> musicPlaylist: A Queue that stores the songs the user 
    //     adds to their playlist.
    //   - Scanner console: Allows the method to read user input to store the 
    //     title of the song
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public static void addSong(Queue<String> musicPlaylist, Scanner console) {
        System.out.print("Enter song name: ");
        String song = console.nextLine();
        System.out.println("Successfully added " + song);
        musicPlaylist.add(song);
        System.out.println();
        System.out.println();
    }

    // Behavior: 
    //   - This method allows the user to "play" a song by printing it in the console. Once
    //     a song is "played" it is removed from the playlist and added to history.
    // Parameters:
    //   - Queue<String> musicPlaylist: A Queue that stores the user's 
    //     music playlist.
    //   - Stack<String> history: A Stack that stores the songs the user has
    //     "played."
    // Returns:
    //   - N/A
    // Exceptions:
    //   - IllegalStateException -> this method throws an IllegalStateException
    //     when the musicPlaylist is empty
    public static void playSong(Queue<String> musicPlaylist, Stack<String> history) {
        if(musicPlaylist.isEmpty()) {
            throw new IllegalStateException();
        }
        String currentSong = musicPlaylist.remove();
        System.out.println("Playing song: " + currentSong);
        history.push(currentSong);
        System.out.println();
        System.out.println();
    }

    // Behavior: 
    //   - This method prints the history of songs the user has "played."
    // Parameters:
    //   - Stack<String> history: A Stack that stores the songs the user has
    //     "played."
    // Returns:
    //   - N/A
    // Exceptions:
    //   - IllegalStateException -> this method throws an IllegalStateException
    //     when history is empty. In other words when the user hasn't "played"
    //     any songs or they cleared their history of songs this exception is thrown.
    public static void printHistory(Stack<String> history) {
        if(history.isEmpty()) {
            throw new IllegalStateException();
        }
        Stack<String> auxiliaryStack = new Stack<String>();
        int originalHistorySize = history.size();
        for(int i = 0; i < originalHistorySize; i++) {
            String nextSong = history.pop();
            System.out.println("    " + nextSong);
            auxiliaryStack.add(nextSong);
        }
        sToS(auxiliaryStack, history, auxiliaryStack.size());
        System.out.println();
        System.out.println();
    }

    // Behavior: 
    //   - This method allows users to delete (n) songs from history. The user can choose to 
    //     either delete the most recently played songs (n > 0), or the songs that were played  
    //     earliest in the history (n < 0). If the user enters 0 (n = 0) no songs are 
    //     deleted.
    // Parameters:
    //   - Stack<String> history: A Stack that stores the songs the user has
    //     "played."
    //   - Scanner console: Allows the method to read user input. This allows the 
    //     method to read the number of songs (n) the user wants to delete.
    // Returns:
    //   - N/A
    // Exceptions:
    //   - IllegalArgumentException -> this method throws an IllegalArgumentException
    //     if the size of the history is less than the absolute value of n, where n is the number
    //     of songs the user wants to delete.
    public static void deleteHistory(Stack<String> history, Scanner console) {
        System.out.println("A positive number will delete from recent history.");
        System.out.println("A negative number will delete from the beginning of history.");
        System.out.print("Enter number of songs to delete: ");
        String input = console.nextLine();
        int n = Integer.parseInt(input);
        if(history.size() < Math.abs(n)) {
            throw new IllegalArgumentException();
        }
        if(n > 0) {
            for(int i = 0; i < n; i++) {
                history.pop();
            }
        } else if(n < 0) {
            Stack<String> auxiliaryStack = new Stack<String>();
            int originalHistorySize = history.size();
            sToS(history, auxiliaryStack, (originalHistorySize - Math.abs(n)));
            history.clear();
            sToS(auxiliaryStack, history, auxiliaryStack.size());
        }
    }

    // Behavior: 
    //   - This helper method transfers a specified amount of elements 
    //     from one stack (stack1) to another stack (stack2).
    // Parameters:
    //   - Stack<String> stack1: contains elements of type String we want to transfer
    //     to stack2
    //   - Stack<String> stack2: where we want to transfer elements to from stack1
    //   - int numElementsToMove: specifies the number of elements the method moves 
    //     from stack1 to stack2
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public static void sToS(Stack<String> stack1, Stack<String> stack2, int numElementsToMove) {
        for(int i = 0; i < numElementsToMove; i++) {
            stack2.push(stack1.pop());
        }
    }
}