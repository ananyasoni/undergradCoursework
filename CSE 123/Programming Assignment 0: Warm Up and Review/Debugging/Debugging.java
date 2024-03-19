// Ananya Soni 
// 01/10/2024
// CSE 123
// P0: Warmup/Review
// TA: Lainey
import java.util.*;

public class Debugging {
    public static void main(String[] args) {
        Map<String, List<Integer>> testMap = new TreeMap<>();
        int[] arr1 = new int[]{42, 17, 42, 42};
        int[] arr2 = new int[]{10, 12, 14};
        int[] arr3 = new int[]{100, 99, 98, -97};
        List<Integer> c121 = arrToList(arr1);
        List<Integer> c122 = arrToList(arr2);
        List<Integer> c123 = arrToList(arr3);
        testMap.put("cse121", c121);
        testMap.put("cse122", c122);
        testMap.put("cse123", c123);
        
        Map<String, List<Integer>> deepCopyMap = deepCopy(testMap);

        System.out.print("{");
        for (String key : deepCopyMap.keySet()) {
            System.out.print(key + "=" + deepCopyMap.get(key) + " ");
        }
        System.out.println("}");
    }

    public static List<Integer> arrToList(int[] arr) {
        List<Integer> l = new ArrayList<>();
        for (int num : arr) {
            l.add(num);
        }
        return l;
    }
    
    //   Produces and returns a "deep copy" of the parameter map, which has the same
    //   structure and values as the parameter, but with all internal data structures
    //   and values copied. After calling this method, modifying the parameter or 
    //   return value should NOT affect the other.
    //
    // Parameters:
    //   inputMap - the map to duplicate
    //
    // Returns:
    //   A deep copy of the parameter map.
    public static Map<String, List<Integer>> deepCopy(Map<String, List<Integer>> inputMap) {
        Map<String, List<Integer>> deepCopy = new TreeMap<>();
        for (String key : inputMap.keySet()) {
            
            List<Integer> inputList = inputMap.get(key);
            deepCopy.put(key, new ArrayList<Integer>());
            for(int nextNum : inputList) {
                deepCopy.get(key).add(nextNum);
            }
        }
        return deepCopy;
    }
}
