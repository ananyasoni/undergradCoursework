// Ananya Soni 
// 11/25/2023
// CSE 122 
// P3: Program Linting
// This class implements the Check class and 
// checks whether a given line contains a blank 
// println statement in other words checks if a 
// line contains the following pattern: {System.out.println("")}
import java.util.*;

public class BlankPrintlnCheck implements Check {

    // Behavior: 
    //   - This method checks whether a given line contains a blank println
    //     statement. In other words this method specifically checks
    //     if a line contains the following pattern: {System.out.println("")}
    // Parameters:
    //   - String line: line being checked for error
    //   - int lineNumber: the line number of the current line in the file
    // Returns:
    //   - Optional<Error>: error (with a custom message "Line has blank println")   
    //     if the given line contains the pattern: {System.out.println("")} 
    //     with an error code of 3. If given line doesn't contain the pattern 
    //     {System.out.println("")} then this method returns an empty optional object. 
    // Exceptions:
    //   - N/A
    public Optional<Error> lint(String line, int lineNumber) {
        if (line.contains("System.out.println(\"\")")) {
            return Optional.of(new Error(3, lineNumber, "Line has blank println"));
        }
        return Optional.empty();
    }	
}