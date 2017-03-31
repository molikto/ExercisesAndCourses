/*
ID: pirripe1
LANG: JAVA
TASK: heritage
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.regex.Pattern;

public class heritage {

    private static final String DEBUG_STRING = "ABEDFCHG\n" + "CBADEFGH";



    private static final boolean DEBUG = false;
    public static final String PRO = "heritage";

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


    public static String postOrder(String in, String pre) {
        if (in.length() <= 1) {
            return in;
        } else {
            char head = pre.charAt(0);
            int index = in.indexOf(head);
            return postOrder(in.substring(0, index), pre.substring(1, index + 1)) + postOrder(in.substring(index + 1), pre.substring(index + 1)) + head;
        }
    }

    private static void problem(Scanner cin, Writer fw) throws IOException {
        String in = cin.nextLine();
        String pre = cin.nextLine();
        fw.write(postOrder(in, pre) + "\n");
    }
}
