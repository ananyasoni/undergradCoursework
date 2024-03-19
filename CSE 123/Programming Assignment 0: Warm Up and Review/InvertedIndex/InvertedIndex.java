// Ananya Soni 
// 01/10/2024
// CSE 123
// P0: Warmup/Review
// TA: Lainey
// This class allows a user to create a list of documents and create an 
// inverted index for that list of documents 
import java.util.*;

public class InvertedIndex {
    public static void main(String[] args) {
        List<String> docs = new ArrayList<>();
        docs.add("Raiders of the Lost Ark");
        docs.add("The Temple of Doom");
        docs.add("The Last Crusade");
        Map<String, Set<String>> result = createIndex(docs);
        System.out.println(docs);
        System.out.println();
        System.out.println(result);
    }

    // Behavior: 
    //   - This method creates an inverted index for a given list of documents where
    //     each document is represented as a string.
    // Parameters:
    //   - List<String> docs: list of documents
    // Returns:
    //   - Map<String, Set<String>> invertedIndex: a map of individual words 
    //     (in all lower case and sorted order) in the list of documents mapped 
    //     to the sets of documents in which those words appear case-insensitively 
    // Exceptions:
    //   - N/A 
    public static Map<String, Set<String>> createIndex(List<String> docs) {
        Map<String, Set<String>> invertedIndex = new TreeMap<String, Set<String>>();
        for(String doc : docs) {
            String[] words = doc.split(" ");
            for(String nextWord : words) {
                if(!invertedIndex.containsKey(nextWord.toLowerCase())) {
                    invertedIndex.put(nextWord.toLowerCase(), new HashSet<String>());
                }
                invertedIndex.get(nextWord.toLowerCase()).add(doc);               
            }         
        }
        return invertedIndex;
    }
}