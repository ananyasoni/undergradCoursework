import java.lang.reflect.Array;
import java.util.Arrays;

public class Word<T> {
    public final T[] slice;

    public Word(T[] arr){
        this.slice = (T[]) new Object[arr.length];
        for(int i = 0; i < arr.length; i++){
            slice[i] = arr[i];
        }
    }

    // produce a "slice" of the grid.
    // The first value of the slice will be grid[xstart][ystart].
    // The remainder of the slice will then copy the next n elements
    // in the direction indicated.
    // direction options: "L", "R", "U", "D", "UL", "DL", "UR", "DR"
    // for Left, Right, Up, Down, Up and Left, Down and Left, Up and Right, Down and Right.
    public Word(T[][] grid, int xstart, int ystart, String direction, int n){
        this.slice = (T[]) new Object[n];
        if(direction.equals("L")){
            for(int i = 0; i < n; i++){
                slice[i] = grid[ystart][xstart-i];
            }
        }
        else if(direction.equals("R")){
            for(int i = 0; i < n; i++){
                slice[i] = grid[ystart][xstart+i];
            }
        }
        else if(direction.equals("U")){
            for(int i = 0; i < n; i++){
                slice[i] = grid[ystart-i][xstart];
            }
        }
        else if(direction.equals("D")){
            for(int i = 0; i < n; i++){
                slice[i] = grid[ystart+i][xstart];
            }
        }
        else if(direction.equals("DR")){
            for(int i = 0; i < n; i++){
                slice[i] = grid[ystart+i][xstart+i];
            }
        }
        else if(direction.equals("UR")){
            for(int i = 0; i < n; i++){
                slice[i] = grid[ystart-i][xstart+i];
            }
        }
        else if(direction.equals("DL")){
            for(int i = 0; i < n; i++){
                slice[i] = grid[ystart+i][xstart-i];
            }
        }
        else if(direction.equals("UL")){
            for(int i = 0; i < n; i++){
                slice[i] = grid[ystart-i][xstart-i];
            }
        }
        else{
            throw new IllegalArgumentException("could not recognize direction");
        }
    }

    public boolean equals(Object other){
        if(this == other){
            return true;
        }
        if(other == null){
            return false;
        }
        if(getClass() != other.getClass()){
            return false;
        }
        Word<T> otherItem = (Word<T>) other;
        if(this.slice.length != otherItem.slice.length){
            return false;
        }
        for(int i = 0; i < this.slice.length; i++){
            if(!this.slice[i].equals(otherItem.slice[i])){
                return false;
            }
        }
        return true;
    }

    // hashCode method for the Word class
    // which returns a hashCode for this Word
    @Override
    public int hashCode(){
        return Arrays.hashCode(slice);
    }

    public String toString(){
        if(slice.length == 0){
            return "[]";
        }
        String s = "[";
        s += slice[0];
        for(int i = 1; i < slice.length; i++){
            s += "|" + slice[i];
        }
        return s + "]";
    }
}
