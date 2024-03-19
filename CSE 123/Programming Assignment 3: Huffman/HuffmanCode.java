// Ananya Soni 
// 03/04/2024
// CSE 123
// P3: Huffman
// This class represents a Huffman Code and allows a user
// to create a Huffman Code for a file of text, save this
// Huffman Code into a new file, and translate a compressed file 
// into its decompressed state with its original contents using 
// this Huffman Code. 
import java.util.*;
import java.io.*;

public class HuffmanCode {

    //root of the Huffman tree containing Huffman encoding
    private HuffmanNode overallRoot;

    // Behavior: 
    //   - This constructor constructs a new HuffmanCode based on the provided frequencies
    //     of characters in a file. If there exists a character with a frequency <= 0, 
    //     then that character is not included in this HuffmanCode.
    // Parameters:
    //   - int[] frequencies: an array of frequencies containing the frequencies of 
    //     various characters where frequences[i] is the count of the character with 
    //     ASCII value i. 
    // Returns: 
    //   - N/A
    // Exceptions:
    //   - N/A 
    public HuffmanCode(int[] frequencies) {
        //create a priority queue ordered by frequency
        Queue<HuffmanNode> freqQueue = new PriorityQueue<>();
        for(int i = 0; i < frequencies.length; i++) {
            int nextFreq = frequencies[i];
            //only add characters with a frequency > 0 to the HuffmanCode object
            if(nextFreq > 0) {
                //add HuffmanNode with frequency and character corrresponding
                //to i to the priority queue 
                freqQueue.add(new HuffmanNode(nextFreq, (char)(i)));
            }
        }
        //build the Huffman tree
        while(freqQueue.size() > 1) { //only do body of loop when there are >= 2 nodes in the P.Q
            //remove the two nodes with the lowest frequency 
            HuffmanNode leftNode = freqQueue.remove(); //remove first node
            HuffmanNode rightNode = freqQueue.remove(); //remove second node
            //join the left and right node with a single node that contains the sum of the 
            //frequencies of the left and right nodes
            HuffmanNode newRoot = new HuffmanNode(leftNode.frequency + rightNode.frequency, 
            leftNode, rightNode);
            //put the new node with the left and right nodes as children back into the P.Q.
            freqQueue.add(newRoot);
        }
        //only one node remaining in the P.Q which is the root of the Huffman tree 
        this.overallRoot = freqQueue.remove();
    }

    // Behavior: 
    //   - This constructor constructs a new HuffmanCode by reading in a previously
    //     constructed HuffmanCode from a provided .code file.
    // Parameters:
    //   - Scanner input: contains the Huffman encoding/code. The input consists of
    //     pair(s) of lines to represent each character in the Huffman code. The first line 
    //     in each pair contains the ASCII code of the character, and the second line contains
    //     the Huffman encoding for that character.
    // Returns: 
    //   - N/A
    // Exceptions:
    //   - N/A 
    public HuffmanCode(Scanner input) {
        //this method builds the Huffman tree again using the provided .code file (input)
        while(input.hasNextLine()) {
            char nextCharacter = (char)Integer.parseInt(input.nextLine());
            String path = input.nextLine();
            overallRoot = buildPath(overallRoot, nextCharacter, path);
        }
    }

