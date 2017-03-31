/*
ID: pirripe1
LANG: JAVA
TASK: rockers
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.regex.Pattern;

public class rockers {

    private static final String DEBUG_STRING = "4 5 2\n" + "4 3 4 2";



    private static final boolean DEBUG = false;
    public static final String PRO = "rockers";

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



    private static void problem(Scanner cin, Writer fw) throws IOException {
        int N = cin.nextInt(); // songs
        int T = cin.nextInt(); // min
        int M = cin.nextInt(); // disks
        int[] songs = new int[N];
        for (int i = 0; i < N; i++) songs[i] = cin.nextInt();

        int K = (int) Math.pow(2, N);
        int max = 0;
        for (int i = 0; i < K; i++) {
            int k = i;
            int m = 0;
            int t = T;
            int mm = 0;
            int j = 0;
            for (; j < N; j++) {
                int head = k % 2;
                if (head == 1) {
                    int s = songs[j];
                    if (s <= t) {
                        t -= s;
                        mm += 1;
                    } else if (m == M - 1) {
                        break;
                    } else if (s <= T) {
                        m += 1;
                        t = T - s;
                        mm += 1;
                    } else {
                        break;
                    }
                }
                k = k / 2;
            }
            if (j == N && mm > max) max = mm;
        }
        fw.write(max + "\n");
    }
}
