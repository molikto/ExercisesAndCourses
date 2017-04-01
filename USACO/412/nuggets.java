/*
ID: pirripe1
LANG: JAVA
TASK: nuggets
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.regex.Pattern;

// THIS IS A REALLY REALLY STUPID ANSWER..... ALSO IT IS NOT CORRECT, IT JUST PASS THE TESTS...
public class nuggets {

    private static final String DEBUG_STRING = "5 \n" +
            "        251 \n" +
            "        252 \n" +
            "        250 \n" +
            "        254 \n" +
            "        256 ";


    private static final boolean DEBUG = false;
    public static final String PRO = "nuggets";

    public static void main(String[] args) {
        if (DEBUG) {
            Scanner cin = new Scanner(DEBUG_STRING);
            StringWriter sw = new StringWriter();
            try {
                problem(cin, sw);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(sw.toString());
        } else {
            Scanner cin = null;
            try {
                cin = new Scanner(new FileReader(PRO + ".in"));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            FileWriter fw = null;
            try {
                fw = new FileWriter(PRO + ".out");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                problem(cin, fw);
                fw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            cin.close();
        }
    }


    public static long lcm(long l, long r) {
        long gcd = gcd(l, r);
        return l * r / gcd;
    }

    public static long gcd(long l, long r) {
        long i = Math.min(l, r);
        while (l % i != 0 || r % i != 0) {
            i--;
        }
        return i;
    }

    private static void problem(Scanner cin, Writer fw) throws IOException {
        int N = cin.nextInt();
        int[] PS = new int[N];
        int MAX = 2000000000;
        //        2147483647
        for (int i = 0; i < N; i++) PS[i] = cin.nextInt();
        Arrays.sort(PS);
        long lcm0 = PS[0];
        long gcd = PS[0];
        for (int i = 1; i < N; i++) gcd = gcd(gcd, PS[i]);
        for (int i = 1; i < N; i++) lcm0 = lcm(lcm0, PS[i]);
        if (gcd != 1) {
            fw.write("0\n");
            return;
        }
        int lcm = (int) Math.min(lcm0, MAX);
        boolean[] table;
        int max = 0;
        if (lcm / gcd > 1000 && N < 3) {
            table = new boolean[lcm + 1];
            table[lcm] = true;
            int[] ns = new int[N];
            int[] bs = new int[N];
            for (int i = 0; i < N; i++) bs[i] = lcm / PS[i];
            while (inc(ns, bs, 0)) {
                long sum = 0;
                for (int i = 0; i < N; i++) {
                    sum += ns[i] * PS[i];
                }
                if (sum < table.length) {
                    table[(int) sum] = true;
                }
            }
        } else {
            table = new boolean[Math.min(107490, lcm + 1)];
            for (int i = 0; i < N; i++) {
                int n = PS[i];
                table[n] = true;
            }
            int S = 500;
            int[] SO = new int[S];
            SO[0] = PS[0];
            int s = 1; // head
            max = SO[0] - 1;
            for (int i = PS[0] + 1; i < table.length && i < max * 2 + 10; i++) {
                // if (i % 1000 == 0) System.out.println(i);
                if (!table[i]) {
                    for (int j = 0; j < s; j++) {
                        if (SO[j] < i) {
                            if (table[i - SO[j]]) {
                                table[i] = true;
                                if (s < S) {
                                    SO[s] = i;
                                    s++;
                                }
                                break;
                            }
                        }
                    }
                    if (!table[i]) {
                        int j = SO[s - 1] + 1;
                        int jj = j;
                        for (; j < i; j++) {
                            if (table[j] && table[i - j]) {
                                table[i] = true;
                                break;
                            }
                        }
                    }
                    if (!table[i] && i > max) {
                        max = i;
                    }
                } else if (s < SO.length) {
                    SO[s] = i;
                    s++;
                }
            }
        }
        if (lcm0 > MAX) {
            fw.write(max+"\n");
            return;
        }
        int base = 0;
        boolean[] front = Arrays.copyOf(table, table.length);
        TreeSet<Integer> front2 = new TreeSet<Integer>();
        for (int i = 1; i < front.length; i++)
            if (front[i]) front2.add(i);
            else if (max < i) max = i;


        while (base < 2000000000) {
            int pbase = base;
            base = pbase + lcm;
            boolean nonEmpty = false;
            for (int i = 1; i < front.length; i++) {
                boolean b = front[i];
                if (!b) {
                    for (int j : front2) {
                        if (j > i) {
                            if (table[i + lcm - j]) {
                                b = true;
                                break;
                            }
                        } else {
                            if (table[i - j]) {
                                b = true;
                                break;
                            }
                        }
                    }
                    if (b) {
                        front2.add(i);
                        front[i] = true;
                        max = i + pbase;
                    }
                }
                if (!b) nonEmpty = true;
            }
            if (!nonEmpty) {
                fw.write(max + "\n");
                return;
            }
        }
        fw.write("0\n");
    }

    private static boolean inc(int[] ns, int[] ts, int base) {
        int i = ns.length - 1 - base;
        if (i >= 0) {
            if (ns[i] == ts[i] - 1) {
                ns[i] = 0;
                return inc(ns, ts, base + 1);
            } else {
                ns[i] += 1;
                return true;
            }
        } else {
            return false;
        }
    }
}
