// Ananya Soni 
// 11/25/2023
// CSE 122 
// P3: Program Linting
// This class mantains the state of an Error object and
// defines the properties of an error. User's can get 
// a string representation of the error, get the line number
// of an error, get the error code, and get the error message.
public class Error {
	private int code;
	private int lineNumber;
	private String message;

    // Behavior: 
    //   - This constructor constructs an Error object given the error code, 
	//     line number and an error message.
    // Parameters:
    //   - int code: error code
	//   - int lineNumber: the line an error is on
	//   - String message: error message
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
	public Error(int code, int lineNumber, String message) {
		this.code = code;
		this.lineNumber = lineNumber;
		this.message = message;
	}

    // Behavior: 
    //   - This method returns a String representation of the error with 
	//     the line number, error code, and error message.  
    // Parameters:
    //   - N/A
    // Returns:
    //   - String representation of the error with the line number, 
	//     error code, and error message.
    // Exceptions:
    //   - N/A
	public String toString() {
		return ("(Line: "  + lineNumber + ") has error code " + code + "\n" + message );
	}

    // Behavior: 
    //   - This method returns the line number of this error 
    // Parameters:
    //   - N/A
    // Returns:
    //   - Returns an integer that represents the line number of the error 
    // Exceptions:
    //   - N/A
	public int getLineNumber() {
		return lineNumber;
	}

    // Behavior: 
    //   - This method returns the error code of this error
    // Parameters:
    //   - N/A
    // Returns:
    //   - Returns an integer that represents the error code of this error 
    // Exceptions:
    //   - N/A
	public int getCode() {
		return code;
	}

    // Behavior: 
    //   - This method returns the message for this error
    // Parameters:
    //   - N/A
    // Returns:
    //   - Returns a String that represents the error message  
    // Exceptions:
    //   - N/A
	public String getMessage() {
		return message;
	}
}