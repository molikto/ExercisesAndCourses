/*
ID: pirripe1
LANG: JAVA
TASK: fence9
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.regex.Pattern;

public class fence9 {

    private static final String DEBUG_STRING = "7 5 10";



    private static final boolean DEBUG = false;
    public static final String PRO = "fence9";

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
        int n = cin.nextInt(); // 7
        int m = cin.nextInt(); // 5
        int p = cin.nextInt();
        int len = 0;
        for (int i = 1; i < m; i ++) {
            // * * * * * * * * * *
            // * * * * * * * * * *
            // * * * * * * * * * *
            // * * * * * * * * * *
            // * * * * * * * * * *
            // * * * * * * * # * *

            // k / i> n / m
            int left = (int) Math.floor(1.0 * n / m * i) + 1;
            // (k - p) / i < (n - p) / m
            int right = (int) Math.ceil(1.0 * (n - p) / m * i + p) - 1;
            len += right - left + 1;
        }
        fw.write(len + "\n");
    }
}
