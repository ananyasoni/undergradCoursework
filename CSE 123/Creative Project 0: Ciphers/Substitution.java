// Ananya Soni 
// 01/19/2024
// CSE 123
// C0: Cipher
// TA: Lainey
// This class represents a Substitution Cipher. It allows the user 
// to provide an input and encrypt that input using a substitution cipher
// where each character in the input is replaced by each unique character in
// the given shifter. This class also allows the user to decrypt a given
// input using the inverse of the substitution cipher. 
import java.util.*;

public class Substitution extends Cipher {

    private List<Character> shifter;

    // Behavior: 
    //   - This constructor constructs a Substitution Cipher provided with an empty 
    //     shifter
    // Parameters:
    //   - N/A
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A 
    public Substitution() {
        this.shifter = new ArrayList<Character>();
    }

    // Behavior: 
    //   - This constructor constructs a Substitution Cipher given a shifter
    // Parameters:
    //   - String shifter: used to encrypt and decrypt user input 
    // Returns:
    //   - N/A
    // Exceptions:
    //   - IllegalArgumentException: this constructor throws an illegal arguement exception 
    //     if the shifter length does not equal the total number of characters in the Cipher's encodable
    //     range, if the shifter contains duplicate characters, or if a character in the shifter is 
    //     not in the encodable range.
    public Substitution(String shifter) {
        this.setShifter(shifter);
    }

    // Behavior: 
    //   - This method allows the user to change the shifter and set it to a different value.
    //     The shifter is used to encrypt user input. 
    // Parameters:
    //   - String shifter: used to encrypt and decrypt user input 
    // Returns:
    //   - N/A
    // Exceptions:
    //   - IllegalArgumentException: this constructor throws an illegal arguement exception 
    //     if the shifter length does not equal the total number of characters in the Cipher's encodable
    //     range, if the shifter contains duplicate characters, or if a character in the shifter is 
    //     not in the encodable range.
    public void setShifter(String shifter) {
        if(shifter.length() != Cipher.TOTAL_CHARS || containsDuplicate(shifter) || !inEncodableRange(shifter)) {
            throw new IllegalArgumentException("Can't have duplicates and letters must be in range A-Z!!!");
        }
        this.shifter = new ArrayList<Character>();
        for(int i = 0; i < shifter.length(); i++) {
            char nextLetter = shifter.charAt(i);
            this.shifter.add(nextLetter);
        }
    }

    // Behavior: 
    //   - This method encrypts a user's given input using a substitution cipher. Every character
    //     in the given input is replaced by the corresonding character in the shifter at the same 
    //     relative position within the Cipher's encodable range
    // Parameters:
    //   - String input: user input that is encrypted by this method using a substitution cipher  
    // Returns:
    //   - String output: encrypted input using substitution cipher 
    // Exceptions:
    //   - IllegalStateException: this methods throws an illegal state exception if the 
    //     shifter is empty or null
    public String encrypt(String input) {
        if(this.shifter.isEmpty() || this.shifter == null) {
            throw new IllegalStateException();
        }
        String output = "";
        for(int i = 0; i < input.length(); i++) {
            int nextLetterIndex = ((int)input.charAt(i) - Cipher.MIN_CHAR);
            output = output + this.shifter.get(nextLetterIndex);
        }
        return output;
    }

    // Behavior: 
    //   - This method decrypts a user's given input using the inverse of the substitution cipher. Every character
    //     in the given input is replaced by the character in the encodable range in the same relative position
    //     as that character in the shifter.
    // Parameters:
    //   - String input: user input that is decrypted by this method using the inverse of the 
    //     substitution cipher  
    // Returns:
    //   - String output: decrypted input using the inverse of the substitution cipher 
    // Exceptions:
    //   - IllegalStateException: this methods throws an illegal state exception if the 
    //     shifter is empty or null
    public String decrypt(String input) {
        if(this.shifter.isEmpty() || this.shifter == null) {
            throw new IllegalStateException();
        }
        String output = "";
        for(int i = 0; i < input.length(); i++) {
            char nextLetter = input.charAt(i);
            int index = this.shifter.indexOf(nextLetter);
            output = output + (char)(index + Cipher.MIN_CHAR);
        }
        return output;
    }

    // Behavior: 
    //   - This helper method checks whether a given string contains any duplicates. 
    //     (Contains any characters that are the same)
    // Parameters:
    //   - String s: string that is checked to see whether it contains duplicates by this method  
    // Returns:
    //   - boolean: returns true if s contains duplicates and false if s does not contain 
    //     duplicates and each character in s is unique
    // Exceptions:
    //   - N/A
    private boolean containsDuplicate(String s) {
        for(int i = 0; i < s.length(); i++) {
            for(int j = i + 1; j < s.length(); j++) {
                if(s.charAt(i) == s.charAt(j)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Behavior: 
    //   - This method checks whether a given string contains characters that are all 
    //     in the encodable range of Cipher
    // Parameters:
    //   - String s: string that is checked to see whether all its characters are in the encodable 
    //     range of Cipher 
    // Returns:
    //   - boolean: returns true if all characters in s are in the encodable range of Cipher and
    //     false otherwise
    // Exceptions:
    //   - N/A
    private boolean inEncodableRange(String s) {
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) < Cipher.MIN_CHAR || s.charAt(i) > Cipher.MAX_CHAR) {
                return false;
            }
        }
        return true;
    }

}