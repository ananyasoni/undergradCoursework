// Ananya Soni 
// 03/06/2024
// CSE 123
// C3: BrettFeed Quiz
// TA: Lainey
// This class represents a buzz-feed style quiz. This class 
// allows a user to create a quiz from a file, take this quiz, 
// export this quiz to a file, add a question to this quiz, 
// and print the percentages of each result in this quiz.  
import java.io.PrintStream;
import java.util.Scanner;

public class QuizTree {

    private QuizTreeNode overallRoot; //root of the QuizTree

    // Behavior: 
    //   - This constructor constructs a new quiz based on the provided input file
    //     with various choices and results in pre-order.
    // Parameters:
    //   - Scanner inputFile: the input used to create this quiz. File Format: 
    //     In the input file, each quiz node is represented by a single line 
    //     containing the text for that quiz node. "Choice" nodes (i.e. 
    //     nodes that represent a choice between two options) are written with the 
    //     two choices separated by a single slash (/) character. For example, red/blue  
    //     represents a choice between red and blue, where red is the "left" choice and 
    //     blue is the "right" choice. "Result" quiz nodes are written as the result 
    //     option prefixed with the text END:. Both kinds of nodes have a dash (-) 
    //     followed by an integer representing the score for that node For example, 
    //     END:froot loops represents a result node for the result "froot loops".
    // Returns: 
    //   - N/A
    // Exceptions:
    //   - N/A 
    public QuizTree(Scanner inputFile) {
        //Constructs a new quiz based on the provided input.
        overallRoot = constructQuizTree(inputFile);
    }

    // Behavior: 
    //   - This helper method helps construct a new quiz based on the provided input file
    //     with various choices and results. Note: the input file respresents a pre-order 
    //     traversal of the resulting quiz tree created by this method.
    // Parameters:
    //   - Scanner inputFile: the input used to create this quiz. File Format: 
    //     In the input file, each quiz node is represented by a single line 
    //     containing the text for that quiz node. "Choice" nodes (i.e. 
    //     nodes that represent a choice between two options) are written with the 
    //     two choices separated by a single slash (/) character. For example, red/blue  
    //     represents a choice between red and blue, where red is the "left" choice and 
    //     blue is the "right" choice. "Result" quiz nodes are written as the result 
    //     option prefixed with the text END:. Both kinds of nodes have a dash (-) 
    //     followed by an integer representing the score for that node For example, 
    //     END:froot loops represents a result node for the result "froot loops".
    // Returns:
    //   - QuizTreeNode: returns the newly created quiz made from the provided 
    //     input file
    // Exceptions:
    //   - N/A   
    private QuizTreeNode constructQuizTree(Scanner inputFile) {
        String nextLine = inputFile.nextLine();
        String[] splitScore = nextLine.split("-");
        int score = Integer.parseInt(splitScore[splitScore.length - 1]);
        //this method creates a new QuizTreeNode in either case: if choice or if result
        //type QuizTreeNode
        QuizTreeNode root = new QuizTreeNode(splitScore[0], score);
        //choice node / branch node / root node ex: red/blue-0
        if(nextLine.contains("/")) {
            root.left = constructQuizTree(inputFile);
            root.right = constructQuizTree(inputFile);
        }
        return root; 
        //always want to return the root such that this method ultimately
        //returns the overall root of the newly created quiz after completing the 
        //first call stack frame
    }

    // Behavior: 
    //   - This method allows a user to take the current quiz using the provided Scanner. 
    //     This method prompts the user to choose between the options in the quiz and 
    //     continues prompting the user to choose between options until a result is reached, 
    //     keeping track of the score as the user continues to take the quiz. When a result is 
    //     reached, the user's result and total score are printed. 
    // Parameters:
    //   - Scanner console: used to process the user's choices in the quiz
    // Returns: 
    //   - N/A
    // Exceptions:
    //   - N/A  
    public void takeQuiz(Scanner console) {
        takeQuiz(overallRoot, 0, console);
    }

