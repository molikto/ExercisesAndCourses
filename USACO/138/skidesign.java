/*
ID: pirripe1
LANG: JAVA
TASK: skidesign
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

public class skidesign {

	private static final String DEBUG_STRING = "5 20 4 1 24 21";
	private static final boolean DEBUG = false;
	public static final String PRO = "skidesign";
	
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

	static int n = 0;
	static int[] hs = null;
	static int[] to = null;
	static int M = 17;

	static int cost = Integer.MAX_VALUE;

	private static void problem(Scanner cin, Writer fw) throws IOException {
		n = cin.nextInt();
		hs = new int[n];
		long arg = 0;
		for (int i = 0; i < n; i ++) {
			hs[i] = cin.nextInt();
			arg += hs[i];
		}
		int av = (int) (arg / n);
		for (int i = av - 1 - M; i <= av + 1; i++) {
			int min = i;
			int max = i + M;
			int c = 0;
			for (int j = 0; j < n; j += 1) {
				int a = hs[j];
				if (a > max) {
					c += (a - max) * (a - max);
				} else if (a < min) {
					c += (min - a) * (min - a);
				}
			}
			if (c < cost) {
				cost = c;
			}
		}
		fw.write(cost + "\n");
	}
	
}
