/*
ID: pirripe1
LANG: JAVA
TASK: range
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.regex.Pattern;

public class range {

    private static final String DEBUG_STRING = "6\n101111\n001111\n111111\n001111\n101101\n111001";



    private static final boolean DEBUG = false;
    public static final String PRO = "range";

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

    static int[][] range;
    static int [][] board;

    private static void problem(Scanner cin, Writer fw) throws IOException {
        int N = cin.nextInt();
        cin.nextLine();
        range = new int[N][];
        board = new int[N][];
        for (int i = 0; i < N; i++) {
            String l = cin.nextLine();
            range[i] = new int[N];
            board[i] = new int[N];
            for (int j = 0; j < N; j++) {
                range[i][j] = l.charAt(j) - '0';
                board[i][j] = range[i][j];
            }
        }
        int[] res = new int[N + 1];
        for (int i = N - 2; i >= 0; i--) {
            for (int j = N - 2; j >= 0; j--) {
                if (range[i][j] == 1) {
                    int max = board[i + 1][j + 1] + 1;
                    int l = 1, k = 1;
                    for (; l < max; l++) {
                        if (range[i + l][j] == 0) break;
                    }
                    for (; k < max; k++) {
                        if (range[i][j + k] == 0) break;
                    }
                    board[i][j] = Math.min(l, k);
                    //System.out.println(i + " " + j + " " + max + " " + board[i][j]);
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                //System.out.print(board[i][j] + " ");
                for (int a = 2; a <= board[i][j]; a++) res[a] += 1;
            }
            //System.out.println();
        }
        for (int i = 2; i <= N; i++) {
            if (res[i] > 0) {
                fw.write(i + " " + res[i]  + "\n");
            }
        }
    }
}
