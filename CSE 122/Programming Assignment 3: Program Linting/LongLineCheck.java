// Ananya Soni 
// 11/25/2023
// CSE 122 
// P3: Program Linting
// TA: Kevin
// This class implements the Check class and 
// checks whether a line has 100 or more 
// characters.
import java.util.*;

public class LongLineCheck implements Check {

    // Behavior: 
    //   - This method checks whether a given line
    //     is 100 characters or greater. And returns an 
    //     error (with a custom message "Line is too long") 
    //     if the given line length is 100 characters or greater 
    //     with an error code of 1.
    // Parameters:
    //   - String line: line being checked for error
    //   - int lineNumber: the line number of the line in the file   
    // Returns:
    //   - Optional<Error>: error (with a custom message "Line is too long") if the  
    //     given line length is 100 characters or greater with an error code of 1.  
    //     If the line length is less than 100 characters this method returns an empty 
    //     optional object.
    // Exceptions:
    //   - N/A
    public Optional<Error> lint(String line, int lineNumber) {
        if (line.length() >= 100) {
            return Optional.of(new Error(1, lineNumber, "Line is too long"));
        }
        return Optional.empty();
    }	
}