// Ananya Soni 
// 01/28/2024
// CSE 123
// C1: Abstract Startegy Game 
// TA: Lainey
// This class represents a game of connect four that implements the 
// AbstractStrategyGame interface. A user can play a game of connect
// four using this class.
import java.util.*;

public class ConnectFour implements AbstractStrategyGame {

    public static final String PLAYER1 = "🔴";
    public static final String PLAYER2 = "🔵";
    private boolean isPlayer1Turn;
    private String[][] board;

    // Behavior: 
    //   - This constructor constructs a new ConnectFour game 
    // Parameters:
    //   - N/A
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A                              
    public ConnectFour() {
        board = new String[][]{{"__", "__", "__", "__", "__", "__", "__"},
                               {"__", "__", "__", "__", "__", "__", "__"},
                               {"__", "__", "__", "__", "__", "__", "__"},
                               {"__", "__", "__", "__", "__", "__", "__"},
                               {"__", "__", "__", "__", "__", "__", "__"},
                               {"__", "__", "__", "__", "__", "__", "__"}};
        isPlayer1Turn = true;
    }

    // Behavior: 
    //   - This method returns a String of instructions describing how to play
    //     ConnectFour, how to make a move, how the game ends, and how to win or tie.
    // Parameters:
    //   - N/A
    // Returns:
    //   - String instructions: instructions describing how to play Connect Four 
    // Exceptions:
    //   - N/A   
    public String instructions() {
        String instructions = "Welcome to Connect Four! \n";
        instructions += ("At the start of each turn, the active player may " +
                            "choose to either remove one of their tokens from " +
                                "the board or place a token (but not both).\n");
        instructions += ("The player is able to remove one of their own tokens " +
                            "from the bottom row of any column, shifting all other " + 
                                "tokens in that column (if any) down by one row.\n");
        instructions += ("A player may add a token to any column as long as it is free.\n");
        instructions += ("Whichever player gets 4 consecutive tokens in a row, column, or " +
                            "diagonally wins!\n");
        instructions += ("If both players win at the same time or the board fills up with no " +
                            "winners there is a tie.\n");
        return instructions;
    }

    // Behavior: 
    //   - This method returns a String representation of the currect Connect Four 
    //     game state. The String representation shows the Connect Four Board with
    //     all the tokens from Player1 and Player2 currently in it. 
    // Parameters:
    //   - N/A
    // Returns:
    //   - String gameBoard: Connect Four Board representing current game state
    //     with all tokens representing all the moves played by the players 
    //     up to the current game state 
    // Exceptions:
    //   - N/A   
    public String toString() {
        String gameBoard = "________________________";
        gameBoard += "\n";
        for(int rows = 0; rows < board.length; rows++) {
            gameBoard += "| ";
            for(int columns = 0; columns < board[0].length; columns++) {
                gameBoard += board[rows][columns];
                gameBoard += " ";
            }
            gameBoard += "|";
            gameBoard += "\n";
        }
        gameBoard += "   0  1  2  3  4  5  6";
        gameBoard += "\n";
        return gameBoard;
    }
    // Behavior: 
    //   - This method returns whether or not the game is over.
    // Parameters:
    //   - N/A
    // Returns:
    //   - boolean: returns whether or not the game is over.
    // Exceptions:
    //   - N/A 
    public boolean isGameOver() {
        return (this.getWinner() >= 0);
    }

