/*
ID: pirripe1
LANG: JAVA
TASK: wormhole
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

public class wormhole {

	private static final String DEBUG_STRING = "10          987878530 332490544          545074228 332490544          571194544 278963943          32922985 229703843          571194544 851333603          90862786 28227282          219975775 267376202          219975775 332490544          90862786 62367085          872930617 951881113";
	private static final boolean DEBUG = false;
	public static final String PRO = "wormhole";
	
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


	static int count = 0;
	static int n = 0;
	static int[] xs = null;
	static int[] ys = null;
	static int[] p = null;

	static void permutation(String prefix, String str) {
		int ll = prefix.length();
		for (int i = 0; i < ll; i++) {
			int k = prefix.charAt(i) - 'a';
			if (k == i) return;
			if (k < ll && prefix.charAt(k) - 'a' != i) return;
		}
    	int n = str.length();
    	if (n == 0) {
    		test(prefix);
    	} else {
        	for (int i = 0; i < n; i++)
            	permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
    	}
	}

	static void test(String s) {
		int c = count;
		for (int i = 0; i < n; i++) {
			p[i] = s.charAt(i) - 'a';
		}
		for (int i = 0; i < n; i++) {
			if (c == count) walk(i, 0);
		}
	}

	static void walk(int w, int step) {
		int t = p[w];
		int ox = xs[t];
		int oy = ys[t];
		int k = -1;
		for (int i = 0; i < n; i++) {
			if (ys[i] == oy && xs[i] > ox && (k == -1 || xs[k] > xs[i])) {
				k = i;
			}
		}
		if (k != -1) {
			if (step > n) {
				count += 1;
			} else {
				walk(k, step + 1);
			}
		}
	}

	// get all permutations and simulate it
	private static void problem(Scanner cin, Writer fw) throws IOException {
		n = cin.nextInt();
		xs = new int[n];
		ys = new int[n];
		p = new int[n];
		for (int i = 0; i < n; i ++) {
			xs[i] = cin.nextInt();
			ys[i] = cin.nextInt();
		}
		permutation("", "abcdefghijklmnopqrst".substring(0, n));

		fw.write(count + "\n");
	}
	
}
