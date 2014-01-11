/*
ID: pirripe1
LANG: JAVA
TASK: milk
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class milk {

	
	/*
	 * 
	 * 
	 * A very stupid and tedious answer, please see the analysis
	 */

	private static final String DEBUG_STRING = "4 50 18 3 4 6 8 14 15 16 17 21 25 26 27 30 31 40 41 42 43";

	private static final boolean DEBUG = false;
	public static final String PRO = "milk";
	
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

	public static class Seller implements Comparable<Seller> {
		public Seller(int p, int a) {
			price = p;
			amount = a;
		}
		int price;
		int amount;
		
		@Override
		public int compareTo(Seller o) {
			return price - o.price;
		}
	}
	private static void problem(Scanner cin, Writer fw) throws IOException {
		// lost
	}
	
}
