import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class Solution {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length == 0) {
            return median(nums2);
        } else if (nums2.length == 0) {
            return median(nums1);
        } else if (nums1.length == 1 && nums2.length == 1) {
            return median(new int[] {nums1[0], nums2[0]});
        }
        int lt = nums1.length + nums2.length;
        int ntake = lt % 2 == 0 ? lt / 2 - 1 : lt / 2;
        // ensures that ntake must > 0
        // ...
        // ......
        int step = 1;
        while (step < nums1.length) step *= 2;
        int shadow = step;
        step /= 2;
        int maxTake1 = Math.min(nums1.length, ntake);
        int minTake1 = Math.max(0, ntake - nums2.length);
        while (true) {
            int take1 = Math.max(minTake1, Math.min(shadow, maxTake1));
            boolean left;
            int take2 = ntake - take1;


            // take
            //     |
            if (take2 == 0) {
                // ....    ....
                //      ...
                if (nums2[0] >= nums1[take1 - 1]) {
                    return median(lt, nums1, take1, nums2, take2);
                } else {
                    left = true;
                }
            } else if (take1 == 0) {

                //          ....
                //  ...   ....
                return median(lt, nums1, take1, nums2, take2);
            } else {
                // .....  ...
                // ... ....
                boolean t1 = nums1[take1 - 1] <=
                        (take2 == nums2.length ? Integer.MAX_VALUE : nums2[take2]);
                if (!t1) {
                    left = true;
                } else {
                    boolean t2 = nums2[take2 - 1] <=
                            (take1 == nums1.length ? Integer.MAX_VALUE : nums1[take1]);
                    if (!t2) {
                        left = false;
                    } else {
                        return median(lt, nums1, take1, nums2, take2);
                    }
                }
            }
            if (left) {
                shadow -= step;
            } else {
                shadow += step;
            }
            step = Math.max(1, step / 2);
        }

    }


    private double median(int lt, int[] nums1, int take1, int[] nums2, int take2) {
        int[] nums = new int[4];
        int m1 = Math.min(take1 + 2, nums1.length);
        int i1 = take1;
        int m2 = Math.min(take2 + 2, nums2.length);
        int i2 = take2;
        int i = 0;
        while (i1 < m1 && i2 < m2) {
            if (nums1[i1] < nums2[i2]) nums[i++] = nums1[i1++];
            else nums[i++] = nums2[i2++];
        }
        if (i1 == m1) while (i2 < m2) nums[i++] = nums2[i2++];
        if (i2 == m2) while (i1 < m1) nums[i++] = nums1[i1++];
        if (lt % 2 == 1) {
            return nums[0];
        } else {
            return median(new int[] {nums[0], nums[1]});
        }
    }


    private double median(int[] n) {
        if (n.length % 2 == 0) {
            return 1.0 * (n[n.length / 2 - 1] + n[n.length / 2]) / 2;
        } else {
            return n[n.length / 2];
        }
    }

}