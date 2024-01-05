// Ananya Soni 
// 12/03/2023
// CSE 122 
// C3: OOP It!
// This class represents a TodoList and supports various operations such as
// adding an item, marking an item as done, printing out to-do items, getting a 
// copy of the to-do list, accessing the to-do list size, loading to-do items, and 
// saving to-do items.
import java.util.*;
import java.io.*;

public class TodoList {
    //list of current to-do items
    private List<String> toDoList;

    // Behavior: 
    //   - This constructor contructs a TodoList 
    // Parameters:
    //   - N/A
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public TodoList() {
        this.toDoList = new ArrayList<String>();
    }
    
    // Behavior: 
    //   - This method returns a list of to-do items 
    // Parameters:
    //   - N/A
    // Returns:
    //   - a list of current to-do items
    // Exceptions:
    //   - N/A
    public List<String> getToDoList() {
        List<String> toDoList = new ArrayList<>();
        toDoList.addAll(this.toDoList);
        return toDoList;
    }

    // Behavior: 
    //   - This method returns the size of the TodoList 
    // Parameters:
    //   - N/A
    // Returns:
    //   - the size of the TodoList 
    // Exceptions:
    //   - N/A
    public int getSize() {
        return toDoList.size();
    }

    // Behavior: 
    //   - This method prints the contents of this TodoList. However, if this TodoList 
    //     is empty then this method prints "You have nothing to do yet today! Relax!"
    // Parameters:
    //   - N/A
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public void printTodos() {
        System.out.println("Today's TODOs:");
        if(toDoList.size() == 0) {
            System.out.println("  You have nothing to do yet today! Relax!");
        } 
        for(int i = 0; i < toDoList.size(); i++) {
            System.out.println("  " + (i + 1) + ": " + toDoList.get(i));
        }
    }

    // Behavior: 
    //   - This method adds a given item to this TodoList at the specified index
    // Parameters:
    //   - String item: item to be added
    //   - indexToAddItem: location at which item is added to this TodoList using 
    //     zero-based indexing
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public void addItem(String item, int indexToAddItem) {
        toDoList.add(indexToAddItem, item);
    }

    // Behavior: 
    //   - This method removes the item in this TodoList at the specified index
    // Parameters:
    //   - positionToRemove: location at which item is removed in this TodoList using zero-based 
    //     indexing 
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public void markItemAsDone(int positionToRemove) {
        toDoList.remove(positionToRemove);
    }

    // Behavior: 
    //   - This method sets up this TodoList so that each element is a line from the file specified
	//     by the given fileName in the same order as the file. If the this TodoList previously contained any items, 
    //     its contents are completely replaced by the contents of the file.
    // Parameters:
    //   - String fileName: name of the file containing to-do items
    // Returns:
    //   - N/A
    // Exceptions:
    //   - Throws FileNotFoundException: this method throws a FileNotFoundException if a file with
    //     the given name does not exist
    public void loadItems(String fileName) throws FileNotFoundException {
        Scanner fileOfToDos = new Scanner(new File(fileName));
        toDoList.clear();
        while(fileOfToDos.hasNextLine()) {
            String todo = fileOfToDos.nextLine();
            toDoList.add(todo);
        } 
    }

    // Behavior: 
    //   - This method prints each todo item in this TodoList to the file given in 
    //     the same order one line at a time.
    // Parameters:
    //   - String fileName: name of the file to print contents of TodoList on 
    // Returns:
    //   - N/A
    // Exceptions:
    //   - Throws FileNotFoundException: this method throws a FileNotFoundException if a file with
    //     the given name does not exist
    public void saveItems(String fileName) throws FileNotFoundException {
        PrintStream output = new PrintStream(new File(fileName));
        for(int i = 0; i < toDoList.size(); i++) {
            output.println(toDoList.get(i));
        }
    }

}