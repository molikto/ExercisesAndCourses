import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class cf584e {

    static boolean DEBUG = false;
    static Scanner cin;
    static PrintStream out = System.out;


    public static void main(String args[]) throws Exception {
        // input
        if ((args.length != 0 && "test".equals(args[0]))) {
            DEBUG = true;
            String TEST = "4\n" +
                    "4 2 1 3\n" +
                    "3 2 4 1";
            cin = new Scanner(new ByteArrayInputStream(TEST.getBytes()));
        } else {
            cin =  new Scanner(System.in);
        }
        int n = cin.nextInt();
        int[] nums = new int[n];
        int[] diff = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = cin.nextInt() - 1;
        }
        for (int i = 0; i < n; i++) {
            diff[cin.nextInt() - 1] = i;
        }
        for (int i = 0; i < n; i++) {
            nums[i] = diff[nums[i]];
        }
        for (int i = 0; i < n; i++) {
            diff[i] = nums[i] - i;
        }
        int coins = 0;
        ArrayList<Pair> sol = new ArrayList<Pair>();
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = 0; i < n; i++) {
                if (nums[i] != i) {
                    for (int j = nums[i]; j > i; j--) {
                        if (nums[j] != j) {
                            if (nums[j] <= i) {
                                coins += j - i;
                                int t = nums[j];
                                nums[j] = nums[i];
                                nums[i] = t;
                                sol.add(new Pair(i, j));
                                changed = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (nums[j] == i) {
                    if (i != j) {
                        coins += j - i;
                        int t = nums[j];
                        nums[j] = nums[i];
                        nums[i] = t;
                        sol.add(new Pair(i, j));
                    }
                    break;
                }
            }
        }
        out.println(coins);
        out.println(sol.size());
        for (Pair p : sol) {
            out.println((p.a + 1) + " " + (p.b + 1));
        }
    }

    public static class Pair {
        int a;
        int b;
        public Pair(int aa, int bb) { a= aa; b = bb;}
    }
}