    // Behavior: 
    //   - This helper method allows a user to take the current quiz using the provided Scanner. 
    //     This method prompts the user to choose between the options in the quiz and 
    //     continues prompting the user to choose between options until a result is reached, 
    //     keeping track of the score as the user continues to take the quiz. If a user chooses 
    //     the left option this method travels left in this quiz and if the user chooses the right 
    //     option this method travels right in the quiz. When a result is reached, the user's 
    //     result and total score are printed, where total the score is the sum of 
    //     the scores of every single quiz node visited by this method. 
    // Parameters:
    //   - QuizTreeNode curr: keeps track of the user's progress in the quiz and at what 
    //     point they are in the quiz
    //   - int currScore: keeps track of the user's current total score as they progress 
    //     through the quiz
    //   - Scanner console: used to process the user's choices in the quiz
    // Returns: 
    //   - N/A
    // Exceptions:
    //   - N/A  
    private void takeQuiz(QuizTreeNode curr, int currScore, Scanner console) {
        if(curr != null) {
            //choice node, not a result node / leaf node
            //branch node / root node / choice node
            if(curr.right != null || curr.left != null) {
                String[] choices = curr.quizData.split("/");
                String choice1 = choices[0];
                String choice2 = choices[choices.length - 1];
                String consoleInput = "";
                //keep making the user type input into console until they enter a valid 
                //input which is one of the two choices --> choice1 or choice2
                while(!consoleInput.equalsIgnoreCase(choice1) && 
                      !consoleInput.equalsIgnoreCase(choice2)) {
                    System.out.print("Do you prefer " + choice1 + " or " + choice2 + "? ");
                    consoleInput = console.nextLine();
                    if(!consoleInput.equalsIgnoreCase(choice1) && 
                       !consoleInput.equalsIgnoreCase(choice2)) {
                        System.out.println("  Invalid response; try again.");
                    }
                }
                //go left if user chooses choice 1
                if(consoleInput.equalsIgnoreCase(choice1)) {
                    takeQuiz(curr.left, currScore + curr.score, console);
                //go right if the user chooses choice 2
                } else {
                    takeQuiz(curr.right, currScore + curr.score, console);
                }
            //result node
            } else {
                String[] results = curr.quizData.split("END:");
                String result = results[results.length - 1];
                System.out.println("Your result is: " + result);
                System.out.println("Your score is: " + (currScore + curr.score));
            }         
        }
    }

    // Behavior: 
    //   - This method prints the current quiz to the provided output file in pre-order. 
    // Parameters:
    //   - PrintStream outputFile: used to export the quiz to the specified output file. 
    //     File Format: In the output file, each quiz node is represented by a single line 
    //     containing the text for that quiz node. "Choice" nodes (i.e. 
    //     nodes that represent a choice between two options) are written with the 
    //     two choices separated by a single slash (/) character. For example, red/blue  
    //     represents a choice between red and blue, where red is the "left" choice and 
    //     blue is the "right" choice. "Result" quiz nodes are written as the result 
    //     option prefixed with the text END:. Both kinds of nodes have a dash (-) 
    //     followed by an integer representing the score for that node For example, 
    //     END:froot loops represents a result node for the result "froot loops".  
    // Returns: 
    //   - N/A
    // Exceptions:
    //   - N/A 
    public void export(PrintStream outputFile) {
        export(overallRoot, outputFile);
    }

    // Behavior: 
    //   - This helper method prints the current quiz to the provided output file as
    ///    a pre-order traversal of the current QuizTree. 
    // Parameters:
    //   - QuizTreeNode curr: keeps track of what point this method is in the quiz
    //   - PrintStream outputFile: used to export the quiz to the specified output file. 
    //     File Format: In the output file, each quiz node is represented by a single line 
    //     containing the text for that quiz node. "Choice" nodes (i.e. 
    //     nodes that represent a choice between two options) are written with the 
    //     two choices separated by a single slash (/) character. For example, red/blue  
    //     represents a choice between red and blue, where red is the "left" choice and 
    //     blue is the "right" choice. "Result" quiz nodes are written as the result 
    //     option prefixed with the text END:. Both kinds of nodes have a dash (-) 
    //     followed by an integer representing the score for that node For example, 
    //     END:froot loops represents a result node for the result "froot loops".  
    // Returns: 
    //   - N/A
    // Exceptions:
    //   - N/A 
    private void export(QuizTreeNode curr, PrintStream outputFile) {
        if(curr != null) {
            //use pre-order traversal to export quiz
            outputFile.println(curr.quizData + "-" + curr.score);
            export(curr.left, outputFile);
            export(curr.right, outputFile);
        }
    }

