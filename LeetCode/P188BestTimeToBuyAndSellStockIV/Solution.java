import java.util.Arrays;

public class Solution {
    //[1,2,4,2,5,7,2,4,9,0]
    // 3,-2,5,-5,7
    // 3, 6, 8
    //
    // 753

    public void buyIn(int[] dp, int[] prices) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < prices.length; i++) {
            max = Math.max(dp[i] - prices[i], max);
            dp[i] = max;
        }
    }

    public int maxProfit(int k, int[] prices) {
        if (prices.length <= 1) return 0;
        if (k > prices.length / 2) trivial(prices);
        // + - + - + - + -
        int[][] dp = new int[2][]; // dp[i][j] means we finish at most i trans in j, but also brought at j
        for (int i = 0; i < dp.length; i++) {
            dp[i] = new int[prices.length + 1];
        }
        for (int i = 1; i <= k; i++) { // k
            buyIn(dp[(i - 1) % 2], prices);
            dp[i % 2][0] = 0;
            dp[i % 2][1] = 0;
            for (int j = 2; j < dp[0].length; j++) { // len(p)
                dp[i % 2][j] = Math.max(
                        dp[i % 2][j - 1], // don't do things at day j
                        dp[(i - 1) % 2][j - 2] + prices[j - 1] // sell at day j
                );
            }
        }
        return dp[k % 2][prices.length];
    }

    public int trivial(int[] prices) {
        // + - + - + - + -
        int j = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            int diff = prices[i + 1] - prices[i];
            if (j == 0 && diff <= 0) {
            } else {
                if (j == 0 || prices[j - 1] * diff < 0) {
                    prices[j++] = diff;
                } else {
                    prices[j - 1] += diff;
                }
            }
        }
        Arrays.sort(prices, 0, j);
        int a = 0;
        for (int i = j - 1; i >= 0 && prices[i] > 0; i--)  {
            a += prices[i];
        }
        return a;
    }

    private void print(int[] ints) {
        System.out.println(Arrays.toString(ints));
    }
}