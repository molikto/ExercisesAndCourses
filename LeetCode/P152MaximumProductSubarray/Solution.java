public class Solution {

    public int maxProduct(int[] nums) {
        if (nums.length == 0) return 0;
        else if (nums.length == 1) return nums[0];
        int s = -1;
        int max = 0; // if there is more than 2 number, you can definitely get 0
        int prod = 1;
        int lastPos = 0;
        for (int i = 0; i <= nums.length; i++) {
            int num = i == nums.length ? 0 : nums[i];
            if (s == -1 && num != 0) {
                prod *= num;
                if (prod > 0) lastPos = prod;
                s = i;
            } else if (s != -1 && num == 0) {
                int can;
                if (prod > 0) {
                    can = prod;
                } else {
                    can = lastPos;
                    for (int ii = s; ii < i - 1; ii++) {
                        prod /= nums[ii];
                        if (prod > 0) {
                            if (prod > lastPos) can = prod;
                            break;
                        }
                    }
                }
                s = -1;
                prod = 1;
                if (can > max) max = can;
            } else if (s != -1) {
                prod *= num;
                if (prod > 0) lastPos = prod;
            }
        }
        return max;
    }
}