    // Behavior: 
    //   - This method adds the provided question to this quiz. It replaces the result 
    //     toReplace with the new question representing a choice between the choices in choices 
    //     leading to leftResult and rightResult respectively. If toReplace is not a possible 
    //     result in this quiz, including if it is a choice rather than a result, this method does 
    //     nothing. note: toReplace is treated case-insensitively by this method. 
    // Parameters:
    //   - String toReplace: the result in the quiz that is replaced by choices case-insesitively
    //   - String choices: the new question that is added to this quiz in the format 
    //     "<left choice>/<right choice>-<score>" where the choice that leads to the left result 
    //     is in place of <left choice>, and the choice that leads to the right result is in place 
    //     of <right choice>, and the score of the new question is in place of <score>. 
    //   - String leftResult: the left result of the new question in the format "<result>-<score>"
    //   - String rightResult: the right result of the new question in the format 
    //     "<result>-<score>"
    // Returns: 
    //   - N/A
    // Exceptions:
    //   - N/A 
    public void addQuestion(String toReplace, String choices, String leftResult, 
                            String rightResult) {
        overallRoot = addQuestion(overallRoot, toReplace, choices, leftResult, rightResult);
    }

    // Behavior: 
    //   - This helper method helps add the provided question to this quiz. It replaces the result 
    //     toReplace with the new question representing a choice between the choices in choices 
    //     leading to leftResult and rightResult respectively. If toReplace is not a possible 
    //     result in this quiz, including if it is a choice rather than a result, this method does 
    //     nothing. note: toReplace is treated case-insensitively by this method. 
    // Parameters:
    //   - QuizTreeNode curr: keeps track of what point this method is in the quiz
    //   - String toReplace: the result in the quiz that is replaced by choices case-insesitively
    //   - String choices: the new question that is added to this quiz in the format 
    //     "<left choice>/<right choice>-<score>" where the choice that leads to the left result 
    //     is in place of <left choice>, and the choice that leads to the right result is in place 
    //     of <right choice>, and the score of the new question is in place of <score>. 
    //   - String leftResult: the left result of the new question in the format 
    //     "<result>-<score>"
    //   - String rightResult: the right result of the new question in the format 
    //     "<result>-<score>"
    // Returns: 
    //   - QuizTreeNode: returns the modified quiz with the added question if toReplace 
    //     was a possible result in this quiz. Otherwise it returns the unmodified quiz.
    // Exceptions:
    //   - N/A    
    private QuizTreeNode addQuestion(QuizTreeNode curr, String toReplace, String choices, 
                                     String leftResult, String rightResult) {
        //keep traversing the tree until we find the result node with the quizData we want to 
        //replace
        if(curr != null) {
            //result node / leaf node
            if(curr.quizData.contains("END:") && curr.left == null && curr.right == null) {
                String nextResult = curr.quizData.substring(4, curr.quizData.length());
                if(nextResult.equalsIgnoreCase(toReplace)) {
                    String[] replacementOptions = choices.split("-");
                    String replacementQuizData = replacementOptions[0];
                    int replacementScore = Integer.parseInt(
                                            replacementOptions[replacementOptions.length - 1]);
                    String[] leftOptions = leftResult.split("-");
                    String leftQuizData = "END:" + leftOptions[0];
                    int leftScore = Integer.parseInt(leftOptions[leftOptions.length - 1]);
                    String[] rightOptions = rightResult.split("-");
                    String rightQuizData = "END:" + rightOptions[0];
                    int rightScore = Integer.parseInt(rightOptions[rightOptions.length - 1]);
                    //replacement left result quiz tree node 
                    QuizTreeNode leftNode = new QuizTreeNode(leftQuizData, leftScore);
                    //replacement right result quiz tree node
                    QuizTreeNode rightNode = new QuizTreeNode(rightQuizData, rightScore);
                    //set curr to be the replacement quiz tree node with the new left result
                    //(leftNode) and the new right result (rightNode)
                    //replacement quiz tree node
                    curr = new QuizTreeNode(replacementQuizData, replacementScore, leftNode, 
                                            rightNode);
                } 
            //not a result node / leaf node so keep recursively calling addQuestion 
            } else {
                curr.left = addQuestion(curr.left, toReplace, choices, leftResult, rightResult);
                curr.right = addQuestion(curr.right, toReplace, choices, leftResult, rightResult);
            } 
        } 
        return curr; 
    }

