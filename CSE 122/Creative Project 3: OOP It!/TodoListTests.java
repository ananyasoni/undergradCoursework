import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.io.*;

public class TodoListTests {
    @Test
    @DisplayName("Test initial ToDoList State")
    public void testConstructor() {
        TodoList toDos = new TodoList();
        assertTrue(toDos.getToDoList().isEmpty());
        assertEquals(0, toDos.getSize());
    }

    @Test
    @DisplayName("Test Adding Items to TodoList")
    public void testAddItem() {
        TodoList toDos = new TodoList();
        toDos.addItem("jog", toDos.getSize());
        assertTrue(toDos.getToDoList().contains("jog"));
        toDos.addItem("walk", 0);
        assertTrue(toDos.getToDoList().get(0).equals("walk"));
        toDos.addItem("eat", 1);
        assertTrue(toDos.getToDoList().get(1).equals("eat"));
    }
    @Test
    @DisplayName("Test Marking Items in ToDoList")
    public void testMarkItem() {
        TodoList toDos = new TodoList();
        toDos.addItem("jog", toDos.getSize());
        toDos.addItem("eat", toDos.getSize());
        toDos.addItem("walk", toDos.getSize());
        assertTrue(toDos.getToDoList().contains("eat"));
        toDos.markItemAsDone(1);
        assertFalse(toDos.getToDoList().contains("eat"));
        assertEquals(0, toDos.getToDoList().indexOf("jog"));
        assertEquals(1, toDos.getToDoList().indexOf("walk"));
    }
    @Test
    @DisplayName("Test Get Size Method")
    public void testGetSize() {
        TodoList toDos = new TodoList();
        toDos.addItem("jog", toDos.getSize());
        toDos.addItem("eat", toDos.getSize());
        toDos.addItem("walk", toDos.getSize());
        assertEquals(3, toDos.getSize());
    }
    @Test
    @DisplayName("Test Get ToDoList Method")
    public void testGetToDoList() {
        TodoList toDos = new TodoList();
        toDos.addItem("jog", toDos.getSize());
        toDos.addItem("eat", toDos.getSize());
        toDos.addItem("walk", toDos.getSize());
        List<String> list = new ArrayList<>(Arrays.asList("jog", "eat", "walk"));
        assertEquals(true, toDos.getToDoList().containsAll(list));
    }
    @Test
    @DisplayName("Test Load Items Method throws FileNotFoundException")
    public void testLoadItems() throws FileNotFoundException {
        TodoList toDos = new TodoList();
        File nonExistentFile = new File("nonExistentFile");
        assertThrows (FileNotFoundException.class, () -> toDos.loadItems("nonExistentFile")); 
    }
    @Test
    @DisplayName("Test Save Items Method")
    public void testSaveItems() throws FileNotFoundException {
        TodoList toDos = new TodoList();
        File file = new File("file");
        toDos.saveItems("file");
        assertEquals(0, file.length());
    }

}