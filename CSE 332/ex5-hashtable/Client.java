import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        chaininghashTableTest();
        smallWordsearchTest();
        largeWordsearchTest();
    }
    public static void chaininghashTableTest() {
        DeletelessDictionary<String, Integer> hashTable = new ChainingHashTable<String,Integer>();
        hashTable.insert("one", 1);
        System.out.println(hashTable.contains("three")); // should return false
        hashTable.insert("two", 2);
        hashTable.insert("three", 3);
        System.out.println(hashTable.contains("three")); // should return true
        hashTable.insert("three", 4); // should return old value 3
        hashTable.insert("three", 3); // should return old value 4
        hashTable.insert("four", 4);
        hashTable.insert("five", 5);
        hashTable.insert("six", 6);
        hashTable.insert("seven", 7);
        hashTable.insert("eight", 8);
        hashTable.insert("nine", 9);
        hashTable.insert("ten", 10);
        hashTable.insert("eleven", 11);
        hashTable.insert("twelve", 12);
        hashTable.insert("thrirteen", 13);
        hashTable.insert("fourteen", 14);
        hashTable.insert("fifteen", 15);
        hashTable.insert("sixteen", 16);
        hashTable.insert("seventeen", 17);
        hashTable.insert("eighteen", 18);
        hashTable.insert("nineteen", 19);
        hashTable.insert("twenty", 20);
        hashTable.insert("twenty-one", 21);
        hashTable.insert("twenty-two", 22);
        hashTable.insert("twenty-three", 23);
        hashTable.insert("twenty-four", 24);
        hashTable.insert("twenty-five", 25);
        hashTable.insert("twenty-six", 26);
        hashTable.insert("twenty-seven", 27);
        hashTable.insert("twenty-eight", 28);
        hashTable.insert("twenty-nine", 29);
        hashTable.insert("thirty", 30);
        hashTable.insert("thrity-one", 31);
        hashTable.insert("thirty-two", 32);
        hashTable.insert("thirty-three", 33);
        hashTable.insert("thirty-four", 34);
        hashTable.insert("thirty-five", 35);
        hashTable.insert("thirty-six", 36);
        hashTable.insert("thirty-seven", 37);
        System.out.println(hashTable.find("thirty"));
        System.out.println("keys: " + hashTable.getKeys());
        System.out.println("values: " + hashTable.getValues());
    }

    public static void smallWordsearchTest(){
        System.out.println("-------------------------------------------------");
        System.out.println();
        String dictionaryFile = "smallWords.txt";
        String puzzleFile = "smallpuzzle.txt";
        List<Word<String>> dictionary = readDictionary(dictionaryFile);
        String[][] stringgrid = makeGrid(puzzleFile);
        WordSearch<String> ws = new WordSearch<>(stringgrid, dictionary);
        System.out.println("keys: " + ws.seen.getKeys());
        System.out.println("values " + ws.seen.getValues());
        int count = ws.countWords();
        ws.printFoundWords();
        if(count == 7){
            System.out.println("small example is correct!");
        } else{
            System.out.println("small example is incorrect.");
        }
    }

    public static void largeWordsearchTest(){
        String dictionaryFile = "bigWords.txt";
        String puzzleFile = "bigpuzzle.txt";
        List<Word<String>> dictionary = readDictionary(dictionaryFile);
        String[][] stringgrid = makeGrid(puzzleFile);
        WordSearch<String> ws = new WordSearch<>(stringgrid, dictionary);
        int count = ws.countWords();
        ws.printFoundWords();
        if(count == 421){
            System.out.println("big example is correct!");
        } else{
            System.out.println("big example is incorrect.");
        }
    }

    public static String[][] makeGrid(String filename){
        List<String[]> lines = new ArrayList<>();
        try{
            File gridFile = new File(filename);
            Scanner gridReader = new Scanner(gridFile);
            while(gridReader.hasNextLine()){
                String line = gridReader.nextLine().toLowerCase();
                String[] splitLine = line.split(" ");
                lines.add(splitLine);
            }
            gridReader.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[][] grid = new String[lines.size()][lines.get(0).length];
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                grid[i][j] = lines.get(i)[j];
            }
        }
        return grid;
    }

    public static List<Word<String>> readDictionary(String filename){
        List<Word<String>> dictionary = new ArrayList<>();
        try{
            File dictFile = new File(filename);
            Scanner dictReader = new Scanner(dictFile);
            while(dictReader.hasNextLine()){
                String word = dictReader.nextLine().toLowerCase();
                String[] arr = new String[word.length()];
                for(int i = 0; i < arr.length; i++){
                    arr[i] = word.substring(i, i+1);
                }
                dictionary.add(new Word<String>(arr));
            }
            dictReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return dictionary;
    }
}
