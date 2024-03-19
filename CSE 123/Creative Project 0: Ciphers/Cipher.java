import java.util.*;
import java.io.*;

// Represents a classical cipher that is able to encrypt a plaintext into a ciphertext, and
// decrypt a ciphertext into a plaintext. Also capable of encrypting and decrypting entire files

public abstract class Cipher {
    // The minimum character able to be encrypted/decrypted by any cipher
    public static final int MIN_CHAR = (int)('A');
    
    // The maximum character able to be encrypted/decrypted by any cipher
    public static final int MAX_CHAR = (int)('Z');
    
    // The total number of characters able to be encrypted/decrypted by any cipher
    // (aka. the encodable range)
    public static final int TOTAL_CHARS = MAX_CHAR - MIN_CHAR + 1;

    //   Behavior: Applies this Cipher's encryption scheme to the file with the
    //             given 'fileName', creating a new file to store the results.
    // Exceptions: Throws a FileNotFoundException if a file with the provided 'fileName'
    //             doesn't exist
    //    Returns: None
    // Parameters: 'fileName' - The name of the file to be encrypted
    public void encryptFile(String fileName) throws FileNotFoundException {
        fileHelper(fileName, true, "-encrypted");
    }
    
    //   Behavior: Applies the inverse of this Cipher's encryption scheme to the file with the
    //             given 'fileName' (reversing a single round of encryption if previously applied)
    //             creating a new file to store the results.
    // Exceptions: Throws a FileNotFoundException if a file with the provided 'fileName'
    //             doesn't exist
    //    Returns: None
    // Parameters: 'fileName' - The name of the file to be decrypted
    public void decryptFile(String fileName) throws FileNotFoundException {
        fileHelper(fileName, false, "-decrypted");
    }
    
    //   Behavior: Reads from an input file with 'fileName', either encrypting or decrypting
    //             depending on 'encrypt', printing the results to a new file with 'suffix'
    //             appended to the input file's name
    // Exceptions: Throws a FileNotFoundException if a file with the provided 'fileName'
    //             doesn't exist
    //    Returns: None
    // Parameters: 'fileName' - the name of the file to be encrypted / decrypted
    //             'encrypt'  - whether or not encryption should occur
    //             'suffix'   - appended to the fileName when creating the output file
    private void fileHelper(String fileName, boolean encrypt, String suffix) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(fileName));
        String out = fileName.split("\\.txt")[0] + suffix + ".txt";
        PrintStream ps = new PrintStream(out);
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            ps.println(encrypt ? encrypt(line) : decrypt(line));
        }
    }

    //   Behavior: Applies this Cipher's encryption scheme to 'input', returning the result
    // Exceptions: None
    //    Returns: The result of applying this Cipher's encryption scheme to `input`
    // Parameters: 'input' - the string to be encrypted
    public abstract String encrypt(String input);
    
    //   Behavior: Applies this inverse of this Cipher's encryption scheme to 'input' (reversing
    //             a single round of encryption if previously applied), returning the result
    // Exceptions: None
    //    Returns: The result of applying the inverse of this Cipher's encryption scheme to `input`
    // Parameters: 'input' - the string to be decrypted
    public abstract String decrypt(String input);
}
