import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Solution {


    public int lengthOfLIS(int[] nums) {
        // [10,9,2,5,3,7,101,18]
        // 10 -> 1
        // 9 -> 1
        // 2 -> 1
        // 3 -> 2
        if (nums.length == 0) return 0;
        // min number to length, and it is strongly increasing
        TreeMap<Integer, Integer> tree = new TreeMap<Integer, Integer>();
        tree.put(nums[0], 1);
        int max = 1;
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            Map.Entry<Integer, Integer> entry = tree.lowerEntry(num);
            if (entry != null) {
                int exp = entry.getValue() + 1;
                Integer now = tree.get(num);
                if (now == null) { // if now is not null, then it must be heq exp
                    Map.Entry<Integer, Integer> higher = tree.higherEntry(num);
                    if (higher != null && higher.getValue() == exp) {
                        tree.remove(higher.getKey());
                    }
                    if (max < exp) max = exp;
                    tree.put(num, exp);
                }
            } else { // if there is no key that is lower,  then it must be a new key
                Map.Entry<Integer, Integer> higher = tree.higherEntry(num);
                if (higher != null && higher.getValue() == 1) {
                    tree.remove(higher.getKey());
                }
                tree.put(num, 1);
            }
        }
        return max;
    }
}