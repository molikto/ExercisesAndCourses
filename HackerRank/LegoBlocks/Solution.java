import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Solution {


    private static final long L = 1000000007;
    private static long[] ws = new long[1001];

    private static long add(long a, long b) {
        return (a + b) % L;
    }

    private static long mult(long a, long b) {
        return (a * b) % L;
    }

    private static long minus(long a, long b) {
        return (a - b + L) % L;
    }

    private static long pow(long a, int b) {
        long c = 1;
        while (b > 0) {
            c = mult(a, c);
            b -= 1;
        }
        return c;
    }

    static {
        ws[1] = 1;
        ws[2] = 2;
        ws[3] = 4;
        ws[4] = 8;
        for (int w = 5; w <= 1000; w++) {
            long s = 0;
            s = add(s, ws[w - 4]);
            s = add(s, ws[w - 3]);
            s = add(s, ws[w - 2]);
            s = add(s, ws[w - 1]);
            ws[w] = s;
        }
    }

    private static long[] ways;
    private static long[] powWsWh;


    public static void main(String args[]) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        //for (int i = 0; i < 1000; i++) {
        //    System.out.println(ws[i]);
        //}
        Scanner input = new Scanner(System.in);

        int T = input.nextInt();
        for (int iT = 0; iT < T; iT++) {
            int h = input.nextInt();
            int w = input.nextInt();
            ways = new long[w + 1];
            powWsWh = new long[w + 1];
            for (int i = 0; i <= w; i++) {
              long base = pow(ws[i], h);
              powWsWh[i] = base;
              for (int j = 1; j < i; j++) {
                base = minus(base, mult(ways[j], powWsWh[i - j]));
              }
              ways[i] = base;
            }
            System.out.println(ways[w]);
        }
    }
}
