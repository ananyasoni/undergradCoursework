// Ananya Soni
// 10/04/2023
// CSE 122
// This class prints a triangle based on the number of rows of stars (numStars).
public class Triangle {
    public static void main(String[] args) {
        printTriangle(5);
    }

// Behavior: 
//   - This method prints a Triangle based on the number of rows of stars  
// Parameters:
//   - numStars: the number of rows of stars and the amount of stars in the longest row
// Returns:
//   - N/A
// Exceptions:
//   - N/A
    public static void printTriangle(int numStars) {
        for(int i = 0; i < numStars; i++) {
            for(int j = i; j < numStars; j++) {
                System.out.print("*");
            }
            System.out.println();   
        }     
    }
}
