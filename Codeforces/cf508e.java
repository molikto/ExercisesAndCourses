import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class cf508e {

    static boolean DEBUG = false;
    static Scanner cin;
    static PrintStream out = System.out;


    public static void main(String args[]) throws Exception {
        // input
        if ((args.length != 0 && "test".equals(args[0]))) {
            DEBUG = true;
            String TEST = "3\n" +
                    "2 3\n" +
                    "1 4\n" +
                    "1 4";
            cin = new Scanner(new ByteArrayInputStream(TEST.getBytes()));
        } else {
            cin =  new Scanner(System.in);
        }
        int n = cin.nextInt();
        int[] l = new int[n];
        int[] r = new int[n];
        for (int i = 0; i < n; i++) {
            l[i] = cin.nextInt();
            r[i] = cin.nextInt();
        }
        LinkedList<Bracket> pairs = new LinkedList<Bracket>();
        for (int i = n - 1; i >= 0; i--) {
            if (pairs.isEmpty()) {
                if (l[i] <= 1 && r[i] >= 1) {
                    pairs.add(new Bracket());
                } else {
                    out.println("IMPOSSIBLE");
                    return;
                }
            } else {
                int ll = l[i];
                int rr = r[i];
                Bracket b = new Bracket();
                while (b.size - 1 < ll) {
                    if (pairs.isEmpty()) {
                        out.println("IMPOSSIBLE");
                        return;
                    } else {
                        Bracket first = pairs.removeFirst();
                        b.inside.add(first);
                        b.size += first.size;
                    }
                }
                if (b.size - 1 > rr) {
                    out.println("IMPOSSIBLE");
                    return;
                }
                pairs.addFirst(b);
            }
        }
        for (Bracket b : pairs) {
            b.print();
        }
    }


    public static class Bracket {
        public LinkedList<Bracket> inside = new LinkedList<Bracket>();
        int size = 2;

        public void print() {
            out.print("(");
            for (Bracket b : inside) {
                b.print();
            }
            out.print(")");
        }
    }
}
