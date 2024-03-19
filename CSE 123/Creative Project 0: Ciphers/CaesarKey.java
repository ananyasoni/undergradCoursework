// Ananya Soni 
// 01/19/2024
// CSE 123
// C0: Cipher
// TA: Lainey
// This class represents a Caesar Key cipher . It allows the user 
// to input a String and encrypt that String using a Caesar Key cipher
// where each character in the input is shiften by the given amount.
// This class also allows the user to decrypt a given input that using the inverse of 
// the Caesar Shift cipher. 
public class CaesarKey extends Substitution {

    // Behavior: 
    //   - This constructor constructs a CaesarKey Cipher given a
    //     key
    // Parameters:
    //   - String key: used to encrypt and decrypt user input by adding key to shifter followed  
    //     by the Cipher's encodable range in its original order minus the characters in key
    // Returns:
    //   - N/A
    // Exceptions:
    //   - IllegalArgumentException: this constructor throws an illegal arguement exception if the 
    //     given shift amount is less than or equal to zero.
    public CaesarKey(String key) {
        if(key.isEmpty() || containsDuplicate(key) || !inEncodableRange(key)) {
            throw new IllegalArgumentException();
        }
        String shifterString = "";
        //first add characters in key to shifterString
        for(int i = 0; i < key.length(); i++) {
            shifterString += key.charAt(i);
        }
        //traverse through encodable range
        for(int i = Cipher.MIN_CHAR; i <= Cipher.MAX_CHAR; i++) {
            String nextChar = String.valueOf((char)i);
            //only if the key doesn't contain the next character in the encodable range,
            //then add it to the shifterString
            if(!key.contains(nextChar)) {
                shifterString += ((char)i);
            }     
        }
        //set shifter field with shifterString containing key and rest of the encodable range
        super.setShifter(shifterString);
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