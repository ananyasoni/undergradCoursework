// Ananya Soni 
// 01/19/2024
// CSE 123
// C0: Cipher
// TA: Lainey
// This class represents a Concealment Cipher. It allows the user 
// to provide an input and filler value and encrypt that input using the concealment cipher. 
// Each character in the provided input is then preceded by a filler amount of junk characters
// in the encodable range. This class also allows the user to decrypt a given
// input using the inverse of the concealment cipher filtering out junk characters. 
import java.util.*;

public class Concealment extends Cipher {

    private int filler;

    // Behavior: 
    //   - This constructor constructs a Concealment Cipher given a
    //     filler amount 
    // Parameters:
    //   - int filler: used to encrypt and decrypt user input by providing the amount
    //     of filler characters to place before each character in the input
    // Returns:
    //   - N/A
    // Exceptions:
    //   - IllegalArgumentException: this constructor throws an illegal arguement exception if the 
    //     given filler amount is less than or equal to zero.
    public Concealment(int filler) {
        if(filler <= 0) {
            throw new IllegalArgumentException();
        }
        this.filler = filler;
    }

    // Behavior: 
    //   - This method encrypts a user's given input using the concealment cipher. This method places 
    //     filler random characters in the encodable range before each character from input .
    // Parameters:
    //   - String input: user input that is encrypted by this method using the concealment cipher 
    // Returns:
    //   - String output: encrypted input using the concealment cipher  
    // Exceptions:
    //   - N/A
    public String encrypt(String input) {
        Random rand = new Random();
        String result = "";
        for(int i = 0; i < input.length(); i++) {
            for(int j = 0; j < filler; j++) {
                char junkChar = (char) rand.nextInt(Cipher.MIN_CHAR, Cipher.MAX_CHAR + 1);
                result += junkChar;
            }
            result += input.charAt(i);  
        }
        return result;
    }

    // Behavior: 
    //   - This method decrypts a user's given input using the inverse of the concealment cipher. 
    //     This method places skips over junk characters returning only the characters after the filler amount.
    // Parameters:
    //   - String input: user input that is decrypted by this method using the inverse of the concealment cipher 
    // Returns:
    //   - String output: decrypted input using the inverse of the concealment cipher  
    // Exceptions:
    //   - N/A
    public String decrypt(String input) {
        String result = "";
        int position = this.filler;
        while(position < input.length()) {
            char nextLetter = input.charAt(position);
            result += nextLetter;
            position = position + filler + 1;
        }
        return result;
    }
}
