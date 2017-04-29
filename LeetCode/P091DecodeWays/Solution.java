public class Solution {
    public int numDecodings(String s) {
        if (s.length() == 0) {
            return 0;
        } else {
            if (s.charAt(0) == '0') {
                return 0;
            } else {
                int[] ds = new int[s.length()];
                ds[0] = 1;
                for (int i = 1; i < s.length(); i++) {
                    char p = s.charAt(i - 1);
                    char c = s.charAt(i);
                    if (c == '0')  {
                        if ((p == '1' || p == '2')) {
                            ds[i] = i == 1 ? 1 : ds[i - 2];
                        } else {
                            return 0;
                        }
                    } else {
                        ds[i] = ds[i - 1];
                        if ((c <= '6' && p == '2') || p == '1') {
                            ds[i] += i == 1 ? 1 : ds[i - 2];
                        }
                    }
                }
                return ds[ds.length - 1];
            }
        }
    }
}