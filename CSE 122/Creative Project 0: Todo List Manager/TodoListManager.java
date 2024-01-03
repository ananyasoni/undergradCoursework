// Ananya Soni 
// 10/12/2023
// CSE 122 
// C0: Todo List Manager
// This class allows a user to create a todo list. A user can add tasks to the todo list,
// mark tasks as complete, view list of completed todos, save todos, load todos from
// a file, and view todos.
import java.util.*;
import java.io.*;

public class TodoListManager {
    public static final boolean EXTENSION_FLAG = false;
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to your TODO List Manager!");
        List<String> toDoList = new ArrayList<String>();
        Scanner input = new Scanner(System.in);
        String userCommand = "";
        List<String> completedToDoList = new ArrayList<String>();
        while(!(userCommand.equalsIgnoreCase("Q"))) {
            userCommand = getUserCommand(input);
            if(userCommand.equalsIgnoreCase("A")) {
                addItem(input, toDoList);    
            } 
            else if(userCommand.equalsIgnoreCase("M")) {
                markItemAsDone(input, toDoList, completedToDoList);
            } 
            else if(userCommand.equalsIgnoreCase("L")) {
                loadItems(input, toDoList);
            } 
            else if(userCommand.equalsIgnoreCase("S")) {
                saveItems(input, toDoList);    
            } else {
                if(!(userCommand.equalsIgnoreCase("Q"))) {
                    System.out.println("Unknown input: " + userCommand);     
                }        
            } 
            if(!(userCommand.equalsIgnoreCase("Q"))) {
                    printTodos(toDoList);
            }
            if(EXTENSION_FLAG) {
                displayCompletedToDos(input, completedToDoList);
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
        System.out.println("What would you like to do?");
        System.out.print("(A)dd TODO, (M)ark TODO as done, (L)oad TODOs, (S)ave TODOs, (Q)uit? ");
        String userCommand = console.nextLine();
        return userCommand;
    }

    // Behavior: 
    //   - This method prints the current to do list created by the user. However, if the to do list  
    //     is empty then this method prints "You have nothing to do yet today! Relax!"
    // Parameters:
    //   - List<String> todos: A List containing the users to do tasks
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public static void printTodos(List<String> todos) {
        System.out.println("Today's TODOs:");
        if(todos.size() == 0) {
            System.out.println("  You have nothing to do yet today! Relax!");
        } 
        for(int i = 0; i < todos.size(); i++) {
            System.out.println("  " + (i + 1) + ": " + todos.get(i));
        }
    }

    // Behavior: 
    //   - This method allows users to add a task to their to do list via the console. Once
    //     the to do list contains at least one item this method allows the user to type a 
    //     number which tells this method where specifically in the list to add the to do task.
    // Parameters:
    //   - Scanner console: allows this method to read in user input
    //   - List<String> todos: A List containing the users to do tasks
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public static void addItem(Scanner console, List<String> todos) {
        int maxRange = todos.size() + 1;
        System.out.print("What would you like to add? ");
        String task = console.nextLine();
        if(todos.size() > 0 ) {
            System.out.print("Where in the list should it be " + "(1-" + maxRange + ")? " + "(Enter for end): " );
            String input = console.nextLine();
            if(input.isEmpty()) {
                todos.add(task);
            } else {
                int positionOfToDo = Integer.parseInt(input); 
                todos.add(positionOfToDo - 1, task);
            }
        } else {
            todos.add(task);
        }       
    }

    // Behavior: 
    //   - This method allows a user to mark an item on their to do list as complete. The method
    //     prompts the user to enter a number in the console which tells this method which specific
    //     task in the to do list to mark as complete and remove from the to do list. If the boolean
    //     extension flag is true this method also adds the marked task to another List called
    //     completedToDos. If the todo list is empty the method prints "All done! Nothing left to mark as done!"
    // Parameters:
    //   - Scanner console: allows the method to read user input
    //   - List<String> todos: A List containing the users to do tasks
    //   - List<String> completedToDos: a List containing the current list of
    //     todos that have been marked completed -> note: only updated when the boolean
    //     extension flag is true
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public static void markItemAsDone(Scanner console, List<String> todos, List<String> completedToDos) {
        int maxRange = todos.size();
        if(todos.size() == 0) {
            System.out.println("All done! Nothing left to mark as done!");
        } else {
            System.out.print("Which item did you complete " + "(1-" + maxRange + ")? " );
            String input = console.nextLine();
            int positionToRemove = Integer.parseInt(input); 
            todos.remove(positionToRemove - 1);
        }
    }

    // Behavior: 
    //   - This method prompts the user for a file name containing the list of todo items, with each item on one line. 
    //     The method then reads the file line by line, with each item being added, in order, to the todo list. If the 
    //     todo list previously contained any items, its contents are completely replaced by the contents of the file.
    // Parameters:
    //   - Scanner console: allows the method to read user input
    //   - List<String> todos: A List containing the users to do tasks
    // Returns:
    //   - N/A
    // Exceptions:
    //   - if the given file name is invalid, an IllegalArgumentException is thrown.
    public static void loadItems(Scanner console, List<String> todos) throws FileNotFoundException {
        System.out.print("File name? ");
        String fileName = console.nextLine();
        Scanner fileOfToDos = new Scanner(new File(fileName));
        todos.clear();
        while(fileOfToDos.hasNextLine()) {
            String todo = fileOfToDos.nextLine();
            todos.add(todo);
        } 
    }

    // Behavior: 
    //   - This method prompts the user for a file name and prints each todo item to to the file in 
    //     the same order one line at a time.
    // Parameters:
    //   - Scanner console: allows the method to read user input
     //   - List<String> todos: A List containing the users to do tasks
    // Returns:
    //   - N/A
    // Exceptions:
    //   - if the given file name is invalid, an IllegalArgumentException is thrown.
    public static void saveItems(Scanner console, List<String> todos) throws FileNotFoundException {
        System.out.print("File name? ");
        String fileName = console.nextLine();
        PrintStream output = new PrintStream(new File(fileName));
        for(int i = 0; i < todos.size(); i++) {
            output.println(todos.get(i));
        }
    }

    // Behavior: 
    //   - This method asks the user whether they want to display the list of todos that they 
    //     marked as completed, only if the list has at least one item. If the user types Y/y 
    //     the method prints the list of completed todos in the order that they were completed
    //     in. If the user presses enter the completed list of todos is no longer printed.
    // Parameters:
    //   - Scanner console: allows the method to read user input
    //   - List<String> completedToDos: a List containing the current list of
    //     todos that have been marked completed -> note: only updated when the boolean
    //     extension flag is true
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public static void displayCompletedToDos(Scanner console, List<String> completedToDos) {
        if(completedToDos.size() > 0) {
            System.out.print("Would you like to display completed (Y) or (Press Enter for No)? ");
            String userCommand = console.nextLine();
            if(userCommand.equalsIgnoreCase("Y")) {
                System.out.println();
                System.out.println("Completed To Do List:");
                for(int i = 0; i < completedToDos.size(); i++) {
                    System.out.println("  " + (i + 1) + ": " + completedToDos.get(i));
                }
            } 
        }
    }
}