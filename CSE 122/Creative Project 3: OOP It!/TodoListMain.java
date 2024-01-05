// Ananya Soni 
// 12/03/2023
// CSE 122 
// C3: OOP It!
// This class allows a user to create a ToDoList. A user can add tasks to the todo list,
// mark tasks as complete, save todos, load todos from a file, and view todos.
import java.util.*;
import java.io.*;

public class TodoListMain {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to your TODO List Manager!");
        TodoList toDos = new TodoList();
        Scanner input = new Scanner(System.in);
        String userCommand = "";
        while(!(userCommand.equalsIgnoreCase("Q"))) {
            userCommand = getUserCommand(input);
            if(userCommand.equalsIgnoreCase("A")) {
                addItem(input, toDos);    
            } 
            else if(userCommand.equalsIgnoreCase("M")) {
                markItemAsDone(input, toDos);
            } 
            else if(userCommand.equalsIgnoreCase("L")) {
                loadItems(input, toDos);
            } 
            else if(userCommand.equalsIgnoreCase("S")) {
                saveItems(input, toDos);    
            } else {
                if(!(userCommand.equalsIgnoreCase("Q"))) {
                    System.out.println("Unknown input: " + userCommand);     
                }        
            } 
            if(!(userCommand.equalsIgnoreCase("Q"))) {
                    toDos.printTodos();
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
    //   - This method allows users to add a task to their ToDoList toDos via the console. Once
    //     the ToDoList contains at least one item this method allows the user to type a 
    //     number which tells this method where specifically in toDos to add the to do task.
    // Parameters:
    //   - Scanner console: allows this method to read in user input
    //   - ToDoList toDos: ToDoList object represesenting a To Do List 
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public static void addItem(Scanner console, TodoList toDos) {
        int maxRange = toDos.getSize() + 1;
        System.out.print("What would you like to add? ");
        String task = console.nextLine();
        if(toDos.getSize() > 0 ) {
            System.out.print("Where in the list should it be " + "(1-" + maxRange + ")? " + "(Enter for end): " );
            String input = console.nextLine();
            if(input.isEmpty()) {
                toDos.addItem(task, toDos.getSize());
            } else {
                int positionOfToDo = Integer.parseInt(input) - 1; 
                toDos.addItem(task, positionOfToDo);
            }
        } else {
            toDos.addItem(task, toDos.getSize());
        }       
    }

    // Behavior: 
    //   - This method allows a user to mark an item in their toDos as complete. The method
    //     prompts the user to enter a number in the console which tells this method which specific
    //     task in toDos to mark as complete and remove from toDos.
    //     If toDos is empty the method prints "All done! Nothing left to mark as done!"
    // Parameters:
    //   - Scanner console: allows the method to read user input
    //   - ToDoList toDos: ToDoList object represesenting a To Do List 
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public static void markItemAsDone(Scanner console, TodoList toDos) {
        int maxRange = toDos.getSize();
        if(toDos.getSize() == 0) {
            System.out.println("All done! Nothing left to mark as done!");
        } else {
            System.out.print("Which item did you complete " + "(1-" + maxRange + ")? " );
            String input = console.nextLine();
            int positionToRemove = Integer.parseInt(input) - 1; 
            toDos.markItemAsDone(positionToRemove);
        }
    }

    // Behavior: 
    //   - This method prompts the user for a file name containing the list of todo items, with each item on one line. 
    //     The method then reads the file line by line, with each item being added, in order, to toDos. If toDos
    //     previously contained any items, its contents are completely replaced by the contents of the file.
    // Parameters:
    //   - Scanner console: allows the method to read user input
    //   - ToDoList toDos: ToDoList object represesenting a To Do List 
    // Returns:
    //   - N/A
    // Exceptions:
    //   - if the given file name is invalid, an IllegalArgumentException is thrown.
    public static void loadItems(Scanner console, TodoList toDos) throws FileNotFoundException {
        System.out.print("File name? ");
        String fileName = console.nextLine();
        toDos.loadItems(fileName);
    }

    // Behavior: 
    //   - This method prompts the user for a file name and prints each todo item to the file in 
    //     the same order one line at a time.
    // Parameters:
    //   - Scanner console: allows the method to read user input
    //   - ToDoList toDos: ToDoList object represesenting a To Do List 
    // Returns:
    //   - N/A
    // Exceptions:
    //   - if the given file name is invalid, an IllegalArgumentException is thrown.
    public static void saveItems(Scanner console, TodoList toDos) throws FileNotFoundException {
        System.out.print("File name? ");
        String fileName = console.nextLine();
        toDos.saveItems(fileName);
    }
}