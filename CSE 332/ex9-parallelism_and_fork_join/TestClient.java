import java.util.Random;

public class TestClient {

    public static void main(String[] args){
        testDotProduct();
        testMatrixMultiply();
        testFilterEmpty();
    }

    public static void testDotProduct(){
        Random rand = new Random(2024);
        int[] a = new int[100];
        int[] b = new int[100];
        for(int i = 0; i < a.length; i++){
            a[i] = rand.nextInt(a.length*10);
            b[i] = rand.nextInt(b.length*10);
        }
        int correct = Sequential.dotProduct(a, b);
        int given = DotProduct.dotProduct(a, b, 1); // make sure it works for any choice of cutoff!
        if(given == correct){
            System.out.println("Correct!");
        }
        else{
            System.out.println("Incorrect.");
        }
    }


    public static void testMatrixMultiply(){
        Random rand = new Random(320);
        int[][] a = new int[100][100];
        int[][] b = new int[100][100];
        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < a.length; j++){
                a[i][j] = rand.nextInt(a.length*10);
                b[i][j] = rand.nextInt(b.length*10);
            }
        }
        int[][] correct = Sequential.multiply(a, b);
        int[][] given = MatrixMultiply.multiply(a, b, 1); // make sure it works for any choice of cutoff!
        for(int i = 0; i < correct.length; i++){
            for(int j = 0; j < correct.length; j++){
                if(correct[i][j] != given[i][j]){
                    System.out.println("Incorrect.");
                    return;
                }
            }
        }
        System.out.println("Correct!");
    }

    public static void testFilterEmpty(){
        Random rand = new Random(434);
        String[] a = new String[100];
        for(int i = 0; i < a.length; i++){
            if(rand.nextBoolean()){
                a[i] = "";
            }
            else{
                a[i] = "" + rand.nextInt();
            }
        }
        String[] correct = Sequential.filterEmpty(a);
        String[] given = FilterEmpty.filterEmpty(a, 1); // make sure it works for any choice of cutoff!
        if(correct.length != given.length){
            System.out.println("incorrect");
            return;
        }
        for(int i = 0; i < correct.length; i++){
            if(!correct[i].equals(given[i])){
                System.out.println("incorrect");
                return;
            }
        }
        System.out.println("Correct!");
    }
}
