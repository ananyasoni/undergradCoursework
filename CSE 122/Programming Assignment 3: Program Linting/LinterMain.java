// Ananya Soni 
// 11/25/2023
// CSE 122 
// P3: Program Linting
// This class checks the code quality of provided code and 
// prints an error if there is a line of code that is greater than or equal 
// to 100 characters, if there is a blank println statement 
// (aka -> System.out.println("")), and lastly if there is 
// any use of the keyword break in all lowerCase outside of a single line comment.

import java.util.*;
import java.io.*;

public class LinterMain {
    public static final String FILE_NAME = "TestFile.java";

    public static void main(String[] args) throws FileNotFoundException {
        List<Check> checks = new ArrayList<>();
        checks.add(new BreakCheck());
        Linter linter = new Linter(checks);
        List<Error> errors = linter.lint(FILE_NAME);
        for (Error e : errors) {
            System.out.println(e);
        }
    }
}