// Ananya Soni 
// 01/19/2024
// CSE 123
// C0: Cipher
// TA: Lainey
// This class represents a Multi Cipher. It allows the user 
// to provide multiple Cipher's and an input and encrypt that input using 
// the provided Ciphers. The input is then processed in order through 
// each Cipher provided. This class also allows the user to decrypt a given
// input using the inverse of each of the provided cipher's in reverse order. 
import java.util.*;

public class MultiCipher extends Cipher {

    private List<Cipher> ciphers;

    // Behavior: 
    //   - This constructor constructs a MultiCipher given a
    //     list of Cipher's
    // Parameters:
    //   - List<Cipher> cipher's: list of Cipher's used to encrypt and decrypt user input 
    // Returns:
    //   - N/A
    // Exceptions:
    //   - IllegalArgumentException: this constructor throws an illegal arguement if the given list
    //     of ciphers is null 
    public MultiCipher(List<Cipher> ciphers) {
        if(ciphers == null) {
            throw new IllegalArgumentException();
        }
        this.ciphers = new ArrayList<Cipher>();
        this.ciphers.addAll(ciphers);
    }

    // Behavior: 
    //   - This method encrypts a user's given input using the list of given ciphers. After encrypting
    //     the input using the first cipher in the list of ciphers, this method uses the encyption as 
    //     input for the next cipher in the list of ciphersm repeating this process until there are no 
    //     ciphers left in the list.
    // Parameters:
    //   - String input: user input that is encrypted by this method using the list of ciphers
    // Returns:
    //   - String output: encrypted input using the list of ciphers 
    // Exceptions:
    //   - N/A
    public String encrypt(String input) {
        for(int i = 0; i < ciphers.size(); i++) {
            Cipher nextCipher = ciphers.get(i);
            input = nextCipher.encrypt(input);
        }
        return input;
    }

    // Behavior: 
    //   - This method decrypts a user's given input using the list of given ciphers. After decrypting
    //     the input using the last cipher in the list of ciphers, this method uses the decryption as 
    //     input for the second to last cipher in the list of ciphersm repeating this process until 
    //     there are no ciphers left in the list.
    // Parameters:
    //   - String input: user input that is decrypted by this method using the list of ciphers
    // Returns:
    //   - String output: decrypted input using the list of ciphers 
    // Exceptions:
    //   - N/A
    public String decrypt(String input) {
        for(int i = ciphers.size() - 1; i >= 0; i--) {
            Cipher nextCipher = ciphers.get(i);
            input = nextCipher.decrypt(input);
        }
        return input;
    }
}