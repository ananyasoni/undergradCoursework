// Ananya Soni 
// 11/25/2023
// CSE 122 
// P3: Program Linting
// This class implements the Check class and 
// checks whether a given line contains the break keyword 
// outside of a single line comment.
import java.util.*;

public class BreakCheck implements Check {

    // Behavior: 
    //   - This method checks whether a given line contains the break 
    //     keyword (in all lowerCase) outside of a single line comment
    //     (comments that start with //). 
    // Parameters:
    //   - String line: line being checked for error
    //   - int lineNumber: the line number of the line in the file  
    // Returns:
    //   - Optional<Error>: error (with a custom message "break is a forbidden method")   
    //     if the given line contains the break keyword (in all lowerCase) outside of a 
    //     single line comment with an error code of 2. If given line contains no 
    //     break keyword (in all lowerCase) outside of a single line comment, then this method
    //     returns an empty optional object.
    // Exceptions:
    //   - N/A
    public Optional<Error> lint(String line, int lineNumber) {
        if ((line.contains("break") && (line.indexOf("//") > line.indexOf("break"))) || (line.contains("break") && !line.contains("//"))) {
            return Optional.of(new Error(2, lineNumber, "break is a forbidden method"));
        }
        return Optional.empty();
    }	
}