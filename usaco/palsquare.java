/*
ID: pirripe1
LANG: JAVA
TASK: palsquare
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Scanner;


public class palsquare {

	
	/*
	 * 
	 * 
	 * A very stupid and tedious answer, please see the analysis
	 */

	private static final String DEBUG_STRING = "10";

	private static final boolean DEBUG = false;
	public static final String PRO = "palsquare";
	
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
		int base = cin.nextInt();
		for (int i = 1; i < 300; i ++) {
			byte[] s = Integer.toString(i*i, base).getBytes();
			boolean is = true;
			for (int j = 0; j < s.length/2; j++) {
				if (s[j] != s[s.length-1-j]) {
					is = false;
					break;
				}
			}
			if (is)
				fw.write(Integer.toString(i, base).toUpperCase() + " " + Integer.toString(i*i, base).toUpperCase() + "\n");
		}
	}
	
}
