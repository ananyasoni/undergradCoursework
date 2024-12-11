public class Sequential {
    public static int dotProduct(int[] a, int[] b){
        int answer = 0;
        for(int i = 0; i < a.length; i++){
            answer += a[i]*b[i];
        }
        return answer;
    }

    private static int dotProduct(int[][] a, int[][] b, int row, int col){
        int answer = 0;
        for(int i = 0; i < a.length; i++){
            answer += a[row][i]*b[i][col];
        }
        return answer;
    }

    public static int[][] multiply(int[][] a, int[][] b){
        int[][] product = new int[a.length][a.length];
        for(int row = 0; row < a.length; row++){
            for(int col = 0; col < a.length; col++){
                product[row][col] = dotProduct(a, b, row, col);
            }
        }
        return product;
    }

    public static String[] filterEmpty(String[] arr){
        int count = 0;
        for(int i = 0; i < arr.length; i++){
            if(!arr[i].isEmpty()){
                ++count;
            }
        }
        String[] filtered = new String[count];
        int index = 0;
        for(int i = 0; i < arr.length; i++){
            if(!arr[i].isEmpty()){
                filtered[index++] = arr[i];
            }
        }
        return filtered;
    }
}
