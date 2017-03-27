/*
ID: pirripe1
LANG: JAVA
TASK: fence
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.math.BigInteger;

public class fence {

    private static final String DEBUG_STRING = "15          2 9          9 7          9 5          5 10          10 1          1 7          7 10          7 3          3 4          4 5          5 3          3 6          6 5          4 10          5 2";



    private static final boolean DEBUG = false;
    public static final String PRO = "fence";

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
                cin = new Scanner(new FileReader(PRO+".in"));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            FileWriter fw = null;
            try {
                fw = new FileWriter(PRO+".out");
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


    static ArrayList<Integer>[] nodes = new ArrayList[501];

    static ArrayList<Integer> circuit = new ArrayList<Integer>();

    static void recurse(int i) {
        List<Integer> k = nodes[i];
        while (k.size() > 0) {
            int min = 502;
            int imin = -1;
            for (int ii = 0; ii < k.size(); ii++) {
                int l = k.get(ii);
                if (l < min) {
                    min = l;
                    imin = ii;
                }
            }
            int j = min;
            k.remove(imin);
            nodes[j].remove((Integer) i);
            recurse(j);
        }
        circuit.add(i);
    }

    private static void problem(Scanner cin, Writer fw) throws IOException {
        int F = cin.nextInt();
        int some = 502;
        for (int i = 0; i < 501; i++) nodes[i] = new ArrayList<Integer>();
        for (int i = 0; i < F; i++) {
            int a = cin.nextInt();
            int b = cin.nextInt();
            nodes[a].add(b);
            nodes[b].add(a);
            if (a < some) some = a;
        }
        int start = 502;
        for (int i = 0; i < 501; i++) {
            if (nodes[i].size() % 2 == 1) {
                if (i < start) start = i;
            }
        }
        if (start != 502) some = start;
        recurse(some);
        Collections.reverse(circuit);
        for (int i : circuit) fw.write(i + "\n");
    }
}