    // Behavior: 
    //   - This helper method helps construct a new HuffmanCode by building part of the Huffman 
    //     Code tree using the provided path and the next character that resides at end of the
    //     provided path.
    // Parameters:
    //   - HuffmanNode curr: keeps track of what point this method is in the HuffmanCode tree
    //   - char nextCharacter: the next chracter that is added at the end of the path and as a leaf
    //     to the HuffmanCode tree
    //   - String path: consists of '0's and '1's that make up the directions to reach the next  
    //     character that is added to HuffmanCode where '0' means travelling left and '1' means
    //     travelling right
    // Returns: 
    //   - the root of the modified HuffmanCode tree 
    // Exceptions:
    //   - N/A 
    private HuffmanNode buildPath(HuffmanNode curr, char nextCharacter, String path) {
        if(path.length() > 0) {
            //we only want to make a new node when curr is null so that
            //we don't lose previous branches, all branches are connected 
            //to one another and the overall root of the Huffman tree 
            if(curr == null) {
                //must create a new Huffman node to fill the path from the root to the leafs
                //containing the characters 
                curr = new HuffmanNode(); 
            }
            char nextStep = path.charAt(0); //tells method whether to travel left/right from curr
            if(nextStep == '0') {
                //travel left
                curr.left = buildPath(curr.left, nextCharacter, path.substring(1, path.length()));
            } else if(nextStep == '1') {
                //travel right
                curr.right = buildPath(curr.right, nextCharacter, 
                                        path.substring(1, path.length()));
            }
        } else {
            //path length is now 0 (empty String), means that we have reached the leaf node ->  
            //finished path so return new leaf node with the character currently being processed
            return new HuffmanNode(nextCharacter); //leaf node 
        }
        return curr; //return modified Huffman tree to complete the x = change(x) pattern
    }

    // Behavior: 
    //   - This method stores the current HuffmanCode to the given output in pre-order
    // Parameters:
    //   - PrintStream output: location where this HuffmanCode is saved. Note: The output file will 
    //     contain pair(s) of lines to represent each character in the Huffman code. The first line 
    //     in each pair will contain the ASCII code of the character, and the second line will  
    //     contain the Huffman encoding for that character.
    // Returns: 
    //   - N/A
    // Exceptions:
    //   - N/A     
    public void save(PrintStream output) {
        save(overallRoot, "", output);
    }
    
    // Behavior: 
    //   - This helper method helps store the current HuffmanCode to the given output in pre-order
    // Parameters:
    //   - HuffmanNode curr: keeps track of what point this method is in the HuffmanCode tree
    //   - String pathSoFar: keeps track of the directions so far to the current point this 
    //     method is in the HuffmanCode tree adding "0" every time this method travels left and 
    //     "1" every time this method travels right in the Huffman tree
    //   - PrintStream output: location where this HuffmanCode is saved. Note: The output file will 
    //     contain pair(s) of lines to represent each character in the Huffman code. The first line 
    //     in each pair will contain the ASCII code of the character, and the second line will  
    //     contain the Huffman encoding for that character.
    // Returns: 
    //   - N/A
    // Exceptions:
    //   - N/A  
    private void save(HuffmanNode curr, String pathSoFar, PrintStream output) {
        if(curr != null) {
            //leaf node --> both left and right nodes equal to null
            //the leaves/characters and their encodings are saved in 
            //the order of a pre-order traversal
            if(curr.left == null && curr.right == null) {
                output.println((int)curr.character); //the character's ASCII value
                output.println(pathSoFar); //encoding
            }
            //travel left
            save(curr.left, pathSoFar + "0", output);
            //travel right
            save(curr.right, pathSoFar + "1", output);
        }
    }

    // Behavior: 
    //   - This method reads individual bits from the input stream and writes the corresponding 
    //     characters from this Huffman Code to the output.
    // Parameters:
    //   - BitInputStream input: contains all the bits of a previously compressed message
    //   - PrintStream output: location where the contents of the original decompressed message
    //     are saved
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A  
    public void translate(BitInputStream input, PrintStream output) {
        translate(overallRoot, input, output);
    }

    // Behavior: 
    //   - This helper method helps read individual bits from the input stream and writes 
    //     the corresponding characters from this Huffman Code to the output. In other words 
    //     this method reads sequences of bits from the input that represent encoded characters 
    //     and figures out what the original characters must have been using this Huffman Code, 
    //     saving them to the output.
    // Parameters:
    //   - HuffmanNode curr: keeps track of what point this method is in the HuffmanCode tree
    //   - BitInputStream input: contains all the bits of a previously compressed message
    //   - PrintStream output: location where the contents of the original decompressed message
    //     are saved
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A  
    private void translate(HuffmanNode curr, BitInputStream input, PrintStream output) {
        while(input.hasNextBit()) {
            int nextBit = input.nextBit();
            //if the next bit is 0 travel left
            if(nextBit == 0) {
                curr = curr.left;
            //if the next bit is 1 travel right
            } else {
                curr = curr.right;
            }
            //reached leaf node
            if(curr.left == null && curr.right == null) {
                output.write(curr.character); //write the character in the leaf node
                curr = overallRoot; //go back to the root of the Huffman tree
            } 
        }
    }

