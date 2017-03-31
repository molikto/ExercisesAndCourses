/*
ID: pirripe1
LANG: JAVA
TASK: game1
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.regex.Pattern;

public class game1 {

    private static final String DEBUG_STRING = "6 4 7 2 9 5 2";



    private static final boolean DEBUG = false;
    public static final String PRO = "game1";

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
        int n = cin.nextInt();
        int[] board = new int[n];
        int[][] opt1 = new int[n][];
        int[][] opt2 = new int[n][];
        for (int i = 0; i < n; i++)  {
            board[i] = cin.nextInt();
            opt1[i] = new int[n + 1];
            opt2[i] = new int[n + 1];
        }
        for (int i = 0; i < n; i++) {
            opt1[i][i + 1] = board[i];
            opt2[i][i + 1] = 0;
        }
        for (int i = 0; i < n - 1; i++) {
            opt1[i][i + 2] = Math.max(board[i], board[i + 1]);
            opt2[i][i + 2] = Math.min(board[i], board[i + 1]);
        }
        for (int l = 3; l <= n; l++) {
            for (int s = 0; s + l <= n; s++) {
                opt1[s][s + l] = Math.max(board[s] + opt2[s + 1][s + l], opt2[s][s + l - 1] + board[s + l - 1]);
                opt2[s][s + l] = Math.min(opt1[s][s + l - 1], opt1[s + 1][s + l]);
            }
        }
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n + 1; j++) {
        //         System.out.print(opt1[i][j] + " ");
        //     }
        //     System.out.println();
        // }
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n + 1; j++) {
        //         System.out.print(opt2[i][j] + " ");
        //     }
        //     System.out.println();
        // }
        fw.write(opt1[0][n] + " " + opt2[0][n] + "\n");
    }
}
