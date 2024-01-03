// Ananya Soni
// 10/12/2023
// CSE 122
// This class prints the most common nucleotide from a String containing a sequence of 
// DNA.
public class DNA {
    public static void main(String[] args) {
        String dna = "ATGCGCACTATGGTAG";
        String mostCommon = mostCommonNucleotide(dna);
        System.out.println(dna + " => " + mostCommon);    
    }
    
// Behavior: 
//   - This method finds the most common nucleotide from a String containing a DNA sequence and 
//     returns the most common nucleotide as a String.   
// Parameters:
//   - String dnaSequence: String containing the sequence of DNA
// Returns:
//   - String (A, C, G, T): returns a String that contains the letter corresponding to the most 
//     common nucleotide in the sequence of DNA. (In the case of a tie this method
//     returns the nucleotide that comes first in the order of A, C, G, T. For example, 
//     if there is a tie between C and T, C is returned because it appears before T in 
//     the sequence A, C, G, T.)
// Exceptions:
//   - N/A
    public static String mostCommonNucleotide(String dnaSequence) {
        String nucleotides = "ACGT";
        int numNucleotide[] = countNucleotides(dnaSequence, nucleotides);
        int indexOfMostFrequentNucleotide = findIndexOfMostFrequent(numNucleotide);
        if(indexOfMostFrequentNucleotide == 0) {
            return "A";
        } 
        else if(indexOfMostFrequentNucleotide == 1) {
            return "C";
        }
        else if(indexOfMostFrequentNucleotide == 2) {
            return "G";
        } else {
            return "T";
        }
    }

// Behavior: 
//   - This method counts the number of each type of nucleotide from a String containing a
//     DNA sequence and returns the counts in an array called numNucleotide.   
// Parameters:
//   - String dnaSequence: String containing the sequence of DNA
//   - String nucleotides: String containing the nucleotides that make up a sequence of DNA 
//     (A, C, G, T) 
// Returns:
//   - int[] numNucleotide: an array containing the number of A, C, G, and T nucleotides in the
//     sequence of DNA
// Exceptions:
//   - N/A
    public static int[] countNucleotides(String dnaSequence, String nucleotides) {
        int[] numNucleotide = new int[4];
        int frequency = 0;
        for(int i = 0; i < numNucleotide.length; i++) {
            for(int j = 0; j < dnaSequence.length(); j++) {
                if(nucleotides.charAt(i) == dnaSequence.charAt(j)) {
                    frequency++;
                }
            }
            numNucleotide[i] = frequency;
            frequency = 0;
        }
        return numNucleotide;
    }

// Behavior: 
//   - This method takes in the array containing the counts of nucleotides and returns the
//     index in the array that corresponds to the most frequently occcuring nucleotide
//     in the sequence of DNA. 
// Parameters:
//   - int[] numNucleotide: an array containing the number of A, C, G, and T nucleotides in the
//     sequence of DNA
// Returns:
//   - int indexMax: the index in the array (numNucleotide) that corresponds to the most 
//     frequently occcuring nucleotide in the sequence of DNA
// Exceptions:
//   - N/A
    public static int findIndexOfMostFrequent(int[] numNucleotide) {
        int max = numNucleotide[0];
        int indexMax = 0;
        for(int i = 1; i < numNucleotide.length; i++) {
            if(numNucleotide[i] > max) {
                max = numNucleotide[i];
                indexMax = i;
            }
        }
        return indexMax;
    }
}