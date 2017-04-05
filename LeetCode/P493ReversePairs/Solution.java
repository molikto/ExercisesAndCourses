import java.util.*;

public class Solution {
    public static void main(String[] args) {
        System.out.println(new Solution().reversePairs(new int[] {1, 3, 2, 3, 1}));
    }


    public int reversePairs(int[] nu) {
        return mergeSort(nu, 0, nu.length);
    }

    private int mergeSort(int[] nu, int start, int end) {
        if (end - start <= 1) return 0;
        else {
            // 0, 2 => 1
            int mid = (start + end) / 2;
            int sum = 0;
            sum += mergeSort(nu, start, mid);
            sum += mergeSort(nu, mid, end);
            sum += merge(nu, start, mid, end);
            return sum;
        }
    }

    private int merge(int[] nu, int start, int mid, int end) {
        int sum = 0;
        int[] temp = new int[end - start];
        int i = start;
        int j = mid;
        while (i < mid) {
            while (j < end) {
                long k = nu[j];
                if (nu[i] > 2 * k) {
                    j++;
                } else {
                    break;
                }
            }
            sum += (j - mid);
            i++;
        }
        i = start;
        j = mid;
        int ii = 0;
        while (i < mid && j < end) {
            if (nu[i] < nu[j]) {
                temp[ii++] = nu[i++];
            } else {
                temp[ii++] = nu[j++];
            }
        }
        while (i < mid) temp[ii++] = nu[i++];
        while (j < end) temp[ii++] = nu[j++];
        System.arraycopy(temp, 0, nu, start, temp.length);
        return sum;
    }
}