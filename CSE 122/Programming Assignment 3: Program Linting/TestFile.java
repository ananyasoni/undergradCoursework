// Ananya Soni 
// 11/25/2023
// CSE 122 
// P3: Program Linting
// This class is a test file containing some style errors that is 
// used as a test input for testing out Linter.java. 
public class TestFile {
    public static void main(String[] args) {
        System.out.println("This is a really really really long line that should fail the long line check");
        while (true) {
            System.out.println("");
            break; //this is a comment
        }
    }
}

