import java.util.Arrays;
import java.util.Map;

public class Solution {
    public static class Entry implements Comparable<Entry> {
        int index;
        int num;

        @Override
        public int compareTo(Entry o) {
            if (num > o.num) return 1;
            else if (num < o.num) return -1;
            else return 0;
        }
    }
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        Entry[] entries = new Entry[nums.length];
        for (int i = 0; i < entries.length; i++) {
            entries[i] = new Entry();
            entries[i].index = i;
            entries[i].num = nums[i];
        }
        Arrays.sort(entries);
        for (int i = 0; i < entries.length; i++) {
            int j = i + 1;
            long ii = entries[i].num;
            while (j < entries.length && entries[j].num - ii <= t) {
                if (Math.abs(entries[i].index - entries[j].index) <= k)  return true;
                j++;
            }
        }
        return false;
    }
}