    // Behavior: 
    //   - This method determines the percentage of the "full score" of the quiz that each result 
    //     of the quiz contains and prints them out in pre-order. In other words, for every 
    ///    possible result, in the quiz, this method computes its total score to reach that result 
    ///    and divides that score by the sum of all scores in the quiz to get the percentage of the 
    //     full score of the quiz that each result covers.
    // Parameters:
    //   - N/A
    // Returns: 
    //   - N/A
    // Exceptions:
    //   - N/A    
    public void creativeExtension() {
        int totalScore = findFullScore(overallRoot);
        printPercentages(overallRoot, 0, totalScore);
    }

    // Behavior: 
    //   - This helper method determines the percentage of the "full score" of the quiz that each 
    //     result of the quiz contains and prints them out in pre-order. In other words, for 
    //     every possible result, in the quiz, this method computes its total score to reach 
    //     that result and divides that score by the sum of all scores in the quiz to get the  
    //     percentage of the full score of the quiz that each result covers.
    // Parameters:
    //   - QuizTreeNode curr: keeps track of what point this method is in the quiz
    //   - int currScore: keeps track of the current total score as this method progresses
    //     through the quiz
    //   - int totalScore: the full score of the whole quiz
    // Returns: 
    //   - N/A
    // Exceptions:
    //   - N/A  
    private void printPercentages(QuizTreeNode curr, int currScore, int totalScore) {
        //keep traversing until we reach leaf node aka the result quiz tree node
        if(curr != null) {
            //reached leaf node / result quiz tree node
            if(curr.left == null && curr.right == null) {
                double percentageOfTotalScore = ((double)((double)currScore + 
                    (double)curr.score) / (double)(totalScore)) * 100.0;
                percentageOfTotalScore = roundTwoPlaces(percentageOfTotalScore);
                String result = curr.quizData.substring(4, curr.quizData.length());
                System.out.println(result + ": " + percentageOfTotalScore + "%");
            } 
            //not a result node / leaf node so keep recursively calling printPercentages
            printPercentages(curr.left, currScore + curr.score, totalScore);
            printPercentages(curr.right, currScore + curr.score, totalScore);
        }
    }

    // Behavior: 
    //   - This helper method computes the "full score" of the quiz or the sum 
    //     of all scores in the quiz and returns it.
    // Parameters:
    //   - QuizTreeNode curr: keeps track of what point this method is in the quiz
    // Returns: 
    //   - int: the sum of all score in the quiz
    // Exceptions:
    //   - N/A  
    private int findFullScore(QuizTreeNode curr) {
        if(curr == null) {
            return 0;
        }
        return curr.score + findFullScore(curr.left) + findFullScore(curr.right); 
    }


    // Returns the given percent rounded to two decimal places.
    private double roundTwoPlaces(double percent) {
        return (double) Math.round(percent * 100) / 100;
    }

    // This class represents a question or result in a buzz-feed style quiz. 
    // This class allows a user to create a question or result. 
    public static class QuizTreeNode {

        public final String quizData;
        public final int score;
        public QuizTreeNode left;
        public QuizTreeNode right;

        // Behavior: 
        //   - This constructor constructs a new quiz node with the provided
        //     question or result and score.
        // Parameters:
        //   - String quizData: a question or result that this quiz node represents
        //   - int score: score alloted to this quiz node 
        // Returns: 
        //   - N/A
        // Exceptions:
        //   - N/A 
        public QuizTreeNode(String quizData, int score) {
            this(quizData, score, null, null);
        }

        // Behavior: 
        //   - This constructor constructs a new quiz node with the provided
        //     question or result, score, left quiz node, and right quiz node.
        // Parameters:
        //   - String quizData: a question or result that this quiz node represents
        //   - int score: score alloted to this quiz node 
        //   - QuizTreeNode left: left result or choice quiz node
        //   - QuizTreeNode right: right result or choice quiz node
        // Returns: 
        //   - N/A
        // Exceptions:
        //   - N/A 
        public QuizTreeNode(String quizData, int score, QuizTreeNode left, QuizTreeNode right) {
            this.quizData = quizData;
            this.left = left;
            this.right = right;
            this.score = score;
        }
    }
}