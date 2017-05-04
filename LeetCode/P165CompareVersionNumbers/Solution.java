public class Solution {
    public int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        int i = 0;
        for (; i < Math.min(v1.length, v2.length); i++) {
            int c = compare(Integer.parseInt(v1[i]), Integer.parseInt(v2[i]));
            if (c != 0) return c;
        }
        if (i == v1.length) {
            for (int j = i; j < v2.length; j++) {
                if (Integer.parseInt(v2[j]) != 0) {
                    return -1;
                }
            }
        } else if (i == v2.length) {
            for (int j = i; j < v1.length; j++) {
                if (Integer.parseInt(v1[j]) != 0) {
                    return 1;
                }
            }
        }
        return 0;
    }
    public static int compare(int x, int y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }
}