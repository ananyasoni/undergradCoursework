// Ananya Soni 
// 01/19/2024
// CSE 123
// C0: Cipher
// This class represents a Caesar Shift Cipher. It allows the user 
// to provide an input and encrypt that input using a caesar shift cipher
// where each character in the shifter is shifted by the given amount.
// Each character in the input is then replaced by each unique character in
// the shifted shifter. This class also allows the user to decrypt a given
// input using the inverse of the caesar shift cipher. 
public class CaesarShift extends Substitution {

    // Behavior: 
    //   - This constructor constructs a CaesarShift Cipher given a
    //     shift amount 
    // Parameters:
    //   - int shift: used to encrypt and decrypt user input by shifting encodable range of Cipher
    //     by shift amount to create shifter   
    // Returns:
    //   - N/A
    // Exceptions:
    //   - IllegalArgumentException: this constructor throws an illegal arguement exception if the 
    //     given shift amount is less than or equal to zero.
    public CaesarShift(int shift) {
        if(shift <= 0) {
            throw new IllegalArgumentException("Can't shift <=0!!!");
        }
        String shifterString = "";
        for(int i = Cipher.MIN_CHAR; i <= Cipher.MAX_CHAR; i++) {
            shifterString += ((char)i);
        }
        //shift values in shifterString left by shift amount 
        for(int j = 0; j < shift; j++) {
            char nextLetter = shifterString.charAt(0);
            shifterString = shifterString.substring(1, shifterString.length());
            shifterString += nextLetter;
        }
        //set shifter field using shifterString containing characters in encodable range, 
        //shifted by given shift amount
        setShifter(shifterString);
    }
   
}