    // Behavior: 
    //   - This method returns the index of the winner of the game. 1 if 
    //     player 1 (🔴), 2 if player 2 (🔵), 0 if a tie occured, and -1
    //    if the game is still not over.
    // Parameters:
    //   - N/A
    // Returns:
    //   - int: returns the index of the winner of the game.
    // Exceptions:
    //   - N/A 
    public int getWinner() {
        //both players 1 and 2 won (tie)
        if(checkIfWon(1) && checkIfWon(2)) {
            return 0;
        //player 1 won
        } else if(checkIfWon(1)) {
            return 1;
        //player 2 won
        } else if(checkIfWon(2)) {
            return 2;
        }
        //board still has empty slots (game not over)
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[col].length; col++) {
                if (board[row][col].equals("__")) {
                    return -1;
                }
            }
        }
        //board filled and no one has won, so tie!
        return 0;
        
    }
    
    // Behavior: 
    //   - This method returns the index of which player's turn it is. 1 if 
    //     player 1 (🔴), 2 if player 2 (🔵), and -1
    //    if the game is still not over.
    // Parameters:
    //   - N/A
    // Returns:
    //   - int: returns the index of which player's turn it is.
    // Exceptions:
    //   - N/A 
    public int getNextPlayer() {
        if(this.isGameOver()) {
            return -1;
        } else if(isPlayer1Turn) {
            return 1;
        }
        return 2;
    }

    // Behavior: 
    //   - This method allows the current player (player1 (🔴) or player2 (🔵)) to decide
    //     whether they want to add or remove (by entering A or R case-insensitively into 
    //     the console) their token from a column they specify. They can only do one or the 
    //     other. When adding or removing a token this method reads the user input from the 
    //     console to see which column the user wants to add or remove a token from. 
    //     When removing a token --> if the bottom row of the column specified does not contain
    //     the current player's token, or if the column does not exist in the Connect Four board
    //     (outside of the range [0, 6]) an IllegalArguementException is thrown 
    //     When adding a token --> if the column specified is already filled, or if the column 
    //     does not exist in the Connect Four board (outside of the range [0, 6]) 
    //     this method throws an IllegalArguementException. 
    // Parameters:
    //   - Scanner input: used to read user input from the console
    // Returns:
    //   - N/A
    // Exceptions:
    //   - IllegalArguementException: When removing a token --> if the bottom row of the column 
    //     specified does not contain the current player's token, or if the column does not exist 
    //     in the Connect Four board (outside of the range [0, 6]) an IllegalArguementException  
    //     is thrown. When adding a token --> if the column specified is already filled, or 
    //     if the column does not exist in the Connect Four board (outside of the range [0, 6]) 
    //     this method throws an IllegalArguementException. 
    public void makeMove(Scanner input) {
        String answer = "";
        //give user two options: they can either remove a token or add a token
        //by entering R or A case-insensitively 
        while(!answer.equalsIgnoreCase("A") && !answer.equalsIgnoreCase("R")) {
            System.out.print("Would you like to remove a token or add a token? (R/A) ");
            answer = input.next();
            if(!answer.equalsIgnoreCase("A") && !answer.equalsIgnoreCase("R")) {
                System.out.println("Unknown Output! Please try again");
            }
        }
        //when the user enters R to remove their token 
        if(answer.equalsIgnoreCase("R")) {
            System.out.print("Column to remove from?: ");
            int column = input.nextInt();
            String targetToken = "";
            if(isPlayer1Turn) {
                targetToken = PLAYER1;
                isPlayer1Turn = false;
            } else {
                targetToken = PLAYER2;  
                isPlayer1Turn = true;
            }
            if(!board[board.length - 1][column].equals(targetToken) || column < 0 || 
                column >= board[0].length) {
                throw new IllegalArgumentException("Can't remove from here!");
            }
            for(int row = board.length - 1 ; row > 0; row--) {
                board[row][column] = board[row - 1][column];
            }
        //when the user enters A to add their token 
        } else if(answer.equalsIgnoreCase("A")) {
            System.out.print("Column to add token?: ");
            int column = input.nextInt();
            if(board[0][column].equals(PLAYER1)|| board[0][column].equals(PLAYER2) || 
                column < 0 || column >= board[0].length) {
                throw new IllegalArgumentException("Can't add token here!");
            }
            boolean foundSpot = false;
            for(int row = board.length - 1; row >= 0 && !foundSpot; row--) {
                if(board[row][column].equals("__")) {
                    foundSpot = true;
                    if(isPlayer1Turn) {
                        board[row][column] = PLAYER1;
                        isPlayer1Turn = false;
                    } else {
                        board[row][column] = PLAYER2;
                        isPlayer1Turn = true;
                    }
                }
            }
        }
    }

    // Behavior: 
    //   - This helper method checks whether the specified player has won. Winning is 
    //     defined as the specified player having 4 consecutive token (player1 (🔴), 
    //     player2 (🔵)) either in a row, column, or diagonal. 
    // Parameters:
    //   - int player: player index (1 for player1 and 2 for player2)
    // Returns:
    //   - boolean: returns true if the specified player has won and false otherwise
    // Exceptions:
    //   - N/A 
    private boolean checkIfWon(int player) {
        //targetToken = token of specified player
        String targetToken = "";
        if(player == 1) {
            targetToken = PLAYER1;
        } else {
            targetToken = PLAYER2;  
        }
        //check all columns first as soon as you see 4 consecutive tokens 
        //matching the specified player's token then return true
        int tokenCount = 0;
        for(int col = 0; col < board[0].length; col++) {
            tokenCount = 0;
            for(int row = board.length - 1; row >= 0; row--) {
                String nextToken = board[row][col];
                if(nextToken.equals(targetToken)) {
                    tokenCount++;
                    if(tokenCount >= 4) {
                        return true;
                    }
                } else {
                    tokenCount = 0;
                }
            }
        }
        //next check all rows, as soon as you see 4 consecutive tokens 
        //matching the specified player's token then return true
        tokenCount = 0;
        for(int row = 0; row < board.length; row++) {
            tokenCount = 0;
            for(int col = 0; col < board[0].length; col++) {
                String nextToken = board[row][col];
                if(nextToken.equals(targetToken)) {
                    tokenCount++;
                    if(tokenCount >= 4) {
                        return true;
                    }
                } else {
                    tokenCount = 0;
                }
            }
        }

        //next check all diagonals (in all 4 directions), as soon as you see 4 consecutive
        //tokens matching the specified player's token then return true 

        //check diagonals from left-side of the grid down toward the right-side (↳)
        if(checkDownwardsDiagonal(targetToken, 0) == true) {
            return true;
        } 
        //check diagonals from right-side of the grid down toward the left-side (↲)
        if(checkDownwardsDiagonal(targetToken, board[0].length - 1) == true) {
            return true;
        } 
        //check diagonals from left-side of the grid up toward the right-side (↱)
        if(checkUpwardsDiagonal(targetToken, 0) == true) {
            return true;
        }
        //check diagonals from right-side of the grid up toward the left-side (↰)
        if(checkUpwardsDiagonal(targetToken, board[0].length - 1) == true) {
            return true;
        }
        //4 consecutive tokens for specified player were not found 
        return false;
    }

    // Behavior: 
    //   - This helper method checks whether a specified player has 4 consecutive tokens 
    //     in the upward facing diagonals of the Connect Four board (↖ or ↗). 
    // Parameters:
    //   - String targetToken: token of the specified player (player1 (🔴), player2 (🔵))
    //   - int initialCol: tells this method which diagonal to check 
    //     (left-to-right or right-to-left)
    // Returns:
    //   - boolean: returns true if the specified player has 4 consecutive tokens in the 
    //     upward facing diagonal of the Connect Four board (↖ or ↗) and false otherwise.
    // Exceptions:
    //   - N/A 
    private boolean checkUpwardsDiagonal(String targetToken, int initialCol) {
        int tokenCount = 0;
        for(int row = board.length - 1; row >= 0; row--) {
            int col = initialCol;
            tokenCount = 0;
            for(int i = row; i >= 0; i--) {
                String nextToken = board[i][col];
                if(nextToken.equals(targetToken)) {
                    tokenCount++;
                    if(tokenCount >= 4) {
                        return true;
                    }
                } else {
                    tokenCount = 0;
                }
                if(initialCol == board[0].length - 1) {
                    col--;
                } else {
                    col++;
                }            
            }
        } 
        return false;
    }

    // Behavior: 
    //   - This helper method checks whether a specified player has 4 consecutive  
    //     tokens in the downward facing diagonals of the Connect Four board (↙ or ↘). 
    // Parameters:
    //   - String targetToken: token of the specified player (player1 (🔴), player2 (🔵))
    //   - int initialCol: tells this method which diagonal to check 
    //     (left-to-right or right-to-left)
    // Returns:
    //   - boolean: returns true if the specified player has 4 consecutive tokens in the 
    //     downward facing diagonal of the Connect Four board (↙ or ↘) and false otherwise.
    // Exceptions:
    //   - N/A 
    private boolean checkDownwardsDiagonal(String targetToken, int initialCol) {
        int tokenCount = 0;
        for(int row = 0; row < board.length; row++) {
            tokenCount = 0;
            int col = initialCol;
            for(int i = row; i < board.length; i++) {
                String nextToken = board[i][col];
                if(nextToken.equals(targetToken)) {
                    tokenCount++;
                    if(tokenCount >= 4) {
                        return true;
                    }
                } else {
                    tokenCount = 0;
                }
                if(initialCol == board[0].length - 1) {
                    col--;
                } else {
                    col++;
                }   
            }
        }  
        return false;
    }
}