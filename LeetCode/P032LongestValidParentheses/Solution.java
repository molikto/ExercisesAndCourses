import java.util.Arrays;

public class Solution {
    public int longestValidParentheses(String s) {
        if (s.length() <= 1) return 0;
        int[] dp = new int[s.length() + 1];
        dp[0] = 0;
        dp[1] = 0;
        for (int l = 2; l < dp.length; l++) {
            int res = 0;
            //  (l-1)
            if (s.charAt(l - 2) == '(' && s.charAt(l - 1) == ')') res = dp[l - 2] + 2;
            else if (s.charAt(l - 1) == ')' && l - 1 - dp[l - 1] > 0 && s.charAt(l - 2 - dp[l - 1]) == '(') {
                res = dp[l - 1] + 2;
                res = res + dp[l - res];
            }
            dp[l] = res;
        }
        return max(dp);
    }

    private int max(int[] dp) {
        int m = 0;
        for (int p : dp) m = Math.max(p, m);
        return m;
    }
}