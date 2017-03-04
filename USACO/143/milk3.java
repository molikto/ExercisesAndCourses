/*
ID: pirripe1
LANG: JAVA
TASK: milk3
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;

public class milk3 {

	private static final String DEBUG_STRING = "8 9 10";

	private static final boolean DEBUG = false;
	public static final String PRO = "milk3";
	
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

	public static final class Grower {
		public final int[] buckets;
		public final Comparator<int[]> COM = new Comparator<int[]>() {
			@Override
			public int compare(int[] a, int[] b) {
				for (int i = 0; i < buckets.length; i++) {
					int diff = a[i] - b[i];
					if (diff != 0) {
						return diff;
					}
				}
				return 0;
			}};
		public TreeSet<int[]> results = new TreeSet<int[]>(COM);
		public TreeSet<int[]> lastResults = new TreeSet<int[]>(COM);
		public Grower(int[] bs, int[] initAmo) {
			buckets = bs;
			results.add(initAmo);
			lastResults.add(initAmo);
		}
		public void grow() {
			TreeSet<int[]> newResults = new TreeSet<int[]>(COM);
			for (int[] amos : lastResults) {
				for (int i = 0; i < buckets.length; i++) {
					for (int j = 0; j < buckets.length; j++) {
						if (i == j)
							continue;
						int pamo = Math.min(amos[i], buckets[j] - amos[j]);
						if (pamo == 0)
							continue;
						int[] namos = Arrays.copyOf(amos, amos.length);
						namos[i] -= pamo;
						namos[j] += pamo;
						if (!results.contains(namos)) {
							newResults.add(namos);
							results.add(namos);
						}
						
					}
				}
			}
			lastResults = newResults;
		}
	}
	private static void problem(Scanner cin, Writer fw) throws IOException {
		int[] buckets = new int[3];
		int[] initAmo = new int[3];
		for (int i = 0; i < 3; i++) {
			initAmo[i] = buckets[i] = cin.nextInt();
		}
		initAmo[0] = 0;
		initAmo[1] = 0;
		Grower g = new Grower(buckets, initAmo);
		ArrayList<Integer> results = new ArrayList<Integer>();
		while (!g.lastResults.isEmpty()) {
			for (int[] amos : g.lastResults) {
				if (amos[0] != 0)
					break;
				results.add(amos[2]);
			}
			g.grow();
		}
		Collections.sort(results);
		for (int i = 0; i < results.size(); i++) {
			fw.write(results.get(i) + (i == results.size() - 1 ? "\n" : " "));
		}
	}
}
