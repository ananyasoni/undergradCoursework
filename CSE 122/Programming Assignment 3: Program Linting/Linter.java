// Ananya Soni 
// 11/25/2023
// CSE 122 
// P3: Program Linting
// This class lints a given file given a list of checks
// to test an input file against.
import java.util.*;
import java.io.*;

public class Linter {
    private List<Check> checks;

    // Behavior: 
    //   - This constructor constructs a Linter given a list of checks to run through.
    // Parameters:
    //   - List<Check> checks: a list of checks 
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public Linter(List<Check> checks) {
        this.checks = checks;
    }

    // Behavior: 
    //   - This method lints a given files line-by-line and runs through 
    //     all the checks for each line in the file, recording any errors. 
    // Parameters:
    //   - String fileName: name of the file to lint through
    // Returns:
    //   - List<Error> errors: a list of errors the file provided contains when 
    //     run against all the checks
    // Exceptions:
    //   - throws FileNotFoundException: this method throws a FileNotFoundException  
    //     if a file with the provided name doesn't exist 
    public List<Error> lint(String fileName) throws FileNotFoundException {
        List<Error> errors = new ArrayList<>();
        Scanner fileInput = new Scanner(new File(fileName));
        int lineNumber = 0;
        while(fileInput.hasNextLine()) {
            lineNumber++;
            String nextLine = fileInput.nextLine();
            for(Check test : checks) {
                Optional<Error> error = test.lint(nextLine, lineNumber);
                if(error.isPresent()) {
                    Error e = error.get();
                    errors.add(e);
                }
            }
        }
        return errors;
    }
}