    // This class represents a Huffman Node that makes up a Huffman Tree
    // and implements the Comparable interface
    public static class HuffmanNode implements Comparable<HuffmanNode> {
    
        public final char character;
        public final int frequency;
        public HuffmanNode left;
        public HuffmanNode right;

        // Behavior: 
        //   - This constructor constructs a new default HuffmanNode
        // Parameters:
        //   - N/A
        // Returns: 
        //   - N/A
        // Exceptions:
        //   - N/A 
        public HuffmanNode() {
            this(-1, '\0', null, null);
        }

        // Behavior: 
        //   - This constructor constructs a new HuffmanNode with the provided
        //     character
        // Parameters:
        //   - char character: character contained inside the HuffmanNode
        // Returns: 
        //   - N/A
        // Exceptions:
        //   - N/A 
        public HuffmanNode(char character) {
            this(-1, character, null, null);
        }

        // Behavior: 
        //   - This constructor constructs a new HuffmanNode with the provided
        //     character and frequency 
        // Parameters:
        //   - int frequency: count of the character/its frequency
       //   - char character: character contained inside the HuffmanNode
        // Returns: 
        //   - N/A
        // Exceptions:
        //   - N/A 
        public HuffmanNode(int frequency, char character) {
            this(frequency, character, null, null);
        }

        // Behavior: 
        //   - This constructor constructs a new HuffmanNde with the provided
        //     frequency, left HuffmanNode, and right HuffmanNode 
        // Parameters:
        //   - int frequency: count of a character/its frequency
        //   - HuffmanNode left: a reference to this HuffmanNode's left child
        //   - HuffmanNode right: a reference to this HuffmanNode's right child
        // Returns: 
        //   - N/A
        // Exceptions:
        //   - N/A 
        public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
            this(frequency, '\0', left, right);
        }     

        // Behavior: 
        //   - This constructor constructs a new HuffmanNode with the provided
        //     frequency, character, left HuffmanNode, and right HuffmanNode 
        // Parameters:
        //   - int frequency: count of a character/its frequency
        //   - char character: character contained inside the HuffmanNode
        //   - HuffmanNode left: a reference to this HuffmanNode's left child
        //   - HuffmanNode right: a reference to this HuffmanNode's right child
        // Returns: 
        //   - N/A
        // Exceptions:
        //   - N/A        
        public HuffmanNode(int frequency, char character, HuffmanNode left, HuffmanNode right) {
            this.frequency = frequency; //default frequency = -1
            this.character = character; //default character = '\0'
            this.left = left; //default left HuffmanNode = null
            this.right = right; //default right HuffmanNode = null
        }

        // Behavior: 
        //   - This method returns an integer that determines the ordering of HuffmanNodes
        //     relative to one another. It uses the frequency of this HuffmanNode relative 
        //     to another HuffmanNode, with lower frequencies considered “less” than higher
        //     frequencies. If two frequencies are equal, the HuffmanNodes are considered 
        //     equal too. Note: HuffmanNodes are sorted by frequency in ascending order
        // Parameters:
        //   - HuffmanNode other: HuffmanNode that this HuffmanNode is being compared too
        // Returns: 
        //   - int: A negative integer if this HuffmanNode's frequency is less than the 
        //     other HuffmanNode's frequency. A positive integer if this HuffmanNode's frequency 
        //     is greater than the other HuffmanNode's frequency, and 0 if this HuffmanNode's
        //     frequency is equal to the other HuffmanNode's frequency. 
        // Exceptions:
        //   - N/A        
        public int compareTo(HuffmanNode other) {
            return this.frequency - other.frequency;
        }


    }
}
