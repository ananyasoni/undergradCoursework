import java.util.*;
import java.io.*;

public class Client {

   // public static void main(String args[]) throws FileNotFoundException {
   //    QuizTree quiz = new QuizTree(new Scanner(new File("simple.txt")));
   //    quiz.export(new PrintStream(new File("output.txt")));
   
   // }
   
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);

        System.out.print("Enter quiz file to read: ");
        String inFileName = console.nextLine();
        File inFile = new File(inFileName);
        while (!inFile.exists()) {
            System.out.println("  File does not exist. Please try again.");
            System.out.print("Enter quiz file to read: ");
            inFileName = console.nextLine();
            inFile = new File(inFileName);
        }

        QuizTree quiz = new QuizTree(new Scanner(inFile));
        System.out.println("Quiz created!");
        System.out.println();

        String option = "";
        while (!option.equalsIgnoreCase("quit")) {
            option = menu(console);
            System.out.println();

            if (option.equalsIgnoreCase("take")) {
                quiz.takeQuiz(console);
                System.out.println();
            } else if (option.equalsIgnoreCase("creative")) {
                quiz.creativeExtension(); // Update with any parameters you need!
                System.out.println();
            } else if (option.equalsIgnoreCase("export")) {
                System.out.print("Enter file to export to: ");
                String outFileName = console.nextLine();
                PrintStream outFile = new PrintStream(new File(outFileName));
                quiz.export(outFile);
                System.out.println("Quiz exported!");
                System.out.println();
            } else if (option.equalsIgnoreCase("add")) {
                addQ(console, quiz);
                System.out.println();
            } else if (!option.equalsIgnoreCase("quit")) {
                System.out.println("  Invalid choice. Please try again.");
                System.out.println();
            }
        }
    }

    private static String menu(Scanner console) {
        System.out.println("What would you like to do? Choose an option in brackets.");
        System.out.println("  [take] quiz");
        System.out.println("  [add] question");
        System.out.println("  [export] quiz");
        System.out.println("  [creative] extension");
        System.out.println("  [quit] program");
        return console.nextLine();
    }

    private static void addQ(Scanner console, QuizTree quiz) {
        System.out.print("Enter result to replace: ");
        String toReplace = console.nextLine();

        System.out.print("Enter left choice: ");
        String leftChoice = console.nextLine();

        System.out.print("Enter right choice: ");
        String rightChoice = console.nextLine();

        System.out.print("Enter score of choices: ");
        String choiceScore = console.nextLine();

        System.out.print("Enter left result: ");
        String leftResult = console.nextLine();

        System.out.print("Enter left score: ");
        int leftScore = Integer.parseInt(console.nextLine());

        System.out.print("Enter right result: ");
        String rightResult = console.nextLine();

        System.out.print("Enter right score: ");
        int rightScore = Integer.parseInt(console.nextLine());

        String choices = leftChoice + "/" + rightChoice + "-" + choiceScore;
        leftResult = leftResult + "-" + leftScore;
        rightResult = rightResult + "-" + rightScore;
        quiz.addQuestion(toReplace, choices, leftResult, rightResult);
    }
}

