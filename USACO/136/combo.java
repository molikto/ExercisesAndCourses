/*
ID: pirripe1
LANG: JAVA
TASK: combo
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;

// I even do not know why this method works...
// please look at analysis!!
// it is the d algorithm....

public class combo {

	private static final String DEBUG_STRING = "4\n1 2 3\n2 3 4";
	private static final boolean DEBUG = false;
	public static final String PRO = "combo";
	
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
				FileReader fr = new FileReader(PRO+".in");
				cin = new Scanner(fr);
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


	static int dis(int a, int b, int n) {
		int m = a > b ? a : b;
		int s = a > b ? b : a;
		int d1 = m - s;
		int d2 = s + n - m;
		int d = d1 > d2 ? d2 : d1;
		int k = 0;
		int join = 5 - d;
		if (join < 0) join = 0;
		return join > n ? n : join;
	}

	// simply done by combination
	private static void problem(Scanner cin, Writer fw) throws IOException {
		int n = cin.nextInt();
		int f1 = cin.nextInt();
		int f2 = cin.nextInt();
		int f3 = cin.nextInt();
		int m1 = cin.nextInt();
		int m2 = cin.nextInt();
		int m3 = cin.nextInt();
		int d = n > 5 ? 5 : n;
		int max = d * d * d * 2;
		int dis1 = dis(f1, m1, n);
		int dis2 = dis(f2, m2, n);
		int dis3 = dis(f3, m3, n);
		int com = dis1 * dis2 * dis3;
		fw.write((max - com) + "\n");
	}
	
}
